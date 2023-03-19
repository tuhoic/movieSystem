package com.example.movie.controller;

import com.example.movie.entity.Order;
import com.example.movie.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping("/{id}")
    public Order getById(@PathVariable("id") Integer id) {
        return orderService.getById(id);
    }

    @GetMapping
    public List<Order> list() {
        return orderService.list();
    }

    @PostMapping
    public void save(@RequestBody Order order) {
        orderService.save(order);
    }

    @PutMapping
    public void update(@RequestBody Order order) {
        orderService.updateById(order);
    }

    @DeleteMapping("/{id}")
    public void deleteById(@PathVariable("id") Integer id) {
        orderService.removeById(id);
    }
}
