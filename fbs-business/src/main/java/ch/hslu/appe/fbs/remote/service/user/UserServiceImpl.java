package ch.hslu.appe.fbs.remote.service.user;

import ch.hslu.appe.fbs.business.authorisation.AuthorisationManager;
import ch.hslu.appe.fbs.business.user.UserManager;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import ch.hslu.appe.fbs.common.rmi.RmiLookupTable;
import ch.hslu.appe.fbs.common.rmi.UserService;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import ch.hslu.appe.fbs.remote.session.UserSessionMap;

import java.rmi.RemoteException;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public final class UserServiceImpl implements UserService {

    private final ClientHost clientHost;
    private final UserSessionMap userSessionMap;

    private final UserManager userManager;

    public UserServiceImpl(final ClientHost clientHost, final UserSessionMap userSessionMap, final UserManager userManager) {
        this.clientHost = clientHost;
        this.userSessionMap = userSessionMap;
        this.userManager = userManager;
    }

    @Override
    public UserDTO performLogin(final String name, final String password) throws RemoteException {
        final UserDTO userDTO = this.userManager.loginUser(name, password);
        this.userSessionMap.addUserSession(this.clientHost.getHostAddress(), userDTO);
        return userDTO;
    }

    @Override
    public void performLogout() throws RemoteException {
        this.userSessionMap.removeUserSession(this.clientHost.getHostAddress());
    }

    @Override
    public Map<UserPermissions, Boolean> checkUserPermissions(final UserPermissions... userPermissions) {
        if (userPermissions == null) {
            throw new IllegalArgumentException("The specified permissions can't be a null reference");
        }
        final Optional<UserDTO> optUserDTO = this.userSessionMap.getUserSession(this.clientHost.getHostAddress());
        final Map<UserPermissions, Boolean> permissionMap = new HashMap<>();
        optUserDTO.ifPresent(userDTO -> {
            for (UserPermissions userPermission : userPermissions) {
                final boolean permissionGranted = AuthorisationManager.checkUserPermission(optUserDTO.get(), userPermission);
                permissionMap.put(userPermission, permissionGranted);
            }
        });
        return permissionMap;
    }

    @Override
    public String getServiceName() throws RemoteException {
        return RmiLookupTable.getUserServiceName();
    }
}