package ioc.session;

import ioc.api.Session;

import java.util.Objects;

public class SessionFactory {
    private static final ThreadLocal<Session> sessions = new ThreadLocal<>();

    public static Session getSession() {
        return sessions.get();
    }

    public static void setSession(Session session) {
        sessions.set(session);
    }

    public static void removeSession(){
        if(Objects.nonNull(sessions.get())){
            sessions.remove();
        }
    }
}
