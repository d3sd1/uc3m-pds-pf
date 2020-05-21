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

package Transport4Future.TokenManagement.controller;

import Transport4Future.TokenManagement.database.TokenRequestsStore;
import Transport4Future.TokenManagement.database.TokensStore;
import Transport4Future.TokenManagement.exception.TokenManagementException;
import Transport4Future.TokenManagement.model.*;
import Transport4Future.TokenManagement.model.skeleton.TokenManagerInterface;
import Transport4Future.TokenManagement.service.FileManager;
import Transport4Future.TokenManagement.service.TokenCodificator;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.MalformedJsonException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;


/**
 * This class manages tokens, so it's purely a controller that handles actions on Tokens.
 */
public class TokenManager implements TokenManagerInterface {
    /**
     * Singleton instance.
     */
    private static TokenManager instance;

    /**
     * Private constructor due to Singleton instantiation.
     */
    private TokenManager() {
        super();
    }

    /**
     * This returns a singleton instance from TokenManager.
     * @return TokenManager singleton instance.
     */
    public static TokenManager getInstance() {
        if (instance == null) {
            instance = new TokenManager();
        }
        return instance;
    }

    /**
     * This function requests a TokenRequest, that will be needed to operate with methods.
     * @param inputFile Input file path.
     * @return String with tokenRequest Hex.
     * @throws TokenManagementException Is thrown with a descriptive message.
     */
    @Override
    public String TokenRequestGeneration(String inputFile) throws TokenManagementException {
        TokenRequest tokenRequest;
        String hex;
        FileManager fileManager = new FileManager();
        TokenRequestsStore tokenRequestsStore = TokenRequestsStore.getInstance();

        try {
            tokenRequest = fileManager.readJsonFile(inputFile, TokenRequest.class);
        } catch (JsonSyntaxException e) {
            throw new TokenManagementException(e.getMessage());
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        } catch (NullPointerException e) {
            throw new TokenManagementException("Error: invalid input data in JSON structure.");
        }

        try {
            hex = tokenRequest.getHex();
        } catch (NullPointerException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }

        tokenRequestsStore.add(tokenRequest);

        return hex;
    }

    /**
     * This function requests a token based on previous TokenRequest operation.
     * @param inputFile Input file path.
     * @return String with hashed token.
     * @throws TokenManagementException Is thrown with a descriptive message.
     */
    @Override
    public String RequestToken(String inputFile) throws TokenManagementException {
        Token token = null;
        FileManager fileManager = new FileManager();
        TokenRequestsStore tokenRequestsStore = TokenRequestsStore.getInstance();
        TokensStore myStore = TokensStore.getInstance();

        try {
            token = fileManager.readJsonFile(inputFile, Token.class);
        } catch (FileNotFoundException e) {
            throw new TokenManagementException("Error: input file not found.");
        } catch (JsonSyntaxException e) {
            throw new TokenManagementException(e.getMessage());
        } catch (IOException e) {
            throw new TokenManagementException("Error: JSON object cannot be created due to incorrect representation");
        }

        tokenRequestsStore.isRequestRegistered(token);
        TokenCodificator tokenHasher = new TokenCodificator();

        String hashedToken;
        try {
            hashedToken = new String(tokenHasher.encode(token), StandardCharsets.UTF_8);
            myStore.add(token);
        } catch (NoSuchAlgorithmException e) {
            throw new TokenManagementException("Error: no such hashing algorithm.");
        } catch (Exception e) {
            throw new TokenManagementException("Error: could not encode token request.");
        }

        return hashedToken;
    }


    /**
     * This function verifies if a base64token is valid.
     * @param base64EncodedToken Base 64 encoded json token.
     * @return Boolean marking if the action was (or not) success.
     * @throws TokenManagementException Is thrown with a descriptive message.
     */
    @Override
    public boolean VerifyToken(String base64EncodedToken) throws TokenManagementException {

        TokenCodificator tokenHasher = new TokenCodificator();
        Token decodedToken;
        try {
            decodedToken = tokenHasher.decode(base64EncodedToken);
        } catch(Exception e) {
            e.printStackTrace();
            return false;
        }
        Token tokenFound = TokensStore.getInstance().find(decodedToken);
        if (tokenFound != null) {
            return tokenFound.isValid();
        }
        return false;
    }

