package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public final class UserSessionVerifierTest {

    private static final String LOCALHOST = "localhost";

    @Mock
    private ClientHost clientHost;

    @Mock
    private UserSessionMap userSessionMap;

    private UserDTO userTestee;

    @Before
    public void setUp() throws UserNotAuthorisedException {
        MockitoAnnotations.initMocks(this);
        this.userTestee = getUserTestee();
        when(this.clientHost.getHostAddress()).thenReturn(LOCALHOST);
        when(this.userSessionMap.getUserSession(LOCALHOST)).thenReturn(Optional.of(this.userTestee));
    }

    @Test
    public void getUserSession_WhenValidUserSessionMapAndClientHost_ThenReturnUserDTO() {
        final UserDTO user = UserSessionVerifier.getUserSession(this.userSessionMap, this.clientHost);
        assertEquals(this.userTestee.getId(), user.getId());
        assertEquals(this.userTestee.getUserName(), user.getUserName());
        assertEquals(this.userTestee.getUserRole(), user.getUserRole());
    }

    @Test(expected = IllegalStateException.class)
    public void getUserSession_WhenNoValidUserSessionFound_ThrowException() {
        when(this.clientHost.getHostAddress()).thenReturn("unknownHostAddress");
        UserSessionVerifier.getUserSession(this.userSessionMap, this.clientHost);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserSession_WhenInvalidUserSessionMapButValidClientHost_ThrowException() {
        UserSessionVerifier.getUserSession(null, this.clientHost);
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserSession_WhenValidUserSessionMapButInvalidClientHost_ThrowException() {
        UserSessionVerifier.getUserSession(this.userSessionMap, null);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SYSADMIN.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }
}
