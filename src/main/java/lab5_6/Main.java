package lab5_6;

import lab4.DirectoryEntry;
import lab4.FileEntry;

import java.sql.SQLException;
import java.time.LocalDate;

public class Main {
    public static void main(String[] args) throws SQLException {
        FilesystemDB fsdb = new FilesystemDB("jdbc:mysql://localhost:3306/filesystem", "root", "1111");

        fsdb.dropTables();
        fsdb.createTables();
        DirectoryEntry root = new DirectoryEntry("root");
        fsdb.addDirectory(root);
        fsdb.addFileEntry(new FileEntry("fe2.txt", 101), root);
        fsdb.addFileEntry(new FileEntry("fe3.png", 570), root);
        fsdb.addFileEntry(new FileEntry("fe4.txt", 570), root);
//        fsdb.deleteFile(1);

        fsdb.updateFileSize(2, 157);
        
        System.out.println(fsdb.getFilesByExtension("txt").size());
        System.out.println(fsdb.getFilesByDateInRange(LocalDate.of(2023, 1, 1), LocalDate.of(2024, 1, 1)));
    }
}
