package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.business.authorisation.model.AuthorisationStorage;
import ch.hslu.appe.fbs.business.authorisation.model.Role;
import ch.hslu.appe.fbs.business.authorisation.model.UserRoles;
import ch.hslu.appe.fbs.business.authorisation.reader.AuthStorageReader;
import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.common.exception.UserNotAuthorisedException;
import ch.hslu.appe.fbs.common.permission.UserPermissions;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.net.URL;
import java.util.*;

public final class AuthorisationManager implements AuthorisationVerifier {

    private static final Logger LOGGER = LogManager.getLogger(AuthorisationManager.class);

    private static final String EXCEPTION_USER_NULL = "user object can't be a null reference";
    private static final String EXCEPTION_PERMISSION_NULL = "permission object can't be a null reference";

    private static final String AUTH_STORAGE_FILE = "authorisation_storage.json";

    private final AuthStorageReader storageReader;

    private Map<String, List<UserPermissions>> authorisationMap;

    public AuthorisationManager(final AuthStorageReader storageReader) {
        this.storageReader = storageReader;
    }

    @Override
    public void checkUserAuthorisation(final UserDTO userDTO, final UserPermissions userPermission)
            throws UserNotAuthorisedException {
        checkObjectNotNull(userDTO, EXCEPTION_USER_NULL);
        checkObjectNotNull(userPermission, EXCEPTION_PERMISSION_NULL);
        initializeAuthorisationMap();
        final boolean permissionGranted = checkUserPermission(userDTO, userPermission);
        if (!permissionGranted) {
            throw new UserNotAuthorisedException(userPermission);
        }
    }

    @Override
    public boolean checkUserPermission(final UserDTO userDTO, final UserPermissions userPermission) {
        checkObjectNotNull(userDTO, EXCEPTION_USER_NULL);
        checkObjectNotNull(userPermission, EXCEPTION_PERMISSION_NULL);
        initializeAuthorisationMap();
        final String userRole = userDTO.getUserRole().getUserRole();
        final List<UserPermissions> userPermissions = this.authorisationMap.get(userRole);
        return (userPermissions != null)
                && (userPermissions.contains(userPermission)
                || userPermissions.contains(UserPermissions.ADMIN));
    }

    private void initializeAuthorisationMap() {
        if(this.authorisationMap != null) {
            return;
        }
        final Optional<String> storageFilePath = retrieveStorageFilePath();
        storageFilePath.ifPresent(pathToStorageFile -> {
            final Optional<AuthorisationStorage> optStorage
                    = this.storageReader.readAuthorisationStorageFromSource(Collections.singletonList(pathToStorageFile));
            optStorage.ifPresent(authorisationStorage -> this.authorisationMap = buildUserPermissionsMap(authorisationStorage));
        });
    }

    private Map<String, List<UserPermissions>> buildUserPermissionsMap(final AuthorisationStorage authorisationStorage) {
        final Map<String, List<UserPermissions>> userPermissionMap = new HashMap<>();
        authorisationStorage.getRolePermissions().forEach(rolePermission -> {
            final Role role = rolePermission.getRole();
            final UserRoles userRole = UserRoles.valueOf(role.getName().toUpperCase());
            final List<UserPermissions> userPermissions = new ArrayList<>();
            rolePermission.getPermissions().forEach(permission -> {
                final UserPermissions userPermission = UserPermissions.valueOf(permission.getName().toUpperCase());
                if(userPermissions.indexOf(userPermission) == -1) {
                    userPermissions.add(userPermission);
                }
            });
            userPermissionMap.put(userRole.getRole(), userPermissions);
        });
        return userPermissionMap;
    }

    private Optional<String> retrieveStorageFilePath() {
        try {
            final URL url = this.getClass().getClassLoader().getResource(AUTH_STORAGE_FILE);
            if(url == null) {
                return Optional.empty();
            }
            return Optional.of(new File(url.toURI()).getAbsolutePath());
        } catch (Exception e) {
            LOGGER.error("Error while retrieving authorisation storage file", e);
        }
        return Optional.empty();
    }

    private void checkObjectNotNull(final Object objectToCheck, final String exceptionMessage) {
        if (objectToCheck == null) {
            throw new IllegalArgumentException(exceptionMessage);
        }
    }
}
