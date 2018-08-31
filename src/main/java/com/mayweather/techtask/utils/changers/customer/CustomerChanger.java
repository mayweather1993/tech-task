package com.mayweather.techtask.utils.changers.customer;


import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.vm.CustomerVM;

public class CustomerChanger {

    public static CustomerVM toCustomerVM(final CustomerEntity entity) {
        final CustomerVM vm = new CustomerVM();
        vm.setId(entity.getId());
        vm.setName(entity.getName());
        vm.setSurname(entity.getSurname());
        vm.setBirthday(entity.getBirthday());
        vm.setSex(entity.getSex());
        vm.setIdentityCode(entity.getIdentityCode());
        vm.setOrders(entity.getOrders());
        vm.setCreatedDate(entity.getCreatedDate());
        vm.setLastModifiedDate(entity.getLastModifiedDate());
        return vm;
    }

    public static CustomerEntity toCustomerEntity(final CustomerVM vm) {
        final CustomerEntity entity = new CustomerEntity();
        entity.setId(vm.getId());
        entity.setName(vm.getName());
        entity.setSurname(vm.getSurname());
        entity.setBirthday(vm.getBirthday());
        entity.setSex(vm.getSex());
        entity.setIdentityCode(vm.getIdentityCode());
        entity.setOrders(vm.getOrders());
        entity.setCreatedDate(vm.getCreatedDate());
        entity.setLastModifiedDate(vm.getLastModifiedDate());
        return entity;
    }
}
