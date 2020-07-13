package com.cw.flink.java.project;


import com.cw.flink.java.pojo.IpAndDomains;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer; //注意这个包不要导错了


import java.text.SimpleDateFormat;
import java.util.*;

/**
 * kafka的 生产者类
 */
public class PKKafkaProducer {

    public static void main(String[] args) throws InterruptedException {
        Properties properties = new Properties();
        //kafka地址
        properties.setProperty("bootstrap.servers", "193.112.189.10:9092");
        //key和value的类型
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<String, String>(properties);
        String topic = "test";
        //通过死循环一直往kafka 发送数据
//        while (true){
//            StringBuilder builder = new StringBuilder();
//            IpAndDomains ipAndDomains =getIps();
//            builder.append("ovopark").append("\t")
//                    .append("CN").append("\t")
//                    .append(getLevel()).append("\t")
//                    .append(new SimpleDateFormat("YYYY-MM-dd HH:mm:ss").format(new Date())).append("\t")
//                    .append(ipAndDomains.getIp()).append("\t") //IP地址
//                    .append(ipAndDomains.getDomain()).append("\t") //域名
//                    .append(getTraffic()).append("\t") //端口号
//                    ;
//            System.out.println(builder.toString());
//            kafkaProducer.send(new ProducerRecord<String,String>(topic,builder.toString()));
//            Thread.sleep(2000);
//        }
//        List<String> list = new ArrayList<>();
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363763909,\"role\":-4,\"city\":\"苏州\",\"user_name\":\"测试测试鲁智深\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"80.0.3987.149\",\"stay_time\":731,\"refer_page_code\":\"salesLogSummary&personnel-file\",\"lat\":31.3041,\"currentUrl\":\"http://bosstest.ovopark.com/salesLogSummary/client-analyze\",\"entry_time\":\"2020-07-10 14:37:12\",\"referUrl\":\"http://bosstest.ovopark.com/salesLogSummary/personnel-file\",\"leave_time\":\"2020-07-10 14:49:23\",\"screen_width\":1920,\"create_time\":1594363763909,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":1084,\"application_id\":66,\"token\":\"3E981F97AC9AB490661056B57364CD0579A2DDA02343F29A3CC81386A26544D32548F2928E9CA9FC6F403E38EC4C8770\",\"user_id\":512161953,\"distinct_id\":\"aa41e7bd085b47963177b198819e4164\",\"page_code\":\"salesLogSummary&client-analyze\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"enterprise_name\":\"测试服企业\",\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":\"\",\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitGoods\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594358881956,\"os\":\"\",\"ip\":\"221.224.63.26\",\"goods_id\":39,\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":519,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":\"\",\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitGoods\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594363949255,\"os\":\"\",\"ip\":\"221.224.63.26\",\"goods_id\":503,\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":1128,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594358900006,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitDept\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594358900006,\"os\":\"\",\"ip\":\"221.224.63.26\",\"dept_name\":\"极味鲜\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":875,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363996730,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":16,\"refer_page_code\":\"bi&devicemanage\",\"lat\":31.3041,\"currentUrl\":\"http://localhost:8082/xinfa/index\",\"entry_time\":\"2020-07-10 14:53:00\",\"referUrl\":\"http://localhost:8082/bi/devicemanage\",\"leave_time\":\"2020-07-10 14:53:16\",\"screen_width\":1920,\"create_time\":1594363996730,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":0,\"application_id\":66,\"user_id\":0,\"distinct_id\":\"365d0eec4b5e81a4b8b0aa46802803e6\",\"page_code\":\"xinfa&index\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594358911723,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitDept\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594358911723,\"os\":\"\",\"ip\":\"221.224.63.26\",\"dept_name\":\"源芝林金溪总店\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":519,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594364016057,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":0,\"refer_page_code\":\"null\",\"lat\":31.3041,\"currentUrl\":\"http://localhost:8082/login.html\",\"entry_time\":\"2020-07-10 14:53:35\",\"referUrl\":\"null\",\"leave_time\":\"2020-07-10 14:53:35\",\"screen_width\":1920,\"create_time\":1594364016057,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":0,\"application_id\":66,\"user_id\":0,\"page_code\":\"login.html\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594358927495,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594358927495,\"os\":\"\",\"ip\":\"221.224.63.26\",\"dept_name\":\"源芝林金溪总店\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":875,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":12,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594364021699,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":0,\"refer_page_code\":\"null\",\"lat\":31.3041,\"currentUrl\":\"http://localhost:8082/login.html\",\"entry_time\":\"2020-07-10 14:53:41\",\"referUrl\":\"null\",\"leave_time\":\"2020-07-10 14:53:41\",\"screen_width\":1920,\"create_time\":1594364021699,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":0,\"application_id\":66,\"user_id\":0,\"page_code\":\"login.html\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594358951356,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitGoods\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594358951356,\"os\":\"\",\"ip\":\"221.224.63.26\",\"goods_id\":566,\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":875,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594364027466,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":0,\"refer_page_code\":\"null\",\"lat\":31.3041,\"currentUrl\":\"http://localhost:8082/login.html\",\"entry_time\":\"2020-07-10 14:53:47\",\"referUrl\":\"null\",\"leave_time\":\"2020-07-10 14:53:47\",\"screen_width\":1920,\"create_time\":1594364027466,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":0,\"application_id\":66,\"user_id\":0,\"page_code\":\"login.html\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594359131945,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"visitDept\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594359131945,\"os\":\"\",\"ip\":\"221.224.63.26\",\"dept_name\":\"源芝林金溪总店\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":280,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363032817,\"role\":-4,\"city\":\"苏州\",\"user_name\":\"测试测试鲁智深\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"80.0.3987.149\",\"stay_time\":40,\"refer_page_code\":\"salesLogSummary&client-analyze\",\"lat\":31.3041,\"currentUrl\":\"http://bosstest.ovopark.com/salesLogSummary/personnel-file\",\"entry_time\":\"2020-07-10 14:36:32\",\"referUrl\":\"http://bosstest.ovopark.com/salesLogSummary/client-analyze\",\"leave_time\":\"2020-07-10 14:37:12\",\"screen_width\":1920,\"create_time\":1594363032817,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":1084,\"application_id\":66,\"token\":\"3E981F97AC9AB490661056B57364CD0579A2DDA02343F29A3CC81386A26544D32548F2928E9CA9FC6F403E38EC4C8770\",\"user_id\":512161953,\"distinct_id\":\"aa41e7bd085b47963177b198819e4164\",\"page_code\":\"salesLogSummary&personnel-file\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"enterprise_name\":\"测试服企业\",\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363451769,\"role\":0,\"city\":\"南京\",\"lon\":118.7778,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":32.0617,\"screen_width\":0,\"create_time\":1594363451769,\"os\":\"\",\"ip\":\"49.95.245.91\",\"dept_name\":\"源芝林金溪总店\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":280,\"location\":{\"lon\":118.7778,\"lat\":32.0617},\"dept_id\":12,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363740561,\"role\":-4,\"city\":\"苏州\",\"user_name\":\"测试服王高鹏\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":1,\"refer_page_code\":\"wisdomcar&inout\",\"lat\":31.3041,\"currentUrl\":\"http://eatest.ovopark.com/wisdomcar/records\",\"entry_time\":\"2020-07-10 14:48:59\",\"referUrl\":\"http://eatest.ovopark.com/wisdomcar/inout\",\"leave_time\":\"2020-07-10 14:49:00\",\"screen_width\":1920,\"create_time\":1594363740561,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":1084,\"application_id\":67,\"token\":\"7A64222355ACDF9FCE6754129726E83973FFF23570ABA8BBEDDDBE7071D3D5EFE7B9F2DD0EE7AFD2D2BDF7C30A55935B\",\"user_id\":512168318,\"distinct_id\":\"300d78e84755e63d5a286326e4abe403\",\"page_code\":\"wisdomcar&records\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"enterprise_name\":\"测试服企业\",\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363747251,\"role\":-4,\"city\":\"苏州\",\"user_name\":\"测试服王高鹏\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":5,\"refer_page_code\":\"wisdomcar&depart\",\"lat\":31.3041,\"currentUrl\":\"http://eatest.ovopark.com/wisdomcar/inout\",\"entry_time\":\"2020-07-10 14:49:02\",\"referUrl\":\"http://eatest.ovopark.com/wisdomcar/depart\",\"leave_time\":\"2020-07-10 14:49:07\",\"screen_width\":1920,\"create_time\":1594363747251,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":1084,\"application_id\":67,\"token\":\"7A64222355ACDF9FCE6754129726E83973FFF23570ABA8BBEDDDBE7071D3D5EFE7B9F2DD0EE7AFD2D2BDF7C30A55935B\",\"user_id\":512168318,\"distinct_id\":\"300d78e84755e63d5a286326e4abe403\",\"page_code\":\"wisdomcar&inout\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"enterprise_name\":\"测试服企业\",\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594363750572,\"role\":-4,\"city\":\"苏州\",\"user_name\":\"测试服王高鹏\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":1080,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"chrome\",\"browser_version\":\"83.0.4103.116\",\"stay_time\":1,\"refer_page_code\":\"wisdomcar&check\",\"lat\":31.3041,\"currentUrl\":\"http://eatest.ovopark.com/wisdomcar/depart\",\"entry_time\":\"2020-07-10 14:49:09\",\"referUrl\":\"http://eatest.ovopark.com/wisdomcar/check\",\"leave_time\":\"2020-07-10 14:49:10\",\"screen_width\":1920,\"create_time\":1594363750572,\"os\":\"Windows\",\"ip\":\"221.224.63.26\",\"enterprise_id\":1084,\"application_id\":67,\"token\":\"7A64222355ACDF9FCE6754129726E83973FFF23570ABA8BBEDDDBE7071D3D5EFE7B9F2DD0EE7AFD2D2BDF7C30A55935B\",\"user_id\":512168318,\"distinct_id\":\"300d78e84755e63d5a286326e4abe403\",\"page_code\":\"wisdomcar&depart\",\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":0,\"enterprise_name\":\"测试服企业\",\"isWdzSys\":true}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594365974038,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594365974038,\"os\":\"\",\"ip\":\"221.224.63.26\",\"dept_name\":\"极味鲜\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":1300,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":79,\"isWdzSys\":false}");
//        list.add("{\"country\":\"中国\",\"syncTime\":1594366141822,\"role\":0,\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\"common\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594366141822,\"os\":\"\",\"ip\":\"221.224.63.26\",\"dept_name\":\"极味鲜\",\"enterprise_id\":0,\"application_id\":70,\"token\":\"\",\"user_id\":1300,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":79,\"isWdzSys\":false}");
//        for(String str: list){
//            kafkaProducer.send(new ProducerRecord<String,String>(topic,str));
//            System.out.println(str);
//            Thread.sleep(5000);
//        }

