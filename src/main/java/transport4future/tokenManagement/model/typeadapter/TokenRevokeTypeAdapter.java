package Transport4Future.TokenManagement.model.typeadapter;

import Transport4Future.TokenManagement.model.TokenRevoke;
import Transport4Future.TokenManagement.model.TokenRevokeType;
import Transport4Future.TokenManagement.model.skeleton.TransportTypeAdapter;
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
public class TokenRevokeTypeAdapter extends TypeAdapter<TokenRevoke> implements TransportTypeAdapter<TokenRevoke> {

    /**
     * From json to object.
     * we must check for integers since gson reflection does not divide strings nor ints, it threats it as the same
     *
     * @param reader JsonReader inhetired from Gson's TypeAdapter.
     * @return T obj filled.
     * @throws IOException If there is any issue.
     */
    @Override
    public TokenRevoke read(JsonReader reader) throws IOException {
        String tokenValue = "",
                reason = "";
        TokenRevokeType tokenRevokeType = null;
        boolean foundTokenValue = false,
                foundTypeOfRevocation = false,
                foundReason = false;
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
                    throw new JsonSyntaxException("Unexpected error on TokenRevokeTypeAdapter.");
                } else if (fieldname.equals("Token Value")) {
                    tokenValue = reader.nextString();
                    foundTokenValue = true;
                } else if (fieldname.equals("Type of revocation")) {
                    String typeOfRevocationStr = reader.nextString();
                    switch(typeOfRevocationStr.toLowerCase()) {
                        case "temporal":
                            tokenRevokeType = TokenRevokeType.Temporal;
                            break;
                        case "final":
                            tokenRevokeType = TokenRevokeType.Final;
                            break;
                    }
                    foundTypeOfRevocation = true;
                } else if (fieldname.equals("Reason")) {
                    reason = reader.nextString();
                    foundReason = true;
                } else {
                    throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
                }
            }
        } catch (IllegalStateException | EOFException | MalformedJsonException e) {
            throw new JsonSyntaxException("Error: JSON object cannot be created due to incorrect representation");
        }
        if (!foundTokenValue
                || !foundTypeOfRevocation
                || !foundReason) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();
        TokenRevoke tokenRevoke = new TokenRevoke(
                tokenValue,
                tokenRevokeType,
                reason
        );
        this.doConstraints(tokenRevoke);
        return tokenRevoke;
    }

    /**
     * Constraint checker. This function is impure, but tests are too, since Gson and tests are not
     * fully compatible, we have to do some parkour here =).
     * @param tokenRevoke T obj.
     * @throws JsonSyntaxException If there is any related error.
     */
    @Override
    public void doConstraints(TokenRevoke tokenRevoke) {
        if (tokenRevoke.getTokenValue() == null
                || tokenRevoke.getTokenRevokeType() == null
                || tokenRevoke.getReason() == null) {
            throw new JsonSyntaxException("El fichero de entrada tiene algún problema de formato o de acceso.");
        }

        if (tokenRevoke.getReason().length() > 100) {
            throw new JsonSyntaxException("Error: invalid reason length");
        }
    }

    /**
     * From object to json.
     * @param writer JsonWriter inhetired from Gson's TypeAdapter.
     * @param tokenRevoke T object
     * @throws IOException If there is any issue.
     */
    @Override
    public void write(JsonWriter writer, TokenRevoke tokenRevoke) throws IOException {
        writer.beginObject();
        writer.name("Token Value");
        writer.value(tokenRevoke.getTokenValue());
        writer.name("Type of revocation");
        writer.value(tokenRevoke.getTokenRevokeType().toString());
        writer.name("Reason");
        writer.value(tokenRevoke.getReason());
        writer.endObject();
    }
}