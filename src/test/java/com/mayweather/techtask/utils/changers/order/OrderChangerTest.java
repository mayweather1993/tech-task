package com.mayweather.techtask.utils.changers.order;

import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.OrderVM;
import org.junit.Ignore;
import org.junit.Test;

import static com.mayweather.techtask.models.Status.NEW;
import static org.junit.Assert.assertEquals;

@Ignore
public class OrderChangerTest {

    @Test
    public void toVM() {
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setStatus(NEW);

        final OrderVM orderVM = OrderChanger.toOrderVM(orderEntity);

        assertEquals(orderEntity.getStatus(), orderVM.getStatus());
    }

    @Test
    public void toEntity() {
        OrderVM orderVM = new OrderVM();
        orderVM.setStatus(NEW);

        final OrderEntity orderEntity = OrderChanger.toOrderEntity(orderVM);

        assertEquals(orderEntity.getStatus() , orderVM.getStatus());
    }
}