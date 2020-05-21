package Transport4Future.TokenManagement.model;

public class TokenExecuteAction {
    private String tokenValue; //"Token Value": "< Cadena de caracteres >",
    private TokenOperationType tokenOperationType; // "Type of operation":"<Send Information from Sensor| Send Request to Actuator | Check State >"

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public TokenOperationType getTokenOperationType() {
        return tokenOperationType;
    }

    public void setTokenOperationType(TokenOperationType tokenOperationType) {
        this.tokenOperationType = tokenOperationType;
    }
}
