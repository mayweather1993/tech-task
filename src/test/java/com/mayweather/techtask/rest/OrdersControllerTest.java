package com.mayweather.techtask.rest;

import com.mayweather.techtask.models.Currency;
import com.mayweather.techtask.models.Sex;
import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.OrderVM;
import com.mayweather.techtask.repository.CustomerRepository;
import com.mayweather.techtask.repository.OrderRepository;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.Instant;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class OrdersControllerTest extends AbstractControllerTest {

    @Before
    public void setup() {
        orderRepository.deleteAllInBatch();
        customerRepository.deleteAllInBatch();
    }

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Test
    public void create() throws Exception {
        final CustomerEntity customerEntity = customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        mockMvc.perform(post("/orders")
                .content(this.json(new OrderVM(null, Status.NEW, 120d, Currency.EUR, Instant.now(), Instant.now(), customerEntity.getId())))
                .contentType(contentType))
                .andExpect(status().isCreated());
        final long count = orderRepository.count();
        assertEquals(1, count);
    }


    @Test
    public void getPerPage() throws Exception {
        final CustomerEntity customerEntity = customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        final CustomerEntity customerEntity2 = customerRepository.saveAndFlush(new CustomerEntity(2L, "Good", "Guy", Instant.now(), Sex.MALE, 4236735, Instant.now(),
                Instant.now(), null));
        orderRepository.saveAndFlush(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        orderRepository.saveAndFlush(new OrderEntity(2L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity2));

        mockMvc.perform(get("/orders")
                .contentType(contentType))
                .andExpect(status().isOk());
        final long count = orderRepository.count();
        assertEquals(2, count);

    }

    @Test
    public void testOrderNotFound() throws Exception {
        mockMvc.perform(get("/orders/223424234")
                .contentType(contentType))
                .andExpect(status().isNotFound());
    }

   /* @Test
    public void testCreateOrder() throws Exception {
        mockMvc.perform(post("/orders")
                .content(this.json(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                        Instant.now(), Instant.now(), null))
                        .contentType(contentType))
                .andExpect(status().isCreated());
        final long count = orderRepository.count();
        assertEquals(1, count);
    }
    */

    @Test
    public void testDeleteOrder() throws Exception {
        final CustomerEntity customerEntity = customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));

        //given create order
        final OrderEntity orderEntity = orderRepository.saveAndFlush(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        long count = orderRepository.count();
        assertEquals(1, count);
        //delete order by id
        mockMvc.perform(delete("/orders/" + orderEntity.getId())
                .contentType(contentType))
                .andExpect(status().isOk());

        //expect zero orders in DB
        count = orderRepository.count();
        assertEquals(0L, count);
    }

    @Test
    public void testConfirmOrder() throws Exception {
        Status status = Status.AGREED;
        final CustomerEntity customerEntity = customerRepository.saveAndFlush(new CustomerEntity(1L, "Pasha", "Kozachek", Instant.now(), Sex.MALE, 1039435, Instant.now(),
                Instant.now(), null));
        //given create order
        final OrderEntity orderEntity = orderRepository.saveAndFlush(new OrderEntity(1L, Status.NEW, 250d, Currency.EUR,
                Instant.now(), Instant.now(), customerEntity));
        final OrderVM orderVM = new OrderVM(orderEntity.getId(), Status.NEW, 120d, Currency.EUR, Instant.now(), Instant.now(), customerEntity.getId());

        mockMvc.perform(patch("/orders/" + orderEntity.getId() + "/confirm")
                .content(json(orderVM))
                .contentType(contentType))
                .andExpect(status().isOk());

        final List<OrderEntity> list = orderRepository.findAll();
        assertEquals(status, list.get(0).getStatus());
    }

}
