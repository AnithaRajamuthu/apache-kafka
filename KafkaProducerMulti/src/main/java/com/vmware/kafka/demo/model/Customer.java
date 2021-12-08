package com.vmware.kafka.demo.model;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Customer Bean
 */
import lombok.Data;


public @Data class Customer {
	private String customerId;
	private String customerName;
	private String customerPhoneNumber;
	private String customerLocation;

}
