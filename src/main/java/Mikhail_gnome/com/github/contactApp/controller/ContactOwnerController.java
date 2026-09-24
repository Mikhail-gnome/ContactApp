package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import Mikhail_gnome.com.github.contactApp.service.ContactOwnerService;
import com.github.javafaker.Faker;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api/owner")
public class ContactOwnerController {
    private final ContactOwnerService contactOwnerService;
@Autowired
    public ContactOwnerController(ContactOwnerService contactOwnerService) {
        this.contactOwnerService = contactOwnerService;
    }


    @GetMapping("/get")
    public ResponseEntity<ServerResponse<List<ContactOwner>>> getAllContactOwner() {
        return ServerResponseHelper.ok(contactOwnerService.getAllContactOwners());
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ServerResponse<ContactOwner>> getAllContactOwner(@PathVariable String id) {
        return ServerResponseHelper.ok(contactOwnerService.getContactOwnerById(id));
    }

    @PostMapping("/create")
    public ResponseEntity<ServerResponse<ContactOwner>> createContactOwner(
            @Valid
            @RequestBody CreateContactOwnerDto createContactOwnerDto) {
        ContactOwner contactOwner = contactOwnerService.createContactOwner(createContactOwnerDto);
        return ServerResponseHelper.ok(contactOwner);
    }

    @PutMapping("/update")
    public ResponseEntity<ServerResponse<ContactOwner>> updateContactOwner(@Valid @RequestBody UpdateContactOwnerDto dto) {
        ContactOwner updateContactOwner = contactOwnerService.updateContactOwner(dto);
        return ServerResponseHelper.ok(updateContactOwner);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ServerResponse<Void>> deleteContactOwner(@PathVariable String id){
    contactOwnerService.deleteContactOwner(id);
        return ServerResponseHelper.ok(null);
    }

    @GetMapping("/search/name/{name}")
    public ResponseEntity<ServerResponse<List<ContactOwner>>> searchByName(@PathVariable String name) {
    List<ContactOwner> found = contactOwnerService.searchContactOwnersByUsername(name);
    return ServerResponseHelper.ok(found);
    }

    @GetMapping("/search/keyword/{keyword}")
    public ResponseEntity<ServerResponse<List<ContactOwner>>> searchByKeyword(@PathVariable String keyword) {
        List<ContactOwner> found = contactOwnerService.searchContactOwnersByKeyword(keyword);
        return ServerResponseHelper.ok(found);
    }
}