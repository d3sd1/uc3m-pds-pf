package Transport4Future.TokenManagement.model;

/**
 * The type Token revoke.
 */
public class TokenRevoke {
    private String tokenValue; //"Token Value";
    private TokenRevokeType tokenRevokeType; //"Type of revocation":"< | Final >";
    private String reason; //"Reason":"<Cadena de 100 caracteres como máximo >" }

    /**
     * Gets token value.
     *
     * @return the token value
     */
    public String getTokenValue() {
        return tokenValue;
    }

    /**
     * Sets token value.
     *
     * @param tokenValue the token value
     */
    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    /**
     * Gets token revoke type.
     *
     * @return the token revoke type
     */
    public TokenRevokeType getTokenRevokeType() {
        return tokenRevokeType;
    }

    /**
     * Sets token revoke type.
     *
     * @param tokenRevokeType the token revoke type
     */
    public void setTokenRevokeType(TokenRevokeType tokenRevokeType) {
        this.tokenRevokeType = tokenRevokeType;
    }

    /**
     * Gets reason.
     *
     * @return the reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * Sets reason.
     *
     * @param reason the reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }
}
