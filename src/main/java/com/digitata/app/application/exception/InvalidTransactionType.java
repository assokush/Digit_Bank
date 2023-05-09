package com.digitata.app.application.exception;

/**
 * Created by user on 7/11/2021.
 */
public class InvalidTransactionType extends RuntimeException {

    public InvalidTransactionType(String trans_type) {
        super("Transaction Type " + trans_type +  "   not allowed for  the account type");
    }
}