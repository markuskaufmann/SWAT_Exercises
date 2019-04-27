package ch.hslu.appe.fbs.business.user;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.data.user.UserPersistor;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
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
public final class UserManagerImplTest {

    @Mock
    private UserPersistor userPersistor;

    private UserManager userManager;

    private User testee;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        this.userManager = new UserManagerImpl(this.userPersistor);
        this.testee = getUserTestee();
        when(this.userPersistor.getByName("maxmuster")).thenReturn(Optional.of(this.testee));
    }

    @Test
    public void loginUser_WhenCredentialsMatching_ThenLoginSuccessful() {
        final UserDTO loginUserDTO = this.userManager.loginUser(this.testee.getUserName(), this.testee.getPassword());
        final UserRoleDTO loginUserRoleDTO = loginUserDTO.getUserRole();

        assertEquals(this.testee.getId().intValue(), loginUserDTO.getId());
        assertEquals(this.testee.getUserName(), loginUserDTO.getUserName());
        assertEquals(this.testee.getUserRoleByUserRole().getId().intValue(), loginUserRoleDTO.getId());
        assertEquals(this.testee.getUserRoleByUserRole().getRoleName(), loginUserRoleDTO.getUserRole());
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_WhenUsernameNotMatching_ThrowException() {
        this.userManager.loginUser("someRandomUserName", this.testee.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_WhenPasswordNotMatching_ThrowException() {
        this.userManager.loginUser(this.testee.getUserName(), "someRandomPassword");
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_WhenCredentialsNotMatching_ThrowException() {
        this.userManager.loginUser("someRandomUserName", "someRandomPassword");
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_WhenUsernameNull_ThrowException() {
        this.userManager.loginUser(null, this.testee.getPassword());
    }

    @Test(expected = IllegalArgumentException.class)
    public void loginUser_WhenUsernameEmpty_ThrowException() {
        this.userManager.loginUser("", this.testee.getPassword());
    }

    private User getUserTestee() {
        final User testee = new User();
        testee.setId(1);
        testee.setUserName("maxmuster");
        testee.setPassword("secret");
        testee.setDeleted((byte) 0);

        final UserRole testeeRole = new UserRole();
        testeeRole.setId(2);
        testeeRole.setRoleName("user");
        testee.setUserRoleByUserRole(testeeRole);

        return testee;
    }
}
