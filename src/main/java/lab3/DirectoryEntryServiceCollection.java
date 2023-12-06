package lab3;

import lab1.DirectoryEntry;
import lab1.FileEntry;
import lab1.Mode;

import java.util.ArrayList;
import java.util.List;

public class DirectoryEntryServiceCollection implements DirectoryEntryService {
    private DirectoryEntry root;

    public DirectoryEntryServiceCollection(DirectoryEntry root) {
        this.root = root;
    }

    @Override
    public List<FileEntry> getFilesByExtension(String extension) {
        List<FileEntry> result = new ArrayList<>();

        List<FileEntry> files = root.getFiles();
        for (FileEntry file : files) {
            if (file.getName().endsWith("." + extension)) {
                result.add(file);
            }
        }

        return result;
    }

    @Override
    public List<FileEntry> getFilesByPartName(String partName) {
        List<FileEntry> result = new ArrayList<>();

        for (FileEntry fe : root.getFiles()) {
            if (fe.getName().contains(partName)) {
                result.add(fe);
            }
        }

        return result;
    }

    @Override
    public List<FileEntry> getFilesByMode(Mode mode) {
        List<FileEntry> result = new ArrayList<>();

        for (FileEntry fe : root.getFiles()) {
            if (fe.getMode().equals(mode)) {
                result.add(fe);
            }
        }

        return  result;
    }

    @Override
    public List<FileEntry> getFilesBySizeInRange(int begin, int end) {
        List<FileEntry> result = new ArrayList<>();

        for (FileEntry fe : root.getFiles()) {
            if (fe.getSize() >= begin && fe.getSize() <= end) {
                result.add(fe);
            }
        }

        return  result;
    }

}
