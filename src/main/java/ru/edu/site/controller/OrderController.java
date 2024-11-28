package ru.edu.site.controller;

import org.springframework.hateoas.CollectionModel;
import org.springframework.web.bind.annotation.*;
import ru.edu.site.entity.Order;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {

    @GetMapping("/user/{id}")
    public CollectionModel<Order> getOrderByUserId(@PathVariable("id") Long userId) {
        System.out.println("some order details of user " + userId);
        return CollectionModel.empty();
    }
}
