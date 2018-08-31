package com.mayweather.techtask.services;


import com.mayweather.techtask.models.domain.OrderEntity;

public interface OrderService extends CrudService<OrderEntity> {
    OrderEntity confirm(Long id);
}
