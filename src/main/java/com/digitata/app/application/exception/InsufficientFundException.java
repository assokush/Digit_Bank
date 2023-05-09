package com.digitata.app.application.exception;

/**
 * Created by user on 7/11/2021.
 */
public class InsufficientFundException  extends RuntimeException {

    public InsufficientFundException(double trans_type) {
        super("insufficient fund in the account " + trans_type + "   is the account balance");
    }
}