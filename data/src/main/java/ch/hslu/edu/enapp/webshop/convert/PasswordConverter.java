package ch.hslu.edu.enapp.webshop.convert;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.xml.bind.annotation.adapters.HexBinaryAdapter;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;

@Converter
public final class PasswordConverter implements AttributeConverter<String, String> {

    private static final String HASHING_ALGORITHM = "SHA-256";

    @Override
    public String convertToDatabaseColumn(final String passwordPlain) {
        try {
            final MessageDigest md = MessageDigest.getInstance(HASHING_ALGORITHM);
            final byte[] digest = md.digest(passwordPlain.getBytes(StandardCharsets.UTF_8));
            return (new HexBinaryAdapter()).marshal(digest);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public String convertToEntityAttribute(final String passwordHash) {
        return passwordHash;
    }
}
