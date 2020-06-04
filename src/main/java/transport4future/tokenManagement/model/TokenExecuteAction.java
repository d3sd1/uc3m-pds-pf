package transport4future.tokenManagement.model;

/**
 * The type Token execute action.
 */
public class TokenExecuteAction {
    private String tokenValue;
    private TokenOperationType tokenOperationType;

    /**
     * Instantiates a new Token execute action.
     *
     * @param tokenValue         the token value
     * @param tokenOperationType the token operation type
     */
    public TokenExecuteAction(String tokenValue, TokenOperationType tokenOperationType) {
        this.tokenValue = tokenValue;
        this.tokenOperationType = tokenOperationType;
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
     * Gets token operation type.
     *
     * @return the token operation type
     */
    public TokenOperationType getTokenOperationType() {
        return tokenOperationType;
    }

    /**
     * Sets token operation type.
     *
     * @param tokenOperationType the token operation type
     */
    public void setTokenOperationType(TokenOperationType tokenOperationType) {
        this.tokenOperationType = tokenOperationType;
    }

    /**
     *
     * @return String representation of object.
     */
    @Override
    public String toString() {
        return "TokenExecuteAction{" +
                "tokenValue='" + tokenValue + '\'' +
                ", tokenOperationType=" + tokenOperationType +
                '}';
    }
}
