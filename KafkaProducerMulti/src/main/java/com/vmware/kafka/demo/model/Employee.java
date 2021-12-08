package com.vmware.kafka.demo.model;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Employee Bean
 */
import lombok.Data;

public @Data class Employee {
	private String empId;
	private String empName;
	private String empPhoneNumber;
	private String empLocation;

}
