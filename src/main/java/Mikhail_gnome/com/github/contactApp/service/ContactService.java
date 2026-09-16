package Mikhail_gnome.com.github.contactApp.service;

import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactDto;
import Mikhail_gnome.com.github.contactApp.model.entity.Contact;

import java.util.List;

public interface ContactService {
    List<Contact> getAllContacts();

    Contact getContactById(int id);

    Contact createContact (CreateContactDto dto);

    Contact updateContact (UpdateContactDto dto);

    boolean deleteContact(int id);
}