package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;

import java.util.Optional;

public interface UserSessionMap {
    void addUserSession(final String host, final UserDTO userDTO);
    void removeUserSession(final String host);
    Optional<UserDTO> getUserSession(final String host);
}
