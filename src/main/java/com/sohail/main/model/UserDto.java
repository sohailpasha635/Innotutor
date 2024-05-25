package com.sohail.main.model;

import javax.validation.constraints.Email;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

	
	private String userName;
	private String userPhone;
	private String userRole;
	@Email(message = "mail not in correct format")
	private String userEmail;
	private String userPassword;
	private String userLine1;
	private String userLine2;
	private String userCountry;
	private String userState;
	private Long userPin;
}
