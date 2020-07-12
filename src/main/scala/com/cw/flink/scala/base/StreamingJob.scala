package com.cw.flink.scala.base

import java.util.Properties

import com.cw.flink.scala.connector.process.processCommon
import org.apache.flink.api.common.serialization.SimpleStringSchema
import org.apache.flink.streaming.api.TimeCharacteristic
import org.apache.flink.streaming.api.scala.StreamExecutionEnvironment
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer

/**
 * Skeleton for a Flink Streaming Job.
 *
 * For a tutorial how to write a Flink streaming application, check the
 * tutorials and examples on the <a href="http://flink.apache.org/docs/stable/">Flink Website</a>.
 *
 * To package your application into a JAR file for execution, run
 * 'mvn clean package' on the command line.
 *
 * If you change the name of the main class (with the public static void main(String[] args))
 * method, change the respective entry in the POM.xml file (simply search for 'mainClass').
 */
object StreamingJob {
  def main(args: Array[String]) {
    //拿到一个stream的执行环境
    val env  = StreamExecutionEnvironment.getExecutionEnvironment
    env.setStreamTimeCharacteristic(TimeCharacteristic.EventTime)
    //接手kafka数据的信息
    var topic = "test"
    var properties = new Properties()
    properties.setProperty("bootstrap.servers", "193.112.189.10:9092")
    properties.setProperty("group.id", "test")
    import org.apache.flink.api.scala._
    //加入Source
    val data = env.addSource(new FlinkKafkaConsumer[String](topic, new SimpleStringSchema(), properties))

    //processData(data)
    data.map( a =>{
      print(a.toString)
    })
    //执行消费
    env.execute("kafka开始了")
  }
}
