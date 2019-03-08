package ch.hslu.edu.enapp.webshop.dto;

public final class PaymentMethod {

    private final int id;
    private final String issuer;
    private final String cardNo;
    private final String encryptedCardNo;
    private final String cvc;
    private final String expiration;
    private final String imageUrl;

    public PaymentMethod(final int id, final String issuer, final String cardNo, final String cvc, final String expiration,
                         final String imageUrl) {
        this.id = id;
        this.issuer = issuer;
        this.cardNo = cardNo;
        this.encryptedCardNo = generateEncryptedCardNo(cardNo);
        this.cvc = cvc;
        this.expiration = expiration;
        this.imageUrl = imageUrl;
    }

    public int getId() {
        return this.id;
    }

    public String getIssuer() {
        return this.issuer;
    }

    public String getCardNo() {
        return this.cardNo;
    }

    public String getEncryptedCardNo() {
        return this.encryptedCardNo;
    }

    public String getCvc() {
        return this.cvc;
    }

    public String getExpiration() {
        return this.expiration;
    }

    public String getImageUrl() {
        return this.imageUrl;
    }

    private static String generateEncryptedCardNo(final String cardNo) {
        final int cardNoLen = cardNo.length();
        final String substrEncrypt = cardNo.substring(0, cardNoLen - 4);
        final String substrPlain = cardNo.substring(cardNoLen - 4, cardNoLen);
        final StringBuilder builder = new StringBuilder();
        builder.append(new String(new char[substrEncrypt.length()]).replace("\0", "*"));
        builder.append(substrPlain);
        return builder.toString();
    }
}
