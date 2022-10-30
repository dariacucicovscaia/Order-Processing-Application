package com.stefanini.orderprocessing.service;

import com.stefanini.orderprocessing.dao.OrderDAO;
import com.stefanini.orderprocessing.dao.UserDAO;
import com.stefanini.orderprocessing.dao.impl.OrderDAOImpl;
import com.stefanini.orderprocessing.dao.impl.UserDAOImpl;
import com.stefanini.orderprocessing.domain.Order;
import com.stefanini.orderprocessing.domain.User;
import com.stefanini.orderprocessing.email.MailSenderService;
import com.stefanini.orderprocessing.service.impl.UserServiceImpl;
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
public class UserServiceTest {
    @Mock
    private OrderDAO<Order> orderDAOTest;
    @Mock
    private UserDAO<User> userDAOTest;

    private UserService userServiceTest;
    @Mock
    private MailSenderService emailSenderService;

    @BeforeEach
    void setupService() {
        orderDAOTest = mock(OrderDAOImpl.class);
        userDAOTest = mock(UserDAOImpl.class);
        userServiceTest = new UserServiceImpl((UserDAOImpl) userDAOTest, orderDAOTest, emailSenderService);
    }

    @Test
    void shouldCreateUser() {
        User user = new User(1, "Masha", "str MariaBiesu 14", "masha@gmail.com");
        userServiceTest.createUser(user);

        ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

        verify(userDAOTest)
                .create(userArgumentCaptor.capture());

        User capturedUser = userArgumentCaptor.getValue();


        assertThat(capturedUser).isEqualTo(user);
        verify(userDAOTest, times(1)).create(user);
    }

    @Test
    void shouldGetAllUsers() {
        List<User> list = new ArrayList<>();
        User john = new User(1, "John", "str MariaBiesu 12", "howtodoinjava@gmail.com");
        User alex = new User(2, "Alex", "str MariaBiesu 12", "alexk@yahoo.com");
        User steve = new User(3, "Steve", "str MariaBiesu 12", "swaugh@gmail.com");

        list.add(john);
        list.add(alex);
        list.add(steve);

        when(userDAOTest.getAll()).thenReturn(list);

        List<User> userList = userServiceTest.getAllUsers();

        assertEquals(3, userList.size());
        assertEquals(john, userList.get(0));
        verify(userDAOTest, times(1)).getAll();
    }



}
