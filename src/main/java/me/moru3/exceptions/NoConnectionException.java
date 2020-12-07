package me.moru3.exceptions;

public class NoConnectionException extends Exception {
    private static final long serialVersionUID = 1L;
    public NoConnectionException(String msg) {
        super(msg);
    }
}
