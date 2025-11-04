package ioc.api;

import org.openqa.selenium.WebDriver;

public interface Session {

    void setWebDriver(WebDriver webDriver);

    WebDriver getWebDriver();

    void setTestDataManagement(TestDataManagement testDataManagement);

    TestDataManagement getTestDataManagement();
}
