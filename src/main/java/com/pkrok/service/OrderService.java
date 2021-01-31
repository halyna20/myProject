package com.pkrok.service;

import com.pkrok.domain.OrderDTO;

import java.util.List;

public interface OrderService {
    List<OrderDTO> findAllOrderByUser();
}
