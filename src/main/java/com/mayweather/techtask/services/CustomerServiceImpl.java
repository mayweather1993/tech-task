package com.mayweather.techtask.services;

import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.CustomerVM;
import com.mayweather.techtask.models.vm.OrderVM;
import com.mayweather.techtask.repository.CustomerRepository;
import com.mayweather.techtask.utils.changers.order.OrderChanger;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static com.mayweather.techtask.utils.changers.customer.CustomerChanger.toCustomerEntity;
import static com.mayweather.techtask.utils.changers.customer.CustomerChanger.toCustomerVM;
import static com.mayweather.techtask.utils.changers.order.OrderListChanger.listChangerToVm;

@Service
@Transactional
public class CustomerServiceImpl extends AbstractService<CustomerRepository, CustomerEntity> implements CustomerService {

    public CustomerServiceImpl(final CustomerRepository repository) {
        super(repository);
    }

    @Override
    public List<OrderVM> getOrders(Long customerId) {
        final CustomerEntity entity = findById(customerId);
        final List<OrderEntity> orders = entity.getOrders();
        return listChangerToVm(orders);
    }

    @Override
    public void saveOrder(Long customerId, OrderVM orderVM) {
        final CustomerEntity customerEntity = findById(customerId);
        final List<OrderEntity> orders = customerEntity.getOrders();
        orders.add(OrderChanger.toOrderEntity(orderVM));
        save(customerEntity);
    }

    @Override
    public CustomerVM saveCustomerByVM(Long id, CustomerVM customerVM) {
        final CustomerEntity customerEntity = toCustomerEntity(customerVM);
        customerEntity.setId(id);
        return toCustomerVM(customerEntity);
    }
}
