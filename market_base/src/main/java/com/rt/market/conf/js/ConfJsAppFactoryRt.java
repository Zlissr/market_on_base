/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.rt.market.conf.js;

import com.rt.config.js.ConfJsApp;
import com.rt.config.js.ConfJsAppFactory_I;
import com.rt.config.js.ConfJsDbFactory_I;
import java.util.HashMap;

/**
 *
 * @author vlitenko
 */
public class ConfJsAppFactoryRt implements ConfJsAppFactory_I
{
    private static final ConfJsAppFactoryRt instance = new ConfJsAppFactoryRt();

    public static ConfJsAppFactoryRt getInstance() {
        return instance;
    }
    
    @Override
    public ConfJsApp newObj(HashMap<String, ConfJsDbFactory_I> factoriesDb) {
        return new ConfJsAppMarket();
    }
    
}
