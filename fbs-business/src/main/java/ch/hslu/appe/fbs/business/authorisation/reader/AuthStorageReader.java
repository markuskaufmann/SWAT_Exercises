package ch.hslu.appe.fbs.business.authorisation.reader;

import ch.hslu.appe.fbs.business.authorisation.model.AuthorisationStorage;

import java.util.List;
import java.util.Optional;

public interface AuthStorageReader {
    Optional<AuthorisationStorage> readAuthorisationStorageFromSource(final List<String> connectionArgs);
}
