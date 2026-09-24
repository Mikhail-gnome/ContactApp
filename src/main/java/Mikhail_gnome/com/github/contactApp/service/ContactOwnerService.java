package Mikhail_gnome.com.github.contactApp.service;

import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;

import java.util.List;

public interface ContactOwnerService {
    List<ContactOwner> getAllContactOwners();
    ContactOwner getContactOwnerById(String id);
    ContactOwner createContactOwner(CreateContactOwnerDto createDto);
    ContactOwner updateContactOwner(UpdateContactOwnerDto updateDto);
    boolean deleteContactOwner(String id);
    List<ContactOwner> searchContactOwnersByUsername(String username);
    List<ContactOwner> searchContactOwnersByKeyword(String keyword);
}
