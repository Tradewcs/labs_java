package lab1;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DirectoryEntry {
    private String name;
    private List<FileEntry> files = new ArrayList<FileEntry>();
    private List<DirectoryEntry> directories = new ArrayList<DirectoryEntry>();

    public static class Builder {
        private String name;

        public Builder name(String directoryName) {
            this.name = directoryName;
            return this;
        }

        public DirectoryEntry build() {
            DirectoryEntry de = new DirectoryEntry(this.name);
            return de;
        }
    }

    public String getName() {
        return name;
    }

    public DirectoryEntry(String name) {
        this.name = name;
    }

    public void addFile(FileEntry fe) {
        files.add(fe);
    }
    public void addSubDirectory(DirectoryEntry de) {
        directories.add(de);
    }

    public void removeFile(String name) {
        files.remove(name);
    }

    /***
     *
     * @return size of all files in directory
     */
    public long getSize() {
        long size = 0;
        for (FileEntry fe : files) {
            size += fe.getSize();
        }

        return size;
    }

    public List<DirectoryEntry> getDirectories() {
        return directories;
    }
    public List<FileEntry> getFiles() {
        return files;
    }

    /***
     *
     * @param name name of directory to be returned
     * @return directory with given name
     */
    public DirectoryEntry getSubDirectory(String name) {
        for (DirectoryEntry de : directories) {
            if (de.getName().equals(name)) {
                return de;
            }
        }

        return null;
    }

    @Override
    public String toString() {
        String filesNames = "";
        for (FileEntry fe : files) {
            if (fe instanceof FileEntry) {
                filesNames += " " + fe.getName();
            }
        }


        return "DirectoryEntry{" +
                "files='" + filesNames +
                "'}";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || this.getClass() != o.getClass()) return false;

        DirectoryEntry de = (DirectoryEntry) o;
        return this.files.equals(de.files);
    }

    @Override
    public int hashCode() {
        return Objects.hash(files);
    }


}
