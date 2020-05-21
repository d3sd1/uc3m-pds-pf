package Transport4Future.TokenManagement.model.skeleton;

import java.security.NoSuchAlgorithmException;

/**
 *
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
     * @param toDecode
     * @return
     * @throws NoSuchAlgorithmException
     */
    // TODO public T decode(byte[] toDecode) throws NoSuchAlgorithmException;

    /**
     *
     * @param encodedData
     * @return
     */
    public String hex(byte[] encodedData);
}
