package Mikhail_gnome.com.github.contactApp.dao;

import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class ContactRepositoryImpl implements ContactRepository{
    private final ArrayList<Contact> contacts = new ArrayList<>();

    public ContactRepositoryImpl() {
        for (int i = 1; i < 10; i++) {
            contacts.add(new Contact(
                    i,
                    "Имя " + i,
                    "Фамилия " + i,
                    "+7 111 222 33 3" + i,
                    "user" +i + "@email"
            ));
        }
    }

    @Override
    public List<Contact> findAll() {
        return new ArrayList<>(contacts);
    }

    @Override
    public Optional<Contact> findById(int id) {
        return contacts.stream().
                filter(c -> c.getId() == id).
                findFirst();
    }

    @Override
    public Contact save(Contact contact) {
        if(contact.getId() == 0) {
            int newId = contacts.stream().mapToInt(Contact::getId).max().orElse(0) + 1;
            contact.setId(newId);
            contacts.add(contact);
            return null;
        } else {
            Contact findContact = contacts.stream()
                    .filter(c -> c.getId() == contact.getId())
                    .findFirst().orElse(null);
            int index = contacts.indexOf(findContact);
            if (index != -1) {
                contacts.set(index, contact);
            }
return  contact;
        }
    }

    @Override
    public boolean deleteById(int id) {
        return contacts.removeIf(contact -> contact.getId() == id);
    }

    @Override
    public Optional<Contact> findByEmail(String email) {
        return contacts.stream().filter(c -> c.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public Optional<Contact> findByTelephone(String telephone) {
        return contacts.stream().filter(c -> c.getTelephone().equalsIgnoreCase(telephone)).findFirst();
    }
}
