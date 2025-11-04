package ioc.api;

import ioc.config.Config;

public interface GlobalConfig {

    void setConfig(Config config);

    Config getConfig();
}
