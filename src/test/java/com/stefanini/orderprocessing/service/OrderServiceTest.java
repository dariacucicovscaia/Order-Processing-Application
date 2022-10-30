package com.stefanini.orderprocessing.service;

import com.stefanini.orderprocessing.dao.OrderDAO;
import com.stefanini.orderprocessing.dao.impl.OrderDAOImpl;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.domain.enums.OrderType;
import com.stefanini.orderprocessing.service.impl.OrderServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;


import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@SpringBootTest
public class OrderServiceTest {
    @Mock
    private OrderDAO<Order> orderDAOTest;
    private OrderService orderServiceTest;

    @BeforeEach
    void setupService() {
        orderDAOTest = mock(OrderDAOImpl.class);
        orderServiceTest = new OrderServiceImpl((OrderDAOImpl) orderDAOTest);
    }

    @Test
    void shouldCheckANewOrderThatIsPayed() {
        Order order = new Order(1, "new", OrderType.REFUND, true, new User(1, "Masha", "str MariaBiesu 14", "masha@gmail.com"));
        orderServiceTest.placeOrder(order);

        ArgumentCaptor<Order> orderArgumentCaptor = ArgumentCaptor.forClass(Order.class);

        verify(orderDAOTest)
                .create(orderArgumentCaptor.capture());

        Order capturedOrder = orderArgumentCaptor.getValue();


        assertThat(capturedOrder.isPaid()).isEqualTo(true);
        assertThat(capturedOrder).isEqualTo(order);
        assertThat(capturedOrder).isNotNull();

        verify(orderDAOTest, times(1)).create(order);
    }

    @Test
    public void getOrderByIdTest()
    {
        when(orderServiceTest.getOrderById(1)).thenReturn(new Order(1, "new", OrderType.REFUND, true, new User(1, "Masha", "str MariaBiesu 14", "masha@gmail.com")));

        Order order = orderServiceTest.getOrderById(1);

        assertEquals(1, order.getUserId());
        assertEquals("new", order.getStatus());
        assertEquals(OrderType.REFUND, order.getType());
    }


    @Test
    void shouldGetAllOrders() {
        List<Order> list = new ArrayList<>();
        Order maria =new Order(1, "new", OrderType.REFUND, true, new User(1, "Masha", "str MariaBiesu 14", "masha@gmail.com"));
        Order alex = new Order(2, "new", OrderType.REPAIR, true, new User(1, "alex", "str MariaBiesu 14", "alex@gmail.com"));
        Order steve = new Order(3, "new", OrderType.ACQUISITION, true, new User(1, "steve", "str MariaBiesu 14", "steve@gmail.com"));

        list.add(maria);
        list.add(alex);
        list.add(steve);

        when(orderDAOTest.getAll()).thenReturn(list);

        List<Order> orderList = orderServiceTest.getAllOrders();

        assertEquals(3, orderList.size());
        assertEquals(maria, orderList.get(0));
        verify(orderDAOTest, times(1)).getAll();
    }

}
