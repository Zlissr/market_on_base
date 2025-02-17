/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt;

import java.util.Random;

/**
 * @author vlitenko
 */
public class ExceptDto {
    private static final Random random = new Random(System.currentTimeMillis());
    protected final String code;
    protected final String extendedMessage;
    protected final Long id;


    public ExceptDto(String errorCode, String message, String extendedMessage) {
        this.code = errorCode;
        this.extendedMessage = extendedMessage;
        this.id = Math.abs(random.nextLong());
    }

    public String getMessage4Support(Exception exception) {
        String sCause = null;
        if (exception.getCause() != null)
            if (exception.getCause() instanceof Except_I)
                sCause = ((Except_I) exception.getCause()).getMessage4Support();
            else
                sCause = exception.getCause().getMessage();

        return String.format("Ed%d | %s | %s | cause: [%s]", id, code, extendedMessage
                , ((sCause != null) ? sCause : ""));
    }

    public String getMessage4User(Exception exception) {
        return String.format("Ed%d | %s", id, exception.getMessage());
    }

    public Long getId() {
        return id;
    }

    public String getCodeId() {
        return String.format("Ed%d", id);
    }
    
    public String getErrorCode(){
        return code;
    }
    
    public String getMessage4Monitor(Exception p_xEx) {
        return String.format("Ed%d | %s | %s", id, code, p_xEx.getMessage());
    }

}
