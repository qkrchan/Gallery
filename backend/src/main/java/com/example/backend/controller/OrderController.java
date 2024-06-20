package com.example.backend.controller;


import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.backend.dto.OrderDto;
import com.example.backend.entity.Cart;
import com.example.backend.entity.Order;
import com.example.backend.repository.CartRepository;
import com.example.backend.repository.ItemRepository;
import com.example.backend.repository.OrderRepository;
import com.example.backend.service.JwtService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;



@RestController
public class OrderController {
  @Autowired
  OrderRepository orderRepository;
  @Autowired
  CartRepository cartRepository;
  @Autowired
  JwtService jwtService;

  @GetMapping("/api/orders")
  public ResponseEntity getOrder(
          @CookieValue(value = "token", required = false) String token
  ) {

    if (!jwtService.isValid(token)) {
        throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    // int memberId = jwtService.getId(token);
    // List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(memberId);
    List<Order> orders = orderRepository.findAll();
    return new ResponseEntity<>(orders, HttpStatus.OK);
  }
  @PostMapping("/api/orders")
  public ResponseEntity pushOrder(@RequestBody OrderDto dto, @CookieValue(value = "token", required = false) String token) {
    if (!jwtService.isValid(token)) {
      throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
    }

    int memberId = jwtService.getId(token);
    Order newOrder = new Order();

    newOrder.setMemberId(memberId);
    newOrder.setName(dto.getName());
    newOrder.setAddress(dto.getAddress());
    newOrder.setPayment(dto.getPayment());
    newOrder.setCardNumber(dto.getCardNumber());
    newOrder.setItems(dto.getItems());

    orderRepository.save(newOrder);
    // cartRepository.deleteByMemberId(memberId);

    return new ResponseEntity<>(HttpStatus.OK);
  }
  
}
