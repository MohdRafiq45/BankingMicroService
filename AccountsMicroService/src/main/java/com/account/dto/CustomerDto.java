package com.account.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class CustomerDto {
	 @NotEmpty(message = "Name can not be a null or empty")
	  @Size(min = 5, max = 30, message = "The length of the customer name should be between 5 and 30")
	  private String name;
	 
	 @NotEmpty(message = "Email address can not be a null or empty")
	 @Email(message = "Email address should be a valid value")
	  private String email;
	    
	 @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")
	 private String mobileNumber;
	 
	  private AccountsDto accountDto;
	public CustomerDto(String name, String email, String mobileNumber) {
		super();
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
	}
	
	public CustomerDto(String name, String email, String mobileNumber, AccountsDto accountDto) {
		super();
		this.name = name;
		this.email = email;
		this.mobileNumber = mobileNumber;
		this.accountDto = accountDto;
	}

	public CustomerDto() {
		super();
		// TODO Auto-generated constructor stub
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getMobileNumber() {
		return mobileNumber;
	}
	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}

	public AccountsDto getAccountDto() {
		return accountDto;
	}

	public void setAccountDto(AccountsDto accountDto) {
		this.accountDto = accountDto;
	}
	  
}
