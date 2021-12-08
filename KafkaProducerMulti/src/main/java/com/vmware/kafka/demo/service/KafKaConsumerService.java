package com.vmware.kafka.demo.service;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : KafkaConsumer Service
 */
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import com.vmware.kafka.demo.model.Employee;
import com.vmware.kafka.demo.model.User;
import com.vmware.kafka.demo.util.SaveOffsetsOnRebalance;

@Service
public class KafKaConsumerService 
{
	private final Logger logger 
		= LoggerFactory.getLogger(KafKaConsumerService.class);
	/**Properties props = new Properties();
	props.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	props.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.StringDeserializer");
	props.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, "org.apache.kafka.common.serialization.IntegerDeserializer");
	private KafkaConsumer<String, String> consumer;
	public KafKaConsumerService(KafkaConsumer<String, String> kafkaConsumer) {
		consumer = kafkaConsumer;
	}**/
	
	@KafkaListener(topics = "${general.topic.name}", 
			groupId = "${general.topic.group.id}")
	public void consume(String message) {
		logger.info(String.format("Message recieved test-log -> %s", message));
	}
	@KafkaListener(topics = "${user.topic.name}", 
			groupId = "${user.topic.group.id}",
			containerFactory = "userKafkaListenerContainerFactory")
	public void consume(User user) {
		logger.info(String.format("User created user-log-> %s", user));
	}
	
	@KafkaListener(topics = "${emp.topic.name}", 
			groupId = "${emp.topic.group.id}",
			containerFactory = "empKafkaListenerContainerFactory")
	    public void consume(Employee emp) {
		logger.info(String.format("Employee created emp-log vmstar-> %s", emp));
	}
	
	 @KafkaListener(topics = "${emp.topic.name}", 
			groupId = "${emp.topic.group2.id}",
			containerFactory = "empKafkaListenerContainerFactory")
	    public void consume2(Employee emp) {
		logger.info(String.format("Employee created emp-log sap-> %s", emp));
	}
}
