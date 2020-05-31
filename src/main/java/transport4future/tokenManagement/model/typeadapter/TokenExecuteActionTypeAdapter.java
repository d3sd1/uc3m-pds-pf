package transport4future.TokenManagement.model.typeadapter;

import transport4future.TokenManagement.model.TokenExecuteAction;
import transport4future.TokenManagement.model.TokenOperationType;
import transport4future.TokenManagement.model.skeleton.TransportTypeAdapter;
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
public class TokenExecuteActionTypeAdapter extends TypeAdapter<TokenExecuteAction> implements TransportTypeAdapter<TokenExecuteAction> {

    /**
     * From json to object.
     * we must check for integers since gson reflection does not divide strings nor ints, it threats it as the same
     *
     * @param reader JsonReader inhetired from Gson's TypeAdapter.
     * @return T obj filled.
     * @throws IOException If there is any issue.
     */
    @Override
    public TokenExecuteAction read(JsonReader reader) throws IOException {
        String tokenValue = "";
        TokenOperationType tokenOperationType = null;
        boolean foundTokenValue = false,
                foundTypeOfOperation = false;
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
                } else if (fieldname.equals("Type of operation")) {
                    String typeOfOperationStr = reader.nextString();
                    switch(typeOfOperationStr.toLowerCase()) {
                        case "send information from sensor":
                            tokenOperationType = TokenOperationType.SendInformationFromSensor;
                            break;
                        case "send request to actuator":
                            tokenOperationType = TokenOperationType.SendRequestToActuator;
                            break;
                        case "check state":
                            tokenOperationType = TokenOperationType.CheckState;
                            break;
                    }
                    foundTypeOfOperation = true;
                } else {
                    throw new JsonSyntaxException("El fichero de entrada tiene algún problema de formato o de acceso.");
                }
            }
        } catch (IllegalStateException | EOFException | MalformedJsonException e) {
            throw new JsonSyntaxException("El fichero de entrada tiene algún problema de formato o de acceso.");
        }
        if (!foundTokenValue
                || !foundTypeOfOperation) {
            throw new JsonSyntaxException("Error: invalid input data in JSON structure.");
        }
        reader.endObject();
        TokenExecuteAction tokenRevoke = new TokenExecuteAction(
                tokenValue,
                tokenOperationType
        );
        this.doConstraints(tokenRevoke);
        return tokenRevoke;
    }

    /**
     * Constraint checker. This function is impure, but tests are too, since Gson and tests are not
     * fully compatible, we have to do some parkour here =).
     * @param tokenExecuteAction T obj.
     * @throws JsonSyntaxException If there is any related error.
     */
    @Override
    public void doConstraints(TokenExecuteAction tokenExecuteAction) {
        if (tokenExecuteAction.getTokenValue() == null
                || tokenExecuteAction.getTokenOperationType() == null) {
            throw new JsonSyntaxException("El fichero de entrada tiene algún problema de formato o de acceso.");
        }

    }

    /**
     * From object to json.
     * @param writer JsonWriter inhetired from Gson's TypeAdapter.
     * @param tokenExecuteAction T object
     * @throws IOException If there is any issue.
     */
    @Override
    public void write(JsonWriter writer, TokenExecuteAction tokenExecuteAction) throws IOException {
        writer.beginObject();
        writer.name("Token Value");
        writer.value(tokenExecuteAction.getTokenValue());
        writer.name("Type of operation");
        writer.value(tokenExecuteAction.getTokenOperationType().toString());
        writer.endObject();
    }
}