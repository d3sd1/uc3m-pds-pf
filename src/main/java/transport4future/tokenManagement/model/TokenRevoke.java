package transport4future.TokenManagement.model;

/**
 * The type Token revoke.
 */
public class TokenRevoke {
    private String tokenValue;
    private TokenRevokeType tokenRevokeType;
    private String reason;

    /**
     * Instantiates a new Token revoke.
     *
     * @param tokenValue      the token value
     * @param tokenRevokeType the token revoke type
     * @param reason          the reason
     */
    public TokenRevoke(String tokenValue, TokenRevokeType tokenRevokeType, String reason) {
        this.tokenValue = tokenValue;
        this.tokenRevokeType = tokenRevokeType;
        this.reason = reason;
    }

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
