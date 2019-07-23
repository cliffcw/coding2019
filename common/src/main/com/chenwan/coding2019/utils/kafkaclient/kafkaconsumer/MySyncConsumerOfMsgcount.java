package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: base
 * @description: 按处理的消息量提交offset
 * @author: cliffcw
 * @create: 2019-07-13 22:36
 */
public class MySyncConsumerOfMsgcount {

    public static void main(String[] args) {
        //用于存储partition的offset信息
        Map<TopicPartition, OffsetAndMetadata> currentOffsets = new HashMap<TopicPartition, OffsetAndMetadata>();
        //用于计数,如count=5000,提交一次offset
        int count = 0;
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
                for (ConsumerRecord<String, String> record : records)
                {
                    //此处使用输出操作模拟项目中的业务处理。
                    System.out.println("MySyncConsumer3 consume message：partition="+record.partition()+",offset="+record.offset()+",key="+record.key()+",value="+record.value());
                    TopicPartition tp = new TopicPartition(record.topic(), record.partition());
                    OffsetAndMetadata om=new OffsetAndMetadata(record.offset(),"");
                    currentOffsets.put(tp,om);
                    //为方便测试，我们设置每5条记录提交一次
                    if (count % 5 == 0){
                        consumer.commitSync(currentOffsets);
                    }
                    count++;
                }
                consumer.commitSync();
            }
        } finally {
            consumer.close();
        }
    }
}
