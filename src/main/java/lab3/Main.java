package lab3;

import lab4.DirectoryEntry;
import lab4.FileEntry;
import lab4.Mode;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        DirectoryEntry root = new DirectoryEntry("/");
        DirectoryEntry current = root;
        FileEntry fe = new FileEntry("f1.txt", 512);
        current.addFile(new FileEntry("p2.py", 157));
        current.addSubDirectory(new DirectoryEntry("d1"));
        current.addFile(new FileEntry("g3.java", 452));
        current.addFile(new FileEntry("p4.txt", 753));
        current.addFile(fe);

        DirectoryEntryService desc = new DirectoryEntryServiceCollection(root);
        desc = new DirectoryEntryServiceStreamAPI(root);

        printList(desc.getFilesByExtension("txt"));
        printList(desc.getFilesByPartName("p"));
        printList(desc.getFilesByMode(Mode.Write));
        printList(desc.getFilesBySizeInRange(0, 500));
    }

    public static void printList(List<FileEntry> list) {
        for (FileEntry fe : list) {
            System.out.println(fe.getName());
        }
        System.out.println();
    }
}
