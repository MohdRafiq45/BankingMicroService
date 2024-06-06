package com.account.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.account.constants.AccountConstants;
import com.account.dto.AccountsDto;
import com.account.dto.CustomerDto;
import com.account.dto.ResponseDto;
import com.account.entity.Accounts;
import com.account.entity.Customer;
import com.account.exception.CustomerAlreadyExistsException;
import com.account.exception.ResourceNotFound;
import com.account.mapper.AccountsMapper;
import com.account.mapper.CustomerMapper;
import com.account.repository.AccountsRepository;
import com.account.repository.CustomerRepository;

@Service
public class AccountServiceImpl implements AccountService {
	@Autowired
	private AccountsRepository accountRepo;
	
	@Autowired
	private CustomerRepository customerRepo;

	
	private Accounts createNewAccount(Customer customer) {
		
		Accounts res= new Accounts();
		
		res.setCustomerId(customer.getCustomerId());
		long randomAccountNumber=  1000000000L + new Random().nextInt(900000000);
//		
//		res.setCreatedAt(LocalDateTime.now());
//		res.setCreatedBy("Anonymouse");
		
		
		
		res.setAccountNumber(randomAccountNumber);
		res.setAccountType(AccountConstants.SAVINGS);
		res.setBranchAddress(AccountConstants.ADDRESS);
		return res;
		
	}
	
	@Override
	public void createCustomer(CustomerDto customerDto) throws CustomerAlreadyExistsException {
		Optional<Customer> cust= customerRepo.findByMobileNumber(customerDto.getMobileNumber());
		if(cust.isPresent()) {
			throw new CustomerAlreadyExistsException("Customer Already registered with given Mobile Number "+ customerDto.getMobileNumber());
		}
		Customer customer= CustomerMapper.mapToCustomer(customerDto, new Customer());
//		customer.setCreatedAt(LocalDateTime.now());
//		customer.setCreatedBy("Anonymous");
//		customer.setUpdatedAt(LocalDateTime.now());
//		customer.setUpdatedBy("Anonymous");
		Customer savedCustomer=customerRepo.save(customer);
		accountRepo.save(createNewAccount(savedCustomer));
		
		
	}

	@Override
	public CustomerDto fetchAccount(String mobileNumber) throws ResourceNotFound {
		Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(()-> new ResourceNotFound("Customer Not Found"));
		
		
		Accounts accounts= accountRepo.findByCustomerId(customer.getCustomerId()).orElseThrow(()-> new ResourceNotFound("Account Not Found"));
		
		CustomerDto res= CustomerMapper.mapToCustomerDto(customer, new CustomerDto());
		res.setAccountDto(AccountsMapper.mapToAccountsDto(accounts, new AccountsDto()));
		return res;
	}

	@Override
    public boolean updateAccount(CustomerDto customerDto) throws ResourceNotFound {
        boolean isUpdated = false;
        AccountsDto accountsDto = customerDto.getAccountDto();
        if(accountsDto !=null ){
            Accounts accounts = accountRepo.findById(accountsDto.getAccountNumber()).orElseThrow(
                    () -> new ResourceNotFound("Account", "AccountNumber", accountsDto.getAccountNumber().toString())
            );
            
            AccountsMapper.mapToAccounts(accountsDto, accounts);
            accounts = accountRepo.save(accounts);

            Long customerId = accounts.getCustomerId();
            Customer customer = customerRepo.findById(customerId).orElseThrow(
                    () -> new ResourceNotFound("Customer", "CustomerID", customerId.toString())
            );
            CustomerMapper.mapToCustomer(customerDto,customer);
            customerRepo.save(customer);
            isUpdated = true;
        }
        return  isUpdated;
    }

	 @Override
	    public boolean deleteAccount(String mobileNumber) throws ResourceNotFound {
	        Customer customer = customerRepo.findByMobileNumber(mobileNumber).orElseThrow(
	                () -> new ResourceNotFound("Customer", "mobileNumber", mobileNumber)
	        );
	        accountRepo.deleteByCustomerId(customer.getCustomerId());
	        customerRepo.deleteById(customer.getCustomerId());
	        return true;
	    }
	

}
