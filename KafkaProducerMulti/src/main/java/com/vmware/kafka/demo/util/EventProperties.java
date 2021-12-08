package com.vmware.kafka.demo.util;
/****
 * Author : Anitha Rajamuthu
 * Date: 01-0ct-2021
 * Description : Not in use
 */
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

@Primary
@Configuration
@ConfigurationProperties(ignoreUnknownFields = true, prefix = "service")
public class EventProperties {
	private long partitionPollTime;
	private String customerTopicname;

	public String getCustomerTopicname() {
		return customerTopicname;
	}

	public void setCustomerTopicname(String customerTopicname) {
		this.customerTopicname = customerTopicname;
	}

	public long getPartitionPollTime() {
		return partitionPollTime;
	}

	public void setPartitionPollTime(long partitionPollTime) {
		this.partitionPollTime = partitionPollTime;
	}

}
