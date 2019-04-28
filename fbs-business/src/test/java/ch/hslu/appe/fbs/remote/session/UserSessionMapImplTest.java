package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.data.userrole.UserRoles;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Optional;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public final class UserSessionMapImplTest {

    private static final String LOCALHOST = "localhost";

    private UserSessionMap userSessionMap;
    private UserDTO userTestee;

    @Before
    public void setUp() throws UserNotAuthorisedException {
        this.userSessionMap = new UserSessionMapImpl();
        this.userTestee = getUserTestee();
    }

    @Test
    public void addUserSession_WhenValidHostAndUserDTO_ThenAddUserSession() {
        this.userSessionMap.addUserSession(LOCALHOST, this.userTestee);

        final Optional<UserDTO> optUser = this.userSessionMap.getUserSession(LOCALHOST);

        assertTrue(optUser.isPresent());

        optUser.ifPresent(userDTO -> {
            assertEquals(this.userTestee.getId(), userDTO.getId());
            assertEquals(this.userTestee.getUserName(), userDTO.getUserName());
            assertEquals(this.userTestee.getUserRole(), userDTO.getUserRole());
        });
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserSession_WhenInvalidHostButValidUserDTO_ThrowException() {
        this.userSessionMap.addUserSession(null, this.userTestee);
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserSession_WhenValidHostButInvalidUserDTO_ThrowException() {
        this.userSessionMap.addUserSession(LOCALHOST, null);
    }

    @Test
    public void removeUserSession_WhenValidHost_ThenRemoveUserSession() {
        this.userSessionMap.addUserSession(LOCALHOST, this.userTestee);
        Optional<UserDTO> optUser = this.userSessionMap.getUserSession(LOCALHOST);

        assertTrue(optUser.isPresent());

        this.userSessionMap.removeUserSession(LOCALHOST);
        optUser = this.userSessionMap.getUserSession(LOCALHOST);

        assertFalse(optUser.isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void addUserSession_WhenInvalidHost_ThrowException() {
        this.userSessionMap.removeUserSession(null);
    }

    @Test
    public void getUserSession_WhenValidHost_ThenReturnAssociatedUserSession() {
        this.userSessionMap.addUserSession(LOCALHOST, this.userTestee);
        final Optional<UserDTO> optUser = this.userSessionMap.getUserSession(LOCALHOST);

        assertTrue(optUser.isPresent());
    }

    @Test
    public void getUserSession_WhenValidHostButNoUserSession_ThenReturnEmptyResult() {
        final Optional<UserDTO> optUser = this.userSessionMap.getUserSession(LOCALHOST);
        assertFalse(optUser.isPresent());
    }

    @Test(expected = IllegalArgumentException.class)
    public void getUserSession_WhenInvalidHost_ThrowException() {
        this.userSessionMap.getUserSession(null);
    }

    private UserDTO getUserTestee() {
        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, UserRoles.SYSADMIN.getRole());
        return new UserDTO(1, userRoleDTO, "maxmuster");
    }
}
