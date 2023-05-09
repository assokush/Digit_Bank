package com.digitata.app.application.dto;

import com.digitata.app.application.model.Account;
import lombok.Data;

/**
 * Created by user on 7/8/2021.
 */
    @Data
    public class CustomerDTO {

        private String username;
        private String firstName;
        private String surname;
        private String email;
        private String password;
        private String phoneNumber ;
        private Account account;






    }
