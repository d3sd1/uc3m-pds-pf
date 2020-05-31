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

package transport4future.TokenManagement.model.skeleton;
import transport4future.TokenManagement.exception.TokenManagementException;

/**
 * The interface Token manager.
 */
public interface TokenManagerInterface {
    /**
     * Generate string.
     *
     * @param inputFile the input file
     * @return the string
     * @throws TokenManagementException the token management exception
     */
    String TokenRequestGeneration(String inputFile) throws TokenManagementException;

    /**
     * Request string.
     *
     * @param inputFile the input file
     * @return the string
     * @throws TokenManagementException the token management exception
     */
    String RequestToken(String inputFile) throws TokenManagementException;

    /**
     * Verify boolean.
     *
     * @param encodedToken the encoded token
     * @return the boolean
     * @throws TokenManagementException the token management exception
     */
    boolean VerifyToken(String encodedToken) throws TokenManagementException;


    /**
     * Revoke token string.
     *
     * @param FilePath the file path
     * @return String represents the notification e-mail.
     * @throws TokenManagementException the token management exception
     */
    String RevokeToken (String FilePath) throws TokenManagementException;

    /**
     * Execute action on autonomous car given it's token.
     *
     * @param FilePath the file path
     * @return Boolean represents the success in the action execution
     * @throws TokenManagementException represents represents the possible error situations
     */
    boolean ExecuteAction (String FilePath) throws TokenManagementException;
}
