package ioc;

import ioc.config.Config;

public interface GlobalConfig {

    void setConfig(Config config);

    Config getConfig();
}
