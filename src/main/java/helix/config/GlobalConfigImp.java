package helix.config;

import helix.GlobalConfig;

public class GlobalConfigImp implements GlobalConfig {

    private Config config;

    @Override
    public void setConfig(Config config) {
        this.config = config;
    }

    @Override
    public Config getConfig() {
        return config;
    }
}
