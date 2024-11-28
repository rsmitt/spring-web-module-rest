package ru.edu.site.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import ru.edu.site.entity.User;

@RestController
public class ClientController {

    private final RestTemplate restTemplate;
    private final String SERVICE_URL = "http://localhost:8089/api/v1/users";

    public ClientController(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @GetMapping("/client/users")
    public ResponseEntity<User[]> getAllUsers() {
        User[] users = restTemplate.getForObject(SERVICE_URL, User[].class);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/client/users/delete/{userId}")
    public ResponseEntity deleteUser(@PathVariable("userId") String id) {
        restTemplate.delete(SERVICE_URL + "/" + id);
        return ResponseEntity.ok().build();
    }
}
