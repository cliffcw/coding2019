package main.com.chenwan.coding2019.utils.kafkaclient.kafkaproducer;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.record.InvalidRecordException;
import org.apache.kafka.common.utils.Utils;

import java.util.List;
import java.util.Map;

/**
 * @program: base
 * @description: 自定义分区器
 * @author: cliffcw
 * @create: 2019-07-12 09:48
 */
public class DefaultPartitioner implements Partitioner {


    /** * @Description:  自定义分区器
     * @Param:
     * @return:
     * @Author: cliffcw
     * @Date:
     */
    @Override
    public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {

        List<PartitionInfo> partitions = cluster.availablePartitionsForTopic(topic);

        int numPartitions = partitions.size();

        /*
         *由于我按key分区，在这里我规定：key值不允许为null。在实际项目中，key为null的消息*，可以发送到同一个分区。
         */
        if (keyBytes == null) {

            throw new InvalidRecordException("key can not be null");
        }

        if ("1".equals(key)) {

            //如果消息的key值为1，那么此消息发送到第二个分区(分区编号从0开始)
            return 1;
        }

        // Utils.murmur2 把字节数组生成32位的 hash
        // hash 生成出来有可能是负数，需要abs绝对值一下
        // 如果消息的key值不为1，那么使用hash值取模，确定分区。
        return (Math.abs(Utils.murmur2(keyBytes))) % (numPartitions);

    }

    @Override
    public void close() {
        // TODO: 2019-07-12

    }

    @Override
    public void configure(Map<String, ?> configs) {
        // TODO: 2019-07-12
    }
}
