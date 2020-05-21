package Transport4Future.TokenManagement.model.skeleton;

import Transport4Future.TokenManagement.model.Token;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public interface TransportTypeAdapter<T> {
    public T read(JsonReader reader) throws IOException;
    void doConstraints(T obj);
    public void write(JsonWriter writer, T obj) throws IOException;
}
