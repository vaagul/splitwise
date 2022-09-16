package com.atlantis.splitwise.utils.exceptions;

public class InvalidTransactionException extends RuntimeException{
    public InvalidTransactionException(String errorMessage){
        super(errorMessage);
    }
}
