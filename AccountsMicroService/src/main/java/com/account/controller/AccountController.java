package com.account.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.account.constants.AccountConstants;
import com.account.dto.AccountsDto;
import com.account.dto.CustomerDto;
import com.account.dto.ErrorResponseDto;
import com.account.dto.ResponseDto;
import com.account.exception.CustomerAlreadyExistsException;
import com.account.exception.ResourceNotFound;
import com.account.service.AccountService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Pattern;

@RestController
@RequestMapping(path="/api/account",produces = {MediaType.APPLICATION_JSON_VALUE})
@Validated
public class AccountController {
	
	@Autowired
	private AccountService service;
	
	
	@Value("${build.version}")
	private String buildVersion;
	
	
	
	@Autowired
	private Environment environment;
	
	
	@PostMapping("/create")
	public ResponseEntity<ResponseDto> createAccount(@Valid @RequestBody CustomerDto customerDto) throws CustomerAlreadyExistsException{
		service.createCustomer(customerDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(new ResponseDto(AccountConstants.STATUS_201,AccountConstants.MESSAGE_201));
		
	}
	@GetMapping("/get")
	public ResponseEntity<CustomerDto> fetchAccountDetails(@RequestParam  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits") String mobileNumber) throws ResourceNotFound{
		CustomerDto res= service.fetchAccount(mobileNumber);
		return ResponseEntity.status(HttpStatus.OK).body(res);
		
	}
	
	@PutMapping("/update")
    public ResponseEntity<ResponseDto> updateAccountDetails(@Valid @RequestBody CustomerDto customerDto) throws ResourceNotFound {
        boolean isUpdated = service.updateAccount(customerDto);
        if(isUpdated) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_UPDATE));
        }
    }
	
	@DeleteMapping("/delete")
    public ResponseEntity<ResponseDto> deleteAccountDetails( @RequestParam  @Pattern(regexp = "(^$|[0-9]{10})", message = "Mobile number must be 10 digits")String mobileNumber) throws ResourceNotFound {
        boolean isDeleted = service.deleteAccount(mobileNumber);
        if(isDeleted) {
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(new ResponseDto(AccountConstants.STATUS_200, AccountConstants.MESSAGE_200));
        }else{
            return ResponseEntity
                    .status(HttpStatus.EXPECTATION_FAILED)
                    .body(new ResponseDto(AccountConstants.STATUS_417, AccountConstants.MESSAGE_417_DELETE));
        }
    }
	
	
	@Operation(
			
		summary = "get Build Information",
		description = "REST API to get the information about the build information and version"
	)
	@ApiResponses({
		
	
			
			@ApiResponse(
					responseCode = "200",
					description = "Http Status ok"
					
			),
			@ApiResponse(
					responseCode = "500",
					description = "Http Status Internal Server Error",
					content = @Content(
								schema = @Schema(implementation = ErrorResponseDto.class)
							)
					
			)
			
	}
	)
	
	@GetMapping("/build-info")
	public ResponseEntity<String> getBuildInfo(){
//		String res= environment.getProperty("buildVersion");
//		System.out.println(res);
		String res= environment.getProperty("build.version");
		return ResponseEntity.status(HttpStatus.OK).body(res);
	}
	
	
	@Operation(
			
			summary = "get Build Version Information",
			description = "REST API to get the information about the build Version  information"
		)
		@ApiResponses({
			
		
				
				@ApiResponse(
						responseCode = "200",
						description = "Http Status ok"
						
				),
				@ApiResponse(
						responseCode = "500",
						description = "Http Status Internal Server Error",
						content = @Content(
									schema = @Schema(implementation = ErrorResponseDto.class)
								)
						
				)
				
		}
		)
	
	@GetMapping("/build-version")
	public ResponseEntity<String> getBuildVersion(){
		return ResponseEntity.status(HttpStatus.OK).body(environment.getProperty("JAVA_HOME"));
	}

}
