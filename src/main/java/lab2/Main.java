package lab2;

import lab1.DirectoryEntry;
import lab1.FileEntry;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.lang.reflect.Type;
import com.google.gson.reflect.TypeToken;

public class Main {
    
    public static void main_Json(String[] args) throws Exception {
        DirectoryEntry root = new DirectoryEntry("/");
        DirectoryEntry current = root;
        current.addFile(new FileEntry("f1", 57));
        current.addSubDirectory(new DirectoryEntry("d1"));

        DirectoryEntry r2 = new DirectoryEntry(".");
        r2.addFile(new FileEntry("fe1", 302));

        Type directoryEntryListType = new TypeToken<List<DirectoryEntry>>() {}.getType();
        JsonSerializer<DirectoryEntry> jsonSerializer = new JsonSerializer<>(directoryEntryListType);

        ArrayList<DirectoryEntry> directories = new ArrayList<>();
        directories.add(root);
        directories.add(r2);
        jsonSerializer.serialize(directories, "file");


//        DirectoryEntry root1 = jsonSerializer.deserialize("file");
        jsonSerializer.deserialize("file");
        List<DirectoryEntry> ds = jsonSerializer.deserializeFromFile("file");
        for (DirectoryEntry de : ds.get(0).getDirectories()) {
            System.out.println(de.getName());
        }
//        System.out.println(root1.get(1).getName());
        
//        for (DirectoryEntry de : deser.getDirectories()) {
//            System.out.println(de.getName());
//        }
        
    }

    public static void main2(String[] args) throws Exception {
        DirectoryEntry root = new DirectoryEntry("/");
        DirectoryEntry current = root;
        current.addFile(new FileEntry("f1", 157));
        current.addSubDirectory(new DirectoryEntry("d1"));

        DirectoryEntry r2 = new DirectoryEntry(".");
        r2.addFile(new FileEntry("fe1", 202));

        ArrayList<DirectoryEntry> directories = new ArrayList<>();
        directories.add(root);
        directories.add(r2);

        Type directoryEntryListType = new TypeToken<DirectoryEntry>() {}.getType();
        XmlSerializer<DirectoryEntry> xmlSerializer = new XmlSerializer<>(directoryEntryListType);
        
//        System.out.println(xmlSerializer.serialize(root));

//        xmlSerializer.serialize(directories, "file_xml");

        DirectoryEntry r1 = xmlSerializer.deserialize(xmlSerializer.serialize(root));
        System.out.println(r1.getName());
    }

    public static void main(String[] args) throws Exception {
        Type directoryEntryListType = new TypeToken<List<DirectoryEntry>>() {}.getType();
        JsonSerializer<DirectoryEntry> txtSerializier = new JsonSerializer<>(directoryEntryListType);
        
        DirectoryEntry root = new DirectoryEntry("/");
        DirectoryEntry current = root;
        current.addFile(new FileEntry("f1", 157));
        current.addSubDirectory(new DirectoryEntry("d1"));

        DirectoryEntry r2 = new DirectoryEntry(".");
        r2.addFile(new FileEntry("fe1", 202));

        ArrayList<DirectoryEntry> directories = new ArrayList<>();
        directories.add(root);
        directories.add(r2);
        
        TextSerializer txtSerializer = new TextSerializer();
    
        txtSerializer.serialize(root, "txt_file");

    }
}
