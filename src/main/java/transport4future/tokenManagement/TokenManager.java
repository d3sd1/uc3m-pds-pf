package transport4future.tokenManagement;

import transport4future.tokenManagement.exception.TokenManagementException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.StringReader;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import javax.json.Json;
import javax.json.JsonObject;


public class TokenManager {

    /**
     * Read token from json.
     * @author d3sd1
     * @param path - Physical path to token
     * @return TokenRequest Object
     * @throws TokenManagementException When file is not available.
     */
    public TokenRequest readTokenRequestFromJson(String path) throws TokenManagementException {
        return null;
    }
}
