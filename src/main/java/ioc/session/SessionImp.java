package ioc.session;

import ioc.api.Session;
import ioc.api.TestDataManagement;
import org.openqa.selenium.WebDriver;

public class SessionImp implements Session {

    private WebDriver webDriver;
    private TestDataManagement testDataManagement;


    @Override
    public void setWebDriver(WebDriver webDriver) {
        this.webDriver = webDriver;
    }

    @Override
    public WebDriver getWebDriver() {
        return webDriver;
    }

    @Override
    public void setTestDataManagement(TestDataManagement testDataManagement) {
        this.testDataManagement = testDataManagement;
    }

    @Override
    public TestDataManagement getTestDataManagement() {
        return testDataManagement;
    }
}
