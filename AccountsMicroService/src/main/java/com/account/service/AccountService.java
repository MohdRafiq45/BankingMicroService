package com.account.service;

import com.account.dto.CustomerDto;
import com.account.exception.CustomerAlreadyExistsException;
import com.account.exception.ResourceNotFound;


public interface AccountService {

	public void createCustomer(CustomerDto customerDto) throws CustomerAlreadyExistsException ;
	
	public CustomerDto fetchAccount(String mobileNumber) throws ResourceNotFound;

	public boolean updateAccount(CustomerDto customerDto) throws ResourceNotFound;
	
	public boolean deleteAccount(String mobileNumber) throws ResourceNotFound;
}
