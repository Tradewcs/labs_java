package lab3;

import lab1.DirectoryEntry;
import lab1.FileEntry;
import lab1.Mode;

import java.util.List;
import java.util.stream.Collectors;

public class DirectoryEntryServiceStreamAPI implements DirectoryEntryService {
    private DirectoryEntry root;

    public DirectoryEntryServiceStreamAPI(DirectoryEntry root) {
        this.root = root;
    }


    @Override
    public List<FileEntry> getFilesByExtension(String extension) {
        return root.getFiles().stream()
                .filter(file -> file.getName().endsWith("." + extension))
                .collect(Collectors.toList());
    }

    @Override
    public List<FileEntry> getFilesByPartName(String partName) {
        return root.getFiles().stream()
                .filter(file -> file.getName().contains(partName))
                .collect(Collectors.toList());
    }

    @Override
    public List<FileEntry> getFilesByMode(Mode mode) {
        return root.getFiles().stream()
                .filter(fe -> fe.getMode().equals(mode))
                .collect(Collectors.toList());
    }

    @Override
    public List<FileEntry> getFilesBySizeInRange(int begin, int end) {
        return root.getFiles().stream()
                .filter(fe -> fe.getSize() >= begin && fe.getSize() <= end)
                .collect(Collectors.toList());
    }


}
