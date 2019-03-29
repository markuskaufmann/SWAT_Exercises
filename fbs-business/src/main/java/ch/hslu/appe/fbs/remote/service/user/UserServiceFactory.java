package ch.hslu.appe.fbs.remote.service.user;

import ch.hslu.appe.fbs.business.user.UserManager;
import ch.hslu.appe.fbs.common.rmi.UserService;

public final class UserServiceFactory {

    private UserServiceFactory() {
    }

    public static UserService createUserService(final UserManager userManager) {
        return new UserServiceImpl(userManager);
    }
}
