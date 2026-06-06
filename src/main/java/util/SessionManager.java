// File: util/SessionManager.java
package util;

import model.User;

public final class SessionManager {
    private static final SessionManager INSTANCE = new SessionManager();
    private User currentUser;

    private SessionManager() {
    }

    public static SessionManager getInstance() {
        return INSTANCE;
    }

    public User getUser() {
        return currentUser;
    }

    public int getCurrentUserId() {
        return currentUser == null ? 0 : currentUser.getIdUser();
    }

    public void setUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public void logout() {
        this.currentUser = null;
    }
}
