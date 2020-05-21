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

package Transport4Future.TokenManagement.service;

import Transport4Future.TokenManagement.model.Token;
import Transport4Future.TokenManagement.model.skeleton.Hasher;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import javax.crypto.spec.PSource;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;
import java.util.Base64;

/**
 * The type Hash manager.
 * Follows Strategy pattern.
 */
public class TokenHasher implements Hasher<Token> {

    /**
     * Sha 256 encode byte [ ].
     *
     * @param tokenToEncode the data to sign
     * @return the byte [ ]
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */
    @Override
    public byte[] encode(Token tokenToEncode) throws NoSuchAlgorithmException {
        Gson gson = new Gson();
        return Base64.getEncoder().encodeToString(gson.toJson(tokenToEncode).getBytes()).getBytes();
    }

    public Token decode(String base64) throws NoSuchAlgorithmException {
        Gson gson = new Gson();
        return gson.fromJson(new String(Base64.getDecoder().decode(base64), StandardCharsets.UTF_8), Token.class);
    }

    /**
     * Gets sha 256 hex.
     *
     * @param sha256 the sha 256
     * @return the sha 256 hex
     */
    @Override
    public String hex(byte[] sha256) {
        return String.format("%064x", new BigInteger(1, sha256));
    }
}
