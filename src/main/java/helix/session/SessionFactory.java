package helix.session;

import helix.ITestSession;

import java.util.Objects;

public class SessionFactory {
    private static final ThreadLocal<ITestSession> iTestSessions = new ThreadLocal<>();

    public static ITestSession getSession() {
        return iTestSessions.get();
    }

    public static void setSession(ITestSession iTestSession) {
        iTestSessions.set(iTestSession);
    }

    public static void removeSession(){
        if(Objects.nonNull(iTestSessions.get())){
            iTestSessions.remove();
        }
    }
}
