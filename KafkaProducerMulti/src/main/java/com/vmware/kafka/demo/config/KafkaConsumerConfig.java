package com.vmware.kafka.demo.config;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Consumer config
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.support.serializer.JsonDeserializer;

import com.vmware.kafka.demo.model.Employee;
import com.vmware.kafka.demo.model.User;

@Configuration
public class KafkaConsumerConfig 
{
	@Value(value = "${kafka.bootstrapAddress}")
	private String bootstrapAddress;

	@Value(value = "${general.topic.group.id}")
	private String groupId;

	@Value(value = "${user.topic.group.id}")
	private String userGroupId;
	
	@Value(value = "${emp.topic.group.id}")
	private String empGroupId;
	
	//@Value(value = "${emp.topic.group2.id}")
	//private String empGroupId2;

	// 1. Consume string data from Kafka

	@Bean
	public ConsumerFactory<String, String> consumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
		props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, 
				StringDeserializer.class);
		props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, 
				StringDeserializer.class);
		props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, 
				"false");
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new DefaultKafkaConsumerFactory<>(props);
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, String> 
									kafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, String> factory 
			= new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(consumerFactory());
		return factory;
	}

	// 2. Consume user objects from Kafka

	public ConsumerFactory<String, User> userConsumerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ConsumerConfig.GROUP_ID_CONFIG, userGroupId);
		props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
		return new DefaultKafkaConsumerFactory<>(props, 
				new StringDeserializer(), 
				new JsonDeserializer<>(User.class));
	}

	@Bean
	public ConcurrentKafkaListenerContainerFactory<String, User> 
									userKafkaListenerContainerFactory() {
		ConcurrentKafkaListenerContainerFactory<String, User> factory 
			= new ConcurrentKafkaListenerContainerFactory<>();
		factory.setConsumerFactory(userConsumerFactory());
		return factory;
	}
	
	
	// 3. Consume employee objects from Kafka

		public ConsumerFactory<String, Employee> empConsumerFactory() {
			Map<String, Object> props = new HashMap<>();
			props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
			props.put(ConsumerConfig.GROUP_ID_CONFIG, empGroupId);
			props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
			return new DefaultKafkaConsumerFactory<>(props, 
					new StringDeserializer(), 
					new JsonDeserializer<>(Employee.class));
		}
		/***
		public ConsumerFactory<String, Employee> empConsumerFactory2() {
			Map<String, Object> props = new HashMap<>();
			props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
			props.put(ConsumerConfig.GROUP_ID_CONFIG, empGroupId2);
			props.put(JsonDeserializer.TRUSTED_PACKAGES, "*");
			return new DefaultKafkaConsumerFactory<>(props, 
					new StringDeserializer(), 
					new JsonDeserializer<>(Employee.class));
		}**/
	
		@Bean
		public ConcurrentKafkaListenerContainerFactory<String, Employee> 
										empKafkaListenerContainerFactory() {
			ConcurrentKafkaListenerContainerFactory<String, Employee> factory 
				= new ConcurrentKafkaListenerContainerFactory<>();
			factory.setConsumerFactory(empConsumerFactory());
			return factory;
		}
}
