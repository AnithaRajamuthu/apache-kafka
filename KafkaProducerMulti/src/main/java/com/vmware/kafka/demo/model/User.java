package com.vmware.kafka.demo.model;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : User Bean
 */
import lombok.Data;

public @Data  class User 
{
	private long userId;
    private String firstName;
    private String lastName;
    
}
