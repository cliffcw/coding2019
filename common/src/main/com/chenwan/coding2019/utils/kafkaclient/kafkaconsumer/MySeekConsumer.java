package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Properties;

/**
 * @program: base 指定offset消费
 * @description:
 * @author: cliffcw
 * @create: 2019-07-13 22:13
 */
public class MySeekConsumer {

    public static void main(String[] args) {
        Properties props=new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("group.id", "group1");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        KafkaConsumer<String,byte[]> consumer=new KafkaConsumer<String,byte[]>(props);
        try {
            TopicPartition seekToEndPartition = new TopicPartition("mySecondTopic", 1);
            consumer.assign(Arrays.asList(seekToEndPartition));
            //此处执行从编号为1的partition的offset为16的位置开始消费到最新的offset
            //需要注意的是：offset为16必须存在，如果不存在，则不会消费。
            consumer.seek(seekToEndPartition,16);
            ConsumerRecords<String, byte[]> records=consumer.poll(1000);
            for(ConsumerRecord<String, byte[]> record : records){
                System.out.println("MySeekToEndConsumer consumer message:partition="+record.partition()+",offset="+record.offset()+",key="+record.key()+",value="+record.value());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            consumer.close();
        }
    }
}
