package com.mayweather.techtask.utils.changers.order;


import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.OrderVM;

public class OrderChanger {
    public static OrderVM toOrderVM(final OrderEntity entity) {
        final OrderVM vm = new OrderVM();
        vm.setId(entity.getId());
        vm.setStatus(entity.getStatus());
        vm.setPrice(entity.getPrice());
        vm.setCurrency(entity.getCurrency());
        vm.setCreatedDate(entity.getCreatedDate());
        vm.setLastModifiedDate(entity.getLastModifiedDate());
        vm.setCustomerId(entity.getCustomer().getId());
        return vm;
    }

    public static OrderEntity toOrderEntity(final OrderVM vm) {
        final OrderEntity entity = new OrderEntity();
        entity.setId(vm.getId());
        entity.setStatus(vm.getStatus());
        entity.setPrice(vm.getPrice());
        entity.setCurrency(vm.getCurrency());
        entity.setCreatedDate(vm.getCreatedDate());
        entity.setLastModifiedDate(vm.getLastModifiedDate());
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setId(vm.getCustomerId());
        entity.setCustomer(customerEntity);
        return entity;
    }
}
