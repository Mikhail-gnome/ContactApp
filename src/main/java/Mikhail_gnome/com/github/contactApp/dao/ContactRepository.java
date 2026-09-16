package Mikhail_gnome.com.github.contactApp.dao;

import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import org.springframework.beans.factory.ObjectProvider;

import java.util.List;
import java.util.Optional;

public interface ContactRepository {

    List<Contact> findAll();

    ObjectProvider<Contact> findById(int id);

    Contact save(Contact contact);

    boolean deleteById(int id);

    Optional<Contact> findByEmail(String email);
    Optional<Contact> findByTelephone(String Telephone);

}
