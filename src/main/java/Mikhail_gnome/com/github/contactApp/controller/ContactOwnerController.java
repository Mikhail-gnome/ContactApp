package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
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

    @Autowired
    private ModelMapper modelMapper;

    private final ArrayList<ContactOwner> contactOwners = new ArrayList<>();

    private final Faker faker = new Faker(Locale.of("ru"));

    public ContactOwnerController() {
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

    @GetMapping("/get")
    public ResponseEntity<ServerResponse<List<ContactOwner>>> getAllContactOwner() {
        return ServerResponseHelper.ok(contactOwners);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<ServerResponse<ContactOwner>> getAllContactOwner(@PathVariable String id) {
        return contactOwners.stream().filter(owner -> owner.getId().equals(id))
                .findFirst().map(ServerResponseHelper::ok).orElseGet(() -> ServerResponseHelper.notFound(null));
    }

    @PostMapping("/create")
    public ResponseEntity<ServerResponse<ContactOwner>> createContactOwner(@Valid
                                                                           @RequestBody CreateContactOwnerDto createContactOwnerDto) {
        if (contactOwners.stream().anyMatch(owner -> owner.getEmail().equalsIgnoreCase(createContactOwnerDto.getEmail()))) {
            return ServerResponseHelper.conflict(null, Collections.singletonList("Email уже занят"));
        }
        ContactOwner newOwner = modelMapper.map(createContactOwnerDto, ContactOwner.class);
        contactOwners.add(newOwner);
        return ServerResponseHelper.created(newOwner);
    }

    @PutMapping("/update")
    public ResponseEntity<ServerResponse<ContactOwner>> updateContactOwner(@Valid @RequestBody UpdateContactOwnerDto dto) {
        ContactOwner existingContactOwner = contactOwners.stream().filter(co -> co.getId().equalsIgnoreCase(dto.getId()))
                .findFirst().orElse(null);
        if (existingContactOwner == null) {
            return ServerResponseHelper.notFound(null, Collections.singletonList("Владелец с указанным ID не найден"));
        }
        boolean emailExists = contactOwners.stream().filter(co -> !co.getId().equalsIgnoreCase(dto.getId()))
                .anyMatch(co -> co.getEmail().equalsIgnoreCase(dto.getEmail()));
        if (emailExists) {
            return ServerResponseHelper.conflict(null, Collections.singletonList("Владелец с указанным Email уже существует"));
        }
        ContactOwner updateContactOwner = modelMapper.map(dto, ContactOwner.class);
        int index = contactOwners.indexOf(existingContactOwner);
        contactOwners.set(index, updateContactOwner);
        return ServerResponseHelper.ok(updateContactOwner);
    }
}