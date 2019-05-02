package ch.hslu.appe.fbs.business.authorisation.reader;

import ch.hslu.appe.fbs.business.authorisation.model.AuthorisationStorage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;
import java.util.List;
import java.util.Optional;

public final class AuthStorageFileReader implements AuthStorageReader {

    private static final Logger LOGGER = LogManager.getLogger(AuthStorageFileReader.class);

    @Override
    public Optional<AuthorisationStorage> readAuthorisationStorageFromSource(final List<String> connectionArgs) {
        if(connectionArgs == null || connectionArgs.isEmpty()) {
            throw new IllegalArgumentException("The provided connectionArgs object can't be null or empty");
        }
        final String pathToFile = connectionArgs.get(0);
        if(pathToFile == null || pathToFile.trim().length() == 0) {
            return Optional.empty();
        }
        final Optional<File> optFile = checkPathToFile(pathToFile.trim());
        if(optFile.isPresent()) {
            final ObjectMapper objectMapper = new ObjectMapper();
            try {
                final AuthorisationStorage authorisationStorage = objectMapper.readValue(optFile.get(), AuthorisationStorage.class);
                return Optional.of(authorisationStorage);
            } catch (Exception e) {
                LOGGER.error("Error while reading JSON Authorisation Storage", e);
            }
        }
        return Optional.empty();
    }

    private Optional<File> checkPathToFile(final String pathToFile) {
        final File file = new File(pathToFile);
        return (file.exists() && file.isFile()) ? Optional.of(file) : Optional.empty();
    }
}
