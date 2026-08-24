package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.entity.Contact;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
    public ArrayList<Contact> getContacts() {
        return contacts;
    }

    // http://localhost:8080/api/contact/getById?id=2
    @GetMapping("/getById")
    public Contact getContactById (@RequestParam int id) {
        for (Contact contact : contacts) {
            if (contact.getId() == id) return contact;
        }
        return new Contact();
    }
}
