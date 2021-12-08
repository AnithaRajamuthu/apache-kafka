package com.vmware.kafka.demo.controller;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Kafka Producer Controller service
 */
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vmware.kafka.demo.model.Customer;
import com.vmware.kafka.demo.model.Employee;
import com.vmware.kafka.demo.model.Eventdata;
import com.vmware.kafka.demo.model.Order;
import com.vmware.kafka.demo.model.User;
import com.vmware.kafka.demo.service.KafKaProducerService;

@RestController
@RequestMapping(value = "/kafka")
public class KafkaProducerController {
	private final KafKaProducerService producerService;

	@Autowired
	public KafkaProducerController(KafKaProducerService producerService) {
		this.producerService = producerService;
	}

	@PostMapping(value = "/publish")
	public void sendMessageToKafkaTopic(@RequestParam("message") String message) {
		this.producerService.sendMessage(message);
	}
	
	@PostMapping(value = "/createUser")
	public void sendMessageToKafkaTopic(
			@RequestParam("userId") long userId, 
			@RequestParam("firstName") String firstName,
			@RequestParam("lastName") String lastName) {
		
		User user = new User();
		user.setUserId(userId);
		user.setFirstName(firstName);
		user.setLastName(lastName);
		
		this.producerService.saveCreateUserLog(user);
	}
	
	@PostMapping(value = "/multiProducer")
    public void sendMessageToKafkaTopic(@RequestBody Employee message) {
		this.producerService.produceMessage(message);
    }
	
	@PostMapping(value = "/customerProducer")
    public void sendMessageToKafkaTopic(@RequestBody Customer message) {
		this.producerService.produceCustomerMessage(message);
    }
	
	@PostMapping(value = "/eventProducer")
    public String sendMessageToKafkaTopic(@RequestBody Eventdata message) {
		this.producerService.produceEventMessage(message);
		return "Event Produced Successfully";
    }
	
	/*@PostMapping(value = "/orderProducer")
    public void sendMessageToKafkaTopic(@RequestBody Order message) {
		this.producerService.produceOrderMessage(message);
    }*/
	
}