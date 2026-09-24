package Mikhail_gnome.com.github.contactApp.dao;

import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import com.github.javafaker.Faker;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.IntStream;

@Repository
public class ContactOwnerRepositoryImpl implements ContactOwnerRepository {
    private final List<ContactOwner> contactOwners = new ArrayList<>();
    private final Faker faker = new Faker();

    public ContactOwnerRepositoryImpl() {
        init();
    }

    private void init() {
        IntStream.range(0, 5).forEach(i -> {
            ContactOwner owner = new ContactOwner();
            owner.setId(UUID.randomUUID().toString());
            owner.setRole(AppRole.USER);
            owner.setEmail(faker.internet().emailAddress());
            owner.setPassword(faker.internet().password(8, 20, true, false, true));
            owner.setDescription(faker.lorem().sentence());
            owner.setUsername(faker.name().username());
            contactOwners.add(owner);
    });
    }
    @Override
    public List findAll() {
        return new ArrayList<>(contactOwners);
    }

    @Override
    public Optional findById(String id) {

        return contactOwners.stream().filter(co -> co.getId().equalsIgnoreCase(id)).findFirst();
    }

    @Override
    public Optional findByEmail(String email) {
        return contactOwners.stream().filter(co -> co.getEmail().equalsIgnoreCase(email)).findFirst();
    }

    @Override
    public ContactOwner save(ContactOwner contactOwner) {
        if (contactOwner.getId() == null){
            contactOwner.setId(UUID.randomUUID().toString());
            contactOwner.setRole(AppRole.USER);
            contactOwners.add(contactOwner);
        } else {
            int index = contactOwners.indexOf(contactOwners.stream().filter(co -> co.getId()
                    .equalsIgnoreCase(contactOwner.getId())).findFirst()
                    .orElse(null));
            if (index != -1) {
                contactOwners.set(index, contactOwner);
            }

        }
        return contactOwner;
    }

    @Override
    public boolean deleteById(String id) {
        return contactOwners.removeIf(co -> co.getId().equalsIgnoreCase(id));
    }

    @Override
    public List findByUsername(String username) {
        return contactOwners.stream().filter(co -> co.getUsername().equalsIgnoreCase(username)).toList();
    }

    @Override
    public List searchByKeyword(String keyword) {
        return contactOwners.stream().filter(
                co -> co.getUsername().contains(keyword)
                ||co.getDescription().contains(keyword)
                ||co.getEmail().contains(keyword)
                )
                .toList();
    }

}
