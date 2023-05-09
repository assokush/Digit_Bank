package com.digitata.app.application.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Created by user on 7/8/2021.
 */
@Entity
@Data
public class Customer implements Serializable {

        @Id
        @GeneratedValue(strategy = GenerationType.AUTO)
        private long id ;
        private String username;
        private String password;
        private String firstName;
        private String surname;
        private String email;
        private String phoneNumber;

       @OneToOne(cascade = CascadeType.ALL)
       private Account account;


       public Customer() {

        }

        public Customer(String username, String password, String firstName, String surname,String email, String phoneNumber, Account account ) {
            this.username = username;
            this.password = password;
            this.firstName=firstName;
            this.surname=surname;
            this.email=email;
            this.phoneNumber= phoneNumber;
            this.account = account;
        }
    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }



    }

