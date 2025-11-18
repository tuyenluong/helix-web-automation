package helix;

import org.openqa.selenium.WebDriver;

public interface ITestSession {

    void setWebDriver(WebDriver webDriver);

    WebDriver getWebDriver();

    void setTestDataManagement(TestDataManagement testDataManagement);

    TestDataManagement getTestDataManagement();
}
