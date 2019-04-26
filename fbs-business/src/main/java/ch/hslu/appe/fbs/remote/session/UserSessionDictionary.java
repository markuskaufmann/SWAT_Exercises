package ch.hslu.appe.fbs.remote.session;

import ch.hslu.appe.fbs.common.dto.UserDTO;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

public final class UserSessionDictionary {

    private static final Logger LOGGER = LogManager.getLogger(UserSessionDictionary.class);

    private static final Map<String, UserDTO> SESSION_MAP = new ConcurrentHashMap<>();

    private UserSessionDictionary() {
    }

    public static void addUserSession(final UserDTO userDTO) {
        if (userDTO == null) {
            throw new IllegalArgumentException("user object can't be a null reference");
        }
        final Optional<String> optRemote = getRemoteHost();
        if (optRemote.isPresent()) {
            final String remote = optRemote.get();
            SESSION_MAP.putIfAbsent(remote, userDTO);
        }
    }

    public static void removeUserSession() {
        final Optional<String> optRemote = getRemoteHost();
        if (optRemote.isPresent()) {
            final String remote = optRemote.get();
            SESSION_MAP.remove(remote);
        }
    }

    public static UserDTO getUserSession() {
        final Optional<String> optRemote = getRemoteHost();
        if (optRemote.isPresent()) {
            final String remote = optRemote.get();
            return SESSION_MAP.get(remote);
        }
        throw new IllegalArgumentException("No user session mapping available");
    }

    private static Optional<String> getRemoteHost() {
        try {
            final String remote = RemoteServer.getClientHost();
            return Optional.of(remote);
        } catch (ServerNotActiveException e) {
            LOGGER.error("Error while retrieving remote (client) host", e);
            return Optional.empty();
        }
    }
}
