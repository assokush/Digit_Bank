package com.digitata.app.application.model;

import lombok.Data;

import javax.persistence.*;

/**
 * Created by user on 7/8/2021.
 */
@Data
@Entity
public class Money {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    private  AccountTypes accountTypes;
    private  double amount;
    private String transactionType;

   // public static final Money ZERO = new Money(Account.,0);

    public Money(AccountTypes accountTypes, double amount) {
        this.accountTypes = accountTypes;
        this.amount = amount;
    }

    public Money() {
    }

    public Money addMoney(final Money money){

        if(accountTypes !=money.getAccountTypes()){
            throw new IllegalArgumentException();
        }
        return new Money(accountTypes ,money.getAmount());
    }

    public Money minusMoney(final Money money){
        if(accountTypes != money.getAccountTypes() || amount < money.getAmount()){
            throw new IllegalArgumentException();
        }
        return new Money(accountTypes ,money.getAmount());
    }


}

