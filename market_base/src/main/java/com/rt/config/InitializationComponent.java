/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.config;


import com.rt.config.sequence.ServiceIdGenPostgre;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author Marina
 */
@Component
public class InitializationComponent {

    private final ServiceIdGenPostgre serviceId;
    @Autowired
    public InitializationComponent(ServiceIdGenPostgre serviceId) {
        this.serviceId = serviceId;
    }

    @PostConstruct
    public void initialize() throws InterruptedException {

    }
}
