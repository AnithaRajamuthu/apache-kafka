package com.vmware.kafka.demo.util;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Not in Use
 */
import java.util.Arrays;
import java.util.Collection;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SaveOffsetsOnRebalance implements ConsumerRebalanceListener {
	private Consumer<?, ?> consumer;
	private static final Logger LOG = LoggerFactory.getLogger(SaveOffsetsOnRebalance.class);

	public SaveOffsetsOnRebalance(Consumer<?, ?> consumer) {
		this.consumer = consumer;
	}
   // when rebalance is revoked against partition leaved
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		LOG.info(" PartitionsRevoked : " + Arrays.toString(partitions.toArray()));
	}
//whenever new partition assigned
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		LOG.info(" PartitionsAssigned : " + Arrays.toString(partitions.toArray()));
		for (TopicPartition partition : partitions) {
			LOG.info("partition =" + partition);
			try {
				LOG.info("committed Offset =" + consumer.committed(partition).offset() + " consumer position= "
						+ consumer.position(partition) + " for Partition = " + partition.partition());
				consumer.seek(partition, consumer.committed(partition).offset() + 1);
			} catch (Exception e) {
				LOG.error(
						"Exception in onPartitionsAssigned(): SaveOffsetsOnRebalance " + consumer.position(partition));
				consumer.seek(partition, consumer.position(partition));
			}
		}
	}
}
