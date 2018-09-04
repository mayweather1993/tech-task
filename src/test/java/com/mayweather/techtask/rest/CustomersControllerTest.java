package com.mayweather.techtask.rest;

import com.mayweather.techtask.models.Currency;
import com.mayweather.techtask.models.Sex;
import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.CustomerVM;
import com.mayweather.techtask.models.vm.OrderVM;
import com.mayweather.techtask.repository.CustomerRepository;
import com.mayweather.techtask.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class CustomersControllerTest extends AbstractControllerTest {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;


    @Before
    public void setup() {
        orderRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
    }

    @Test
    public void testCustomerNotFound() throws Exception {
        mockMvc.perform(get("/customers/223424234")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getPage() throws Exception {
        customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        customerRepository.saveAndFlush(new CustomerEntity(2L, "Good", "Guy", Instant.now(), Sex.MALE, 4236735, Instant.now(),
                Instant.now(), null));
        mockMvc.perform(get("/customers")
                .contentType(contentType))
                .andExpect(status().isOk());
        final long count = customerRepository.count();
        assertEquals(2, count);
    }

    @Test
    public void create() throws Exception {
        mockMvc.perform(post("/customers")
                .content(this.json(new CustomerVM(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 124244, Instant.now(), Instant.now(), null)))
                .contentType(contentType))
                .andExpect(status().isCreated());
        final long count = customerRepository.count();
        assertEquals(1, count);
    }

    @Test
    public void createOrder() throws Exception {
        final CustomerEntity customerEntity = customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        mockMvc.perform(post("/customers/" + customerEntity.getId() + "/orders")
                .content(this.json(new OrderVM(1L, Status.NEW, 120d, Currency.EUR, Instant.now(), Instant.now(), customerEntity.getId())))
                .contentType(contentType))
                .andExpect(status().isOk());

        final List<OrderEntity> orders = customerEntity.getOrders();

        assertFalse(orders.isEmpty());
    }

    @Test
    public void delete() throws Exception {
        final CustomerEntity customerEntity = customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        long count = customerRepository.count();
        assertEquals(1, count);
        //delete customer by id
        mockMvc.perform(MockMvcRequestBuilders.delete("/customers/" + customerEntity.getId()))
                .andExpect(status().isNoContent());

        //expect zero orders in DB
        count = orderRepository.count();
        assertEquals(0L, count);
    }
}