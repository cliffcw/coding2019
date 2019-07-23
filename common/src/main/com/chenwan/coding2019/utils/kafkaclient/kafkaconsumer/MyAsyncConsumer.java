package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;

import java.util.Arrays;
import java.util.Map;
import java.util.Properties;

/**
 * @program: base
 * @description: 消息offset的异步提交
 * @author: cliffcw
 * @create: 2019-07-13 22:31
 */
public class MyAsyncConsumer {

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
            while(true){
                ConsumerRecords<String, String> records=consumer.poll(100);
                for(ConsumerRecord<String, String> record : records){
                    //此处使用输出操作模拟项目中的业务处理。
                    System.out.println("MySyncConsumer consume message：partition="+record.partition()
                            +",offset="+record.offset()
                            +",key="+record.key()
                            +",value="+record.value());
                }
                //异步提交offset，使用回调函数onComplete处理异常，如果不需要回调函数，可以直接调用无参数的异步提交方法：consumer.commitAsync();
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception exception) {
                        if (exception != null) {
                            // 处理异常逻辑
                        }
                    }
                });
            }
        } finally{
            consumer.close();
        }
    }
}
