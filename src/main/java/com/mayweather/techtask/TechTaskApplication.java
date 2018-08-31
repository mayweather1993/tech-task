package com.mayweather.techtask;

import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.repository.CustomerRepository;
import com.mayweather.techtask.repository.OrderRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TechTaskApplication implements CommandLineRunner {

	private final CustomerRepository customerRepository;
	private final OrderRepository orderRepository;

	public TechTaskApplication(CustomerRepository customerRepository, OrderRepository orderRepository) {
		this.customerRepository = customerRepository;
		this.orderRepository = orderRepository;
	}

	public static void main(String[] args) {
		SpringApplication.run(TechTaskApplication.class, args);
	}

	@Override
	public void run(String... args) {

	}

	//blya tam zhe validaciya vezde karoch popozhe
	private void loadCustomers() {
		CustomerEntity customerEntity = new CustomerEntity();
		customerEntity.setName("Fred");

		CustomerEntity customerEntity1  = new CustomerEntity();
		customerEntity.setName("Dred");

		CustomerEntity customerEntity2 = new CustomerEntity();
		customerEntity2.setName("Josh");

		CustomerEntity customerEntity3 = new CustomerEntity();
		customerEntity3.setName("Billi");

		CustomerEntity customerEntity4 = new CustomerEntity();
		customerEntity4.setName("God");

		customerRepository.save(customerEntity);
		customerRepository.save(customerEntity1);
		customerRepository.save(customerEntity2);
		customerRepository.save(customerEntity3);
		customerRepository.save(customerEntity4);

		System.out.println("Customers Loaded: " + customerRepository.count());
	}

	private void loadOrders() {
		OrderEntity orderEntity = new OrderEntity();
		orderEntity.setId(1L);
		orderEntity.setStatus(Status.NEW);
		orderRepository.save(orderEntity);

		OrderEntity orderEntity1 = new OrderEntity();
		orderEntity1.setId(2l);
		orderEntity1.setStatus(Status.AGREED);
		orderEntity1.setPrice(322d);

		orderRepository.save(orderEntity1);

		System.out.println("Orders Loaded: " + orderRepository.count());
	}
}

