/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.market.controller;

import com.rt.Except;

/**
 *
 * @author vlitenko
 */
public class ExceptFormBind extends Except
{
    public ExceptFormBind(String errorCode, String message, String extendedMessage, Throwable cause) {
        super(errorCode, message, extendedMessage, cause);
    }

}
