package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.Properties;

/**
 * @program: base 消息offset的同步提交
 * @description:
 * @author: cliffcw
 * @create: 2019-07-13 22:28
 */
public class MySyncConsumer {

    //消息至少消费一次
    public static void main(String[] args) {
        Properties props=new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        //禁用自动提交：
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer=new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList("mySecondTopic"));
        try {
            while(true){
                ConsumerRecords<String, String> records=consumer.poll(100);
                for(ConsumerRecord<String, String> record : records){
                    //此处使用输出操作模拟项目中的业务处理。
                    System.out.println("MySyncConsumer consume message：partition="+record.partition()+",offset="+record.offset()+",key="+record.key()+",value="+record.value());
                }
                consumer.commitSync();//同步提交
            }
        } finally{
            consumer.close();
        }
    }

    //消息最多消费一次（将offset的提交拿到业务处理逻辑之前）
    public static void main2(String[] args) {
        Properties props=new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        //禁用自动提交：
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer=new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList("mySecondTopic"));
        try {
            while(true){
                ConsumerRecords<String, String> records=consumer.poll(100);
                consumer.commitSync();//同步提交
                for(ConsumerRecord<String, String> record : records){
                    //此处使用输出操作模拟项目中的业务处理。
                    System.out.println("MySyncConsumer consume message：partition="+record.partition()+",offset="+record.offset()+",key="+record.key()+",value="+record.value());
                }

            }
        } finally{
            consumer.close();
        }
    }
}
