package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class UserSessionMapImpl implements UserSessionMap {

    private final Map<String, UserDTO> userSessions;

    public UserSessionMapImpl() {
        this.userSessions = new ConcurrentHashMap<>();
    }

    @Override
    public void addUserSession(final String host, final UserDTO userDTO) {
        checkHostIsNullEmpty(host);
        checkUserIsNull(userDTO);
        this.userSessions.putIfAbsent(host, userDTO);
    }

    @Override
    public void removeUserSession(final String host) {
        checkHostIsNullEmpty(host);
        this.userSessions.remove(host);
    }

    @Override
    public Optional<UserDTO> getUserSession(final String host) {
        checkHostIsNullEmpty(host);
        return Optional.ofNullable(this.userSessions.get(host));
    }

    private void checkHostIsNullEmpty(final String host) {
        if(host == null || host.trim().length() == 0) {
            throw new IllegalArgumentException("Invalid host (null / empty)");
        }
    }

    private void checkUserIsNull(final UserDTO userDTO) {
        if(userDTO == null) {
            throw new IllegalArgumentException("Invalid user object (null)");
        }
    }
}
