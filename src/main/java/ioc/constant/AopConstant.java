package ioc.constant;

public class AopConstant {

    // public static final String WEBDRIVER_POINT_CUT = "get(private org.openqa.selenium.WebDriver *.*.*) || get(private org.openqa.selenium.WebDriver *.*.*)";
    // match any field-get of a WebDriver anywhere (any package)
    public static final String WEBDRIVER_POINT_CUT = "get(org.openqa.selenium.WebDriver *..*) && @annotation(ioc.Driver)";

    // public static final String INJECT_ANNOTATION_PAGE_POINT_CUT = "( within(*.*..*) || within(*.*..*) ) && !within(ioc..*) && @annotation(ioc.Inject)";
    // match fields annotated with your framework's @Inject (anywhere except ioc package)
    public static final String INJECT_ANNOTATION_PAGE_POINT_CUT = "(@annotation(ioc.Inject)) && !within(ioc..*)";

    // public static final String SESSIONS_ON_START_SUITE_LISTENER_POINT_CUT = "execution(void ioc.listeners.SuiteListener.onStart(..))";
    // public static final String SESSIONS_ON_FINISH_SUITE_LISTENER_POINT_CUT = "execution(void ioc.listeners.SuiteListener.onFinish(..))";
    // suite listener start/finish - keep as-is (explicit)
    public static final String SESSIONS_ON_START_SUITE_LISTENER_POINT_CUT = "execution(void ioc.listeners.SuiteListener.onStart(..))";
    public static final String SESSIONS_ON_FINISH_SUITE_LISTENER_POINT_CUT = "execution(void ioc.listeners.SuiteListener.onFinish(..))";

    // public static final String SESSIONS_FIELD_POINT_CUT = "get(private ioc.Sessions *.*.*) || get(private ioc.Sessions *.*.*)";
    // match any field-get of your Sessions type anywhere
//    public static final String SESSIONS_FIELD_POINT_CUT = "get(ioc.Sessions *..*) && @annotation(ioc.Session) && !within(ioc.listeners..*)";
    public static final String SESSIONS_FIELD_POINT_CUT = "get(ioc.Sessions *..*) && @annotation(ioc.Session) && !within(ioc.listeners..*)";

    // public static final String FIND_BY_FIELD_POINT_CUT = "get(private org.openqa.selenium.WebElement *.*.*) && @annotation(org.openqa.selenium.support.FindBy)";
    // match WebElement fields annotated with @FindBy
    public static final String FIND_BY_FIELD_POINT_CUT = "get(org.openqa.selenium.WebElement *..*) && @annotation(org.openqa.selenium.support.FindBy)";

}
