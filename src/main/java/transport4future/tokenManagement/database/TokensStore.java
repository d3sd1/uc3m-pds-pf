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

package transport4future.tokenManagement.database;

import transport4future.tokenManagement.config.Constants;
import transport4future.tokenManagement.exception.TokenManagementException;
import transport4future.tokenManagement.model.Token;
import transport4future.tokenManagement.model.skeleton.Database;
import transport4future.tokenManagement.model.typeadapter.TokenTypeAdapter;
import transport4future.tokenManagement.service.FileManager;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * The type Token database.
 */
public class TokensStore extends Database<Token> {
    /**
     * Singleton instance.
     */
    protected static TokensStore database;
    /**
     * The In memory db.
     */
    protected static List<Token> inMemoryDb;

    /**
     * Private instantiation, so they must implement this as singleton.
     */
    private TokensStore() {
        super();
    }

    /**
     * Init database requirements, and instantiate it.
     */
    @Override
    protected void initDatabase() {
        try {
            FileManager fileManager = new FileManager();
            fileManager.createJsonFileIfNotExists(Constants.TOKEN_STORAGE_FILE, new ArrayList<>());
            database.reload();
        } catch (IOException e) {
        }
    }

    /**
     * This returns a singleton instance from TokenRequestsStore.
     *
     * @return TokenManager singleton instance.
     */
    public static TokensStore getInstance() {
        if (database == null) {
            database = new TokensStore();
            database.initDatabase();
        }
        return database;
    }

    /**
     * Adds a token to database.
     *
     * @param newToken Token to add to database.
     * @throws TokenManagementException If there was any issue on the add operation.
     */
    @Override
    public void add(Token newToken) throws TokenManagementException {
        if (this.find(newToken) == null) {
            if (newToken.getDeviceType() == null) {
                newToken.setDeviceType(TokenTypeAdapter.getTokenDeviceType(newToken.getTokenRequest()));
            }
            inMemoryDb.add(newToken);
            this.save();
        }
    }

    /**
     * Flush the database with current values.
     *
     * @throws TokenManagementException If there was any issue flushing the database.
     */
    @Override
    protected void save() throws TokenManagementException {
        try {
            FileManager fileManager = new FileManager();
            if (inMemoryDb != null) {
                fileManager.writeObjectToJsonFile(Constants.TOKEN_STORAGE_FILE, inMemoryDb);
            }
        } catch (Exception e) {
            throw new TokenManagementException("Error: Unable to save a new token in the internal licenses store");
        }
    }

    /**
     * Attempts to find any token on database.
     *
     * @param tokenToFind Token to find on database.
     * @return Found token, either null.
     */
    public Token find(Token tokenToFind) {
        Token result = null;
        this.reload();
        for (Token token : inMemoryDb) {
            if (token.equals(tokenToFind)) {
                result = token;
            }
        }
        return result;
    }

    /**
     * Refresh database.
     */
    @Override
    protected void reload() {
        FileManager fileManager = new FileManager();
        try {
            List<Token> tokens = fileManager.readJsonFile(Constants.TOKEN_STORAGE_FILE, new TypeToken<List<Token>>() {
            }.getType());
            if (tokens == null) {
                throw new JsonSyntaxException("No tokens previously stored");
            }
        } catch (Exception e) {
            inMemoryDb = new ArrayList<Token>();
        }
    }

    /**
     * Prevents current object being cloned, so follow singleton pattern.
     *
     * @return Never returns anything.
     * @throws CloneNotSupportedException Throwed always.
     */
    @Override
    public TokensStore clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}
