package com.mayweather.techtask.utils.changers.order;

import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.OrderVM;

import java.util.List;
import java.util.stream.Collectors;

public class OrderListChanger {
    public static List<OrderVM> listChangerToVm(List<OrderEntity> orders) {
        return orders.stream().map(OrderChanger::toOrderVM).collect(Collectors.toList());
    }
}
