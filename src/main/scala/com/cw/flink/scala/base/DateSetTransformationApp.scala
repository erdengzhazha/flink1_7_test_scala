package com.cw.flink.scala.base

import org.apache.flink.api.scala.ExecutionEnvironment
//隐式转换
import org.apache.flink.api.scala._
object DateSetTransformationApp {
  def main(args: Array[String]): Unit = {
    val env = ExecutionEnvironment.getExecutionEnvironment
    mapFuntion(env)
  }

  def mapFuntion(env: ExecutionEnvironment): Unit ={
    val data =env.fromCollection(List(1,2,3,4,5,6,7,8,9))
    //data.map((x=>x.toInt+1)).print()
    data.map(_ + 1).print()
  }
}
