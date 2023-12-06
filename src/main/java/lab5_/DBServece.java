package lab5_;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBServece {
    final String url;
    final String username;
    final String passord;
    final Connection connection;

    public DBServece(String url, String username, String password) throws SQLException {
        this.url = url;
        this.username = username;
        this.passord = password;
        this.connection = DriverManager.getConnection(url, username, password);
    }

    void createTables() throws SQLException {
        String sqlStatement[] =
//                {
//                        """
//                        CREATE TABLE filesystem.directory_entry (
//                            id INT NOT NULL AUTO_INCREMENT,
//                            name VARCHAR(50),
//                            PRIMARY KEY (id)
//                        );
//
//                        CREATE TABLE filesystem.file_entry (
//                            id INT NOT NULL AUTO_INCREMENT,
//                            name VARCHAR(255),
//                            size BIGINT,
//                            created DATE,
//                            PRIMARY KEY (id),
//                        );
//                    """
//                };
        {
                """
                        CREATE TABLE filesystem.directory_entry (
                            id INT NOT NULL AUTO_INCREMENT,
                            name varchar(50),
                            PRIMARY KEY (id)
                        );
                    """,
                """
                        CREATE TABLE filesystem.file_entry (
                            id INT NOT NULL AUTO_INCREMENT,
                            size INT NOT NULL,
                            name varchar(50),
                            created date,
                            PRIMARY KEY (id)
                        );
                    """
        };

        Statement statement = connection.createStatement();
        for (String sqlSt : sqlStatement) {
            statement.execute(sqlSt);
        }
    }

    void dropTables() throws SQLException {
        String sql[] =
                {
                        "DROP TABLE IF EXISTS directory_entry",
                        "DROP TABLE IF EXISTS file_entry"
                };
        Statement statement = connection.createStatement();
        for (String sqlSt : sql) {
            statement.execute(sqlSt);
        }
    }
}
