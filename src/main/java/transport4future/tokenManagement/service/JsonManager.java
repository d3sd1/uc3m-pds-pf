package transport4future.TokenManagement.service;

import transport4future.TokenManagement.config.Constants;
import transport4future.TokenManagement.model.Token;
import transport4future.TokenManagement.model.TokenExecuteAction;
import transport4future.TokenManagement.model.TokenRequest;
import transport4future.TokenManagement.model.TokenRevoke;
import transport4future.TokenManagement.model.typeadapter.TokenExecuteActionTypeAdapter;
import transport4future.TokenManagement.model.typeadapter.TokenRequestTypeAdapter;
import transport4future.TokenManagement.model.typeadapter.TokenRevokeTypeAdapter;
import transport4future.TokenManagement.model.typeadapter.TokenTypeAdapter;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.Type;

/**
 * This class manages the IO on Json object - text.
 */
public class JsonManager {
    /**
     * Prepares the Gson class with our customs type adapters.
     *
     * @return Gson object created with the builder.
     */
    private Gson getDeserializer() {
        GsonBuilder builder = new GsonBuilder();
        if (Constants.IS_DEV) {
            builder.setPrettyPrinting();
        }
        builder.create();
        builder.registerTypeAdapter(Token.class, new TokenTypeAdapter());
        builder.registerTypeAdapter(TokenRequest.class, new TokenRequestTypeAdapter());
        builder.registerTypeAdapter(TokenRevoke.class, new TokenRevokeTypeAdapter());
        builder.registerTypeAdapter(TokenExecuteAction.class, new TokenExecuteActionTypeAdapter());
        return builder.create();
    }

    /**
     * Encode a object to json.
     *
     * @param <T> The TypeToken reflection class that is the object class.
     * @param obj The object to encode.
     * @return JSON string.
     */
    public <T> String encode(T obj) {
        return this.getDeserializer().toJson(
                obj
        );
    }

    /**
     * Decode json to object.
     *
     * @param <T>              The TypeToken reflection class that is the object class.
     * @param json             JSON string.
     * @param deserializeClass Class to reflect.
     * @return Reflected object.
     */
    public <T> T decode(String json, Class<T> deserializeClass) {
        return this.getDeserializer().fromJson(json, deserializeClass);
    }

    /**
     * Decode json to object.
     *
     * @param <T>  The TypeToken reflection class that is the object class.
     * @param json JSON string.
     * @param type Type class to reflect.
     * @return Reflected object.
     */
    public <T> T decode(String json, Type type) {
        return this.getDeserializer().fromJson(json, type);
    }
}
