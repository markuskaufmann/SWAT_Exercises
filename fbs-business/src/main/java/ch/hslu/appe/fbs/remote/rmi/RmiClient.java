package ch.hslu.appe.fbs.remote.rmi;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.rmi.server.RemoteServer;
import java.rmi.server.ServerNotActiveException;
import java.util.Optional;

public final class RmiClient implements ClientHost {

    private static final Logger LOGGER = LogManager.getLogger(RmiClient.class);

    @Override
    public String getHostAddress() {
        return getRemoteHost().orElseThrow(() -> new IllegalStateException("Invalid host address"));
    }

    private Optional<String> getRemoteHost() {
        try {
            final String remote = RemoteServer.getClientHost();
            final String infoRemote = String.format("Remote (client) host: %s", remote);
            LOGGER.info(infoRemote);
            return Optional.of(remote);
        } catch (ServerNotActiveException e) {
            LOGGER.error("Error while retrieving remote (client) host", e);
            return Optional.empty();
        }
    }
}
