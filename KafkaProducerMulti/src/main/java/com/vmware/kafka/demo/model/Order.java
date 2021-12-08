package com.vmware.kafka.demo.model;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Order Bean
 */
import lombok.Data;

public @Data class Order {
	private String orderId;
	private String orderHeaderId;
}
