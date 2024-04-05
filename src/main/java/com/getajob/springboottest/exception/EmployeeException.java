package com.getajob.springboottest.exception;

import com.getajob.springboottest.model.Employee;

public class EmployeeException extends RuntimeException{


    public  EmployeeException(String message){
        super(message);
    }

    public EmployeeException(String message, Throwable cause){
        super(message,cause);
    }
}
