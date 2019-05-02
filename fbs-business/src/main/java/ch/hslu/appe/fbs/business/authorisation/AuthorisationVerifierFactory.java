package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.business.authorisation.reader.AuthStorageFileReader;

public final class AuthorisationVerifierFactory {

    private AuthorisationVerifierFactory() {
    }

    public static AuthorisationVerifier createAuthorisationVerifier() {
        return new AuthorisationManager(new AuthStorageFileReader());
    }
}
