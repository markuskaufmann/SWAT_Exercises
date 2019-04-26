package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.data.user.UserPersistorFactory;

public final class UserManagerFactory {
    private UserManagerFactory() {
    }

    public static UserManager createUserManager() {
        return new UserManagerImpl(UserPersistorFactory.createUserPersistor());
    }
}
