package com.account.dto;

import java.util.List;
import java.util.Map;

import org.springframework.boot.context.properties.ConfigurationProperties;

//sometimes we want our pojo class or dto classes to simply act as a data carriers, which means first
//i will create an object of this dto class and someone can read the data from the object of this dto class, but
//but they should not be able to change
// so whatever fields data that you pass during the object creation, the same values are going to be final
// and anyone can read using the getter methods and there won't be any setter methods



@ConfigurationProperties(prefix = "accounts")
public record AccountsContactInfoDto(String message, Map<String, String> contactDetails, List<String> OnCallSupport) {

}

//Here there won't be any setter methods which means whenever you are using record type classes. you can only 
//initialize the data only once and youu cannot change that and whatever you have provided during the object creation, it is 
//going to  be final

