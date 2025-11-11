package ioc.session;

import ioc.Sessions;

import java.util.Objects;

public class SessionFactory {
    private static final ThreadLocal<Sessions> sessions = new ThreadLocal<>();

    public static Sessions getSession() {
        return sessions.get();
    }

    public static void setSession(Sessions sessions) {
        SessionFactory.sessions.set(sessions);
    }

    public static void removeSession(){
        if(Objects.nonNull(sessions.get())){
            sessions.remove();
        }
    }
}
