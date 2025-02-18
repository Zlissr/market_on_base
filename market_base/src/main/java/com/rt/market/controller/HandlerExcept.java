/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.market.controller;

import com.rt.Except;
import com.rt.Except4Support;
import com.rt.Except4SupportDocumented;
import com.rt.config.js.ExceptConf;
import com.rt.market.ExceptDb;
import com.rt.market.controller.api.response.ErrorResponse;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.InvalidPropertyException;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.firewall.RequestRejectedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.servlet.ModelAndView;

import java.util.logging.Level;
import java.util.logging.Logger;


/**
 * @author michael
 */
@Order(1)
@ControllerAdvice
public class HandlerExcept {

    /*
    actionService.closeActionWithError(actionId); - пока закомментировано , ищем решение
    */

    private final static Logger logger = Logger.getLogger(HandlerExcept.class.getName());

    public HandlerExcept() {
    }

    private ModelAndView newModelAndView(HttpServletRequest req, Except ex) {
        ModelAndView xRes = new ModelAndView("redirect:" + ErrorContr.DEFAULT_URL_ERROR);

        req.setAttribute(Contr.PARAMETER_ERROR_MESSAGE, ex.getMessage4User());
        req.setAttribute(Contr.PARAMETER_URL, req.getRequestURL());
        xRes.addObject(Contr.PARAMETER_ERROR_MESSAGE, ex.getMessage4User());
        xRes.addObject(Contr.PARAMETER_URL, req.getRequestURL());

        return xRes;
    }

    private ModelAndView newModelAndView(HttpServletRequest req, String message) {
        ModelAndView xRes = new ModelAndView("redirect:" + ErrorContr.DEFAULT_URL_ERROR);

        req.setAttribute(Contr.PARAMETER_ERROR_MESSAGE, message);
        req.setAttribute(Contr.PARAMETER_URL, req.getRequestURL());
        xRes.addObject(Contr.PARAMETER_ERROR_MESSAGE, message);
        xRes.addObject(Contr.PARAMETER_URL, req.getRequestURL());

        return xRes;
    }

    @ExceptionHandler({ExceptConf.class})
    public void onFatal(HttpServletRequest req, ExceptConf ex) {
        logger.severe("[--FATAL--] " + "[Configuration Exception" + "] " + ex.getMessage4Support());
        System.exit(-1);
    }

    @ExceptionHandler({ExceptFormBind.class})
    public ModelAndView onExceptProgramist(HttpServletRequest req, Except ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[ExceptFormBind in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage4Support());
        logger.severe(message);

        return newModelAndView(req, ex);
    }

    @ExceptionHandler({Except4Support.class})
    public ModelAndView onExcept(HttpServletRequest req, Except ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[Exception in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage4Support());
        logger.severe(message);

        return newModelAndView(req, ex);
    }

    @ExceptionHandler({ExceptAccess.class})
    public ModelAndView onAccessDenied(HttpServletRequest req, ExceptAccess ex) {
        return newModelAndView(req, ex);
    }

    /**
     * Catch exceptions, which cannot be transformed to Except
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler({UsernameNotFoundException.class})
    public ModelAndView onExceptKnown(HttpServletRequest req, UsernameNotFoundException ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[UsernameNotFoundException in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage());

        Except4SupportDocumented kRes = new Except4SupportDocumented("ErrKn1", message, ex);
        logger.severe(message);
        return newModelAndView(req, kRes);
    }

    /**
     * Catch unknown exceptions
     *
     * @param req
     * @param ex
     * @return
     */
    @ExceptionHandler(IllegalArgumentException.class)
    public ModelAndView onRequestError(HttpServletRequest req, IllegalArgumentException ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[IllegalArgumentException in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage());

        return newModelAndView(req, "Некорректный ввод");
    }

    @ExceptionHandler({Throwable.class})
    public ModelAndView onUnknown(HttpServletRequest req, Throwable ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[Exception in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage());


        Except4SupportDocumented kEx = new Except4SupportDocumented("ErrUnk4", message, ex);
        ModelAndView xRes = onExceptProgramist(req, kEx);
        logger.log(Level.SEVERE, message, ex);
        return xRes;
    }

    // when call method which not exist
    @ExceptionHandler({HttpRequestMethodNotSupportedException.class})
    public ModelAndView onUnsupportedMethod(HttpServletRequest req, HttpRequestMethodNotSupportedException ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[HttpRequestMethodNotSupportedException in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage());
        logger.severe(message);
        return newModelAndView(req, "Операция не может быть выполнена.");
    }

    // when attempt to hack us
    @ExceptionHandler({RequestRejectedException.class, InvalidPropertyException.class, MultipartException.class})
    public ResponseEntity<ErrorResponse> onPotencialErrorMethod(HttpServletRequest req, RuntimeException ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[Suspicious requests in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage());


        ErrorResponse response = new ErrorResponse(ex.getMessage());
        logger.log(Level.WARNING, message);
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @ExceptionHandler({ExceptDb.class})
    public ModelAndView onExceptDb(HttpServletRequest req, ExceptDb ex) {
        Long actionId = Contr.getActionIdFromReq(req);
        RemoteClientDto kRemote = new RemoteClientDto(req);

        String message = String.format("[Exception in %s]. IP: %s, actionId: %s, message: %s",
                req.getRequestURI(),
                kRemote.getClientIp(),
                (actionId != null) ? actionId.toString() : "N/A",
                ex.getMessage4Support());

        logger.severe(message);

        return newModelAndView(req, ex.getMessage());
    }
}
