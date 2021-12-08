package com.vmware.kafka.demo.service;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.HashSet;
import java.util.Iterator;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Producer Service
 */
import java.util.Map;
import java.util.Set;

import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.clients.consumer.OffsetCommitCallback;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.util.concurrent.ListenableFutureCallback;
import org.springframework.web.client.RestTemplate;

import com.vmware.kafka.demo.model.Customer;
import com.vmware.kafka.demo.model.Employee;
import com.vmware.kafka.demo.model.EventStoreAuditEvent;
import com.vmware.kafka.demo.model.Eventdata;
import com.vmware.kafka.demo.model.Eventaudit;
import com.vmware.kafka.demo.model.Order;
import com.vmware.kafka.demo.model.User;
import java.util.UUID;

@Service
public class KafKaProducerService {
	private static final Logger logger = LoggerFactory.getLogger(KafKaProducerService.class);

	// 1. General topic with string payload

	@Value(value = "${general.topic.name}")
	private String topicName;

	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;

	// 2. Topic with user object payload

	@Value(value = "${user.topic.name}")
	private String userTopicName;

	@Autowired
	private KafkaTemplate<String, User> userKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Customer> customerKafkaTemplate;

	@Autowired
	private KafkaTemplate<String, Eventdata> eventKafkaTemplate;
	
	@Autowired
	private KafkaTemplate<String, Order> orderKafkaTemplate;

	// 3. Topic with employee object payload

	@Value(value = "${emp.topic.name}")
	private String empTopicName;

	@Value(value = "${customer.topic.name}")
	private String customerTopicName;

	@Value(value = "${order.topic.name}")
	private String orderTopicName;
	
	@Value(value = "${event.topic.name}")
	private String eventTopicName;

	@Autowired
	private KafkaTemplate<String, Employee> empKafkaTemplate;
	
	RestTemplate restTemplate = new RestTemplate();
	final String baseUrl = "http://localhost:8881/api/eventaudit/createAuditEvent";
	URI uri;
	ResponseEntity<Customer> response;
	final String baseUrl1 = "http://localhost:8080/v1/hermes/eventdatas";
	RestTemplate eventrestTemplate = new RestTemplate();
	ResponseEntity<Eventdata> eventresponse;

	public void sendMessage(String message) {
		ListenableFuture<SendResult<String, String>> future = this.kafkaTemplate.send(topicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, String>>() {
			@Override
			public void onSuccess(SendResult<String, String> result) {
				logger.info("Sent message: " + message + " with offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Unable to send message : " + message, ex);
			}
		});
	}

	public void saveCreateUserLog(User user) {
		ListenableFuture<SendResult<String, User>> future = this.userKafkaTemplate.send(userTopicName, user);

		future.addCallback(new ListenableFutureCallback<SendResult<String, User>>() {
			@Override
			public void onSuccess(SendResult<String, User> result) {
				logger.info("User created: " + user + " with offset: " + result.getRecordMetadata().offset());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("User created : " + user, ex);
			}
		});
	}

	public void produceMessage(Employee message) {
		ListenableFuture<SendResult<String, Employee>> future = this.empKafkaTemplate.send(empTopicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Employee>>() {
			@Override
			public void onSuccess(SendResult<String, Employee> result) {
				logger.info("Sent employee message: " + message + " with offset: " + result.getRecordMetadata().offset()
						+ "with partition:" + result.getRecordMetadata().partition());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Unable to send message : " + message, ex);
			}
		});
	}

