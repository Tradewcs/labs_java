package lab2;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

public class XmlSerializer<T> implements Serializer<T> {
    private XmlMapper xmlMapper;
    private Type type;

    public XmlSerializer(Type type) {
        this.type = type;
        this.xmlMapper = new XmlMapper();
    }

    @Override
    public String serialize(T entity) throws Exception {
        return xmlMapper.writeValueAsString(entity);
    }

    @Override
    public void serialize(T entity, String filePath) throws IOException {
        xmlMapper.writeValue(new File(filePath), entity);
//        try (FileWriter writer = new FileWriter(filePath)) {
//            xmlMapper.writeValue(writer, entity);
//        }
    }

    @Override
    public void serialize(List<T> entities, String filePath) throws IOException {
        xmlMapper.writeValue(new File(filePath), entities);
//        try (FileWriter writer = new FileWriter(filePath)) {
//            xmlMapper.writeValue(writer, entities);
//        }
    }

    @Override
    public T deserialize(String strObj) throws Exception {
        return xmlMapper.readValue(strObj, xmlMapper.constructType(type));
    }

    @Override
    public List<T> deserializeFromFile(String filePath) throws IOException {
        return xmlMapper.readValue(new File(filePath), xmlMapper.constructType(type));
//        try (FileReader reader = new FileReader(filePath)) {
//            return xmlMapper.readValue(reader, xmlMapper.constructType(type));
//        }
    }

}
