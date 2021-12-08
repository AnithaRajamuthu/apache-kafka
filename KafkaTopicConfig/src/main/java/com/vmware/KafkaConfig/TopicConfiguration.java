package com.vmware.KafkaConfig;
/**
 * Author : Anitha Rajamuthu
 * Date: 24 Sep 2021
 * Description: Configuratipn project to create kafka topic
 */
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class TopicConfiguration {
	 @Value(value = "${kafka.producer.bootstrap-servers}")
	    private String bootstrapAddress;
	 
	    @Value(value = "${general.topic.name}")
	    private String topicName;
	 
	    @Value(value = "${employee.topic.name}")
	    private String empTopicName;
	 
	    @Value(value = "${customer.topic.name}")
	    private String customerTopicName;
	 
	    @Value(value = "${order.topic.name}")
	    private String orderTopicName;
	 
	    
	    @Value(value = "${anifirst.topic.name}")
	    private String firstTopicName;
	    
	    @Value(value = "${anisecond.topic.name}")
	    private String secondTopicName;
	    
	    @Value(value = "${animulti.topic.name}")
	    private String multiTopicName;
    
	    //ANI_First_Topic
	    @Bean
	    public NewTopic generalTopic() {
	        return TopicBuilder.name(topicName)
	                  .partitions(1)
	                  .replicas(1)
	                  .build();
	    } 
	    @Bean
	    public NewTopic userTopic() {
	        return TopicBuilder.name(empTopicName)
	                  .partitions(1)
	                  .replicas(1)
	                  .build();
	    }    
	    @Bean
	    public NewTopic firstTopic() {
	        return TopicBuilder.name(firstTopicName)
	                  .partitions(1)
	                  .replicas(1)
	                  .build();
	    } 
	    @Bean
	    public NewTopic secondTopic() {
	        return TopicBuilder.name(secondTopicName)
	                  .partitions(1)
	                  .replicas(1)
	                  .build();
	    }	    
	    @Bean
	    public NewTopic multiTopic() {
	        return TopicBuilder.name(multiTopicName)
	                  .partitions(2)
	                  .replicas(1)
	                  .build();
	    }    
	    @Bean
	    public NewTopic customerTopic() {
	        return TopicBuilder.name(customerTopicName)
	                  .partitions(3)
	                  .replicas(1)
	                  .build();
	    }	    
	    @Bean
	    public NewTopic orderTopic() {
	        return TopicBuilder.name(orderTopicName)
	                  .partitions(3)
	                  .replicas(1)
	                  .build();
	    }
}
