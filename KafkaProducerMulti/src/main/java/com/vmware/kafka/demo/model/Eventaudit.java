package com.vmware.kafka.demo.model;

/**
 * Author : Anitha Rajamuthu
 * Date : 20-Oct-2021
 * Description : Eventaudit entity
 */
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import org.hibernate.annotations.CreationTimestamp;

import lombok.Data;

@Entity
@Data
public class Eventaudit {
	@Id
	@Column(insertable = false, updatable = false)
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public int id;
	public String producer_instance_id;
	public String event_transaction_id;
	public String transaction_entity_key_name;
	public String consumer_instance_id;
	public Date transaction_date;
	public String eventstatus;
	public String status_message;
	public String eventid;
	public String event_version;
	public String eventmsgrefid;
	public String domain;
	public String source;
	public String original_event_transaction_id;
	public String created_by;
	@CreationTimestamp
	@Column(updatable = false, insertable = false)
	public Date creation_date;
	public String last_updated_by;
	public String last_update_login;
	public String attribute1;
	public String attribute2;
	public String attribute3;
	public String attribute4;
	public String attribute5;
	public String attribute6;
	public String attribute7;
	public String attribute8;
	public String attribute9;
	public String attribute10;

	public int getResourceid() {
		return id;
	}

}
