package Transport4Future.TokenManagement.model;

public class TokenRevoke {
    private String tokenValue; //"Token Value";
    private TokenRevokeType tokenRevokeType; //"Type of revocation":"< | Final >";
    private String reason; //"Reason":"<Cadena de 100 caracteres como máximo >" }

    public String getTokenValue() {
        return tokenValue;
    }

    public void setTokenValue(String tokenValue) {
        this.tokenValue = tokenValue;
    }

    public TokenRevokeType getTokenRevokeType() {
        return tokenRevokeType;
    }

    public void setTokenRevokeType(TokenRevokeType tokenRevokeType) {
        this.tokenRevokeType = tokenRevokeType;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }
}
