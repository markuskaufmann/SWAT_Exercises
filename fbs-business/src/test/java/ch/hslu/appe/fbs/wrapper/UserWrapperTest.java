package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class UserWrapperTest {

    @Test
    public void dtoFromEntity_when_userEntity_then_userDTO() {
        // arrange
        final UserWrapper userWrapper = new UserWrapper();

        final int userRoleId = 1;
        final String userRoleName = "Test";
        final UserRole userRoleEntity = new UserRole();
        userRoleEntity.setId(userRoleId);
        userRoleEntity.setRoleName(userRoleName);

        final int userId = 2;
        final String userName = "maxmuster";
        final User userEntity = new User();
        userEntity.setId(userId);
        userEntity.setUserName(userName);
        userEntity.setUserRoleByUserRole(userRoleEntity);

        //act
        final UserDTO userDTO = userWrapper.dtoFromEntity(userEntity);
        final UserRoleDTO userRoleDTO = userDTO.getUserRole();

        //assert
        Assert.assertEquals(userId, userDTO.getId());
        Assert.assertEquals(userName, userDTO.getUserName());
        Assert.assertEquals(userRoleId, userRoleDTO.getId());
        Assert.assertEquals(userRoleName, userRoleDTO.getUserRole());
    }

    @Test
    public void entityFromDTO_when_userDTO_then_userEntity() {
        // arrange
        final UserWrapper userWrapper = new UserWrapper();

        final int userRoleId = 1;
        final String userRoleName = "Test";
        final UserRoleDTO userRoleDTO = new UserRoleDTO(userRoleId, userRoleName);

        final int userId = 2;
        final String userName = "maxmuster";
        final UserDTO userDTO = new UserDTO(userId, userRoleDTO, userName);

        //act
        final User userEntity = userWrapper.entityFromDTO(userDTO);

        //assert
        Assert.assertEquals(userId, userEntity.getId().intValue());
        Assert.assertEquals(userName, userEntity.getUserName());
        Assert.assertEquals(userRoleId, userEntity.getUserRole().intValue());
        Assert.assertEquals((byte) 0, userEntity.getDeleted().byteValue());
    }
}
