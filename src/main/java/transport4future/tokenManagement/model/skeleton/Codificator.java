package Transport4Future.TokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

/**
 * Strategy base file for encoding-decoding.
 * As two way algorithm, we face that Hasher is only one-way, and Codificator is double-way.
 */
public interface Codificator<T> {
    /**
     *
     * @param toEncode
     * @return
     * @throws NoSuchAlgorithmException
     */
    public byte[] encode(T toEncode) throws NoSuchAlgorithmException;

    /**
     *
     * @param toDecode
     * @return
     * @throws NoSuchAlgorithmException
     */
    public T decode(String toDecode) throws NoSuchAlgorithmException;
}
