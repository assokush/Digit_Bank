package com.digitata.app.application.service;

import com.digitata.app.application.exception.CustomerNotFindException;
import com.digitata.app.application.exception.InsufficientFundException;
import com.digitata.app.application.exception.InvalidTransactionType;
import com.digitata.app.application.model.AccountTypes;
import com.digitata.app.application.model.Customer;
import com.digitata.app.application.model.Money;
import com.digitata.app.application.repository.CustomerRepository;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by user on 7/11/2021.
 */

@Component
public class BalanceService {

    final private CustomerRepository customerRepository;

    private Logger logger = LoggerFactory.getLogger(BalanceService.class);


    @Autowired
    public BalanceService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


     public void topUpBalance(final Money money, Long cust_d){
         double initial_balance = money.getAmount();
         if ((money.getAccountTypes().toString() == "CURRENT") | (money.getAccountTypes().toString() == "CREDIT_CARD")) {
            Customer customer = findCustomer(cust_d);
            logger.info("Account to be toped up ->" + customer.getAccount());

            if (customer.getAccount().getMoneyMap() != null) {
                logger.info("mortal " + customer.getAccount().getMoneyMap().get("Money"));
                Map<AccountTypes, Money> money_updated = customer.getAccount().getMoneyMap();
                Iterator<Map.Entry<AccountTypes, Money>> itr1 = money_updated.entrySet().iterator();

                while (itr1.hasNext()) {
                    Map.Entry<AccountTypes, Money> entry = itr1.next();
                    initial_balance = entry.getValue().getAmount() + initial_balance;
                }
            }


            Map<AccountTypes, Money> money_updated = customer.getAccount().topUp(money);
            customer.getAccount().getMoneyMap().entrySet().iterator().next().getValue().setAmount(initial_balance);
            customer.getAccount().setMoneyMap(money_updated);
            customerRepository.save(customer);
       }else{
            throw new InvalidTransactionType(money.getTransactionType());

       }
       
     }

    public void withDrawFromBalance(final Money money,Long id){
        if ((money.getAccountTypes().toString() == "CREDIT_CARD")) {
        Customer customer = findCustomer(id);
        double account_balance_to_debit = customer.getAccount().getMoneyMap().entrySet().iterator().next().getValue().getAmount();

        if(account_balance_to_debit <= money.getAmount()){
            System.out.println("insufficient amount  , balance is "+ account_balance_to_debit);
            throw new InsufficientFundException(account_balance_to_debit);
        }

        Map<AccountTypes,Money> money_updated =customer.getAccount().withdraw(money);
        double balance =computeBalance(money_updated, account_balance_to_debit);
        customer.getAccount().getMoneyMap().entrySet().iterator().next().getValue().setAmount(balance);
        customer.getAccount().setMoneyMap(money_updated);

        customerRepository.save(customer);
       }else{
            throw new InvalidTransactionType(money.getTransactionType());
       }
    }

    private Customer findCustomer(Long customerId){
        Customer cust =   customerRepository.findById(customerId).orElseThrow(()->new CustomerNotFindException(String.valueOf(customerId)));
        logger.info("cust found in top up "+cust);
        return cust;
    }

    public double computeBalance(Map<AccountTypes,Money> money_updated, double balance) {
       double initial_balance = balance;
        Iterator<Map.Entry<AccountTypes, Money>> itr1 = money_updated.entrySet().iterator();

        while (itr1.hasNext()) {
            Map.Entry<AccountTypes, Money> entry = itr1.next();
            System.out.println("key " + entry.getKey());
            System.out.println("Value " + entry.getValue().getAmount());
            initial_balance = initial_balance - entry.getValue().getAmount();
        }
        return initial_balance;
    }

    @Transactional
    public void transferFund(long customerIDToPay , long beneficiary, final Money money){
         if ((money.getAccountTypes().toString() == "MORTGAGE_LOAN")) {
            Customer customer_to_debit = findCustomer(customerIDToPay);
            Customer customer_to_credit = findCustomer(beneficiary);

           double account_balance_to_debit = customer_to_debit.getAccount().getMoneyMap().entrySet().iterator().next().getValue().getAmount();
            if( money.getAmount() >= account_balance_to_debit){
               throw new InsufficientFundException(account_balance_to_debit);
            }

              this.withDrawFromBalance(money,customerIDToPay);
              this.topUpBalance(money, beneficiary);

         }else{
           throw new InvalidTransactionType(money.getTransactionType());
       }
    }
}
