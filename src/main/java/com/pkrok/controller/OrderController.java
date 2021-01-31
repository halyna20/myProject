package com.pkrok.controller;

import com.pkrok.domain.OrderDTO;
import com.pkrok.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("orderr")
public class OrderController {
@Autowired
private OrderService orderService;
    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrder() {
        return ResponseEntity.ok(orderService.findAllOrderByUser());
    }
}
