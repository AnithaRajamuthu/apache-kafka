package com.kafka.demo;

/***
 * Author : Anitha Rajamuthu
 * Date: 22 Sep 2020
 * Description : kafa demo consumer code
 */

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;


public class ConsumerDemo {

    public static void main(String[] args) {

        Logger logger = LoggerFactory.getLogger(ConsumerDemo.class.getName());

       /** String bootstrapServers = "127.0.0.1:9092";
        String groupId = "my-application";
        String topic = "my_topic"; **/

        // String bootstrapServers = "kafka-dev-app-a1.vmware.com:9092";
    	String bootstrapServers="kafka-stg-poc1.infra-nprd.vmware.com:9092,kafka-stg-poc2.infra-nprd.vmware.com:9092,kafka-stg-poc3.infra-nprd.vmware.com:9092";
       // String bootstrapServers = "127.0.0.1:9092";
         String groupId = "my-application";
        // String topic = "ANI_DEMO_TOPIC"; 
         String topic = "demo-cluster-link"; 
        // create consumer configs
        Properties properties = new Properties();
        properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
        //Auto commit - default true,default time every 5 secs
        properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG,"true");
        properties.setProperty(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG,"7000");
       
        // create consumer
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);

        // subscribe consumer to our topic(s)
        consumer.subscribe(Arrays.asList(topic));
        //Duplicate message if rebalancing occurs
        try {
        // poll for new data
        while(true){
            ConsumerRecords<String, String> records =
                    consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records){
                logger.info("Key: " + record.key() + ", Value: " + record.value());
                logger.info("Partition: " + record.partition() + ", Offset:" + record.offset());
            }
            //commit as soon as processed and retries if there is failure
            consumer.commitSync();
            
            //if commit fails  order will be jumbled - retries are not supported in Async
           // consumer.commitASync();
        }
        }
        finally {
        	consumer.close();
        }

    }
}
