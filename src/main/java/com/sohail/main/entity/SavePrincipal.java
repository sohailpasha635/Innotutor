package com.sohail.main.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@NoArgsConstructor
@Data
public class SavePrincipal {
	 @Id
	   @ GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	// private Long userid;
	private String username;
	private Date loginTime;
	public SavePrincipal(String username, Date loginTime) {
		super();
		this.username = username;
		this.loginTime = loginTime;
	}
	
	

}

