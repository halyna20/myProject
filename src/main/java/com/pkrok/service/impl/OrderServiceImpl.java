package com.pkrok.service.impl;

import com.pkrok.domain.OrderDTO;
import com.pkrok.entity.OrderEntity;
import com.pkrok.entity.TrashEntity;
import com.pkrok.repository.OrderRepository;
import com.pkrok.service.OrderService;
import com.pkrok.utils.ObjectMapperUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService {
    private OrderRepository orderRepository;
    private ObjectMapperUtils modelMapper;

    @Autowired
    public OrderServiceImpl(OrderRepository orderRepository, ObjectMapperUtils modelMapper) {
        this.orderRepository = orderRepository;
        this.modelMapper = modelMapper;
    }




    @Override
    public List<OrderDTO> findAllOrderByUser() {
        List<OrderEntity> orderEntities = orderRepository.findAllByOrderByUser();
        return modelMapper.mapAll(orderEntities, OrderDTO.class);
    }
}
