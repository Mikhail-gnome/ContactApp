package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactDto;
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



    // private ArrayList<Contact> contacts = new ArrayList<>();

//    public ContactController() {
//        for (int i = 1; i < 10; i++) {
//            contacts.add(new Contact(
//                    i,
//                    "Имя " + i,
//                    "Фамилия " + i,
//                    "+7 111 222 33 3" + i,
//                    "user" +i + "@email"
//            ));
//        }
//
//    }

    // http://localhost:8080/api/contact/get
    @GetMapping("/get")
    public ResponseEntity<ServerResponse<ArrayList<Contact>>> getContacts() {

return null;
       // return ServerResponseHelper.ok(); //this.contacts);
    }

    // http://localhost:8080/api/contact/getById?id=2
    @GetMapping("/get/{id}")
    public ResponseEntity<ServerResponse<Contact>> getContactById (@PathVariable int id) {
       return null;
    }
@DeleteMapping("/delete/{id}")
    public ResponseEntity<ServerResponse<Void>> deleteContact(@PathVariable int id){

       return null;
}

@PostMapping("/create")
public ResponseEntity<ServerResponse<Contact>> createContact(
        @RequestBody
        @Valid
        CreateContactDto createContactDto){

        return null;
}
    @PutMapping("/update")
    public ResponseEntity<ServerResponse<Contact>> updateContact(
            @RequestBody
            @Valid
            UpdateContactDto updateContactDto) {
//Contact existingContact = contacts.stream().filter(c -> c.getId() == updateContactDto.getId())
//        .findFirst().orElse(null);
//if (existingContact == null) {
//    return ServerResponseHelper
//            .notFound(null, Collections.singletonList("Контакт с указанным ID не найден"));
//}
//
//boolean emailExists = contacts.stream().filter(c -> c.getId() != updateContactDto.getId())
//        .anyMatch(c -> c.getEmail().equalsIgnoreCase(updateContactDto.getEmail()));
//if (emailExists){
//    return ServerResponseHelper
//            .conflict(null, Collections.singletonList("Контакт с указанным email уже существует"));
//}
//
//        boolean phoneExists = contacts.stream().filter(c -> c.getId() != updateContactDto.getId())
//                .anyMatch(c -> c.getTelephone().equals(updateContactDto.getTelephone()));
//        if (phoneExists){
//            return ServerResponseHelper
//                    .conflict(null, Collections.singletonList("Контакт с указанным телефоном уже существует"));
//        }
//Contact updateContact = modelMapper.map(updateContactDto, Contact.class);
//
//        int index = contacts.indexOf(existingContact);
//
//        contacts.set(index, updateContact);
//        return ServerResponseHelper.ok(updateContact);
        return null;
    }
}
