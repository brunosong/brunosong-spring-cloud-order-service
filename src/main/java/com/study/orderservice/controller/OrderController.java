package com.study.orderservice.controller;

import com.study.orderservice.dto.OrderDto;
import com.study.orderservice.jpa.OrderEntity;
import com.study.orderservice.messagequeue.KafkaProducer;
import com.study.orderservice.service.OrderService;
import com.study.orderservice.vo.RequestOrder;
import com.study.orderservice.vo.ResponseOrder;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order-service")
public class OrderController {

    private final Environment env;
    private final OrderService orderService;

    private final KafkaProducer kafkaProducer;

    @GetMapping("/health_check")
    public String status() {
        return String.format("It's work PORT : %s" , env.getProperty("local.server.port"));
    }

    // http://127.0.0.1:0000/order-service/{user_id}/orders/
    @PostMapping("/{userId}/orders")
    public ResponseEntity<ResponseOrder> createOrder(@RequestBody RequestOrder order ,
                                                     @PathVariable String userId) {

        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);

        OrderDto orderDto = mapper.map(order, OrderDto.class);
        orderDto.setUserId(userId);

        OrderDto creOrderDto = orderService.createOrder(orderDto);

        ResponseOrder responseOrder = mapper.map(creOrderDto, ResponseOrder.class);

        /* 카푸카로 오더 데이터를 전달한다. */
        kafkaProducer.send("example-catalog-topic", orderDto );

        return ResponseEntity.status(HttpStatus.CREATED).body(responseOrder);
    }


    // 사용자가 조회를 한다.
    @GetMapping("/{userId}/orders")
    public ResponseEntity<List<ResponseOrder>> getOrders(@PathVariable String userId) {

        Iterable<OrderEntity> orders = orderService.getOrdersByUserId(userId);

        List<ResponseOrder> result = new ArrayList<>();

        orders.forEach( order -> {
            result.add(new ModelMapper().map(order,ResponseOrder.class));
        });

        return ResponseEntity.status(HttpStatus.OK).body(result);
    }




}
