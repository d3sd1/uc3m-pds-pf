package Transport4Future.TokenManagement.model;

/**
 * The type Token execute action.
 */
public class TokenExecuteAction {
    private String tokenValue; //"Token Value": "< Cadena de caracteres >",
    private TokenOperationType tokenOperationType; // "Type of operation":"<Send Information from Sensor| Send Request to Actuator | Check State >"

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
}
