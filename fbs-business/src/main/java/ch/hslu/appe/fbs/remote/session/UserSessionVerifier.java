package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import ch.hslu.appe.fbs.remote.rmi.ClientHost;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Optional;

public final class UserSessionVerifier {

    private static final Logger LOGGER = LogManager.getLogger(UserSessionVerifier.class);

    private UserSessionVerifier() {
    }

    public static UserDTO getUserSession(final UserSessionMap userSessionMap, final ClientHost clientHost) {
        checkArgumentNotNull(userSessionMap, "userSessionMap");
        checkArgumentNotNull(clientHost, "clientHost");
        final String logMessage = String.format("Verify user session: Host = %s", clientHost.getHostAddress());
        LOGGER.info(logMessage);
        final Optional<UserDTO> optUserDTO = userSessionMap.getUserSession(clientHost.getHostAddress());
        if(optUserDTO.isPresent()) {
            LOGGER.info("User session found");
            return optUserDTO.get();
        } else {
            final String error = String.format("No valid user session found. Host: %s", clientHost.getHostAddress());
            LOGGER.error(error);
            throw new IllegalStateException(error);
        }
    }

    private static void checkArgumentNotNull(final Object argumentToCheck, final String argumentName) {
        if(argumentToCheck == null) {
            final String error = String.format("The provided argument '%s' is invalid (null)", argumentName);
            throw new IllegalArgumentException(error);
        }
    }
}
