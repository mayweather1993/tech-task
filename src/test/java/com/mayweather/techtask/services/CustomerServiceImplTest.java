package com.mayweather.techtask.services;

import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;

import static org.junit.Assert.*;

@Ignore
public class CustomerServiceImplTest {

    @Mock
    CustomerRepository customerRepository;

    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        MockitoAnnotations.initMocks(this);

        customerService = new CustomerServiceImpl(customerRepository);
    }

    @Test
    public void getOrders() {
        CustomerEntity customer1 = new CustomerEntity();
        customer1.setId(1L);
        customer1.setName("Michale");
        customer1.setSurname("Weston");

        CustomerEntity customer2 = new CustomerEntity();
        customer2.setId(2L);
        customer2.setName("Sam");
        customer2.setSurname("Axe");

        Mockito.when(customerRepository.findAll()).thenReturn(Arrays.asList(customer1 , customer2));


    }

    @Test
    public void saveOrder() {
    }

    @Test
    public void saveCustomerByVM() {
    }
}