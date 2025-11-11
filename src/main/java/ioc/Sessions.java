package ioc;

import org.openqa.selenium.WebDriver;

public interface Sessions {

    void setWebDriver(WebDriver webDriver);

    WebDriver getWebDriver();

    void setTestDataManagement(TestDataManagement testDataManagement);

    TestDataManagement getTestDataManagement();
}
