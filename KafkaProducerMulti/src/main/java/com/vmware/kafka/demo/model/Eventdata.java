package com.vmware.kafka.demo.model;
/**
 * Author : Anitha Rajamuthu
 * Date : 20-Oct-2021
 * Description : Eventdata entity
 */

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import org.springframework.data.rest.core.annotation.RestResource;
import lombok.Data;

@Entity
@Data 
public class Eventdata {
	 @Id
     @Column(nullable = false,updatable = false)
     @GeneratedValue(strategy = GenerationType.IDENTITY)
     public int eventmsgrefid; 
	 public String  eventmsg;
	 public int getResourceid() {
		 return eventmsgrefid;
	 }
	  @JoinColumn(name = "eventmsgrefid")
      @OneToMany(fetch= FetchType.EAGER, cascade = CascadeType.ALL, orphanRemoval = true)
	  @RestResource(exported=false)
      public Set<Eventaudit> eventaudit;
	
}
