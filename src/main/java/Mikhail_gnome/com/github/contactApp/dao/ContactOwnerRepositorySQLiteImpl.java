package Mikhail_gnome.com.github.contactApp.dao;

import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class ContactOwnerRepositorySQLiteImpl implements ContactOwnerRepository{

    private final JdbcTemplate jdbcTemplate;
@Autowired
    public ContactOwnerRepositorySQLiteImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    private static class ContactOwnerMapper implements RowMapper<ContactOwner> {

        @Override
        public ContactOwner mapRow(ResultSet rs, int rowNum) throws SQLException {
            ContactOwner owner = new ContactOwner();
            owner.setId(rs.getString("id"));
            owner.setUsername(rs.getString("username"));
            owner.setDescription(rs.getString("description"));
            owner.setEmail(rs.getString("email"));
            owner.setPassword(rs.getString("password"));
            owner.setRole(AppRole.valueOf(rs.getString("role")));
            return owner;
        }
    }

    @Override
    public List<ContactOwner> findAll() {
        String sql = "SELECT * FROM contact_owners;";
        return jdbcTemplate.query(sql, new ContactOwnerMapper());
    }

    @Override
    public Optional<ContactOwner> findById(String id) {
        String sql = "SELECT * FROM contact_owners WHERE id =?;";
        List<ContactOwner> owners = jdbcTemplate.query(sql, new ContactOwnerMapper(), id);
        return owners.isEmpty() ? Optional.empty() : Optional.of(owners.getFirst());
    }

    @Override
    public Optional<ContactOwner> findByEmail(String email) {
        String sql = "SELECT * FROM contact_owners WHERE email =?;";
        List<ContactOwner> owners = jdbcTemplate.query(sql, new ContactOwnerMapper(), email);
        return owners.isEmpty() ? Optional.empty() : Optional.of(owners.getFirst());
    }

    @Override
    public ContactOwner save(ContactOwner contactOwner) {
        if (contactOwner.getId() == null) {
            String id = UUID.randomUUID().toString();
            contactOwner.setId(id);
            contactOwner.setRole(AppRole.USER);

            String sql = """
                    INSERT INTO contact_owners (id, username, description, email, password, role)
                    VALUES (?,?,?,?,?,?)
                    """;
            jdbcTemplate.update(sql, contactOwner.getId(),
                    contactOwner.getUsername(),
                    contactOwner.getDescription(),
                    contactOwner.getEmail(),
                    contactOwner.getPassword(),
                    contactOwner.getRole().name()
            );
            } else {
            String sql = """
                    UPDATE contact_owners SET 
                    username = ?,
                    description = ?, 
                    email = ?, 
                    password = ?,
                    role = ?
                    WHERE id = ?;
                    """;

            jdbcTemplate.update(sql,
                    contactOwner.getUsername(),
                    contactOwner.getDescription(),
                    contactOwner.getEmail(),
                    contactOwner.getPassword(),
                    contactOwner.getRole().name(),
                    contactOwner.getId()
            );
        }
        return contactOwner;
    }

    @Override
    public boolean deleteById(String id) {
        String sql = "DELETE FROM contact_owners WHERE id = ?;";
        return jdbcTemplate.update(sql, id) > 0;
    }

    @Override
    public List<ContactOwner> findByUsername(String username) {
        String sql = "SELECT * FROM contact_owners WHERE username = ?;";
        return jdbcTemplate.query(sql, new ContactOwnerMapper(), username);
    }

    @Override
    public List<ContactOwner> searchByKeyword(String keyword) {
        String sql = """
                SELECT * FROM contact_owners 
                WHERE username LIKE ? OR description LIKE ? OR email LIKE ?;
                """;
        return jdbcTemplate.query(sql, new ContactOwnerMapper(),
                "%" + keyword + "%",
                "%" + keyword + "%",
                "%" + keyword + "%"
        );
    }
}
