package helix;

import helix.config.Config;

public interface GlobalConfig {

    void setConfig(Config config);

    Config getConfig();
}
