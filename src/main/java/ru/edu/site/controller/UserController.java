package ru.edu.site.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.Link;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.edu.site.entity.Order;
import ru.edu.site.entity.User;
import ru.edu.site.service.UserService;

import java.util.List;
import java.util.Optional;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@RestController
@RequestMapping(value = "/api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class UserController {

    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = service.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping("/{id}/orders")
    public CollectionModel<Order> getOrderByUserId(@PathVariable("id") Long userId) {
        System.out.println("some order details of user " + userId);
        return CollectionModel.empty();
    }

    @GetMapping(value = "/hal", produces = "application/hal+json")
    public ResponseEntity<?> getAllUsersHal() {
        List<User> users = service.getAllUsers();

        users.forEach(u -> {
            u.add(linkTo(UserController.class).slash(u.getId()).withSelfRel());
            u.add(linkTo(methodOn(OrderController.class).getOrderByUserId(u.getId())).withRel("orders"));
        });
        Link link = linkTo(UserController.class).withSelfRel();

        return new ResponseEntity<>(CollectionModel.of(users, link), HttpStatus.OK);
    }

    @GetMapping("/{userId}")
    public ResponseEntity<User> getUser(@PathVariable("userId") Long userId) {
        Optional<User> user = service.getUserByid(userId);
        return user.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> ResponseEntity.notFound().build());

    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User user) {
        service.saveUser(user);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", "/api/v1/users/" + user.getId());
        return new ResponseEntity<>(null, headers, HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        service.updateUser(user);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<User> deleteUser(@PathVariable("userId") Integer userId) {
        service.deleteUser(userId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
