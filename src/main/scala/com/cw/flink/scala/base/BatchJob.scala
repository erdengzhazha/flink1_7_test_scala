package com.cw.flink.scala.base
import com.cw.flink.java.pojo.People
import org.apache.flink.api.scala.ExecutionEnvironment
import org.apache.flink.configuration.Configuration
//隐式转换
import org.apache.flink.api.scala._
/**
 * Skeleton for a Flink Batch Job.
 */
object BatchJob {
  def main(args: Array[String]) {
    // set up the batch execution environment
    val env = ExecutionEnvironment.getExecutionEnvironment
    /** 读取集合*/
    //fromCollection(env)
    /** 读取文件*/
    //textFile(env)
    //csvFile(env)

    readRecursiveFiles(env);

    // execute program
    //env.execute("Flink Batch Scala API Skeleton")
  }

  /***
   *  读取压缩的文件
   */
  def readZipFiles(env: ExecutionEnvironment): Unit ={
    var filePath = ""
    env.readTextFile(filePath)
  }



  /**
   * 读取递归的txt 文件
   * @param env
   */
  def readRecursiveFiles(env: ExecutionEnvironment): Unit ={
    var filePath = "file:///Users/wei/tmp/tmpdidi"
    var parameters = new Configuration
    parameters.setBoolean("recursive.file.enumeration",true)
    env.readTextFile(filePath).withParameters(parameters).print();
  }

  /**
   * 从csv文件读取
   * @param env
   */
  def csvFile(env: ExecutionEnvironment): Unit ={
    val csvPath= "file:///Users/wei/tmp/hello.csv"
    /** ingnoreFirstLine解析忽略第一行 , 第一行不输出*/
    //env.readCsvFile[(String,String,String)](csvPath,ignoreFirstLine=true).print()
    /** 指定列输出  （1）*/
    //env.readCsvFile[(String,String)](filePath = csvPath,includedFields = Array(0,1),ignoreFirstLine = true).print()

    /** 指定列输出 非java对象 （2）*/
//    case class MyCaseClass(name: String,age: Int)
//    env.readCsvFile[MyCaseClass](filePath = csvPath,includedFields = Array(0,1),ignoreFirstLine = true).print()
    /** 指定列输出 java POJO （3）*/
      env.readCsvFile[People](filePath = csvPath,pojoFields = Array("name","age"),ignoreFirstLine = true).print()
  }

  /**
   * 从文件 .txt读取
   * @param env
   */
  def textFile(env: ExecutionEnvironment): Unit ={
    var filePath = "file:///Users/wei/tmp/hello.txt";
    env.readTextFile(filePath).print();
  }

  /**
   * 从集合读取
   * @param env
   */
  def fromCollection(env: ExecutionEnvironment): Unit={
    val data = 1 to 10
    env.fromCollection(data).print()
  }
}
