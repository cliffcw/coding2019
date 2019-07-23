package main.com.chenwan.coding2019.utils.kafkaclient.kafkaproducer;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.Future;

/**
 * @program: base Synchronous Send（同步发送）
 * @description:
 * @author: cliffcw
 * @create: 2019-07-11 23:23
 */
public class KafkaProducerSynSendProxy {

    private String kafkaBrokers;

    private Producer<String, String> producer;

    private static Map<String, KafkaProducerSynSendProxy> instanceFactoryMap;

    private KafkaProducerSynSendProxy(String kafkaBrokers) {

        this.kafkaBrokers = kafkaBrokers;

        this.producer = new KafkaProducer<String, String>(initProducerConfigProperties(kafkaBrokers));
    }


    /**
     * @Description: 初始化生产者配置
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static Properties initProducerConfigProperties(String kafkaBrokers) {

        Properties properties = new Properties();

        //可以是broker的一个子集 建议列表中至少包含两个 brokers，因为这样即使一个 broker 连接不上，可以连接另一个 broker，从而提高程序的健壮性。
        properties.put("bootstrap.servers", kafkaBrokers);

        /*
         * acks:消息的确认机制，默认值是0。
         * acks=0：如果设置为0，生产者不会等待kafka的响应。
         * acks=1：这个配置意味着kafka会把这条消息写到本地日志文件中，但是不会等待集群中其他机器的成功响应。
         * acks=all：这个配置意味着leader会等待所有的follower同步完成。这个确保消息不会丢失，除非kafka集群中所有机器挂掉。这是最强的可用性保证。
         *
         */
        properties.put("acks", "all");

        //发送消息的key,类型为String,使用String类型的序列化器
        //Kafka 客户端包中有 ByteArraySerializer, StringSerializer和IntegerSerializer。 三种类型的序列化器，如果发送常用类型的消息，不需要自定义序列化器。注意：即使发送只包含 value 的消息，也要设置 key.serializer 。
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //发送消息的value,类型为String,使用String类型的序列化器
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        //配置为大于0的值的话，客户端会在消息发送失败时重新发送
        properties.put("retries", 0);

        //当多条消息需要发送到同一个分区时，生产者会尝试合并网络请求。这会提高client和生产者的效率
        properties.put("batch.size", 16384);

        //配置partitionner选择策略，可选配置
//        properties.put("partitioner.class", "自己写的类名");

        return properties;

    }

    /**
     * @Description: KafkaProducerSynSendProxy 实例
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static KafkaProducerSynSendProxy getInstance(String kafkaBrokers) throws Exception {

        if (kafkaBrokers == null) {
            throw new Exception("kafkaBrokers is null");
        }

        String key = kafkaBrokers;

        if (instanceFactoryMap == null || instanceFactoryMap.get(key) == null) {

            instanceFactoryMap = new HashMap<String, KafkaProducerSynSendProxy>();

            KafkaProducerSynSendProxy kafkaProducerSynSendProxy = new KafkaProducerSynSendProxy(kafkaBrokers);

            instanceFactoryMap.put(key, kafkaProducerSynSendProxy);

            return kafkaProducerSynSendProxy;
        } else {

            return instanceFactoryMap.get(key);
        }
    }

    /** * @Description:  发送消息
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public void sendMsg(String topic, String routeKey, String msg) {

        if (topic != null && msg != null) {

            /*
             * 使用ProducerRecord<String, String>(String topic, String key, String value)构造函数创建消息对象
             * 构造函数接受三个参数：
             * topic--告诉kafkaProducer消息发送到哪个topic;
             * key--告诉kafkaProducer，所发送消息的key值，注意：key值类型需与前面设置的key.serializer值匹配
             * value--告诉kafkaProducer，所发送消息的value值，即消息内容。注意：value值类型需与前面设置的value.serializer值匹配
             */
            ProducerRecord<String, String> record = new ProducerRecord<String, String>(topic, routeKey, msg);

            try {
                //发送前面创建的消息对象ProducerRecord到kafka集群
                //发送消息过程中可能发送错误，如无法连接kafka集群，所以在这里使用捕获异常代码
                Future<RecordMetadata> future = producer.send(record);

                RecordMetadata recordMetadata = future.get();

                long offset = recordMetadata.offset();

                int partition = recordMetadata.partition();

                System.out.println("the message  offset : "+offset+" ,partition:"+partition+"。");

            } catch (Throwable throwable) {

                throwable.printStackTrace();

                //关闭kafkaProducer对象
                this.producer.close();

                instanceFactoryMap.remove(this.kafkaBrokers);
            }
        }
    }

    public static void main(String[] args) throws Exception {
        String topic = "order_test";
        String routeKey = null;
        String msg = "hello world";
        KafkaProducerSynSendProxy.getInstance("localhost:9092,localhost:9093,localhost:9094").sendMsg(topic, routeKey, msg);
    }
}
