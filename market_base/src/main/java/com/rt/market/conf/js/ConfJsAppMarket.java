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
    private String urlBase;
    private String serverType;
    public String actionServiceUrlCreate;
    public String actionServiceUrlClose;
    public String keycloakOpenidUrl;
    public String keycloakPublicKey;
    @JsonIgnore
    public String keycloakClientSecret;
    public String keycloakClientUrl;
    public String domain;
    private int hikariPoolMaxSize;

    private int errorsSize;
    private int errorsInterval;
    private int errorsSleepInterval;
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
            urlBase = getStringRequired(p_xParser, "url_base");
            serverType = getStringRequired(p_xParser, "server_type");
            domain = getStringRequired(p_xParser, "domain");
            hikariPoolMaxSize = getIntRequired(p_xParser, "hikari_pool_max_size");
            // API
            actionServiceUrlCreate = getStringRequired(p_xParser, "action_service_url_create");
            actionServiceUrlClose = getStringRequired(p_xParser, "action_service_url_close");
            //ERRORS
            errorsSize = getIntRequired(p_xParser, "attack_errors_size");
            errorsInterval = getIntRequired(p_xParser, "attack_errors_interval_sec");
            errorsSleepInterval = getIntRequired(p_xParser, "attack_errors_sleep_interval_sec");
            //keycloak
            keycloakOpenidUrl =  getStringRequired(p_xParser, "keycloak_openid_url");
            keycloakPublicKey =  getStringRequired(p_xParser, "keycloak_public_key");
            keycloakClientSecret =  getStringRequired(p_xParser, "keycloak_client_secret");
            keycloakClientUrl =  getStringRequired(p_xParser, "keycloak_client_url");
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

    public String getUrlBase() {
        return urlBase;
    }

    public String getActionServiceUrlCreate() {
        return actionServiceUrlCreate;
    }

    public String getActionServiceUrlClose() {
        return actionServiceUrlClose;
    }

    public String getServerType() {
        return serverType;
    }
    public String getDomain() {
        return domain;
    }

    public int getErrorsSize() {
        return errorsSize;
    }

    public int getErrorsInterval() {
        return errorsInterval;
    }

    public int getErrorsSleepInterval() {
        return errorsSleepInterval;
    }

    public String getKeycloakOpenidUrl() {
        return keycloakOpenidUrl;
    }

    public String getKeycloakPublicKey() {
        return keycloakPublicKey;
    }

    public String getKeycloakClientSecret() {
        return keycloakClientSecret;
    }

    public String getKeycloakClientUrl() {
        return keycloakClientUrl;
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
        return "urlBase=" + urlBase + "\n"
                + "serverType=" + serverType + "\n"
                + "domain=" + domain + "\n"
                + "actionServiceUrlCreate=" + actionServiceUrlCreate + "\n"
                + "actionServiceUrlClose=" + actionServiceUrlClose + "\n"
                + "keycloakOpenidUrl=" + keycloakOpenidUrl + "\n"
                + "keycloakPublicKey=" + keycloakPublicKey + "\n"
                + "keycloakClientUrl=" + keycloakClientUrl + "\n";

    }
}
