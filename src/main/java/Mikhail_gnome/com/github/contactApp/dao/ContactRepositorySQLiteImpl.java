package Mikhail_gnome.com.github.contactApp.dao;

import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

@Primary
@Repository
public class ContactRepositorySQLiteImpl implements ContactRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactRepositorySQLiteImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public static class ContactRowMapper implements RowMapper<Contact> {

        @Override
        public Contact mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new Contact(
                    rs.getInt("id"),
                    rs.getString("first_name"),
                    rs.getString("last_name"),
                    rs.getString("telephone"),
                    rs.getString("email")
            );
        }
    }

    @Override
    public List<Contact> findAll() {
        String sql = "SELECT * FROM contacts;";
        return jdbcTemplate.query(sql, new ContactRowMapper());
    }

    @Override
    public Optional<Contact> findById(int id) {
        String sql = "SELECT * FROM contacts WHERE id =?";
        List<Contact> contacts = jdbcTemplate.query(sql, new ContactRowMapper(), id);
        return contacts.isEmpty() ? Optional.empty() : Optional.of(contacts.getFirst());
    }

    @Override
    public Contact save(Contact contact) {
        if (contact.getId() == 0) {
            String sql = """
                    INSERT INTO contacts (first_name, last_name, telephone, email)
                    VALUES (?,?,?,?)
                    """;
            jdbcTemplate.update(sql, contact.getFirstName(),
                    contact.getLastName(),
                    contact.getTelephone(),
                    contact.getEmail());
            String idSql = "SELECT last_insert_rowid()";
            int id = Optional.ofNullable(jdbcTemplate.queryForObject(idSql, Integer.class)).orElse(0);
            contact.setId(id);
        } else {
            String sql = """
                    UPDATE contacts SET 
                    first_name = ?,
                    last_name = ?, 
                    telephone = ?, 
                    email = ?
                    WHERE id = ?;
                    """;
            jdbcTemplate.update(sql, contact.getFirstName(),
                    contact.getLastName(),
                    contact.getTelephone(),
                    contact.getEmail(),
                    contact.getId());
        }
        return contact;
    }

    @Override
    public boolean deleteById(int id) {
        return false;
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public Optional<Contact> findByTelephone(String Telephone) {
        return Optional.empty();
    }
}
