package com.rt.market.except;

import com.rt.Except;

public class ExceptQuantity extends Except {

    public ExceptQuantity(String errorCode, String message) {
        super(errorCode, message, message);
    }
}
