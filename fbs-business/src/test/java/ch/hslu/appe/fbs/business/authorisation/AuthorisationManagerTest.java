package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public final class AuthorisationManagerTest {

    @Test
    public void checkUserPermission_WhenUserHasPermission_ThenReturnTrue() {
        final UserDTO authorizedUser = getUserTestee(UserRoles.SALESPERSON);
        final boolean isUserAuthorized = AuthorisationManager.checkUserPermission(authorizedUser, UserPermissions.CREATE_CUSTOMER);
        assertTrue(isUserAuthorized);
    }

    @Test
    public void checkUserPermission_WhenUserHasNoPermission_ThenReturnFalse() {
        final UserDTO unauthorizedUser = getUserTestee(UserRoles.BRANCHMANAGER);
        final boolean isUserAuthorized = AuthorisationManager.checkUserPermission(unauthorizedUser, UserPermissions.CREATE_CUSTOMER);
        assertFalse(isUserAuthorized);
    }

    @Test
    public void checkUserPermission_WhenUserHasNoValidRole_ThenReturnFalse() {
        final UserDTO unauthorizedUser = getUnauthorizedUserTestee();
        final boolean isUserAuthorized = AuthorisationManager.checkUserPermission(unauthorizedUser, UserPermissions.CREATE_CUSTOMER);
        assertFalse(isUserAuthorized);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserPermission_WhenUserIsNull_ThrowException() {
        AuthorisationManager.checkUserPermission(null, UserPermissions.CREATE_CUSTOMER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserPermission_WhenPermissionIsNull_ThrowException() {
        final UserDTO authorizedUser = getUserTestee(UserRoles.SALESPERSON);
        AuthorisationManager.checkUserPermission(authorizedUser, null);
    }

    @Test
    public void checkUserPermission_WhenUserIsAdmin_ThenAlwaysReturnTrue() {
        final UserDTO admin = getAdminTestee();
        for(final UserPermissions permission : UserPermissions.values()) {
            final boolean isAuthorized = AuthorisationManager.checkUserPermission(admin, permission);
            assertTrue(isAuthorized);
        }
    }

    @Test
    public void checkUserAuthorisation_WhenUserHasPermission_ThenDoNothing() throws UserNotAuthorisedException {
        final UserDTO authorizedUser = getUserTestee(UserRoles.SALESPERSON);
        AuthorisationManager.checkUserAuthorisation(authorizedUser, UserPermissions.CREATE_CUSTOMER);
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void checkUserAuthorisation_WhenUserHasNoPermission_ThrowException() throws UserNotAuthorisedException {
        final UserDTO unauthorizedUser = getUserTestee(UserRoles.BRANCHMANAGER);
        AuthorisationManager.checkUserAuthorisation(unauthorizedUser, UserPermissions.CREATE_CUSTOMER);
    }

    @Test(expected = UserNotAuthorisedException.class)
    public void checkUserAuthorisation_WhenUserHasNoValidRole_ThrowException() throws UserNotAuthorisedException {
        final UserDTO unauthorizedUser = getUnauthorizedUserTestee();
        AuthorisationManager.checkUserAuthorisation(unauthorizedUser, UserPermissions.CREATE_CUSTOMER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserAuthorisation_WhenUserIsNull_ThrowException() throws UserNotAuthorisedException {
        AuthorisationManager.checkUserAuthorisation(null, UserPermissions.CREATE_CUSTOMER);
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserAuthorisation_WhenPermissionIsNull_ThrowException() throws UserNotAuthorisedException {
        final UserDTO authorizedUser = getUserTestee(UserRoles.SALESPERSON);
        AuthorisationManager.checkUserAuthorisation(authorizedUser, null);
    }

    @Test
    public void checkUserAuthorisation_WhenUserIsAdmin_ThenAlwaysReturnTrue() throws UserNotAuthorisedException {
        final UserDTO admin = getAdminTestee();
        for(final UserPermissions permission : UserPermissions.values()) {
            AuthorisationManager.checkUserAuthorisation(admin, permission);
        }
    }

    private UserDTO getUserTestee(final UserRoles userRole) {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, userRole.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }

    private UserDTO getAdminTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SYSADMIN.getRole());
        return new UserDTO(1, userRoleDTO, "Admin");
    }

    private UserDTO getUnauthorizedUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, "unauthorized");
        return new UserDTO(42, userRoleDTO, "unauthorizedUser");
    }
}
