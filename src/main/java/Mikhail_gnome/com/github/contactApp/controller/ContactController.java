package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactDto;
import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import Mikhail_gnome.com.github.contactApp.service.ContactService;
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
    private final ContactService contactService;

    @Autowired
    public ContactController(ContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping("/get")
    public ResponseEntity<ServerResponse<ArrayList<Contact>>> getContacts() {
ArrayList<Contact> result = new ArrayList<>(contactService.getAllContacts());
return ServerResponseHelper.ok(result);
    }

    // http://localhost:8080/api/contact/getById?id=2
    @GetMapping("/get/{id}")
    public ResponseEntity<ServerResponse<Contact>> getContactById (@PathVariable int id) {
        Contact result = contactService.getContactById(id);
       return ServerResponseHelper.ok(result);
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity<ServerResponse<Void>> deleteContact(@PathVariable int id){
contactService.deleteContact(id);
       return ServerResponseHelper.ok(null);
}

@PostMapping("/create")
public ResponseEntity<ServerResponse<Contact>> createContact(
        @Valid
        @RequestBody
        CreateContactDto createContactDto){
Contact contact = contactService.createContact(createContactDto);
        return ServerResponseHelper.ok(contact);
}
    @PutMapping("/update")
    public ResponseEntity<ServerResponse<Contact>> updateContact(
            @Valid
            @RequestBody
            UpdateContactDto updateContactDto) {
        Contact updateContact = contactService.updateContact(updateContactDto);
        return ServerResponseHelper.ok(updateContact);
    }
}
