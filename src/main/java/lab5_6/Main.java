package lab5_6;

import lab4.DirectoryEntry;
import lab4.FileEntry;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        use_things();
    }

    public static void use_things() {
        FilesystemDB fsdb = new FilesystemDB("jdbc:mysql://localhost:3306/filesystem", "root", "1111");

//        fsdb.dropTables();
//        fsdb.createTables();
//        DirectoryEntry root = new DirectoryEntry("root");
//        fsdb.addDirectory(root);
//        fsdb.addFile(new FileEntry("fe2", 101), root);
//        fsdb.addFile(new FileEntry("fe3", 570), root);
//        fsdb.deleteFile(1);

        fsdb.updateFileSize(2, 157);
    }
}
