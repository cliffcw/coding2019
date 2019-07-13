package main.com.chenwan.coding2019.utils.kafkaclient.kafkaconsumer;

import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * @program: base 个人demo
 * 通过向消费者组中添加消费者 consumer，提高对某个 topic 中消息的读取速度。由此我们可以得出：如果一个 topic 接收的消息量很大，那么最好多创建几个 partition，以提高消息的吞吐量。
 *
 * 如果一些应用场景允许接收重复消息，那么可以使用至少一次语义；
 * 如果一些应用场景不允许接收重复消息，允许少量消息的丢失，那么可以使用最多一次语义；
 * 如果一些场景既不允许接收重复消息，也不允许消息的丢失，此时要使用恰好一次语义。恰好一次（有且仅有一次）--最难实现的一种语义。真实项目场景中，常用的有两种方式来实现恰好一次语义：
 * 将消息内容和消息 offset 都保存在数据库中，利用数据库的事务特性来保证消息恰好被消费一次；此时读取消息时，要先从数据库中获取 offset，从获取的 offset 指定位置读取消息；
 * 另一种方式是使用至少一次语义来实现恰好一次，方法是保证消息的消费具有幂等性--即消费一次和消费多次，业务结果是一样的。这种实现方式较简单。
 *
 * @description:
 * @author: cliffcw
 * @create: 2019-07-12 10:54
 */
public class KafkaConsumerDemoProxy {

    private static Map<String, KafkaConsumerDemoProxy> instanceFactoryMap;
    private String kafkaBrokers;
    private String topic;
    private String group;
    private Map<String, Integer> topicMap;

    private KafkaConsumerDemoProxy(String kafkaBrokers, String topic, String group, Integer fetchThreadNum, Long consumerInterval) {
        this.kafkaBrokers = kafkaBrokers;
        this.topic = topic;
        this.group = group;

        topicMap.put(topic, 1);

        KafkaConsumer<String, byte[]> stringKafkaConsumer = new KafkaConsumer<String, byte[]>(initConsumerProxyProperties(kafkaBrokers, group));

        stringKafkaConsumer.subscribe(Arrays.asList(topic));

    }

    /** * @Description:  初始化Consumer配置
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    private static Properties initConsumerProxyProperties (String kafkaBrokers, String groupId) {
        Properties properties = new Properties();

        //可以是broker的一个子集 建议列表中至少包含两个 brokers，因为这样即使一个 broker 连接不上，可以连接另一个 broker，从而提高程序的健壮性。
        properties.put("bootstrap.servers", kafkaBrokers);

        //组id
        properties.put("group.id", groupId);

        //key 反序列化器
        properties.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");

        //value 反序列化器
        properties.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");


        return properties;
    }

    /** * @Description:  获取ComsumerProxy实例
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    public static synchronized KafkaConsumerDemoProxy getInstance(String kafkaBrokers, String topic, String group, Integer fetchThreadNum, Long consumerInterval) throws Exception {
        String key = kafkaBrokers + ":" + topic + ":" + group;

        if (instanceFactoryMap == null) {

            instanceFactoryMap = new HashMap<String, KafkaConsumerDemoProxy>();

            KafkaConsumerDemoProxy kafkaConsumerProxy = new KafkaConsumerDemoProxy(kafkaBrokers, topic, group, fetchThreadNum, consumerInterval);

            instanceFactoryMap.put(key, kafkaConsumerProxy);

            return kafkaConsumerProxy;

        } else if (instanceFactoryMap.get(key) == null) {

            KafkaConsumerDemoProxy kafkaConsumerProxy = new KafkaConsumerDemoProxy(kafkaBrokers, topic, group, fetchThreadNum, consumerInterval);

            instanceFactoryMap.put(key, kafkaConsumerProxy);

            return kafkaConsumerProxy;

        } else {

            return instanceFactoryMap.get(key);
        }
    }



}
