package com.cw.flink.scala.pojo

case class PvUvHourModelToMySql(
                           pv: Int,
                     uv:Int,
                     manager_num_rate:Long,
                     store_manager_num_rate:Long,
                     supervisor_num_rate:Long,
                     application_id: Int,
                     manager_num: Int,
                     store_manager_num: Int,
                     supervisor_num: Int,
                     avg_stay_time: Long,
                     hour: Int,
                     date_time: String,
                     create_time: String,
                     day: String,
                     event_code: String) extends Product with Serializable{
  override def productElement(n: Int): Any = n match {
    case 0 =>pv: Int
    case 1 => uv:Int
    case 2 => manager_num_rate:Long
    case 3 => store_manager_num_rate:Long
    case 4 => supervisor_num_rate:Long
    case 5 => application_id: Int
    case 6 => manager_num: Int
    case 7 => store_manager_num: Int
    case 8 => supervisor_num: Int
    case 9 => avg_stay_time: Long
    case 10 => hour: Int
    case 11 => date_time: String
    case 12 => create_time: String
    case 13 => day: String
    case 14 => event_code: String
    case _ => throw new IndexOutOfBoundsException(n.toString)
  }

  override def productArity: Int = 15

  override def canEqual(that: Any): Boolean = that.isInstanceOf[PvUvHourModelToMySql]

  override def toString: String = {
    s"PvUvHourModel[${
      (for(i <- 0 until productArity) yield  productElement(i) match {
        case Some(x) => x
        case t => t
      }).mkString(",")
    }]"
  }
  object PvUvHourModel{
    def main(args: Array[String]): Unit = {

    }
  }
}

