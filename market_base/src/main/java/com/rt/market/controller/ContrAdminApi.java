/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.market.controller;

import com.rt.config.DataSourceConfig;
import com.rt.config.monitor.MonitorErrorsDto;
import com.rt.config.monitor.ServiceSecurityRequest;
import com.rt.market.conf.js.ConfJsMarket;

import static com.rt.config.monitor.MonitorErrorsContr.STATUS_ERROR;
import static com.rt.config.monitor.MonitorErrorsContr.STATUS_OK;

import com.rt.config.monitor.MonitorErrorsService;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vl.utils.FormatsDate;

/**
 * @author marina
 */
@RequestMapping(Contr.ROUTE_INTERNAL_ADMIN)
@RestController
public class ContrAdminApi extends Contr {

    public static final String URL_CONFIG = "/config";
    public static final String URL_ERROR = "/error";


    private final MonitorErrorsService serviceMonitorErrors;
    private final ServiceSecurityRequest serviceSecurityRequest;
    private final DataSourceConfig dataSourceConfig;

    public ContrAdminApi(MonitorErrorsService serviceHealth, ServiceSecurityRequest serviceSecurityRequest, DataSourceConfig dataSourceConfig) {
        this.serviceMonitorErrors = serviceHealth;
        this.serviceSecurityRequest = serviceSecurityRequest;
        this.dataSourceConfig = dataSourceConfig;
    }

    @GetMapping(URL_ERROR)
    public ResponseEntity<MonitorErrorsDto> getErrors() {
        List<String> errors = serviceMonitorErrors.read();

        return ResponseEntity.ok(formatTextResponse(errors));
    }

    @DeleteMapping(URL_ERROR)
    public ResponseEntity<?> clearErrors() {
        serviceMonitorErrors.clean();

        return ResponseEntity.ok().build();
    }

    @GetMapping(URL_CONFIG)
    public ResponseEntity<?> getConfig() {
        return ResponseEntity.ok(ConfJsMarket.getInstance().getApp());
    }


    @PostMapping(URL_CONFIG)
    public ResponseEntity<?> reloadConfig() {
        ConfJsMarket.getInstance().updateConf();

        //add update config methods here
        serviceSecurityRequest.updateUsingConfig();
        dataSourceConfig.updateUsingConfig();

        return ResponseEntity.ok(ConfJsMarket.getInstance().getApp());
    }

    private MonitorErrorsDto formatTextResponse(List<String> errors) {
        MonitorErrorsDto errorsDto = new MonitorErrorsDto(
                serviceMonitorErrors.getLastFix().format(FormatsDate.DTF_DOT_DATE_TIME),
                errors.isEmpty() ? STATUS_OK : STATUS_ERROR,
                serviceMonitorErrors.getLastMonitor().format(FormatsDate.DTF_DOT_DATE_TIME),
                serviceMonitorErrors.getServerStarted().format(FormatsDate.DTF_DOT_DATE_TIME)
        );

        int count = 1;
        for (String error : errors) {
            errorsDto.addError((count++) + ". " + error);
        }

        return errorsDto;
    }
}
