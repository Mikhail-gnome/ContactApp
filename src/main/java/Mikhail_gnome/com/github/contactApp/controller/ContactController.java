package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Collections;

@RestController
@RequestMapping("/api/contact")
public class ContactController {

    @Autowired
    private ModelMapper modelMapper;

    private ArrayList<Contact> contacts = new ArrayList<>();

    public ContactController() {
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

    // http://localhost:8080/api/contact/get
    @GetMapping("/get")
    public ResponseEntity<ServerResponse<ArrayList<Contact>>> getContacts() {


        return ServerResponseHelper.ok(this.contacts);
    }

    // http://localhost:8080/api/contact/getById?id=2
    @GetMapping("/get/{id}")
    public ResponseEntity<ServerResponse<Contact>> getContactById (@PathVariable int id) {
        Contact contact = contacts.stream().
                filter(c -> c.getId() == id).
                findFirst().
                orElse(null);

        if (contact == null) {
            return ServerResponseHelper.notFound(null);
        }
        return ServerResponseHelper.ok(contact);
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity<ServerResponse<Void>> deleteContact(@PathVariable int id){
        boolean removed = contacts.removeIf(contact -> contact.getId() == id);
        if (removed) return ServerResponseHelper.ok(null);
        else return ServerResponseHelper
                .notFound(null, Collections.singletonList("Контакт с указанным ID не был найден"));
}

@PostMapping("/create")
public ResponseEntity<ServerResponse<Contact>> createContact(
        @RequestBody
        @Valid
        CreateContactDto createContactDto){
        int newId = contacts.stream().mapToInt(Contact::getId).max().orElse(0) + 1;
        boolean emailExists = contacts.stream()
                .anyMatch(c -> c.getEmail().equalsIgnoreCase(createContactDto.getEmail()));
        if (emailExists) {
            return ServerResponseHelper.conflict(null, Collections.singletonList("Контакт с таким email уже существует"));
        }
    Contact contact = modelMapper.map(createContactDto, Contact.class);
            contact.setId(newId);
            contacts.add(contact);
        return ServerResponseHelper.created(contact);
}
    @PutMapping("/update")
    public ResponseEntity<ServerResponse<Contact>> updateContact(@RequestBody Contact contact) {
        return contacts.stream().filter(c -> c.getId() == contact.getId()).findFirst()
                .map(existingContact -> {
                    int index = contacts.indexOf(existingContact);
                    contacts.set(index, contact);
                    return ServerResponseHelper.ok(contact);
                }).orElse(ServerResponseHelper
                        .notFound(null, Collections.singletonList("Контакт с указанным ID не был найден")));
    }
}
