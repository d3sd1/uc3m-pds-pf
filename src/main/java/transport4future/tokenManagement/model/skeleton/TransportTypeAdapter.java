package transport4future.TokenManagement.model.skeleton;

import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * The interface Transport type adapter.
 *
 * @param <T> the type parameter
 */
public interface TransportTypeAdapter<T> {
    /**
     * Read t.
     *
     * @param reader the reader
     * @return the t
     * @throws IOException the io exception
     */
    T read(JsonReader reader) throws IOException;

    /**
     * Do constraints.
     *
     * @param obj the obj
     */
    void doConstraints(T obj);

    /**
     * Write.
     *
     * @param writer the writer
     * @param obj    the obj
     * @throws IOException the io exception
     */
    void write(JsonWriter writer, T obj) throws IOException;
}
