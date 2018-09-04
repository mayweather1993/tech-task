package com.mayweather.techtask.services;

import com.mayweather.techtask.models.Currency;
import com.mayweather.techtask.models.Sex;
import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.OrderVM;
import com.mayweather.techtask.repository.CustomerRepository;
import com.mayweather.techtask.repository.OrderRepository;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomerServiceImplTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private OrderRepository orderRepository;

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
    public void testCreateCustomer() {
        long count = 2L;
        customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        customerRepository.save(new CustomerEntity(2L, "Good", "Guy", Instant.now(), Sex.MALE, 424235532, Instant.now(),
                Instant.now(), null));

        final Page<CustomerEntity> page = customerService.getPerPage(Pageable.unpaged());
        final long elements = page.getTotalElements();
        assertEquals(count, elements);
    }

    @Test
    public void testDeleteCustomer() {
        final CustomerEntity customer = customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));

        customerService.delete(customer.getId());

        final List<CustomerEntity> list = customerRepository.findAll();

        assertTrue(String.valueOf(list.isEmpty()), true);
    }

    @Test
    public void testGetCustomers() {
        long count = 2L;
        customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        customerRepository.save(new CustomerEntity(2L, "Good", "Guy", Instant.now(), Sex.MALE, 424235532, Instant.now(),
                Instant.now(), null));

        final Page<CustomerEntity> customers = customerService.getPerPage(Pageable.unpaged());
        assertEquals(count, customers.getTotalElements());
    }

    @Test
    public void getOrders() {
        long count = 2L;
        final CustomerEntity customerEntity = customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        orderService.save(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        orderService.save(new OrderEntity(2L, Status.NEW, 356d, Currency.USD,
                Instant.now(), Instant.now(), customerEntity));

        final List<OrderVM> orders = customerService.getOrders(customerEntity.getId());

        assertEquals(count, orders.size());

    }

    @Test
    public void saveOrder() {
        final CustomerEntity customerEntity = customerRepository.save(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        final OrderVM orderVM = new OrderVM(1L, Status.NEW, 245d, Currency.UAH, Instant.now(), Instant.now(), customerEntity.getId());
        List<OrderVM> orders = customerService.getOrders(customerEntity.getId());
        Assert.assertEquals(0, orders.size());
        customerService.saveOrder(customerEntity.getId(), orderVM);
        orders = customerService.getOrders(customerEntity.getId());
        Assert.assertEquals(1, orders.size());
    }
}