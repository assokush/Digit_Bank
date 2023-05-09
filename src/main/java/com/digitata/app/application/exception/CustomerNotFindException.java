package com.digitata.app.application.exception;

/**
 * Created by user on 7/8/2021.
 */
public class CustomerNotFindException extends RuntimeException {

    public CustomerNotFindException(String userName) {
        super("user with User Name "+userName+"not found");
    }
}
