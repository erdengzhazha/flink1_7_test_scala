package com.cw.flink.java.base;

import com.cw.flink.java.pojo.People;
import org.apache.flink.api.java.ExecutionEnvironment;

import java.util.ArrayList;
import java.util.List;

public class BatchJob {
    public static void main(String[] args) throws Exception {
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();
        /** 读取一个集合*/
        //BatchJob.fromCollection(env);
        /** 可以读取一个文件夹或者一个文件*/
        //BatchJob.readtextFile(env);
    }

    public static void readcsvFile(ExecutionEnvironment env){
        env.readCsvFile("file:///Users/wei/tmp/hello.txt");
    }

    public static void readtextFile(ExecutionEnvironment env) throws Exception {
        env.readTextFile("file:///Users/wei/tmp/hello.txt").print();
    }

    public static void fromCollection(ExecutionEnvironment env) throws Exception {
        List<Integer> list = new ArrayList<>();
        for (int i =0;i<10;i++){
            list.add(i);
        }
        env.fromCollection(list).print();
    }
}
