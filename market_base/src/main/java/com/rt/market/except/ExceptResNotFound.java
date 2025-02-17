package com.rt.market.except;

import com.rt.Except;

public class ExceptResNotFound extends Except {

    public ExceptResNotFound(String errorCode, String message) {
        super(errorCode, message, message);
    }
}
