package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;

public interface AuthorisationVerifier {
    void checkUserAuthorisation(final UserDTO userDTO, final UserPermissions userPermission) throws UserNotAuthorisedException;
    boolean checkUserPermission(final UserDTO userDTO, final UserPermissions userPermission);
}
