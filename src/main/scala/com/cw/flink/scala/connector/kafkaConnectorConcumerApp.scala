package com.cw.flink.scala.connector

import java.text.SimpleDateFormat
import java.util.{Date, Properties}

import org.apache.flink.api.common.functions.RuntimeContext
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.api.java.tuple.Tuple
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.streaming.connectors.elasticsearch.{ElasticsearchSinkFunction, RequestIndexer}
import org.apache.flink.streaming.connectors.elasticsearch6.ElasticsearchSink
import org.apache.flink.util.Collector
import org.apache.http.HttpHost
import org.elasticsearch.action.index.IndexRequest
import org.elasticsearch.client.Requests
import org.slf4j.LoggerFactory
import com.cw.flink.scala.connector.process.processCommon

import scala.collection.mutable.ArrayBuffer
object kafkaConnectorConcumerApp {
  //在生产上记录日志
  val logger = LoggerFactory.getLogger("kafkaConnectorConcumerApp")

  def main(args: Array[String]): Unit = {
    //拿到一个stream的执行环境
    val env  = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //接手kafka数据的信息
    var topic = "test"
    var properties = new Properties()
    properties.setProperty("bootstrap.servers", "193.112.189.10:9092")
    properties.setProperty("group.id", "test")
    //加入Source
    val data = env.addSource(new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties))

    //processData(data)
    processCommon.process(data)
    //print("a")
    //执行消费
    env.execute("kafka开始了")
  }

  def processData(data: DataStream[String]): Unit ={
    val logdata = data.map(str => {
      val splits = str.split("\t")
      val level = splits(2)
      val timeStr = splits(3)
      var time = 0l
      try {
        val sourseFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
        time = sourseFormat.parse(timeStr).getTime;
      }catch {
        case e:Exception =>{
          logger.error(s"time parse error: $timeStr"+e.getMessage)
        }
      }
      val domain = splits(4)
      val trafic = splits(6).toLong
      (level,time,domain,trafic)
    })
      .filter(_._2!=0)//在生产上处理业务一定要过滤不必要的数据
        .filter(_._1=="E")
        .map(x => {   //对数据再进行转换
          (x._2,x._3,x._4)
        })
    //设置水印
    val resultData: DataStream[(String,String,Long)]=logdata.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[(Long, String, Long)] {
      //解决无序的问题
      val maxOutOfOrderness = 10000L // 3.5 seconds
      var currentMaxTimestamp: Long = _  //scala里面的占位符
      override def getCurrentWatermark: Watermark = {
        new Watermark(currentMaxTimestamp - maxOutOfOrderness)
      }
      override def extractTimestamp(element: (Long, String, Long), previousElementTimestamp: Long): Long = {
        val timestamp = element._1
        currentMaxTimestamp = Math.max(timestamp, currentMaxTimestamp)
        timestamp
      }
    }).keyBy(1) //按照ip为分组
      .window(TumblingEventTimeWindows.of(Time.seconds(10)))//滑动窗口
      .apply(new WindowFunction[(Long,String,Long),(String,String,Long),Tuple,TimeWindow]{
        override def apply(key: Tuple, window: TimeWindow, input: Iterable[(Long, String, Long)], out: Collector[(String, String, Long)]): Unit = {

          val domain = key.getField(0).toString
          var sum = 0l
          val times = ArrayBuffer[Long]()
          val iterator = input.iterator
          while(iterator.hasNext){
            val next = iterator.next()
            sum +=next._3
            // TODO... 是能拿到你这个window里面的时间的  next._1
            times.append(next._1)
          }
          var time:String = ""
          try {
            time = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date(times.max))
          }catch {
            case e:Exception => {
              logger.error(s"time format error"+time)
            }
          }
          out.collect((time,domain,sum))
        }
      })
      //.print().setParallelism(1)   //打印输出用
    resultData.print()

    val httpHosts = new java.util.ArrayList[HttpHost]
    httpHosts.add(new HttpHost("193.112.189.10", 9200, "http"))
    val esSinkBuilder = new ElasticsearchSink.Builder[(String,String,Long)](
      httpHosts,
      new ElasticsearchSinkFunction[(String,String,Long)] {
        def createIndexRequest(element: (String,String,Long)): IndexRequest = {
          val json = new java.util.HashMap[String, String]
          json.put("time",element._1)
          json.put("domain", element._2)
          json.put("traffic", element._3.toString)
          val id = element._1+"-"+element._2
          return Requests.indexRequest()
            .index("cw")
            .`type`("doc")
            .id(id)
            .source(json)
        }
        override def process(t: (String, String, Long), runtimeContext: RuntimeContext, requestIndexer: RequestIndexer): Unit = {
          requestIndexer.add(createIndexRequest(t))
        }
      }
    )
    // configuration for the bulk requests; this instructs the sink to emit after every element, otherwise they would be buffered
    esSinkBuilder.setBulkFlushMaxActions(1)
    // finally, build and add the sink to the job's pipeline
    resultData.addSink(esSinkBuilder.build)
  }
}
