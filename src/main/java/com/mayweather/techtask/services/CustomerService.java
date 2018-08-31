package com.mayweather.techtask.services;


import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.vm.CustomerVM;
import com.mayweather.techtask.models.vm.OrderVM;

import java.util.List;

public interface CustomerService extends CrudService<CustomerEntity> {

    List<OrderVM> getOrders(final Long customerId);

    void saveOrder(final Long customerId , OrderVM orderVM);

    CustomerVM saveCustomerByVM(Long id, CustomerVM customerVM);
}
