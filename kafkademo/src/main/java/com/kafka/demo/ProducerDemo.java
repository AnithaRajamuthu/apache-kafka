package com.kafka.demo;

/***
 * Author : Anitha Rajamuthu
 * Date: 22 Sep 2020
 * Description : kafa demo producer code
 */
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class ProducerDemo {

	public static void main(String[] args) {
		//String bootstrapServers="127.0.0.1:9092";
		//String bootstrapServers="kafka-dev-app-a1.vmware.com:9092";
		String bootstrapServers="kafka-stg-poc1.infra-nprd.vmware.com:9092,kafka-stg-poc2.infra-nprd.vmware.com:9092,kafka-stg-poc3.infra-nprd.vmware.com:9092";
		// create Producer properties
		Properties properties=new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
		//properties.setProperty("bootstrap.servers", bootstrapServers);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.ACKS_CONFIG,"1");

		// create the producer
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(properties);

        // create a producer record
       /** ProducerRecord<String, String> record =
                new ProducerRecord<String, String>("my_topic", "Ani!hello world");**/
        //ProducerRecord<String, String> record =
                //new ProducerRecord<String, String>("ANI_DEMO_TOPIC", "Ani!Enterprise hello world");
        
        ProducerRecord<String, String> record =
                new ProducerRecord<String, String>("demo-cluster-link", "Ani!Enterprise hello world");
        System.out.println("Produced Message..to topic ANI_DEMO_TOPIC under Dev instance kafka....Messgae is : Ani!Enterprise hello world");
        // send data - asynchronous
        producer.send(record);

        // flush data
        producer.flush();
        // flush and close producer
        producer.close();
	}

}
