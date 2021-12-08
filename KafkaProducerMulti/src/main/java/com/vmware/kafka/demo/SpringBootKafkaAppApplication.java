package com.vmware.kafka.demo;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Kafka producer and consumer group demo
 */
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.EnableKafka;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@EnableSwagger2
@EnableKafka
@SpringBootApplication
public class SpringBootKafkaAppApplication {

	public static void main(String[] args) 
	{
		SpringApplication.run(SpringBootKafkaAppApplication.class, args);
	}
}
