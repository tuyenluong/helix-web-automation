package helix.session;

import helix.ITestSession;
import helix.TestDataManagement;
import org.openqa.selenium.WebDriver;

public class SessionsImp implements ITestSession {

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
