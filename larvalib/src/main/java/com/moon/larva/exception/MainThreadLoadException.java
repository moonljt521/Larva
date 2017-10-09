package com.moon.larva.exception;

/**
 * author: moon
 * created on: 17/10/2 上午11:21
 * description:
 */
public class MainThreadLoadException extends RuntimeException {

    private String msg;

    public MainThreadLoadException(String msg){
        this.msg = msg ;
    }
}
