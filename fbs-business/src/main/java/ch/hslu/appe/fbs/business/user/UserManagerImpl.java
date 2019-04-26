package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.business.logger.Logger;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.data.user.UserPersistor;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.wrapper.UserWrapper;

import java.util.Optional;

public final class UserManagerImpl implements UserManager {

    private final UserPersistor userPersistor;
    private UserWrapper userWrapper;

    public UserManagerImpl(final UserPersistor userPersistor) {
        this.userPersistor = userPersistor;
        this.userWrapper = new UserWrapper();
    }

    @Override
    public UserDTO loginUser(final String name, final String password) {
        if (name == null || name.trim().length() == 0) {
            throw new IllegalArgumentException("user name can be neither a null reference nor an empty string");
        }
        final String username = name.trim();
        final Optional<User> optionalUser = this.userPersistor.getByName(username);
        if (optionalUser.isPresent()) {
            final User user = optionalUser.get();
            if (user.getPassword().equals(password)) {
                Logger.logInfo(getClass(), user.getUserName(), "Login successful");
                return this.userWrapper.dtoFromEntity(user);
            } else {
                Logger.logInfo(getClass(), user.getUserName(), "Login failed");
            }
        }
        throw new IllegalArgumentException();
    }
}
