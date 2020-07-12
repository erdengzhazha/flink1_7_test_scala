package com.cw.flink.scala.connector.process
import java.text.SimpleDateFormat
import java.util.Date
import com.cw.flink.scala.pojo.PvUvHourModelToMySql
import com.cw.flink.scala.pojo.CommonModel
import com.alibaba.fastjson.JSON
import com.cw.flink.scala.connector.kafkaConnectorConcumerApp.logger
import org.apache.flink.streaming.api.scala.DataStream
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.functions.AssignerWithPeriodicWatermarks
import org.apache.flink.streaming.api.scala.function.WindowFunction
import org.apache.flink.streaming.api.watermark.Watermark
import org.apache.flink.streaming.api.windowing.assigners.TumblingEventTimeWindows
import org.apache.flink.streaming.api.windowing.time.Time
import org.apache.flink.streaming.api.windowing.windows.TimeWindow
import org.apache.flink.util.Collector
import scala.collection.mutable.ArrayBuffer
/**
 *  处理数据对象
 */
object processCommon {
  def process(data: DataStream[String]): Unit = {
    //---------------------------------数据清洗      开始--------------------------------------
    val logcat: DataStream[CommonModel] = data.map(str => {  //从kafka里面独处的每一行json数据  (注意这里的json数据最外层可能是数组)
      //println("没有处理过的数据"+str)
      var resultArray = JSON.parseObject(str)  //转为对象
      var country: String = resultArray.getString("country")
      var syncTime: String= resultArray.getString("syncTime")
      var role: String= resultArray.getString("role")
      var city: String= resultArray.getString("city")
      var lon: String= resultArray.getString("lon")
      var `type`: String= resultArray.getString("type")
      var lib_version: String= resultArray.getString("lib_version")
      var screen_height: String= resultArray.getString("screen_height")
      var province: String= resultArray.getString("province")
      var event_code: String= resultArray.getString("event_code")
      var browser: String= resultArray.getString("browser")
      var browser_version: String= resultArray.getString("browser_version")
      var lat: String= resultArray.getString("lat")
      var screen_width: String= resultArray.getString("screen_width")
      var create_time: String= resultArray.getString("create_time")
      var os: String  = resultArray.getString("os")
      var ip: String= resultArray.getString("ip")
      var dept_name: String= resultArray.getString("dept_name")
      var enterprise_id: String= resultArray.getString("enterprise_id")
      var application_id: String= resultArray.getString("application_id")
      var token: String= resultArray.getString("token")
      var user_id: String= resultArray.getString("user_id")
      //------------------处理locationJson  开始-------------------
      var locationJson: String= resultArray.getString("location") //获得location的json数据
      var locationObject = JSON.parseObject(locationJson) //将location_json转为对象
      var location_lon = locationObject.getString("lon")
      var location_lat = locationObject.getString("lat")
      //------------------处理locationJson  结束-------------------
      var dept_id: String= resultArray.getString("dept_id")
      var isWdzSys: String= resultArray.getString("isWdzSys")
      //--------------------数据的格式处理 开始------------------

      //--------------------数据的格式处理 结束------------------
      //将处理后的结果数据放入 CommonModel
      val c =CommonModel(
        country,
        syncTime,
        role,
        city,
        lon,
        `type`,
        lib_version,
        screen_height,
        province,
        event_code,
        browser,
        browser_version,
        lat,
        screen_width,
        create_time,
        os,
        ip,dept_name,
        enterprise_id,application_id,token,user_id,location_lon,location_lat,dept_id,isWdzSys)
      (c) //形成结果
    }).filter(_.application_id!="")    //过滤掉应用ID为空的
      .filter(_.syncTime!="")  //过滤掉 时间为空的数据，肯定为异常的日志数据
      .filter(_.ip!="") //过滤掉ip获取不到的

    //println("清洗后的数据"+logcat.toString)
    //---------------------------------数据清洗      结束--------------------------------------

    //---------------------------------业务逻辑实现   开始--------------------------------------
    //设置水印
    val resultData: DataStream[PvUvHourModelToMySql]=logcat.assignTimestampsAndWatermarks(new AssignerWithPeriodicWatermarks[CommonModel] {
      //解决无序的问题
      val maxOutOfOrderness = 10000L // 10 seconds  最大的无序的容忍的时间是多少
      var currentMaxTimestamp: Long = _  //scala里面的占位符  最大的占位符
      override def getCurrentWatermark: Watermark = {
        new Watermark(currentMaxTimestamp - maxOutOfOrderness)
      }
      override def extractTimestamp(element: (CommonModel), previousElementTimestamp: Long): Long = {
        val timestamp = element.syncTime // 设置 EventTime
        //println("时间为"+timestamp)
        var time = 0l
        try {
          val sourseFormat = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");
          time = sourseFormat.parse(timestamp).getTime;
        }catch {
          case e:Exception =>{
            logger.error(s"time parse error: $time"+e.getMessage)
          }
        }
        currentMaxTimestamp = Math.max(time, currentMaxTimestamp) //求一个最大值
        time
      }
    })
      .keyBy(a => {
        (a.application_id,a.event_code)  //同时对 应用id和事件变量分组
      })
      .window(TumblingEventTimeWindows.of(Time.seconds(10))) //滑动EventTime窗口为 10秒
      .apply(new WindowFunction[(CommonModel),(PvUvHourModelToMySql),(String,String),TimeWindow]{
        override def apply(key: (String,String), window: TimeWindow, input: Iterable[(CommonModel)], out: Collector[(PvUvHourModelToMySql)]): Unit = {
          val iterator = input.iterator //获取window内的 json集合
          println("key ："+key._1)
          var i=0 //测试用的计数变量不用关注
          var pv: Int = 0
          var uv: Int = 0
          var manager_num_rate: Long = 0
          var store_manager_num_rate: Long=0l
          var supervisor_num_rate: Long=0l
          var application_id: Int=key._1.toInt
          var manager_num: Int=0
          var store_manager_num: Int=0
          var supervisor_num: Int=0
          var avg_stay_time: Long=0l
          var hour: Int=0
          var date_time: String= ""
          var create_time: String= ""
          var day: String= ""
          var event_code: String= key._2
          var uvList=List[String]()
//        *******************请注意！ 以上的变量赋初始值不能使用 iterator获取下一个元素！！！不然下面的集合遍历得重新获取一个新的iterator
          val times = ArrayBuffer[Long]() //时间数组
//          iterator.map( next => {
//
//          })
//          println("iterator的长度为"+iterator.size)
          while (iterator.hasNext){
            var next =iterator.next()
            //计算pv
            pv+=1
            //将所有ip地址记录在list集合
            uvList=next.ip::uvList
            /*计算manager_num_rate
              *1.计算 当前
              *
             */
            times.append(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").parse(next.syncTime).getTime)

            //var time: String =""
            //time = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date(next.syncTime.toLong))
            println("***********这是第"+i+"个"+next.syncTime+"key:"+next.application_id+"ip:"+next.ip+"\tcreate_time"+next.create_time+"\tcommon:"+
              next.event_code)
            i+=1
          }
          //计算uv
          uv=uvList.distinct.size //补足自身的一个值
          //计算时间
          try {
            create_time = new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date(times.max))
          }catch {
            case e:Exception => {
              logger.error(s"time format error"+create_time)
            }
          }
          val a:PvUvHourModelToMySql = PvUvHourModelToMySql(pv,uv,manager_num_rate,store_manager_num_rate,supervisor_num_rate,
            application_id,manager_num,store_manager_num,supervisor_num,avg_stay_time,hour,date_time,create_time,
            day,event_code
          )   //这是输出的类型
          out.collect(a)
        }
      })

    //对最终的结果打印输出
    resultData.map( a=> {
      println("我骚吗？"+a.toString)
    })

  }
}
