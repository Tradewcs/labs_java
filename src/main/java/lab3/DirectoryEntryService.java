package lab3;

import lab4.FileEntry;
import lab4.Mode;

import java.time.LocalDate;
import java.util.List;

public interface DirectoryEntryService {
    List<FileEntry> getFilesByExtension(String extension);
    List<FileEntry> getFilesByPartName(String partName);
    List<FileEntry> getFilesByMode(Mode mode);

    List<FileEntry> getFilesByDateInRange(LocalDate begin, LocalDate end);
    List<FileEntry> getFilesBySizeInRange(int begin, int end);

}
