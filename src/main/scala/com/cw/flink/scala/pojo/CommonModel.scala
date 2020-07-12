package com.cw.flink.scala.pojo

case class CommonModel(
                        country: String,
                        syncTime: String,
                        role: String,
                        city: String,
                        lon: String,
                        `type`: String,
                        lib_version: String,
                        screen_height: String,
                        province: String,
                        event_code: String,
                        browser: String,
                        browser_version: String,
                        lat: String,
                        screen_width: String,
                        create_time: String,
                        os: String,
                        ip: String,
                        dept_name: String,
                        enterprise_id: String,
                        application_id: String,
                        token: String,
                        user_id: String,
                        location_lon: String,
                        location_lat: String,
                        dept_id: String,
                        isWdzSys: String
                      )extends Product with Serializable{
  override def productElement(n: Int): Any = n match {
    case 0 =>country: String
    case 1 => syncTime: String
    case 2 => role: String
    case 3 => city: String
    case 4 => lon: String
    case 5 => `type`: String
    case 6 => lib_version: String
    case 7 => screen_height: String
    case 8 => province: String
    case 9 => event_code: String
    case 10 => browser: String
    case 11 => browser_version: String
    case 12 => lat: String
    case 13 => screen_width: String
    case 14 => create_time: String
    case 15 => os: String
    case 16 => ip: String
    case 17 => dept_name: String
    case 18 => enterprise_id: String
    case 19 => application_id: String
    case 20 => token: String
    case 21 => user_id: String
    case 22 => location_lon: String
    case 23 => location_lat: String
    case 24 => dept_id: String
    case 25 => isWdzSys: String
    case _ => throw new IndexOutOfBoundsException(n.toString)
  }
  override def productArity: Int = 26

  override def canEqual(that: Any): Boolean = that.isInstanceOf[CommonModel]

  override def toString: String = {
    s"CommonModel[${
      (for(i <- 0 until productArity) yield  productElement(i) match {
        case Some(x) => x
        case t => t
      }).mkString(",")
    }]"
  }
  object CommonModel{
    def main(args: Array[String]): Unit = {

    }
  }
}
