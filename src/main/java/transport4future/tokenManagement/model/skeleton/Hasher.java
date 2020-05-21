package Transport4Future.TokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

/**
 * Strategy base file for encoding.
 * We can't decode since when we hash, it is only a one-way algorithm.
 */
public interface Hasher<T> {
    /**
     *
     * @param toEncode
     * @return
     * @throws NoSuchAlgorithmException
     */
    public byte[] encode(T toEncode) throws NoSuchAlgorithmException;
    /**
     *
     * @param encodedData
     * @return
     */
    public String hex(byte[] encodedData);
}
