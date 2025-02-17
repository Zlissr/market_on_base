/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.market.conf.js;

import com.rt.config.js.ExceptConf;
import com.rt.config.js.ConfJs;
import com.rt.config.js.ExceptCJsNoObject;
import com.rt.config.js.ExceptCJsUnsupported;
import java.io.FileNotFoundException;

/**
 *
 * @author vlitenko
 */
public class ConfJsMarket extends ConfJs {

    public static final String APP_NAME = "rt_server";
    private static final ConfJsMarket instance = new ConfJsMarket();
    private static final String CONF_FILE_NMAE = "conf_rtmarket_serv.json";

    private ConfJsMarket() {
        super(APP_NAME, ConfJsAppFactoryRt.getInstance());
        try {
            load(CONF_FILE_NMAE, "../" + CONF_FILE_NMAE);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NMAE, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NMAE, ex);
        }
    }

    public void updateConf() {
        try {
            load(CONF_FILE_NMAE, "../" + CONF_FILE_NMAE);
        } catch (FileNotFoundException ex) {
            throw new ExceptConf("ErrConf1", "Can't load project configuration", "Can't find configuration file " + CONF_FILE_NMAE, ex);
        } catch (ExceptCJsUnsupported ex) {
            throw new ExceptConf("ErrConf2", "Can't process project configuration", "Cant't parse configuration file " + CONF_FILE_NMAE, ex);
        }
    }

    public static ConfJsMarket getInstance() {
        return instance;
    }

    public ConfJsAppMarket getApp() {
        try {
            return (ConfJsAppMarket) super.getApp(APP_NAME);
        } catch (ExceptCJsNoObject ex) {
            throw new ExceptConf("ErrConf3", "Can't process project configuration",
                     String.format("Cant't get app %s in file %s", APP_NAME, CONF_FILE_NMAE), ex);
        }
    }

}
