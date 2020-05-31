package transport4future.TokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

/**
 * Strategy base file for encoding.
 * We can't decode since when we hash, it is only a one-way algorithm.
 *
 * @param <T> the type parameter
 */
public interface Hasher<T> {
    /**
     * Encodes string to String from T object.
     *
     * @param toEncode String to encode.
     * @return byte[] sequence of encoded object.
     * @throws NoSuchAlgorithmException If there is not known algorithm.
     */
    byte[] encode(T toEncode) throws NoSuchAlgorithmException;

    /**
     * Gives the HexCode for a given encoded data.
     *
     * @param encodedData Encoded data to hex.
     * @return String matching HexCode of given byte object.
     */
    String hex(byte[] encodedData);
}
