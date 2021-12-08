package com.vmware.kafka.demo.config;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Producer config
 */
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import com.vmware.kafka.demo.model.Customer;
import com.vmware.kafka.demo.model.Employee;
import com.vmware.kafka.demo.model.Eventdata;
import com.vmware.kafka.demo.model.Order;
import com.vmware.kafka.demo.model.User;

@Configuration
public class KafkaProducerConfig 
{
	@Value(value = "${kafka.bootstrapAddress}")
    private String bootstrapAddress;
	
	//1. Send string to Kafka
	
	@Bean
	public ProducerFactory<String, String> producerFactory() {
		Map<String, Object> props = new HashMap<>();
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
	    return new DefaultKafkaProducerFactory<>(props);
	}
	 
	@Bean
	public KafkaTemplate<String, String> kafkaTemplate() {
	    return new KafkaTemplate<>(producerFactory());
	}
	
	//2. Send User objects to Kafka
	@Bean
    public ProducerFactory<String, User> userProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, User> userKafkaTemplate() {
        return new KafkaTemplate<>(userProducerFactory());
    }
    
	//3. Send Employee objects to Kafka
	@Bean
    public ProducerFactory<String, Employee> employeeProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }
	  @Bean
	    public KafkaTemplate<String, Employee> employeeKafkaTemplate() {
	        return new KafkaTemplate<>(employeeProducerFactory());
	    }
	
	//4. Send Customer objects to Kafka
	@Bean
    public ProducerFactory<String, Customer> customerProducerFactory() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        //Ack property
        //configProps.put(ProducerConfig.ACKS_CONFIG, "1"); //0,1 or all possible values
        //Buffer message before send to broker
        //configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "3334566777");
        //compress message 
        //configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); //snappy-less cpu ,gzip -ok with saves n/w ,lz4
        //Producer will retry if leader gone done
        //configProps.put(ProducerConfig.RETRIES_CONFIG, "0"); //0,1,2 -wait for 100ms and retry
        //producer will never retry for unrecoverable failure
        //configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "400"); //400ms
        //batch size config
        //configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, "1024"); //In bytes memory size for batch - 16kb - give higher
        //producer waits for this second
        //configProps.put(ProducerConfig.LINGER_MS_CONFIG, "500"); 
        //producer waits for this time for respinse from broker ,timesout if no response
        //configProps.put(ProducerConfig.LINGER_MS_CONFIG, "500"); 
        //Message deliver Semantics - no duplicates -idempotent
        //configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true"); //unique 
      
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    @Bean
    public KafkaTemplate<String, Customer> customerKafkaTemplate() {
        return new KafkaTemplate<>(customerProducerFactory());
    }
    
  //4. Send Order objects to Kafka
  	@Bean
      public ProducerFactory<String, Order> orderProducerFactory() {
          Map<String, Object> configProps = new HashMap<>();
          configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
          configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
          configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
          //Ack property
          //configProps.put(ProducerConfig.ACKS_CONFIG, "1"); //0,1 or all possible values
          //Buffer message before send to broker
          //configProps.put(ProducerConfig.BUFFER_MEMORY_CONFIG, "3334566777");
          //compress message 
          //configProps.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy"); //snappy-less cpu ,gzip -ok with saves n/w ,lz4
          //Producer will retry if leader gone done
          //configProps.put(ProducerConfig.RETRIES_CONFIG, "0"); //0,1,2 -wait for 100ms and retry
          //producer will never retry for unrecoverable failure
          //configProps.put(ProducerConfig.RETRY_BACKOFF_MS_CONFIG, "400"); //400ms
          //batch size config
          //configProps.put(ProducerConfig.BATCH_SIZE_CONFIG, "1024"); //In bytes memory size for batch - 16kb - give higher
          //producer waits for this second
          //configProps.put(ProducerConfig.LINGER_MS_CONFIG, "500"); 
          //producer waits for this time for respinse from broker ,timesout if no response
          //configProps.put(ProducerConfig.LINGER_MS_CONFIG, "500"); 
          //Message deliver Semantics - no duplicates -idempotent
         //configProps.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG,"true"); //unique           
          return new DefaultKafkaProducerFactory<>(configProps);
      }

      @Bean
      public KafkaTemplate<String, Order> orderKafkaTemplate() {
          return new KafkaTemplate<>(orderProducerFactory());
      }
      
      //4 Event kafka
      
  	//3. Send Employee objects to Kafka
  	@Bean
      public ProducerFactory<String, Eventdata> eventProducerFactory() {
          Map<String, Object> configProps = new HashMap<>();
          configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
          configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
          configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
          return new DefaultKafkaProducerFactory<>(configProps);
      }
  	  @Bean
  	    public KafkaTemplate<String, Eventdata> eventKafkaTemplate() {
  	        return new KafkaTemplate<>(eventProducerFactory());
  	    }
  	
}
