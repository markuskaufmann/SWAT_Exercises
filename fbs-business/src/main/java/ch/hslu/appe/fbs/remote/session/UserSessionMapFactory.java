package ch.hslu.appe.fbs.remote.session;

public final class UserSessionMapFactory {

    private UserSessionMapFactory() {
    }

    public static UserSessionMap createUserSessionMap() {
        return new UserSessionMapImpl();
    }
}
