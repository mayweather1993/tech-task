package com.mayweather.techtask.services;

import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.repository.OrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class OrderServiceImpl extends AbstractService<OrderRepository, OrderEntity> implements OrderService {

    public OrderServiceImpl(final OrderRepository repository) {
        super(repository);
    }

    @Override
    public OrderEntity confirm(Long id) {
        final OrderEntity orderEntity = findById(id);
        orderEntity.setStatus(Status.AGREED);
        return save(orderEntity);
    }
}
