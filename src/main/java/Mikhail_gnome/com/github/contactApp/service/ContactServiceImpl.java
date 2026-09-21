package Mikhail_gnome.com.github.contactApp.service;

import Mikhail_gnome.com.github.contactApp.dao.ContactRepository;
import Mikhail_gnome.com.github.contactApp.exception.handler.customException.EntityNotFoundException;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactDto;
import Mikhail_gnome.com.github.contactApp.model.entity.Contact;
import jakarta.validation.ValidationException;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private ModelMapper modelMapper;

    @Autowired
    public ContactServiceImpl(ContactRepository contactRepository, ModelMapper modelMapper) {
        this.contactRepository = contactRepository;
        this.modelMapper = modelMapper;
    }

    @Override
    public List<Contact> getAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact getContactById(int id) {
        return contactRepository.findById(id).orElseThrow(() ->new EntityNotFoundException("Контакт не найден"));
    }

    @Override
    public Contact createContact(CreateContactDto dto) {
        contactRepository.findByEmail(dto.getEmail())
                .ifPresent(contact -> {
                    throw new ValidationException("Контакт с таким Email уже существует");
        });
        Contact contact = modelMapper.map(dto, Contact.class);

        return contactRepository.save(contact);// метод save в contactRepository возвращает contact
    }

    @Override
    public Contact updateContact(UpdateContactDto dto) {
Contact existingContact = contactRepository.findById(dto.getId()).orElseThrow(() -> new EntityNotFoundException("Контакт не найден"));
contactRepository.findByEmail(dto.getEmail()).ifPresent(contact -> {
    if (contact.getId() != dto.getId()) {
        throw new ValidationException("Контакт с таким Email уже существует");
    }
});

        contactRepository.findByTelephone(dto.getTelephone()).ifPresent(contact -> {
            if (contact.getId() != dto.getId()) {
                throw new ValidationException("Контакт с таким телефоном уже существует");
            }
        });
        Contact updateContact = modelMapper.map(dto, Contact.class);
        return contactRepository.save(updateContact);
    }

    @Override
    public boolean deleteContact(int id) {
        if (contactRepository.findById(id).isEmpty()){
            throw new EntityNotFoundException("Контакт не найден");
            }
        return contactRepository.deleteById(id);
    }
}
