package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;

@RestController
@RequestMapping("/api/contact")
public class ContactController {
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
    public ServerResponse<ArrayList<Contact>> getContacts() {

        return ServerResponse.<ArrayList<Contact>>builder()
                .result(this.contacts)
                .statusCode(200)
                .isSuccess(true)
                .errorMessages(new ArrayList<>())
                .build();
    }

    // http://localhost:8080/api/contact/getById?id=2
    @GetMapping("/get/{id}")
    public ServerResponse<Contact> getContactById (@PathVariable int id) {
        Contact contact = contacts.stream().
                filter(c -> c.getId() == id).
                findFirst().
                orElse(null);
        if (contact == null) {
            return ServerResponse.<Contact>builder()
                    .result(null)
                    .statusCode(204)
                    .isSuccess(true)
                    .errorMessages(new ArrayList<>())
                    .build();
        }
        return ServerResponse.<Contact>builder()
                .result(contact)
                .statusCode(200)
                .isSuccess(true)
                .errorMessages(new ArrayList<>())
                .build();
    }
}
