package lab5_6;

import lab4.DirectoryEntry;
import lab4.FileEntry;

import java.io.File;
import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class FilesystemDB {
    private final Connection connection;

    public FilesystemDB(String url, String username, String password) {
        try {
            this.connection = DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void executeSQL(String[] sql) throws SQLException {
        Statement statement = connection.createStatement();
        for (String sqlSt : sql) {
            statement.execute(sqlSt);
        }
    }

    public void createTables() {
        String[] sql = {
                """
                        CREATE TABLE filesystem.directory_entry (
                            id INT AUTO_INCREMENT,
                            name varchar(50),
                            PRIMARY KEY (id)
                        );
                """,
                """
                        CREATE TABLE filesystem.file_entry (
                            id INT AUTO_INCREMENT,
                            parentID INT,
                            size INT,
                            name varchar(50),
                            created TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
                            PRIMARY KEY (id)
                        );
                """
        };

        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }

    public void dropTables() {
        String[] sql = {
                "DROP TABLE IF EXISTS directory_entry",
                "DROP TABLE IF EXISTS file_entry"
        };

        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addDirectory(DirectoryEntry de) throws SQLException {
        String[] sql = {
                "INSERT INTO filesystem.directory_entry (name) VALUES ('" + de.getName() + "');"
        };

        for (FileEntry fe : de.getFiles()) {
            addFileEntry(fe, de);
        }

        for (DirectoryEntry d : de.getDirectories()) {
            addDirectory(d);
        }

        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFileEntry(FileEntry fe, DirectoryEntry parent) throws SQLException {
        int parentID = 0;
        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT ID FROM filesystem.directory_entry WHERE name = \"" + parent.getName() + "\"";
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    parentID = resultSet.getInt("id");
                }
            }
        }

        String[] sql = {
                "INSERT INTO filesystem.file_entry (size, name, parentID, created) VALUES (" + fe.getSize() + ", '" + fe.getName() + "', '" + parentID + "', '" + fe.getCreated() + "');"
        };

        executeSQL(sql);
    }

    public void deleteFileEntry(int fileID) throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM file_entry WHERE id=?");
        preparedStatement.setInt(1, fileID);
        preparedStatement.executeUpdate();
    }

    public void updateFileSize(int fileID, int newSize) throws SQLException {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE filesystem.file_entry SET size = ? WHERE id = ?")) {
            statement.setFloat(1, newSize);
            statement.setInt(2, fileID);
            statement.executeUpdate();
        }
    }

    public List<FileEntry> getAllFiles() throws SQLException {
        List<FileEntry> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM file_entry";
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    LocalDate created = resultSet.getDate("created").toLocalDate();
                    FileEntry fe = new FileEntry(resultSet.getString("name"), resultSet.getInt("size"));
                    fe.setCreated(created);

                    result.add(fe);
                }
            }
        }

        return result;
    }



    public List<FileEntry> getFilesByExtension(String extension) throws SQLException {
        List<FileEntry> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM file_entry WHERE name LIKE ?")) {
            statement.setString(1, "%." + extension);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate created = resultSet.getDate("created").toLocalDate();
                    FileEntry fe = new FileEntry(resultSet.getString("name"), resultSet.getInt("size"));
                    fe.setCreated(created);

                    result.add(fe);
                }
            }
        }

        return result;
    }

    public List<FileEntry> getFilesByPartName(String partName) throws SQLException {
        List<FileEntry> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM file_entry WHERE name LIKE ?")) {
            statement.setString(1, "%" + partName + "%");
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate created = resultSet.getDate("created").toLocalDate();
                    FileEntry fe = new FileEntry(resultSet.getString("name"), resultSet.getInt("size"));
                    fe.setCreated(created);

                    result.add(fe);
                }
            }
        }

        return result;
    }

    public List<FileEntry> getFilesByDateInRange(LocalDate begin, LocalDate end) throws SQLException {
        List<FileEntry> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM file_entry WHERE created BETWEEN ? AND ?")) {
            statement.setDate(1, java.sql.Date.valueOf(begin));
            statement.setDate(2, java.sql.Date.valueOf(end));
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate created = resultSet.getDate("created").toLocalDate();
                    FileEntry fe = new FileEntry(resultSet.getString("name"), resultSet.getInt("size"));
                    fe.setCreated(created);

                    result.add(fe);
                }
            }
        }

        return result;
    }

    public List<FileEntry> getFilesBySizeInRange(int begin, int end) throws SQLException {
        List<FileEntry> result = new ArrayList<>();
        try (PreparedStatement statement = connection.prepareStatement("SELECT * FROM file_entry WHERE size BETWEEN ? AND ?")) {
            statement.setInt(1, begin);
            statement.setInt(2, end);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    LocalDate created = resultSet.getDate("created").toLocalDate();
                    FileEntry fe = new FileEntry(resultSet.getString("name"), resultSet.getInt("size"));
                    fe.setCreated(created);

                    result.add(fe);
                }
            }
        }

        return result;
    }

}
