package ch.hslu.appe.fbs.remote.service.user;

import ch.hslu.appe.fbs.business.user.UserManager;
import ch.hslu.appe.fbs.common.dto.*;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;
import ch.hslu.appe.fbs.data.orderstate.OrderStates;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.model.db.*;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.service.customer.CustomerServiceImpl;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;
import ch.hslu.appe.fbs.wrapper.BillWrapper;
import ch.hslu.appe.fbs.wrapper.CustomerWrapper;
import ch.hslu.appe.fbs.wrapper.OrderWrapper;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.rmi.RemoteException;
import java.security.Permissions;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.*;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public final class UserServiceImplTest {

    private static final String LOCALHOST = "localhost";

    @Mock
    private UserManager userManager;

    @Mock
    private ClientHost clientHost;

    @Mock
    private UserSessionMap userSessionMap;

    private UserService userService;
    private UserDTO userTestee;

    @Before
    public void setUp() throws UserNotAuthorisedException {
        MockitoAnnotations.initMocks(this);
        this.userService = new UserServiceImpl(this.clientHost, this.userSessionMap, this.userManager);
        this.userTestee = getUserTestee(UserRoles.SYSADMIN);
        when(this.clientHost.getHostAddress()).thenReturn(LOCALHOST);
        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(userTestee));
    }

    @Test
    public void performLogin_WhenValidNameAndPassword_ThenReturnAssociatedUserDTO() throws RemoteException {
        final String expectedPassword = "secret";

        doAnswer(invocationOnMock -> {
            final String username = invocationOnMock.getArgument(0);
            final String password = invocationOnMock.getArgument(1);

            assertEquals(this.userTestee.getUserName(), username);
            assertEquals(expectedPassword, password);

            return this.userTestee;
        }).when(this.userManager).loginUser(any(String.class), any(String.class));

        doNothing().when(this.userSessionMap).addUserSession(any(String.class), any(UserDTO.class));

        final UserDTO user = this.userService.performLogin(this.userTestee.getUserName(), expectedPassword);

        assertEquals(this.userTestee.getId(), user.getId());
        assertEquals(this.userTestee.getUserName(), user.getUserName());
        assertEquals(this.userTestee.getUserRole(), user.getUserRole());
    }

    @Test
    public void performLogout_WhenUserSessionExists_ThenRemoveIt() throws RemoteException {

        doAnswer(invocationOnMock -> {

            when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.empty());

            return null;
        }).when(this.userSessionMap).removeUserSession(any(String.class));

        Optional<UserDTO> optUser = this.userSessionMap.getUserSession(LOCALHOST);
        optUser.ifPresent(userDTO -> {
            assertEquals(this.userTestee.getId(), userDTO.getId());
            assertEquals(this.userTestee.getUserName(), userDTO.getUserName());
            assertEquals(this.userTestee.getUserRole(), userDTO.getUserRole());
        });

        this.userService.performLogout();

        optUser = this.userSessionMap.getUserSession(LOCALHOST);

        assertTrue(optUser.isEmpty());
    }

    @Test
    public void checkUserPermissions_WhenGivenPermissionsAndUserPermitted_ThenReturnPositiveResult() throws RemoteException {
        final List<UserPermissions> permissions = Arrays.asList(UserPermissions.ADMIN, UserPermissions.CREATE_CUSTOMER,
                UserPermissions.CREATE_ORDER, UserPermissions.DELETE_ITEM);

        final Map<UserPermissions, Boolean> permissionMap = this.userService.checkUserPermissions(permissions);

        assertEquals(permissions.size(), permissionMap.size());

        final Set<UserPermissions> permissionSet = permissionMap.keySet();
        for(final UserPermissions permission : permissions) {
            assertTrue(permissionSet.contains(permission));
            assertTrue(permissionMap.get(permission));
        }
    }

    @Test
    public void checkUserPermissions_WhenGivenPermissionsAndUserNotPermitted_ThenReturnNegativeResult() throws RemoteException {
        final UserDTO userNotPermitted = getUserTestee(UserRoles.BRANCHMANAGER);

        final List<UserPermissions> permissions = Arrays.asList(UserPermissions.CREATE_CUSTOMER, UserPermissions.DELETE_CUSTOMER,
                UserPermissions.CREATE_ORDER, UserPermissions.CANCEL_ORDER);

        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(userNotPermitted));

        final Map<UserPermissions, Boolean> permissionMap = this.userService.checkUserPermissions(permissions);

        assertEquals(permissions.size(), permissionMap.size());

        final Set<UserPermissions> permissionSet = permissionMap.keySet();
        for(final UserPermissions permission : permissions) {
            assertTrue(permissionSet.contains(permission));
            assertFalse(permissionMap.get(permission));
        }
    }

    @Test
    public void checkUserPermissions_WhenGivenPermissionsAndUserPartlyPermitted_ThenReturnMixedResult() throws RemoteException {
        final UserDTO userPartlyPermitted = getUserTestee(UserRoles.DATATYPIST);

        final List<UserPermissions> permissions = Arrays.asList(UserPermissions.CREATE_CUSTOMER, UserPermissions.CREATE_ORDER,
                UserPermissions.GET_ORDER, UserPermissions.GET_ITEM);

        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(userPartlyPermitted));

        final Map<UserPermissions, Boolean> permissionMap = this.userService.checkUserPermissions(permissions);

        assertEquals(permissions.size(), permissionMap.size());

        final Set<UserPermissions> permissionSet = permissionMap.keySet();
        for(final UserPermissions permission : permissions) {
            assertTrue(permissionSet.contains(permission));
        }

        assertFalse(permissionMap.get(UserPermissions.CREATE_CUSTOMER));
        assertFalse(permissionMap.get(UserPermissions.CREATE_ORDER));
        assertTrue(permissionMap.get(UserPermissions.GET_ORDER));
        assertTrue(permissionMap.get(UserPermissions.GET_ITEM));
    }

    @Test
    public void checkUserPermissions_WhenGivenPermissionsButInvalidUser_ThenReturnEmptyResult() throws RemoteException {
        final List<UserPermissions> permissions = Arrays.asList(UserPermissions.CREATE_CUSTOMER, UserPermissions.CREATE_ORDER,
                UserPermissions.GET_ORDER, UserPermissions.GET_ITEM);

        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.empty());

        final Map<UserPermissions, Boolean> permissionMap = this.userService.checkUserPermissions(permissions);

        assertTrue(permissionMap.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void checkUserPermissions_WhenPermissionsNull_ThrowException() throws RemoteException {
        this.userService.checkUserPermissions(null);
    }

    @Test
    public void getServiceName_WhenServiceNameExists_ThenReturnIt() throws RemoteException {
        final String serviceName = this.userService.getServiceName();
        assertEquals(RmiLookupTable.getUserServiceName(), serviceName);
    }

    private UserDTO getUserTestee(final UserRoles userRole) {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, userRole.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }
}
