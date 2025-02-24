/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.market.conf.js;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.annotation.JsonNaming;
import com.rt.config.js.ExceptConf;
import com.fasterxml.jackson.databind.JsonNode;
import com.rt.config.js.ConfJsApp;
import com.rt.config.js.ConfJsDb;

/**
 * @author vlitenko
 */
@JsonNaming(PropertyNamingStrategies.SnakeCaseStrategy.class)
public class ConfJsAppMarket extends ConfJsApp {

    private String nameServer;
    private String serverType;
    private int hikariPoolMaxSize;

    private String mailSmtpHost;
    private int mailSmtpPort;
    private String mailSmtpUser;
    private String mailSmtpPass;
    private boolean mailSmtpAuth;
    private boolean mailStartTls;
    private String mailAdmin;
    private String mailFrom;


    public static final String SERVER_TYPE_DEV = "dev";
    public static final String SERVER_TYPE_TEST = "test";
    public static final String SERVER_TYPE_PREPROD = "preprod";
    public static final String SERVER_TYPE_PROD = "prod";

    public ConfJsAppMarket() {
        super(ConfJsDb.knownDb);
    }

    public ConfJsAppMarket(ConfJsApp p_kCopy) {
        super(p_kCopy);
    }

    @Override
    protected void initApp(JsonNode p_xParser) throws ExceptConf {
        try {

            // TECHNICAL
            nameServer = getStringRequired(p_xParser, "name");
            serverType = getStringRequired(p_xParser, "server_type");
            hikariPoolMaxSize = getIntRequired(p_xParser, "hikari_pool_max_size");
            //mail sender
            mailSmtpHost = getStringRequired(p_xParser, "mail_smtp_host");
            mailSmtpUser = getStringRequired(p_xParser, "mail_smtp_user");
            mailSmtpPass = getStringRequired(p_xParser, "mail_smtp_pass");
            mailSmtpAuth = getBooleanRequired(p_xParser, "mail_smtp_auth");
            mailStartTls = getBooleanRequired(p_xParser, "mail_smtp_start_tls");
            mailSmtpPort = getIntRequired(p_xParser, "mail_smtp_port");
            mailFrom = getStringRequired(p_xParser, "mail_from");
            mailAdmin = getStringRequired(p_xParser, "mail_admin");


        } catch (RuntimeException ex) {
            throw new ExceptConf("ErrConfA1", "Can't process project configuration",
                    ex.getMessage(), ex);
        }
    }

    public String getNameServer() {
        return nameServer;
    }

    public String getServerType() {
        return serverType;
    }

    public int getHikariPoolMaxSize() {
        return hikariPoolMaxSize;
    }

    public String getMailSmtpHost() {
        return mailSmtpHost;
    }

    public int getMailSmtpPort() {
        return mailSmtpPort;
    }

    public String getMailSmtpUser() {
        return mailSmtpUser;
    }

    public String getMailSmtpPass() {
        return mailSmtpPass;
    }

    public boolean isMailSmtpAuth() {
        return mailSmtpAuth;
    }

    public boolean isMailStartTls() {
        return mailStartTls;
    }

    public String getMailAdmin() {
        return mailAdmin;
    }

    public String getMailFrom() {
        return mailFrom;
    }

    @Override
    public String toString() {
        return "nameServer='" + nameServer + '\'' +
                ", serverType='" + serverType;
    }
}
