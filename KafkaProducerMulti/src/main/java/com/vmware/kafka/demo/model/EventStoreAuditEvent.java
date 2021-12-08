package com.vmware.kafka.demo.model;

/****
 * Author : Anitha Rajamuthu
 * Date: 09-0ct-2021
 * Description : Audit Service to create ,update,delete or get all event audit information to and from Mongo DB.
 * This will get triggered from kafka producer code or other source for auditing purpose
 */

import lombok.Data;

public @Data class EventStoreAuditEvent {
   
    private String eventKey;
    private String topicName;
	private String partitionNumber;
	private String offsetNumber;
	private String eventData;
	private String source;
	private String status;
	private String createdDate;
	private String createdBy;
    
}
