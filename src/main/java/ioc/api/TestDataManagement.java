package ioc.api;

import ioc.data.LocalData;

public interface TestDataManagement {

    void setLocalData(LocalData localData);

    LocalData getLocalData();
}
