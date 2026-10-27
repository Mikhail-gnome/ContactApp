package Mikhail_gnome.com.github.contactApp.config.database;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.sql.*;
import java.util.UUID;

@Component
public class DatabaseInitializer {
private final String dbPath;
    public DatabaseInitializer(@Value("${spring.datasource.url}") String dbPath) {

        this.dbPath = dbPath;
    }

    private void createTables(Statement stmt) throws SQLException {
        String createContactTable = """
                           CREATE TABLE IF NOT EXISTS contacts(
                            id INTEGER PRIMARY KEY AUTOINCREMENT,
                            first_name TEXT NOT NULL,
                            last_name TEXT NOT NULL,
                            telephone TEXT NOT NULL,
                            email TEXT NOT NULL)
                        """;
        stmt.execute(createContactTable);

        String createContactOwnerTable = """
                           CREATE TABLE IF NOT EXISTS contact_owners(
                            id TEXT PRIMARY KEY,
                            username TEXT UNIQUE,
                            description TEXT,
                            email TEXT UNIQUE,
                            password TEXT,
                            role TEXT)
                        """;
        stmt.execute(createContactOwnerTable);
    }

    private void insertTestDta(Statement stmt) throws SQLException {
        String checkIfEmpty = "SELECT count(*) FROM contacts;";

        ResultSet rs = stmt.executeQuery(checkIfEmpty);

        if (rs.getInt(1) == 0) {
            String insertData = """
                    INSERT INTO contacts (first_name, last_name, telephone, email)
                    VALUES
                    ('Анна', 'Смирнова', '+7(926) 555-78-90', 'anna.smirnova@yandex.ru'),
                    ('Дмитрий', 'Козлов', '+7(905) 123 45 67', 'dmitry_kozlov@gmail.com'),
                    ('Елена', 'Новикова', '+7(916) 987 65 43', 'elena_n@mail.ru'),
                    ('Сергей', 'Волков', '+7(903) 333 22 11', 'sergey.volkov@outlook.com'),
                    ('Ольга', 'Морозова', '+7(495) 765 43 21', 'olga.morozova@rambler.ru'),
                     ('Марина', 'Иванова', '+7(495) 765 43 00', 'ma.ivanova@ex.ru')
                    """;
            stmt.execute(insertData);
        }

        String checkOwnersEmpty = "SELECT count(*) FROM contact_owners;";
        ResultSet rsOwners = stmt.executeQuery(checkOwnersEmpty);
        if (rsOwners.getInt(1) == 0) {
            String insertOwnersData = """
                    INSERT INTO contact_owners (id, username, description, email, password, role)
                    VALUES
                    (?, 'admin', 'Администратор системы', 'admin@example.com', 'AdminPass123', 'USER'),
                    (?, 'user1', 'Первый пользователь', 'user1@example.com', 'User1Pass123', 'USER'),
                    (?, 'user2', 'Второй пользователь', 'user2@example.com', 'User1Pass456', 'USER')
                    """;
            PreparedStatement preparedStatement = stmt.getConnection().prepareStatement(insertOwnersData);
            for (int i = 1; i <= 3; i++) {
                preparedStatement.setString(i, UUID.randomUUID().toString());
            }
            preparedStatement.execute();
            preparedStatement.close();
        }

    }

        public void createDatabaseStructure () throws SQLException {
        try (Connection connection = DriverManager.getConnection(dbPath)){
            try (Statement stmt = connection.createStatement()){
                createTables(stmt);
                insertTestDta(stmt);
            }
        }
    }
    public void init() {
        try {
            createDatabaseStructure();
        } catch (SQLException ex) {
            throw new RuntimeException("Не удалось создать базу данных");
        }
    }
}
