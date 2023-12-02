package lab2;

import lab1.DirectoryEntry;
import lab1.FileEntry;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class TextSerializer implements Serializer<DirectoryEntry> {
    @Override
    public String serialize(DirectoryEntry entity) throws Exception {
        StringWriter stringWriter = new StringWriter();

        PrintWriter writer = new PrintWriter(stringWriter);
        serializeDirectory(entity, writer, 0);
        writer.close();
        return stringWriter.toString();
    }

    @Override
    public void serialize(DirectoryEntry entity, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            serializeDirectory(entity, writer, 0);
        }
    }

    @Override
    public void serialize(List<DirectoryEntry> entities, String filePath) throws IOException {
        try (PrintWriter writer = new PrintWriter(new FileWriter(filePath))) {
            for (DirectoryEntry entity : entities) {
                serializeDirectory(entity, writer, 0);
                writer.println();
            }
        }
    }

    @Override
    public DirectoryEntry deserialize(String strObj) throws Exception {
        // Not implemented for single DirectoryEntry
        return null;
    }

    @Override
    public List<DirectoryEntry> deserializeFromFile(String filePath) throws IOException {
        List<DirectoryEntry> result = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            String line;
            DirectoryEntry current = null;

            List<DirectoryEntry> parents = new ArrayList<>();
            int parentIndex = 0;

            int currentIndentLevel = -1;

            while ((line = reader.readLine()) != null) {
                int indentLevel = getIndentLevel(line);

                if (indentLevel == currentIndentLevel) {
                    String[] lineArr = line.strip().split(",");
                    if (lineArr[0].startsWith("File")) {
                        FileEntry fe = new FileEntry(lineArr[0].split(" ")[1], Integer.parseInt(lineArr[1].split(" ")[1]));
                        
                    } else {
                        DirectoryEntry de = new DirectoryEntry(lineArr[0].split(" ")[1]);
                    }
                } else if (indentLevel > currentIndentLevel) {
                    if (current == null) {
                        String[] lineArr = line.strip().split(" ");
                        current = new DirectoryEntry(lineArr[1]);
                        result.add(current);
                    } else {

                    }
                    currentIndentLevel = indentLevel;
                } else {
                    // Move up to the parent directory

                }
            }
        }
        return result;
    }

    private int getIndentLevel(String line) {
        int level = 0;
        while (line.charAt(level) == ' ') {
            level++;
        }
        return level / 4;
    }

    private void serializeDirectory(DirectoryEntry directory, PrintWriter writer, int indentLevel) {
        for (int i = 0; i < indentLevel; i++) {
            writer.print("    ");
        }
        writer.println("Directory: " + directory.getName());

        for (FileEntry file : directory.getFiles()) {
            for (int i = 0; i < indentLevel + 1; i++) {
                writer.print("    ");
            }
            writer.println("File: " + file.getName() + ", Size: " + file.getSize() + " bytes");
        }

        for (DirectoryEntry subdirectory : directory.getDirectories()) {
            serializeDirectory(subdirectory, writer, indentLevel + 1);
        }
    }

//    private DirectoryEntry parseDirectoryEntry(String line) {
//        String name = line.trim();
//        return new DirectoryEntry(name);
//    }
//
//    private FileEntry parseFileEntry(String line) {
//        String[] parts = line.trim().split(", Size: ");
//        String[] namePart = parts[0].split("File: ");
//        String name = namePart[1].trim();
//        int size = Integer.parseInt(parts[1].split(" bytes")[0]);
//        return new FileEntry(name, size);
//    }

}