package Mikhail_gnome.com.github.contactApp.dao;

import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Primary
@Repository
public class ContactOwnerRepositorySqLiteImpl implements ContactOwnerRepository{
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public ContactOwnerRepositorySqLiteImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }


    @Override
    public List<ContactOwner> findAll() {
        return jdbcTemplate.query(
                "SELECT * FROM contact_owners;",
                new BeanPropertyRowMapper<>(ContactOwner.class)
        );
    }

    @Override
    public Optional<ContactOwner> findById(String id) {
        List<ContactOwner> owners = jdbcTemplate.query(
                "SELECT * FROM contact_owners WHERE id = ?;",
                new BeanPropertyRowMapper<>(ContactOwner.class),
                id
        );
        return owners.isEmpty() ? Optional.empty() : Optional.of(owners.getFirst());
    }

    @Override
    public Optional<ContactOwner> findByEmail(String email) {
        List<ContactOwner> owners = jdbcTemplate.query(
                "SELECT * FROM contact_owners WHERE email = ?;",
                new BeanPropertyRowMapper<>(ContactOwner.class),
                email
        );
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
            jdbcTemplate.update(sql,
                    contactOwner.getId(),
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
        return jdbcTemplate.query("SELECT * FROM contact_owners WHERE username = ?",
                new BeanPropertyRowMapper<>(ContactOwner.class),
                username);
    }

    @Override
    public List<ContactOwner> searchByKeyword(String keyword) {
        String sql = """
                SELECT * FROM contact_owners WHERE username LIKE ? 
                OR description LIKE ? 
                OR email LIKE ?;
                """;

        String searchPattern = "%" + keyword + "%";
        return jdbcTemplate.query(
                sql,
                new BeanPropertyRowMapper<>(ContactOwner.class),
                searchPattern,
                searchPattern,
                searchPattern
        );
    }
}
