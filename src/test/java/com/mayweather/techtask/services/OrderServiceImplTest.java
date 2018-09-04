package com.mayweather.techtask.services;

import com.mayweather.techtask.exceptions.ResourceNotFoundException;
import com.mayweather.techtask.models.Currency;
import com.mayweather.techtask.models.Sex;
import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.repository.CustomerRepository;
import com.mayweather.techtask.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderServiceImplTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private OrderService orderService;


    @Rule
    public ExpectedException expectedException = ExpectedException.none();
    @Before
    public void setUp() {
        orderRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
    }

    @Test
    public void testCreateOrder(){
        long count = 1L;
        final CustomerEntity customerEntity = customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        orderService.save(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        final List<OrderEntity> orderEntities = orderRepository.findAll();

        assertEquals(count , orderEntities.size());
    }

    @Test
    public void testingConfirmOperation() {
        Status confirmedStatus = Status.AGREED;


        final CustomerEntity customerEntity = customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        final OrderEntity orderEntity = orderService.save(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        final OrderEntity confirmedOrder = orderService.confirm(orderEntity.getId());

        final Status confirmedOrderStatus = confirmedOrder.getStatus();
        assertEquals(confirmedStatus , confirmedOrderStatus);

    }
    @Test
    public void testGetOrders() {
        //given
        final CustomerEntity customerEntity = customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        final CustomerEntity customerEntity2 = customerRepository.save(new CustomerEntity(2L, "Good", "Guy", Instant.now(), Sex.MALE, 777777, Instant.now(),
                Instant.now(), null));
        orderService.save(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        orderService.save(new OrderEntity(2L, Status.NEW, 149d, Currency.USD,
                Instant.now(), Instant.now(), customerEntity2));
        //when
        final Page<OrderEntity> page = orderService.getPerPage(Pageable.unpaged());
        //then
        assertEquals(2, page.getTotalPages());

    }

    @Test
    public void testOrderNotFound() {
        //given
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("[OrderEntity] not found by id: [21313]");
        //when
        orderService.findById(21313L);
    }

    @Test
    public void testDeleteOrderNotFound() {
        //given
        expectedException.expect(ResourceNotFoundException.class);
        expectedException.expectMessage("[OrderEntity] not found by id: [21313]");
        //when
        orderService.delete(21313L);
    }

}

