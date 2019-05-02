package ch.hslu.appe.fbs.business.authorisation;

import ch.hslu.appe.fbs.business.authorisation.model.AuthorisationStorage;
import ch.hslu.appe.fbs.business.authorisation.reader.AuthStorageFileReader;
import ch.hslu.appe.fbs.business.authorisation.reader.AuthStorageReader;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Optional;

import static org.junit.Assert.assertTrue;

@RunWith(MockitoJUnitRunner.class)
public final class AuthStorageFileReaderTest {

    private static final String AUTH_STORAGE_FILE = "authorisation_storage.json";
    private static final String INVALID_AUTH_STORAGE_FILE = "authorisation_storage_invalid.json";

    private AuthStorageReader authStorageReader;

    @Before
    public void setUp() {
        this.authStorageReader = new AuthStorageFileReader();
    }

    @Test
    public void readAuthorisationStorageFromSource_WhenValidSourceFile_ThenReturnAuthorisationStorage() throws URISyntaxException {
        final Optional<String> optStorageFilePath = retrieveStorageFilePath(AUTH_STORAGE_FILE);
        optStorageFilePath.ifPresent(filePath -> {
            final Optional<AuthorisationStorage> optAuthStorage
                    = this.authStorageReader.readAuthorisationStorageFromSource(Collections.singletonList(filePath));
            assertTrue(optAuthStorage.isPresent());
        });
    }

    @Test
    public void readAuthorisationStorageFromSource_WhenInvalidSourceFile_ThenReturnEmptyResult() throws URISyntaxException {
        final Optional<String> optStorageFilePath = retrieveStorageFilePath(INVALID_AUTH_STORAGE_FILE);
        optStorageFilePath.ifPresent(filePath -> {
            final Optional<AuthorisationStorage> optAuthStorage
                    = this.authStorageReader.readAuthorisationStorageFromSource(Collections.singletonList(filePath));
            assertTrue(optAuthStorage.isEmpty());
        });
    }

    @Test
    public void readAuthorisationStorageFromSource_WhenInvalidSourcePath_ThenReturnEmptyResult() {
        final String filePath = "invalidPath";
        final Optional<AuthorisationStorage> optAuthStorage
                = this.authStorageReader.readAuthorisationStorageFromSource(Collections.singletonList(filePath));
        assertTrue(optAuthStorage.isEmpty());
    }

    @Test
    public void readAuthorisationStorageFromSource_WhenNullSourcePath_ThenReturnEmptyResult() {
        final Optional<AuthorisationStorage> optAuthStorage
                = this.authStorageReader.readAuthorisationStorageFromSource(Collections.singletonList(null));
        assertTrue(optAuthStorage.isEmpty());
    }

    @Test
    public void readAuthorisationStorageFromSource_WhenEmptySourcePath_ThenReturnEmptyResult() {
        final Optional<AuthorisationStorage> optAuthStorage
                = this.authStorageReader.readAuthorisationStorageFromSource(Collections.singletonList(""));
        assertTrue(optAuthStorage.isEmpty());
    }

    @Test
    public void readAuthorisationStorageFromSource_WhenEmptySourcePathWithSpaces_ThenReturnEmptyResult() {
        final Optional<AuthorisationStorage> optAuthStorage
                = this.authStorageReader.readAuthorisationStorageFromSource(Collections.singletonList("   "));
        assertTrue(optAuthStorage.isEmpty());
    }

    @Test(expected = IllegalArgumentException.class)
    public void readAuthorisationStorageFromSource_WhenNullConnectionArgs_ThrowException() {
        this.authStorageReader.readAuthorisationStorageFromSource(null);
    }

    @Test(expected = IllegalArgumentException.class)
    public void readAuthorisationStorageFromSource_WhenEmptyConnectionArgs_ThrowException() {
        this.authStorageReader.readAuthorisationStorageFromSource(Collections.emptyList());
    }

    private Optional<String> retrieveStorageFilePath(final String resourceFileName) throws URISyntaxException {
        final URL url = this.getClass().getClassLoader().getResource(resourceFileName);
        if(url == null) {
            return Optional.empty();
        }
        return Optional.of(new File(url.toURI()).getAbsolutePath());
    }
}
