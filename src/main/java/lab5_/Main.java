package lab5_;

import java.sql.SQLException;

public class Main {
    public static void main(String[] args) throws SQLException {
        DBServece dbServece = new DBServece("jdbc:mysql://localhost:3306/filesystem", "root", "1111");

        dbServece.createTables();
//        dbServece.dropTables();
    }
}
