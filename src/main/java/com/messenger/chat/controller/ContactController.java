package com.messenger.chat.controller;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("v1/chat")
public class ContactController {
    /*@Autowired
    ContactService contactService;

    @GetMapping(value = "getContacts")
    public List<ContactDto> getContacts() {
        return contactService.getContacts();
    }

    @GetMapping(value = "getContact")
    public ContactDto getContact(@RequestParam Long contactId) {
        return contactService.getContactById(contactId);
    }

    @DeleteMapping(value = "deleteContact")
    public void deleteContact(@RequestParam Long contactId) {
        contactService.deleteContact(contactId);
    }*/
}
