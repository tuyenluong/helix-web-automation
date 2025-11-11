package ioc.constant;

public class AopConstant {

    public static final String WEBDRIVER_POINT_CUT = "get(private org.openqa.selenium.WebDriver tests.pages.*.*) || get(private org.openqa.selenium.WebDriver tests.tests.*.*)";
    public static final String INJECT_ANNOTATION_PAGE_POINT_CUT = "( within(tests.pages..*) || within(tests.tests..*) ) && !within(ioc..*) && @annotation(ioc.Inject)";
    public static final String SESSIONS_ON_START_SUITE_LISTENER_POINT_CUT = "execution(void ioc.listeners.SuiteListener.onStart(..))";
    public static final String SESSIONS_ON_FINISH_SUITE_LISTENER_POINT_CUT = "execution(void ioc.listeners.SuiteListener.onFinish(..))";
    public static final String SESSIONS_FIELD_POINT_CUT = "get(private ioc.Sessions tests.*.*.*) || get(private ioc.Sessions ioc.*.*.*)";
    public static final String FIND_BY_FIELD_POINT_CUT = "get(private org.openqa.selenium.WebElement tests.pages.*.*) && @annotation(org.openqa.selenium.support.FindBy)";
}
