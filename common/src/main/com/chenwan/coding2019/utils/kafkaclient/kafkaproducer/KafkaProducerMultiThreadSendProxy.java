package main.com.chenwan.coding2019.utils.kafkaclient.kafkaproducer;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

/**
 * @program: base 多线程发送-生产常用-待研究
 * @description:
 * @author: cliffcw
 * @create: 2019-07-11 23:40
 */
public class KafkaProducerMultiThreadSendProxy extends Thread{

    //声明KafkaProducer类型的全局变量，由于在多线程环境，所以声明为final类型
    private final KafkaProducer<String, String> producer;

    //声明用于存储topic名称的全局变量，由于在多线程环境，所以声明为final类型
    private final String topic;

    //设置一次发送消息的条数
    private final int messageNumToSend = 10;

    /**
     * 在构造函数中创建KafkaProducer对象
     *
     * @param topicName Topic名称
     */
    public KafkaProducerMultiThreadSendProxy(String topicName) {

        //创建一个Properties对象，用于存储连接kafka所需要的配置信息
        Properties kafkaProps = new Properties();

        // Broker地址列表
        kafkaProps.put("bootstrap.servers", "localhost:9092");

        //向kafka集群发送消息,除了消息内容本身,还包括key信息,key信息用于消息在partition之间均匀分布。
        //发送消息的key,类型为String,使用String类型的序列化器
        kafkaProps.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //发送消息的value,类型为String,使用String类型的序列化器
        kafkaProps.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //创建KafkaProducer对象
        producer = new KafkaProducer<String, String>(kafkaProps);

        //设置全局变量topic的值
        topic = topicName;
    }

    /**
     * 生产者线程执行函数，循环发送消息。
     */
    public void run() {

        //用于记录已发送信息的条数，同时作为消息的key值。
        int messageNo = 0;

        while (messageNo <= messageNumToSend) {

            //所发送消息的内容
            String messageContent = "Message_" + messageNo;

            // 构造消息记录
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, messageNo + "", messageContent);

            // 异步发送
            producer.send(record, new DemoProducerCallback());

            //已发送消息的数量增加1
            messageNo++;

        }
        //刷新缓存，将消息发送到kafka集群
        producer.flush();

    }

    public static void main(String[] args) {

        //启动3个线程发送消息
        for (int i = 0; i < 3; i++) {

            new KafkaProducerMultiThreadSendProxy("mySecondTopic").start();

        }
    }
}

/**
 * @Description: 发送消息时，传入一个实现了Callback接口的对象，此时发送消息不会阻塞，发送完成后，会调用Callback接口的onCompletion方法。
 * @Param:
 * @return:
 * @Author: cliffcw
 * @Date:
 */
class DemoProducerCallback implements Callback {

    public void onCompletion(RecordMetadata recordMetadata, Exception exception) {

        if (exception != null) {

            exception.printStackTrace();

        } else {

            long offset = recordMetadata.offset();

            int partition = recordMetadata.partition();

            String topic = recordMetadata.topic();

            System.out.println("the message topic: " + topic + ",offset: " + offset + ",partition: " + partition);
        }
    }
}
