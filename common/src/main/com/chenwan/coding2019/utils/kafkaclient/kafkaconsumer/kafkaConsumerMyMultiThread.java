package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.List;
import java.util.Properties;

/**
 * @program: base 多线程消费者
 * @description:
 * @author: cliffcw
 * @create: 2019-07-12 14:21
 */
//实现Runnable接口，用于多线程环境
public class kafkaConsumerMyMultiThread implements Runnable {

    //声明KafkaConsumer对象，用于订阅topic,读取消息
    private final KafkaConsumer<String, String> consumer;

    //声明topic列表变量，作为KafkaConsumer对象订阅的topic列表
    private final List<String> topics;

    //声明一个id变量，用于区分不同的consumer对象,方便查看哪个consumer消费了哪些数据
    private final int id;

    //构造函数，实例化上面声明的三个变量
    public kafkaConsumerMyMultiThread(int id, String groupId, List<String> topics) {

        this.id = id;
        this.topics = topics;

        Properties props = new Properties();

        props.put("bootstrap.servers", "localhost:9092");

        props.put("group.id", groupId);

        props.put("key.deserializer", StringDeserializer.class.getName());

        props.put("value.deserializer", StringDeserializer.class.getName());

        //此处new了一个 KafkaConsumer，保证一个KafkaConsumer对象用于一个线程中
        this.consumer = new KafkaConsumer<String, String>(props);

    }



    //Runnable接口方法
    @Override
    public void run() {
        try {

            //订阅topic
            consumer.subscribe(topics);

            while (true) {

                //无限循环读取消息
                ConsumerRecords<String, String> records = consumer.poll(100);

                for (ConsumerRecord<String, String> record : records) {

                    //打印消息内容
                    System.out.println("Consumer-" + id + "'s consumption message：partition=" + record.partition() + ",offset=" + record.offset() + ",key=" + record.key() + ",value=" + record.value());

                }
            }
        } catch (WakeupException e) {

            e.printStackTrace();

        } finally {

            consumer.close();//关闭consumer

        }
    }

}