    /**
     * This function revokes token access to a non-expired and non-revoked already token.
     * @param inputFile Input file path.
     * @return String with email of the token that was updated.
     * @throws TokenManagementException Is thrown with a descriptive message.
     */
    @Override
    public String RevokeToken(String inputFile) throws TokenManagementException {

        FileManager fileManager = new FileManager();
        TokenRevoke tokenRevoke;
        TokenCodificator tokenHasher = new TokenCodificator();
        Token decodedToken;
        //TODO: el typeadapter!
        try {
            tokenRevoke = fileManager.readJsonFile(inputFile, TokenRevoke.class);
            decodedToken = tokenHasher.decode(tokenRevoke.getTokenValue());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new TokenManagementException("El fichero de entrada tiene algún problema de formato o de acceso.");
        } catch (JsonSyntaxException e) {
            throw new TokenManagementException(e.getMessage());
        }

        Token tokenFound = TokensStore.getInstance().find(decodedToken);
        if (tokenFound == null) {
            throw new TokenManagementException("El token que se quiere revocar no existe.");
        } else if(tokenFound.isExpired()) {
            throw new TokenManagementException("El token que se quiere revocar ya ha caducado.");
        } else if(tokenRevoke.getTokenRevokeType() == decodedToken.getTokenRevokeType()) {
            throw new TokenManagementException("El token que se quiere revocar ya está revocado en la misma modalidad.");
        }
        decodedToken.setTokenRevokeType(tokenRevoke.getTokenRevokeType());
        decodedToken.setTokenRevokeReason(tokenRevoke.getReason());
        return decodedToken.getNotificationEmail();
    }

    /**
     * This function executes an action on a car given it's token.
     * @param inputFile Input file path.
     * @return Boolean marking if the action was (or not) success.
     * @throws TokenManagementException Is thrown with a descriptive message.
     */
    @Override
    public boolean ExecuteAction(String inputFile) throws TokenManagementException {

        FileManager fileManager = new FileManager();
        TokenExecuteAction tokenExecuteAction;
        TokenCodificator tokenHasher = new TokenCodificator();
        Token decodedToken;
        //TODO: el tupeadapter!
        try {
            tokenExecuteAction = fileManager.readJsonFile(inputFile, TokenExecuteAction.class);
            decodedToken = tokenHasher.decode(tokenExecuteAction.getTokenValue());
        } catch (IOException | NoSuchAlgorithmException e) {
            throw new TokenManagementException("El fichero de entrada tiene algún problema de formato o de acceso.");
        } catch (JsonSyntaxException e) {
            throw new TokenManagementException(e.getMessage());
        }

        boolean executedSuccess = false;


        boolean isOperationValid = false;

        String tokenType = "Sensor"; /// O Actuator //TODO
        if(tokenType == "Sensor" && tokenExecuteAction.getTokenOperationType() == TokenOperationType.SendInformationFromSensor) {
            isOperationValid = true;
        } else if(tokenType == "Actuator" && tokenExecuteAction.getTokenOperationType() == TokenOperationType.SendRequestToActuator) {
            isOperationValid = true;
        } else if((tokenType == "Actuator" || tokenType == "Sensor") && tokenExecuteAction.getTokenOperationType() == TokenOperationType.CheckState) {
            isOperationValid = true;
        }
        if(!isOperationValid) {
            throw new TokenManagementException("La operación solicitada no puede ser realizada con el token adjuntado a la solicitud.");
        }

        return executedSuccess;
    }

    /**
     * Prevents current object being cloned, so follow singleton pattern.
     * @return Never returns anything.
     * @throws CloneNotSupportedException Throwed always.
     */
    @Override
    public TokenManager clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException();
    }
}