package com.digitata.app.application.mapper;

import com.digitata.app.application.dto.CustomerDTO;
import com.digitata.app.application.model.Customer;

/**
 * Created by user on 7/8/2021.
 */
public class CustomerMapper {


    public static CustomerDTO toCustomer(Customer entity) {

        if (entity == null) return null;

        CustomerDTO customer = new CustomerDTO();
        customer.setUsername(entity.getUsername());
        customer.setPassword(entity.getPassword());
        customer.setFirstName(entity.getFirstName());
        customer.setSurname(entity.getSurname());
        customer.setEmail(entity.getEmail());
        customer.setPhoneNumber(entity.getPhoneNumber());

        return customer;
    }

    public static Customer toCustomerEntity(CustomerDTO customer) {

        if (customer == null) return null;

        Customer entity = new Customer();
        entity.setUsername(customer.getUsername());
        entity.setPassword(customer.getPassword());
        entity.setFirstName(customer.getFirstName());
        entity.setSurname(customer.getSurname());
        entity.setEmail(customer.getEmail());
        entity.setPhoneNumber(customer.getPhoneNumber());

        return entity;
    }






    public static Customer toCustomer(CustomerDTO customerDTO, Customer customer) {

        if (customerDTO == null) return null;

        //Customer entity = new Customer();
        customer.setUsername(customerDTO.getUsername());
        customer.setPassword(customerDTO.getPassword());
        customer.setFirstName(customerDTO.getFirstName());
        customer.setSurname(customerDTO.getSurname());
        customer.setEmail(customerDTO.getEmail());
        customer.setPhoneNumber(customerDTO.getPhoneNumber());
        customer.setAccount(customerDTO.getAccount());

        return customer;
    }
}
