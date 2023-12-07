package lab5_6;

import lab4.DirectoryEntry;
import lab4.FileEntry;

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
                            created date,
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

    public void addDirectory(DirectoryEntry de) {
        String[] sql = {
                "INSERT INTO filesystem.directory_entry (name) VALUES ('" + de.getName() + "');"
        };

        for (FileEntry fe : de.getFiles()) {
            addFile(fe, de);
        }

        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void addFile(FileEntry fe, DirectoryEntry parent) {
        int parentID = 0;
        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT ID FROM filesystem.directory_entry WHERE name = \"" + parent.getName() + "\"";
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    parentID = resultSet.getInt("id");
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        String[] sql = {
                "INSERT INTO filesystem.file_entry (size, name, parentID, created) VALUES (" + fe.getSize() + ", '" + fe.getName() + "', '" + parentID + "', '" + fe.getCreated().toString() + "');"
        };

        try {
            executeSQL(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteFile(int fileID) {
        try (PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM file_entry WHERE id=?")) {
            preparedStatement.setInt(1, fileID);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void updateFileSize(int fileID, int newSize) {
        try (PreparedStatement statement = connection.prepareStatement("UPDATE filesystem.file_entry SET size = ? WHERE id = ?")) {
            statement.setFloat(1, newSize);
            statement.setInt(2, fileID);
            statement.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public List<FileEntry> getAllFiles() {
        List<FileEntry> result = new ArrayList<>();
        try (Statement statement = connection.createStatement()) {
            String selectQuery = "SELECT * FROM file_entry";
            try (ResultSet resultSet = statement.executeQuery(selectQuery)) {
                while (resultSet.next()) {
                    LocalDate created = null;
                    java.sql.Date sqlCreated = resultSet.getDate("created");
                    if (sqlCreated != null) {
                        created = sqlCreated.toLocalDate();
                    }

                    FileEntry fe = new FileEntry(resultSet.getString("name"), resultSet.getInt("size"));
                    fe.setCreated(created);

                    result.add(fe);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return result;
    }

}
