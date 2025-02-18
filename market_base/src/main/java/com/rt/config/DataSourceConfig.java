package com.rt.config;

import com.rt.market.conf.js.ConfJsMarket;
import com.zaxxer.hikari.HikariConfigMXBean;
import com.zaxxer.hikari.HikariDataSource;
import com.zaxxer.hikari.HikariPoolMXBean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DataSourceConfig {
    private HikariDataSource ds;

    public DataSourceConfig(HikariDataSource ds) {
        this.ds = ds;
        updateUsingConfig();
    }

        public void updateUsingConfig() {
        HikariConfigMXBean config = ds.getHikariConfigMXBean();
        HikariPoolMXBean pool = ds.getHikariPoolMXBean();
        int newPool = ConfJsMarket.getInstance().getApp().getHikariPoolMaxSize();

        if (config.getMaximumPoolSize() != newPool) {
            pool.suspendPool(); // Block new connections being leased

            config.setMaximumPoolSize(newPool);

            pool.softEvictConnections(); // Close unused cnxns & mark open ones for disposal
            pool.resumePool(); // Re-enable connections
        }
    }
}
