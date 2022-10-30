package com.stefanini.orderprocessing.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.domain.enums.OrderType;
import com.stefanini.orderprocessing.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.BDDMockito.given;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.CoreMatchers.is;

@SpringBootTest
@AutoConfigureMockMvc
public class OrderControllerTest {

    @Autowired
    private OrderController orderController;
    @MockBean
    private OrderServiceImpl orderService;
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;


    @Test
    void shouldUpdateOrderStatus() throws Exception {
        Order orderToUpdate = new Order(1, "new", OrderType.REFUND, true, new User(1, "Masha", "str MariaBiesu 14", "masha@gmail.com"));
        Order orderWithUpdatedStatus =new Order(1, "in process", OrderType.REFUND, true, new User(1, "Masha", "str MariaBiesu 14", "masha@gmail.com"));
        given(orderService.getOrderById(orderToUpdate.getId())).willReturn(orderToUpdate);
        given(orderService.updateOrderStatus(orderToUpdate.getId(), "in process")).willReturn(orderWithUpdatedStatus );

        this.mockMvc.perform(put("/api/order/{orderId}/{newStatus}", orderToUpdate.getId(), orderWithUpdatedStatus.getStatus())
                        .content(objectMapper.writeValueAsString(orderToUpdate)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status", is(orderWithUpdatedStatus.getStatus())));

    }

    @Test
    @Disabled
    void shouldChangePayedToTrue(){

    }
}
