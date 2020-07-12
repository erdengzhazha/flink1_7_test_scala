import com.alibaba.fastjson.JSON
import org.json4s.DefaultFormats
import org.json4s.jackson.JsonMethods.parse

object JsonUtilTest {
  def main(args: Array[String]): Unit = {
    var strjson: String = """
         { "name": "joe",
            "kk":3.0,
           "children": [
             {
               "name": "Mary",
               "age": 5
             },
             {
               "name": "Mazy"

             },
             {
               "name": "Mazy",
               "age": 6
             }
           ]
         }
       """
    var json = parse("""
         { "name": "joe",
            "kk":3.0,
           "children": [
             {
               "name": "Mary",
               "age": 5
             },
             {
               "name": "Mazy"

             },
             {
               "name": "Mazy",
               "age": 6
             }
           ]
         }
       """,useBigDecimalForDouble = true)

    //val json :JValue= parse("{\"country\":\"中国\",\"syncTime\":1594363949255.552,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitGoods\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594363949255,\"os\":\"\",\"ip\":\"221.224.63.26\",\"goods_id\":503,\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":1128,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}")
//    var result1 =for {
//      JObject(child) <- json
//      JField("country", JString(country)) <- child
//      //JField("syncTime",JDouble(syncTime)) <- child
//    } yield (country)
    try {
      var parseObject = JSON.parseObject(strjson)
      val getCildern = parseObject.getString("children") //获取到childran
      println(getCildern)

      var jsonArray = parseObject.getJSONArray("children")
      val nObject = jsonArray.getJSONObject(0)
      println(nObject)


    }catch {
      case e:Exception => {
        println("parseObject转化错误"+e.getMessage)
      }
    }
    try {
      implicit val formats = DefaultFormats
//      val JString(country) = (json \ "country")
//      val JDouble(syncTime) = (json \ "syncTime")
//      val JObject(location) = (json \ "location")
      case class child(name: String,age: Int)
      var childes: child = (json \ "children").extract[child]

      print(childes)
    }catch {
      case e:Exception => {
        println("报错了"+e.getMessage)
      }
    }

    //print(country+syncTime+location+age)
//    result1.map( a =>{
//      println(a)
//    })

//    val result = for {
//      JObject(child) <- json
//      JField("name", JString(name)) <- child
//      JField("age", JInt(age)) <- child
//      if age > 4
//    } yield (name, age)
//    print(result)





//    import java.util
//    def getNode(body: Nothing, key: String, value: Any, result: Nothing) = {
//      var i = 0
//      while ( {
//        i < body.size
//      }) {
//        val jsonObject = body.getJSONObject(i)
//        if (jsonObject.get(key).toString.equals(value.toString)) {
//          import scala.collection.JavaConversions._
//          for (entry <- jsonObject.entrySet) {
//            result.put(entry.getKey, entry.getValue)
//          }
//        }
//        else if (jsonObject.getJSONArray("children") != null) getNode(jsonObject.getJSONArray("children"), key, value, result)
//
//        i += 1
//      }
//      result
//    }
  }

}
