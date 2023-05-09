package com.digitata.app.application.controller;

import com.digitata.app.application.dto.CustomerDTO;
import com.digitata.app.application.model.Account;
import com.digitata.app.application.model.AccountTypes;
import com.digitata.app.application.model.Customer;
import com.digitata.app.application.model.Money;
import com.digitata.app.application.service.BalanceService;
import com.digitata.app.application.service.CustomerService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * Created by user on 7/8/2021.
 */
@RestController
@RequestMapping(value = "/customers")
public class CustomerController {


    private CustomerService customerService;
    private final BalanceService balanceService;
    private Logger logger = LoggerFactory.getLogger(CustomerController.class);

    @Autowired
    public CustomerController(CustomerService customerService, BalanceService balanceService) {
        this.customerService = customerService;
        this.balanceService = balanceService;
    }



    //Api register as a customer
    @PostMapping(value = "/register")
    public void registerCustomer(@RequestBody Customer userDto) {
         logger.info("userDto " + "" + userDto.getAccount());
        Customer newcustomer = new Customer(userDto.getUsername(), userDto.getPassword(), userDto.getFirstName(), userDto.getSurname(), userDto.getEmail(), userDto.getPhoneNumber(), userDto.getAccount());

        logger.info("newcustomer " + "" + newcustomer.toString());
        customerService.saveCustomer(newcustomer);
        logger.info("Customer saved " + newcustomer.toString());

    }

    //API to view all Customers
    @GetMapping(value = "/all")
    public List<Customer> getAllCustomers(HttpServletRequest request ){
         return customerService.findAllCustomer();
    }

    @PostMapping(value = "/{id}")
    public String ValidateCustomer(@PathVariable("id") Long id) {
        System.out.println("running this" + id);
        return customerService.validateCustomerById(id);

    }

    //Api to update customer details
    @GetMapping(value = "/update/{customerID}")
    public CustomerDTO updateCustomer(@PathVariable("customerID") Long customerID, @RequestBody CustomerDTO customerDTO) {
        System.out.println("running this" + customerID);
        logger.info("customerDTO " + "" + customerDTO.getAccount());
        return customerService.updateCustomerById(customerDTO, customerID);

    }
   //API to withdraw from Account Type
    @PostMapping("/topup/{token}")
    public void depositMoney(final @RequestBody Money money, final @PathVariable("token") Long token) {
        balanceService.topUpBalance(money, token);
    }

    @PostMapping("/withdraw/{customerID}")
    public void withDrawMoney(final @RequestBody Money money, final @PathVariable Long customerID) {
        balanceService.withDrawFromBalance(money, customerID);
    }

    //API to transfer fund
    @PostMapping(value = "/transfer/{customerIDToPay}/{beneficiary}")
    public void transferFund(@PathVariable("customerIDToPay") long customerIDToPay,@PathVariable("beneficiary") long beneficiary, @RequestBody Money money){
        balanceService.transferFund(customerIDToPay , beneficiary, money );


    }

}