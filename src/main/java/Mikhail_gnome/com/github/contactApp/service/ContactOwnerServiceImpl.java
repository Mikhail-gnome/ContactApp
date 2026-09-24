package Mikhail_gnome.com.github.contactApp.service;

import Mikhail_gnome.com.github.contactApp.dao.ContactOwnerRepository;
import Mikhail_gnome.com.github.contactApp.exception.handler.customException.EntityNotFoundException;
import Mikhail_gnome.com.github.contactApp.exception.handler.customException.ValidationException;
import Mikhail_gnome.com.github.contactApp.model.dto.CreateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.dto.UpdateContactOwnerDto;
import Mikhail_gnome.com.github.contactApp.model.entity.ContactOwner;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContactOwnerServiceImpl implements ContactOwnerService {

    private final ContactOwnerRepository contactOwnerRepository;
    private final ModelMapper modelMapper;

  @Autowired
  public ContactOwnerServiceImpl(ContactOwnerRepository contactOwnerRepository,
          ModelMapper modelMapper) {
      this.contactOwnerRepository = contactOwnerRepository;
      this.modelMapper = modelMapper;
  }


    @Override
    public List<ContactOwner> getAllContactOwners() {
        return contactOwnerRepository.findAll();
    }

    @Override
    public ContactOwner getContactOwnerById(String id) {
        return contactOwnerRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Владелец не найден"));
    }

    @Override
    public ContactOwner createContactOwner(CreateContactOwnerDto createDto) {
        String normalizedEmail = createDto
                .getEmail()
                .trim()
                .toLowerCase();
        createDto.setEmail(normalizedEmail);

        boolean emailExists = contactOwnerRepository
                .findAll()
                .stream()
                .anyMatch(co -> co.getEmail().trim().equalsIgnoreCase(normalizedEmail));

        if (emailExists) {
            throw new ValidationException("Владелец с таким email уже существует");
        }

        ContactOwner newOwner = modelMapper.map(createDto, ContactOwner.class);
        return contactOwnerRepository.save(newOwner);
    }


    @Override
    public ContactOwner updateContactOwner(UpdateContactOwnerDto updateDto) {
        String normalizedEmail = updateDto.getEmail().trim().toLowerCase();

        ContactOwner existingContactOwner = contactOwnerRepository.findById(updateDto.getId())
                .orElseThrow(() -> new EntityNotFoundException("Владелец не найден"));

        contactOwnerRepository.findAll().stream().filter(co -> co.getEmail().equalsIgnoreCase(normalizedEmail)
                        && !co.getId().equalsIgnoreCase(updateDto.getId()))
                .findFirst()
                .ifPresent(co -> {throw new ValidationException("Владелец с таким Email уже существует");
                });

        ContactOwner updateContactOwner = modelMapper.map(updateDto, ContactOwner.class);
        updateContactOwner.setRole(existingContactOwner.getRole());
        updateContactOwner.setPassword(existingContactOwner.getPassword());
        contactOwnerRepository.save(updateContactOwner);
        return updateContactOwner;
    }

    @Override
    public boolean deleteContactOwner(String id) {
        if (contactOwnerRepository.findById(id).isEmpty()) {
            throw new EntityNotFoundException("Владелец с указанным ID не найден");
        }
        return contactOwnerRepository.deleteById(id);
    }

    @Override
    public List<ContactOwner> searchContactOwnersByUsername(String username) {
        return contactOwnerRepository.findByUsername(username);
    }

    @Override
    public List<ContactOwner> searchContactOwnersByKeyword(String keyword) {
        return contactOwnerRepository.searchByKeyword(keyword);
    }
}