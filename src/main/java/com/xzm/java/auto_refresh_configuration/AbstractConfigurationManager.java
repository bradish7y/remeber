package com.xzm.java.auto_refresh_configuration;

import com.google.common.base.Preconditions;
import com.google.common.base.Stopwatch;
import com.google.common.collect.Maps;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Created by Bradish7Y on 15/10/20.
 * Dependent on com.google.guava
 */
public abstract class AbstractConfigurationManager<T, M> {
    public static final Logger Log = LoggerFactory.getLogger(AbstractConfigurationManager.class.getName());
    private Map<T, M> obj;
    private Map<T, M> tmpObj;
    private Map<T, M> midObj;
    private String childClassName;

    private volatile boolean update = false;

    /**
     * @param childClassName the name of table or config file or parameter name
     * @param size           The size of the Map
     */
    public AbstractConfigurationManager(String childClassName, int size) {
        Preconditions.checkArgument(size > 0, "Illegal size:" + size);

        this.childClassName = childClassName;
        obj = Maps.newHashMapWithExpectedSize(size);
        tmpObj = Maps.newHashMapWithExpectedSize(size);
    }

    /**
     * load data when run at first
     */
    public final void loadData() {
        if (!update) {
            synchronized (this) {
                try {
                    if (!update) {
                        tmpObj.clear();
                        Stopwatch stop = Stopwatch.createStarted();
                        Log.info("Configuration<{}>, Start to update", childClassName);

                        load(tmpObj);

                        Log.info("Configuration<{}>, Update completed, elapsed {}ms", childClassName, stop.elapsed(TimeUnit.MILLISECONDS));

                        update = true;
                    }
                } catch (Exception e) {
                    Log.warn("Configuration<{}>, update exception", childClassName);
                    e.printStackTrace();
                    update = false;
                }

            }
        }
    }


    public final Map<T, M> getObject() {
        if (update) {
            synchronized (this) {
                if (update && tmpObj != null && !tmpObj.isEmpty()) {
                    midObj = obj;
                    obj = tmpObj;
                    tmpObj = midObj;

                    Log.info("Configuration<{}>, Got the latest configuration", childClassName);
                }
                update = false;
            }
        }

        return obj;
    }

    /**
     * return new Map<T, M> it contains all configuration, if the size of Map is 0 or Map is equals to null,
     * getObject method can't be updated to new configuration
     * <p/>
     * <p/>
     * Example:
     * public class ConfigurationManagerImpl extends AbstractConfigurationManager<String, String> {
     * public ConfigurationManagerImpl(String childClassName, int size) {
     * super(childClassName, size);
     * }
     *
     * @Override protected void load(Map<String, String> configuration) {
     * <p/>
     * for (int i = 0; i < 10; i++) {
     * int num = (int) (Math.random() * 100);
     * configuration.put(i + "", num + "");
     * }
     * <p/>
     * }
     * <p/>
     * }
     */
    protected abstract void load(Map<T, M> configuration);
}
