package com.ccd.backend.framework.exception;

public class MyException extends RuntimeException {

    public MyException(){

    }
    public MyException(String message){
        super(message);
    }
}
