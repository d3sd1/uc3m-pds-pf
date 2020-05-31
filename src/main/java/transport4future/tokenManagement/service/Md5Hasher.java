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

package transport4future.TokenManagement.service;

import transport4future.TokenManagement.model.skeleton.Hasher;

import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * The type Hash manager.
 * Follows Strategy pattern for Codificator basis.
 */
public class Md5Hasher implements Hasher<String> {
    /**
     * Md 5 encode byte [ ].
     *
     * @param toEncode the data to encode
     * @return the byte [ ]
     * @throws NoSuchAlgorithmException the no such algorithm exception
     */

    @Override
    public byte[] encode(String toEncode) throws NoSuchAlgorithmException {
        MessageDigest md = MessageDigest.getInstance("MD5");
        String input = "Stardust" + "-" + toEncode;
        md.update(input.getBytes(StandardCharsets.UTF_8));
        return md.digest();
    }

    /**
     * Gets sha md 5 hex.
     *
     * @param md5 the md 5
     * @return the sha md 5 hex
     */
    @Override
    public String hex(byte[] md5) {
        return String.format("%32x", new BigInteger(1, md5));
    }
}
