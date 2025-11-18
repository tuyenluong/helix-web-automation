package helix.constant;

public class AopConstant {

    // match any field-get of a WebDriver anywhere (any package)
    public static final String WEBDRIVER_POINT_CUT =
            "get(org.openqa.selenium.WebDriver *..*) && @annotation(helix.Driver)";

    // match fields annotated with your framework's @Inject (anywhere except ioc package)
    public static final String INJECT_ANNOTATION_PAGE_POINT_CUT =
            "(@annotation(helix.Inject)) && !within(helix..*)";

    // suite listener start/finish - keep as-is (explicit)
    public static final String SESSIONS_ON_START_TEST_LISTENER_POINT_CUT =
            "execution(void helix.listeners.TestListener.onTestStart(..))";
    public static final String SESSIONS_ON_FINISH_TEST_LISTENER_POINT_CUT =
            "execution(void helix.listeners.TestListener.onTestSuccess(..)) || " +
            "execution(void helix.listeners.TestListener.onTestFailure(..)) || " +
            "execution(void helix.listeners.TestListener.onTestSkipped(..)) || " +
            "execution(void helix.listeners.TestListener.onTestFailedButWithinSuccessPercentage(..)) || " +
            "execution(void helix.listeners.TestListener.onTestFailedWithTimeout(..))";


    // match any field-get of your Sessions type anywhere
    public static final String SESSIONS_FIELD_POINT_CUT =
            "get(helix.ITestSession *..*) && @annotation(helix.AnnoTestSession) && !within(helix.listeners..*)";

    // match WebElement fields annotated with @FindBy
    public static final String FIND_BY_FIELD_POINT_CUT =
            "get(org.openqa.selenium.WebElement *..*) && @annotation(org.openqa.selenium.support.FindBy)";

}
