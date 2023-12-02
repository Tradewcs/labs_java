package lab1;

public class Main {
    public static void main(String[] args) {
        DirectoryEntry root = new DirectoryEntry("/");
        root.addFile(new FileEntry("file1", 1024));
        root.addFile(new FileEntry("file2", 555));
        root.addSubDirectory(new DirectoryEntry("dir1"));
        root.addSubDirectory(new DirectoryEntry("dir2"));
        DirectoryEntry de = new DirectoryEntry("dir1.dir_");
        root.getSubDirectory("dir1").addSubDirectory(de);

        
        Explorer explorer = new Explorer(root);
        explorer.traversal();
        //        explorer.enterDirectory("dir1");



    }
}
