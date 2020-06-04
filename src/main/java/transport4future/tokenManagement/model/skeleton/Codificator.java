package transport4future.tokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

/**
 * Strategy base file for encoding-decoding.
 * As two way algorithm, we face that Hasher is only one-way, and Codificator is double-way.
 *
 * @param <T> the type parameter
 */
public interface Codificator<T> {
    /**
     * Encodes string to String from T object.
     *
     * @param toEncode String to encode.
     * @return byte[] sequence of encoded object.
     * @throws NoSuchAlgorithmException If there is not known algorithm.
     */
    byte[] encode(T toEncode) throws NoSuchAlgorithmException;

    /**
     * Decodes string to T object.
     *
     * @param toDecode String to decode.
     * @return T object that has been decoded.
     * @throws NoSuchAlgorithmException If there is not known algorithm.
     */
    T decode(String toDecode) throws NoSuchAlgorithmException;
}
