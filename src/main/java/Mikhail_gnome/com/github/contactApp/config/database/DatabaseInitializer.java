package Mikhail_gnome.com.github.contactApp.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

@Component
public class DatabaseInitializer {
private final String dbPath;
    public DatabaseInitializer(@Value("${spring.datasource.url}") String dbPath) {
        this.dbPath = dbPath;
    }

        public void createDatabase () throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbPath)){
            try (Statement stmt = connection.createStatement()){
                String createContactTable = """
                           CREATE TABLE IF NOT EXISTS contacts(
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            first_name TEXT NOT NULL,
                            last_name TEXT NOT NULL,
                            telephone TEXT NOT NULL,
                            email TEXT NOT NULL)
                        """;
                stmt.execute(createContactTable);
            }
        }
        }
}
