package com.digitata.app.application.model;

import lombok.Data;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by user on 7/8/2021.
 */
@Data
@Entity
public class Account  {

    @Id
   @GeneratedValue
   private long id;

   // @OneToOne(targetEntity=Money.class, cascade = CascadeType.ALL)
   //  private AccountTypes accountTypes;

    @ElementCollection
    @MapKeyClass(AccountTypes.class)
    @OneToMany(targetEntity=Money.class, cascade = CascadeType.ALL)

    private Map<AccountTypes,Money> moneyMap = new HashMap<>();

    public Map<AccountTypes,Money>  topUp(final Money money){

        if(moneyMap.get(money.getAmount())==null){
            moneyMap.put(money.getAccountTypes(), money);
        }
       moneyMap.put(money.getAccountTypes(), moneyMap.get(money.getAccountTypes()).addMoney(money));

        return  moneyMap;
        //System.out.println("moneyMap "+moneyMap);
    }

    public  Map<AccountTypes,Money>  withdraw(final Money money){
        final Money moneyInBalance = moneyMap.get(money.getAccountTypes());
        if(moneyInBalance == null){
            throw new IllegalStateException();
        }
         moneyMap.put(money.getAccountTypes(), moneyMap.get(money.getAccountTypes()).minusMoney(money));

        return moneyMap;
    }

    // public Map<Currency,Money> getBalance(){
    //   return moneyMap;
    //}

}
