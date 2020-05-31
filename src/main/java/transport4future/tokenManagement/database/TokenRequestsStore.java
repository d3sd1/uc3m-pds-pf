/*
 * Copyright (c) 2020.
 * Content created by:
 * - Andrei García Cuadra
 * - Miguel Hernández Cassel
 *
 * For the module PDS, on university Carlos III de Madrid.
 * Do not share, review nor edit any content without implicitly asking permission to it's owners, as you can contact by this email:
 * andreigarciacuadra@gmail.com
 *
 * All rights reserved.
 */

package Transport4Future.TokenManagement.database;

import Transport4Future.TokenManagement.config.Constants;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.TokenRequest;
import Transport4Future.TokenManagement.model.skeleton.Database;
import Transport4Future.TokenManagement.service.FileManager;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.HashMap;

/**
 * The type Token request database.
 */
public class TokenRequestsStore extends Database<TokenRequest> {
    /**
     * Singleton instance.
     */
    protected static TokenRequestsStore database;
    /**
     * The In memory db.
     */
    protected static HashMap<String, TokenRequest> inMemoryDb;


    /**
     * Private instantiation, so they must implement this as singleton.
     */
    private TokenRequestsStore() {
        super();
    }


    /**
     * Init database requirements, and instantiate it.
     *
     */
    @Override
    protected void initDatabase() {
        try {
            FileManager fileManager = new FileManager();
            fileManager.createJsonFileIfNotExists(Constants.TOKEN_REQUEST_STORAGE_FILE, new HashMap<>());
        } catch (IOException e) {

        }
    }

    /**
     * This returns a singleton instance from TokenRequestsStore.
     *
     * @return TokenManager singleton instance.
     */
    public static TokenRequestsStore getInstance() {
        if (database == null) {
            database = new TokenRequestsStore();
            database.initDatabase();
        }
        return database;
    }

    /**
     * Adds a TokenRequest to database.
     * @param tokenRequest TokenRequest to add to database.
     * @throws TokenManagementException If there was any issue on the add operation.
     */
    @Override
    public void add(TokenRequest tokenRequest) throws TokenManagementException {
        this.reload();
        if (!inMemoryDb.containsKey(tokenRequest.getHex())) {
            inMemoryDb.put(tokenRequest.getHex(), tokenRequest);
            this.save();
        }
    }

    /**
     * Flush the database with current values.
     * @throws TokenManagementException If there was any issue flushing the database.
     */
    @Override
    protected void save() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        try {
            fileManager.writeObjectToJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, inMemoryDb);
        } catch (Exception e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }


    /**
     * Refresh database.
     * @throws TokenManagementException When there is any error related to that refresh.
     */
    @Override
    protected void reload() throws TokenManagementException {
        FileManager fileManager = new FileManager();
        try {
            inMemoryDb = fileManager.readJsonFile(Constants.TOKEN_REQUEST_STORAGE_FILE, new TypeToken<HashMap<String, TokenRequest>>(){}.getType());
        } catch (Exception e) {
            throw new TokenManagementException("Error: unable to recover Token Requests Store.");
        }
    }

    /**
     * Is request registered.
     *
     * @param token the token
     * @throws TokenManagementException the token management exception
     */
    public void isRequestRegistered(Token token) throws TokenManagementException {
        this.reload();
        if (inMemoryDb == null || !inMemoryDb.containsKey(token.getTokenRequest())) {
            throw new TokenManagementException("Error: Token Request Not Previously Registered");
        }
    }
    /**
     * Attempts to find any token on database.
     *
     * @param hexTokenRequest TokenRequest to find on database.
     * @return Found token, either null.
     */
    public TokenRequest find(String hexTokenRequest) {
        return inMemoryDb != null ? inMemoryDb.get(hexTokenRequest):null;
    }


    /**
     * Prevents current object being cloned, so follow singleton pattern.
     * @return Never returns anything.
     * @throws CloneNotSupportedException Throwed always.
     */
    @Override
    public TokenRequestsStore clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }

}
