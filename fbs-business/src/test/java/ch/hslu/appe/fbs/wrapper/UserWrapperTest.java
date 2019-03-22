package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.model.db.User;
import ch.hslu.appe.fbs.model.db.UserRole;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class UserWrapperTest {

    @Test
    public void dtoFromEntity_when_userentity_then_userdto() {
        // arrange
        final UserWrapper userWrapper = new UserWrapper();

        final UserRole userRoleEntity = new UserRole();
        userRoleEntity.setId(1);
        userRoleEntity.setRoleName("Test");

        final User userEntity = new User();
        userEntity.setId(1);
        userEntity.setUserName("maxmuster");
        userEntity.setUserRoleByUserRole(userRoleEntity);

        //act
        final UserDTO userDTO = userWrapper.dtoFromEntity(userEntity);
        final UserRoleDTO userRoleDTO = userDTO.getUserRole();

        //assert
        Assert.assertEquals(userEntity.getId().intValue(), userDTO.getId());
        Assert.assertEquals(userEntity.getUserName(), userDTO.getUserName());
        Assert.assertEquals(userRoleEntity.getId().intValue(), userRoleDTO.getId());
        Assert.assertEquals(userRoleEntity.getRoleName(), userRoleDTO.getUserRole());
    }

    @Test
    public void entityFromDTO_when_userdto_then_userentity() {
        // arrange
        final UserWrapper userWrapper = new UserWrapper();

        final UserRoleDTO userRoleDTO = new UserRoleDTO(1, "Test");

        final UserDTO userDTO = new UserDTO(1, userRoleDTO, "maxmuster");

        //act
        final User userEntity = userWrapper.entityFromDTO(userDTO);

        //assert
        Assert.assertEquals(userDTO.getId(), userEntity.getId().intValue());
        Assert.assertEquals(userDTO.getUserName(), userEntity.getUserName());
        Assert.assertEquals(userRoleDTO.getId(), userEntity.getUserRole().intValue());
        Assert.assertEquals((byte) 0, userEntity.getDeleted().byteValue());
    }
}
