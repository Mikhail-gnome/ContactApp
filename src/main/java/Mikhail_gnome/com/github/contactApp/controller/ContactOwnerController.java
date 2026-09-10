package Mikhail_gnome.com.github.contactApp.controller;

import Mikhail_gnome.com.github.contactApp.common.util.ServerResponseHelper;
import Mikhail_gnome.com.github.contactApp.model.ServerResponse;
import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import Mikhail_gnome.com.github.contactApp.model.enums.AppRole;
import com.github.javafaker.Faker;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.stream.IntStream;

@RestController
@RequestMapping("api/owner")
public class ContactOwnerController {

    @Autowired
    private ModelMapper modelMapper;

    private final ArrayList<ContactOwner> contactOwners = new ArrayList<>();

    private final Faker faker = new Faker(Locale.of("ru"));

    public ContactOwnerController(){
        init();
    }

    private void init(){
        IntStream.range(0, 5).forEach(i -> {
            ContactOwner owner = new ContactOwner();
            owner.setId(UUID.randomUUID().toString());
            owner.setRole(AppRole.USER);
            owner.setEmail(faker.internet().emailAddress());
            owner.setPassword(faker.internet().password(8, 20, true, false,true));
            owner.setDescription(faker.lorem().sentence());
            owner.setUsername(faker.name().username());
            contactOwners.add(owner);

        });
    }

    @GetMapping("/get")
    public ResponseEntity<ServerResponse<List<ContactOwner>>> getAllContactOwner(){
        return ServerResponseHelper.ok(contactOwners);
    }

}
