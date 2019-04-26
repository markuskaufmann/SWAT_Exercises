package ch.hslu.appe.fbs.remote.service.user;

import ch.hslu.appe.fbs.business.user.UserManager;
import ch.hslu.appe.fbs.common.rmi.UserService;
import ch.hslu.appe.fbs.remote.rmi.RmiClient;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

public final class UserServiceFactory {

    private UserServiceFactory() {
    }

    public static UserService createUserService(final UserSessionMap userSessionMap, final UserManager userManager) {
        return new UserServiceImpl(new RmiClient(), userSessionMap, userManager);
    }
}
