package lab2;

import com.google.gson.Gson;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class JsonSerializer<T> implements Serializer<T> {
    private Gson gson;
    private Type type;

    public JsonSerializer(Type type) {
        this.type = type;
        this.gson = new Gson();
    }

    @Override
    public String serialize(T entity) throws Exception {
        return gson.toJson(entity);
    }

    @Override
    public void serialize(T entity, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(entity, writer);
        }
    }

    @Override
    public void serialize(List<T> entities, String filePath) throws IOException {
        try (FileWriter writer = new FileWriter(filePath)) {
            gson.toJson(entities, writer);
        }
    }

    @Override
    public T deserialize(String strObj) throws IllegalArgumentException {
        try {
             return gson.fromJson(strObj, type);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Deserialization error", e);
        }
    }

//    @Override
//    public T deserializeFromFile(String filePath) throws IOException

    @Override
    public List<T> deserializeFromFile(String filePath) throws IOException {
        try (FileReader reader = new FileReader(filePath)) {
            return gson.fromJson(reader, type);
        }
    }

}
