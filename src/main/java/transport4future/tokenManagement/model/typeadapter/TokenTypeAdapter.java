package transport4future.TokenManagement.model.typeadapter;

import transport4future.TokenManagement.config.RegexConstants;
import transport4future.TokenManagement.database.TokenRequestsStore;
import transport4future.TokenManagement.model.Token;
import transport4future.TokenManagement.model.TokenDeviceType;
import transport4future.TokenManagement.model.TokenRequest;
import transport4future.TokenManagement.model.TokenRevokeType;
import transport4future.TokenManagement.model.skeleton.TransportTypeAdapter;
import transport4future.TokenManagement.service.PatternChecker;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import com.google.gson.stream.MalformedJsonException;

import java.io.EOFException;
import java.io.IOException;

/**
 * The type Token type adapter.
 */
public class TokenTypeAdapter extends TypeAdapter<Token> implements TransportTypeAdapter<Token> {

    /**
     * From json to object.
     * we must check for integers since gson reflection does not divide strings nor ints, it threats it as the same
     *
     * @param reader JsonReader inhetired from Gson's TypeAdapter.
     * @return T obj filled.
     * @throws IOException If there is any issue.
     */
    @Override
    public Token read(JsonReader reader) throws IOException {
        String tokenRequest = "",
                notificationEmail = "",
                requestDate = "",
                revokeType = "",
                revokeReason = "";
        boolean foundTokenRequest = false,
                foundNotificationEmail = false,
                foundRequestDate = false,
                foundRevokeType = false,
                foundRevokeReason = false;
        try {
            reader.setLenient(false);
            reader.beginObject();
            String fieldname = null;

            while (reader.hasNext()) {
                JsonToken token = reader.peek();
                if (token.equals(JsonToken.NAME)) {
                    fieldname = reader.nextName();
                }

                if (fieldname == null) {
                    throw new JsonSyntaxException("Unexpected errorr on TokenRequestDeserializer.");
                } else if (fieldname.equals("Token Request")) {
                    tokenRequest = reader.nextString();
                    foundTokenRequest = true;
                } else if (fieldname.equals("Notification e-mail")) {
                    notificationEmail = reader.nextString();
                    foundNotificationEmail = true;
                } else if (fieldname.equals("Request Date")) {
                    requestDate = reader.nextString();
                    foundRequestDate = true;
                } else if (fieldname.equals("Revoke Type")) {
                    revokeType = reader.nextString();
                    foundRevokeType = true;
                } else if (fieldname.equals("Revoke Reason")) {
                    revokeReason = reader.nextString();
                    foundRevokeReason = true;
                } else {
                    throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
                }
            }
        } catch (IllegalStateException | EOFException | MalformedJsonException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (!foundTokenRequest
                || !foundNotificationEmail
                || !foundRequestDate) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();

        TokenDeviceType tokenDeviceType = getTokenDeviceType(tokenRequest);
        Token token = new Token(
                tokenRequest,
                notificationEmail,
                requestDate,
                tokenDeviceType
        );
        try {
            token.setTokenRevokeType(TokenRevokeType.valueOf(revokeType));
        } catch(Exception e) {

        }
        token.setTokenRevokeReason(revokeReason);
        this.doConstraints(token);
        return token;
    }

    /**
     * Constraint checker. This function is impure, but tests are too, since Gson and tests are not
     * fully compatible, we have to do some parkour here =).
     *
     * @param token T obj.
     * @throws JsonSyntaxException If there is any related error.
     */
    @Override
    public void doConstraints(Token token) {

        if (token.getTokenRequest() == null
                && token.getNotificationEmail() == null
                && token.getRequestDate() == null) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (token.getTokenRequest() == null
                || token.getNotificationEmail() == null
                || token.getRequestDate() == null) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        if (token.getTokenRequest().contains("\"")) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }

        PatternChecker patternChecker = new PatternChecker();
        if (patternChecker.checkRegex(token.getTokenRequest(), RegexConstants.DEVICE)) {
            throw new JsonSyntaxException("Error: invalid Device in token request.");
        }

        if (patternChecker.checkRegex(token.getNotificationEmail(), RegexConstants.EMAIL_RFC822)) {
            throw new JsonSyntaxException("Error: invalid E-mail data in JSON structure.");
        }
        if (patternChecker.checkRegex(token.getRequestDate(), RegexConstants.JSON_DATE_FORMAT)) {
            throw new JsonSyntaxException("Error: invalid date data in JSON structure.");
        }
    }

    /**
     * From object to json.
     *
     * @param writer JsonWriter inhetired from Gson's TypeAdapter.
     * @param token  T object
     * @throws IOException If there is any issue.
     */
    @Override
    public void write(JsonWriter writer, Token token) throws IOException {
        writer.beginObject();
        writer.name("Token Request");
        writer.value(token.getTokenRequest());
        writer.name("Notification e-mail");
        writer.value(token.getNotificationEmail());
        writer.name("Request Date");
        writer.value(token.getRequestDate());
        writer.name("Revoke Type");
        if(token.getTokenRevokeType() != null) {
            writer.value(token.getTokenRevokeType().toString());
        } else {
            writer.value("");
        }
        writer.name("Revoke Reason");
        writer.value(token.getTokenRevokeReason());
        writer.endObject();
    }

    public static TokenDeviceType getTokenDeviceType(String tokenRequest) {
        TokenRequest tokenRequestDecoded = TokenRequestsStore.getInstance().find(tokenRequest);
        TokenDeviceType tokenDeviceType = null;
        if (tokenRequestDecoded != null) {
            switch (tokenRequestDecoded.getTypeOfDevice().toLowerCase()) {
                case "actuator":
                    tokenDeviceType = TokenDeviceType.Actuator;
                    break;
                case "sensor":
                    tokenDeviceType = TokenDeviceType.Sensor;
                    break;
            }
        }
        return tokenDeviceType;
    }
}