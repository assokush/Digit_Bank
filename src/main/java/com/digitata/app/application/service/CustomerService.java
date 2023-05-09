package com.digitata.app.application.service;

import com.digitata.app.application.dto.CustomerDTO;
import com.digitata.app.application.exception.CustomerNotFindException;
import com.digitata.app.application.mapper.CustomerMapper;
import com.digitata.app.application.model.Customer;
import com.digitata.app.application.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;

/**
 * Created by user on 7/8/2021.
 */

@Component
public class CustomerService {


    private CustomerRepository customerRepository;
    private Logger logger = LoggerFactory.getLogger(CustomerService.class);
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void saveCustomer(Customer customer){
        customerRepository.save(customer);
    }

    public List<Customer> findAllCustomer(){
       return customerRepository.findAll();
    }

    public String validateCustomerById(@RequestHeader("Authorization") Long token){
        return customerRepository.findById(token).orElseThrow(() -> new CustomerNotFindException(String.valueOf(token))).getUsername();
    }


    public CustomerDTO updateCustomerById(CustomerDTO customerDTO, Long customerID){

       if (customerDTO == null) {
            throw new CustomerNotFindException(CustomerDTO.class.getSimpleName());
        }
        Customer customer =  customerRepository.findById(customerID).orElseThrow(() -> new CustomerNotFindException(String.valueOf(customerID)));

        logger.info("found "+customer);
        Customer updatedCustomerEntity = CustomerMapper.toCustomer(customerDTO, customer);

        customer.getAccount().setMoneyMap(customerDTO.getAccount().getMoneyMap());
        customerRepository.save(customer);
        logger.info("updated "+customer);

        return CustomerMapper.toCustomer(customer);
    }



}