	public void produceCustomerMessage(Customer message) {
		ListenableFuture<SendResult<String, Customer>> future = this.customerKafkaTemplate.send(customerTopicName,
				message);

		try {
			uri = new URI(baseUrl);
			future.addCallback(new ListenableFutureCallback<SendResult<String, Customer>>() {
			

				@Override
				public void onSuccess(SendResult<String, Customer> result) {
					logger.info(
							"Sent Customer message: " + message + " with offset: " + result.getRecordMetadata().offset()
									+ "with partition:" + result.getRecordMetadata().partition() + "topic name:"
									+ result.getRecordMetadata().topic());
					EventStoreAuditEvent event = new EventStoreAuditEvent();
					event.setEventKey("test");
					event.setTopicName(result.getRecordMetadata().topic());
					event.setPartitionNumber(String.valueOf(result.getRecordMetadata().partition())); // int
					event.setOffsetNumber(String.valueOf(result.getRecordMetadata().offset())); // long
					event.setEventData(message.toString());
					event.setSource("kafka-producer");
					event.setStatus("PUBLISHED");
					event.setCreatedBy("Integrations");
					event.setCreatedDate("01-Oct-21");
					response = restTemplate.postForEntity(uri, event, Customer.class);
					logger.info("Response.." + response.toString());
					//success call url from event registry

				}

				@Override
				public void onFailure(Throwable ex) {
					logger.error("Unable to send message : " + message, ex);
					EventStoreAuditEvent event = new EventStoreAuditEvent();
					event.setEventKey("test");
					event.setTopicName("FAILED");
					event.setPartitionNumber("FAILED"); // int
					event.setOffsetNumber("FAILED"); // long
					event.setEventData(message.toString());
					event.setSource("kafka-producer");
					event.setStatus("NOT-PUBLISHED");
					event.setCreatedBy("Integrations");
					event.setCreatedDate("01-Oct-21");
					response = restTemplate.postForEntity(uri, event, Customer.class);
					logger.info("Response.." + response.toString());
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			EventStoreAuditEvent event = new EventStoreAuditEvent();
			event.setEventKey("test");
			event.setTopicName("FAILED");
			event.setPartitionNumber("FAILED"); // int
			event.setOffsetNumber("FAILED"); // long
			event.setEventData(message.toString());
			event.setSource("kafka-producer");
			event.setStatus("NOT-PUBLISHED");
			event.setCreatedBy("Integrations");
			event.setCreatedDate("01-Oct-21");
			response = restTemplate.postForEntity(uri, event, Customer.class);
			logger.info("Exception Handled.." + response.toString());
		}

	}

	public void produceOrderMessage(Order message) {
		ListenableFuture<SendResult<String, Order>> future = this.orderKafkaTemplate.send(orderTopicName, message);

		future.addCallback(new ListenableFutureCallback<SendResult<String, Order>>() {
			@Override
			public void onSuccess(SendResult<String, Order> result) {
				logger.info("Sent Order message: " + message + " with offset: " + result.getRecordMetadata().offset()
						+ "with partition:" + result.getRecordMetadata().partition() + "topic name:"
						+ result.getRecordMetadata().topic() + "date:" + result.getRecordMetadata().toString());
			}

			@Override
			public void onFailure(Throwable ex) {
				logger.error("Unable to send message : " + message, ex);
			}
		});

	}

	public String produceEventMessage(Eventdata message) {
		
		ListenableFuture<SendResult<String, Eventdata>> future = this.eventKafkaTemplate.send(eventTopicName,
				message);

		try {
			uri = new URI(baseUrl1);
			future.addCallback(new ListenableFutureCallback<SendResult<String, Eventdata>>() {
			

				@Override
				public void onSuccess(SendResult<String, Eventdata> result) {
					logger.info(
							"Sent Customer message: " + message + " with offset: " + result.getRecordMetadata().offset()
									+ "with partition:" + result.getRecordMetadata().partition() + "topic name:"
									+ result.getRecordMetadata().topic());
					//Set<Eventaudit> reqEvent=message.getEventaudit();
					//Iterator<Eventaudit> itr = reqEvent.iterator();
					Eventdata event = new Eventdata();
					event.setEventmsg(message.eventmsg);
					
					Eventaudit eventaudit=new Eventaudit();
					eventaudit.setProducer_instance_id("123");
					UUID uuid = UUID.randomUUID();
			        String randomUUIDString = uuid.toString();
					eventaudit.setEvent_transaction_id("Party_"+randomUUIDString);
					eventaudit.setTransaction_entity_key_name("party");
					eventaudit.setConsumer_instance_id("123");
					eventaudit.setTransaction_date(null);
					eventaudit.setEventstatus("Success");
					eventaudit.setStatus_message("Sucessfully Pushed to Topic");
					eventaudit.setEventid("1");
					eventaudit.setEvent_version("1");
					eventaudit.setDomain("cm");
					eventaudit.setSource("oracle EBS");
					eventaudit.setOriginal_event_transaction_id("Party_"+randomUUIDString);
					eventaudit.setCreated_by("Anitha");
					eventaudit.setLast_updated_by(null);
					eventaudit.setLast_update_login(null);
					eventaudit.setAttribute1(null);
					eventaudit.setAttribute2(null);
					eventaudit.setAttribute3(null);
					eventaudit.setAttribute4(null);
					eventaudit.setAttribute5(null);
					eventaudit.setAttribute6(null);
					eventaudit.setAttribute7(null);
					eventaudit.setAttribute8(null);
					eventaudit.setAttribute9(null);
					eventaudit.setAttribute10(null);
					Set<Eventaudit> audit=new HashSet<Eventaudit>();
					audit.add(eventaudit);
					event.setEventaudit(audit);
					//return "Success";
					
					
					/**event.setEventKey("test");
					event.setTopicName(result.getRecordMetadata().topic());
					event.setPartitionNumber(String.valueOf(result.getRecordMetadata().partition())); // int
					event.setOffsetNumber(String.valueOf(result.getRecordMetadata().offset())); // long
					event.setEventData(message.toString());
					event.setSource("kafka-producer");
					event.setStatus("PUBLISHED");
					event.setCreatedBy("Integrations");
					event.setCreatedDate("01-Oct-21");**/
					eventresponse = eventrestTemplate.postForEntity(uri, event, Eventdata.class);
					logger.info("Response.." + response.toString());
					//success call url from event registry

				}

				@Override
				public void onFailure(Throwable ex) {
					logger.error("Unable to send message : " + message, ex);
					Eventdata event = new Eventdata();
					event.setEventmsg(message.eventmsg);
					
					Eventaudit eventaudit=new Eventaudit();
					eventaudit.setProducer_instance_id("123");
					UUID uuid = UUID.randomUUID();
			        String randomUUIDString = uuid.toString();
					eventaudit.setEvent_transaction_id("Party_"+randomUUIDString);
					eventaudit.setTransaction_entity_key_name("party");
					eventaudit.setConsumer_instance_id("123");
					eventaudit.setTransaction_date(null);
					eventaudit.setEventstatus("Error");
					eventaudit.setStatus_message("End Point Unavilable");
					eventaudit.setEventid("1");
					eventaudit.setEvent_version("1");
					eventaudit.setDomain("cm");
					eventaudit.setSource("oracle EBS");
					eventaudit.setOriginal_event_transaction_id("Party_"+randomUUIDString);
					eventaudit.setCreated_by("Anitha");
					eventaudit.setLast_updated_by(null);
					eventaudit.setLast_update_login(null);
					eventaudit.setAttribute1(null);
					eventaudit.setAttribute2(null);
					eventaudit.setAttribute3(null);
					eventaudit.setAttribute4(null);
					eventaudit.setAttribute5(null);
					eventaudit.setAttribute6(null);
					eventaudit.setAttribute7(null);
					eventaudit.setAttribute8(null);
					eventaudit.setAttribute9(null);
					eventaudit.setAttribute10(null);
					Set<Eventaudit> audit=new HashSet<Eventaudit>();
					audit.add(eventaudit);
					event.setEventaudit(audit);
					eventresponse = eventrestTemplate.postForEntity(uri, event, Eventdata.class);
					logger.info("Response.." + response.toString());
				}
			});
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return "Event Produced Successfully!";
	
	}
}
