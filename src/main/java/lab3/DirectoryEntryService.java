package lab3;

import lab1.FileEntry;
import lab1.Mode;

import java.util.List;

public interface DirectoryEntryService {
    List<FileEntry> getFilesByExtension(String extension);
    List<FileEntry> getFilesByPartName(String partName);
    List<FileEntry> getFilesByMode(Mode mode);
    List<FileEntry> getFilesBySizeInRange(int begin, int end);

}
