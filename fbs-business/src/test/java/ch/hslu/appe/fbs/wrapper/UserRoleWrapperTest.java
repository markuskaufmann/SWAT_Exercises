package ch.hslu.appe.fbs.wrapper;

import ch.hslu.appe.fbs.common.dto.UserRoleDTO;
import ch.hslu.appe.fbs.model.db.UserRole;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public final class UserRoleWrapperTest {

    @Test
    public void dtoFromEntity_when_userRoleEntity_then_userRoleDTO() {
        // arrange
        final UserRoleWrapper userRoleWrapper = new UserRoleWrapper();

        final int userRoleId = 1;
        final String userRoleName = "Test";
        final UserRole userRoleEntity = new UserRole();
        userRoleEntity.setId(userRoleId);
        userRoleEntity.setRoleName(userRoleName);

        //act
        final UserRoleDTO userRoleDTO = userRoleWrapper.dtoFromEntity(userRoleEntity);

        //assert
        Assert.assertEquals(userRoleId, userRoleDTO.getId());
        Assert.assertEquals(userRoleName, userRoleDTO.getUserRole());
    }

    @Test
    public void entityFromDTO_when_userRoleDTO_then_userRoleEntity() {
        // arrange
        final UserRoleWrapper userRoleWrapper = new UserRoleWrapper();

        final int userRoleId = 1;
        final String userRoleName = "Test";
        final UserRoleDTO userRoleDTO = new UserRoleDTO(userRoleId, userRoleName);

        //act
        final UserRole userRoleEntity = userRoleWrapper.entityFromDTO(userRoleDTO);

        //assert
        Assert.assertEquals(userRoleId, userRoleEntity.getId().intValue());
        Assert.assertEquals(userRoleName, userRoleEntity.getRoleName());
    }
}
