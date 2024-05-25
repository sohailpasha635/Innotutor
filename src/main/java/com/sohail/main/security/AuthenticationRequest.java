package com.sohail.main.security;

import java.io.Serializable;

public class AuthenticationRequest implements Serializable {


    private String username;
    private String password;
    private String firstName;
    

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    //need default constructor for JSON Parsing
    public AuthenticationRequest()
    {

    }

    public AuthenticationRequest(String username, String password, String firstName) {
		super();
		this.username = username;
		this.password = password;
		this.firstName = firstName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public AuthenticationRequest(String username, String password) {
        this.setUsername(username);
        this.setPassword(password);
    }
}
