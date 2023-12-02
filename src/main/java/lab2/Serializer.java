package lab2;

import java.io.IOException;
import java.util.List;

public interface Serializer<T> {
    String serialize(T entity) throws Exception;
    void serialize(T entity, String filePath) throws IOException;
    void serialize(List<T> entities, String filePath) throws IOException;
    T deserialize(String strObj) throws Exception;
    List<T> deserializeFromFile(String filePath) throws IOException;
}
