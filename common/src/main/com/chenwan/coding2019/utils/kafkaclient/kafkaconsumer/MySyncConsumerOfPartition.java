package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Properties;

/**
 * @program: base
 * @description: 按partition维度提交offset
 * @author: cliffcw
 * @create: 2019-07-13 22:33
 */
public class MySyncConsumerOfPartition {

    public static void main(String[] args) {
        Properties props=new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        props.put("enable.auto.commit", "false");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        KafkaConsumer<String,String> consumer=new KafkaConsumer<String,String>(props);
        consumer.subscribe(Arrays.asList("mySecondTopic"));
        try {
            while (true) {
                ConsumerRecords<String, String> records = consumer.poll(100);
                //遍历每个partition
                for (TopicPartition partition : records.partitions()) {
                    //获取指定partition中的消息记录
                    List<ConsumerRecord<String, String>> partitionRecords = records.records(partition);
                    //遍历指定partition中的消息记录
                    for (ConsumerRecord<String, String> record : partitionRecords){
                        //此处使用输出操作模拟项目中的业务处理。
                        System.out.println("MySyncConsumer2 consume message：partition="+record.partition()+",offset="+record.offset()+",key="+record.key()+",value="+record.value());
                    }
                    //获取partition中最后一条记录的offset
                    long lastoffset = partitionRecords.get(partitionRecords.size() - 1).offset();
                    //同步提交一个partition中的offset
                    consumer.commitSync(Collections.singletonMap(partition, new OffsetAndMetadata(lastoffset+ 1)));
                }
            }
        } finally {
            consumer.close();
        }
    }
}
