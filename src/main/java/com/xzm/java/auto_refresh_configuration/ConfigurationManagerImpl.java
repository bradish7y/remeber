package com.xzm.java.auto_refresh_configuration;

import java.util.Map;

/**
 * Created by Bradish7Y on 15/10/20.
 */
public class ConfigurationManagerImpl extends AbstractConfigurationManager<String, String> {
    public ConfigurationManagerImpl(String childClassName, int size) {
        super(childClassName, size);
    }

    @Override
    protected void load(Map<String, String> configuration) {

        for (int i = 0; i < 10; i++) {
            int num = (int) (Math.random() * 100);
            configuration.put(i + "", num + "");
        }

    }

}