        while(true){
            String str = getMessage();
            System.out.println(str);
            kafkaProducer.send(new ProducerRecord<String,String>(topic,str));
            Thread.sleep(2000);
        }
    }

    /**
     * 造出数据
     * @return
     */
    public static String getMessage(){
        SimpleDateFormat smp =new SimpleDateFormat("YYYY-MM-dd HH:mm:ss");

        return "{\"country\":\"中国\",\"syncTime\":\""+smp.format(new Date())+"\",\"role\":"+getRole()+",\"city\":\"苏州\",\"lon\":120.5954,\"type\":1,\"lib_version\":\"1.0.0\",\"screen_height\":0,\"province\":\"江苏省\",\"event_code\":\""+getEvenCode()+"\",\"browser\":\"\",\"browser_version\":\"\",\"lat\":31.3041,\"screen_width\":0,\"create_time\":1594366141822,\"os\":\"\",\"ip\":\""+getIps().getIp()+"\",\"dept_name\":\"极味鲜\",\"enterprise_id\":0,\"application_id\":"+new Random().nextInt(10)+",\"token\":\"\",\"user_id\":1300,\"location\":{\"lon\":120.5954,\"lat\":31.3041},\"dept_id\":79,\"isWdzSys\":false}";

    }

    private static String getRole(){
        String[] str = new String[]{"-4","-2","-3"};
        return str[new Random().nextInt(str.length)];
    }

    public static String getEvenCode(){
        String[] str = new String[] {"common1","common2","common3","common4"};
        return str[new Random().nextInt(str.length)];
    }

    public static String getLevel(){
        String[] strArray = new String[]{"E"};
        return strArray[new Random().nextInt(strArray.length)];
    }

    public static IpAndDomains getIps(){
        List<IpAndDomains> list = new ArrayList<>();
        list.add(new IpAndDomains("192.168.10.1","qiusunzuo.com"));
        list.add(new IpAndDomains("192.168.20.6","scala.com"));
        list.add(new IpAndDomains("32.69.63.123","chenwei.com"));
        list.add(new IpAndDomains("25.69.36.85","php.com"));
        list.add(new IpAndDomains("48.12.255.7","java.com"));
        list.add(new IpAndDomains("4.58.69.2","c++.com"));
        return list.get(new Random().nextInt(list.size()));
    }
    public static long getTraffic(){
        return new Random().nextInt(10000);
    }
}
