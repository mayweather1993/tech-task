package com.mayweather.techtask.utils.changers.customer;

import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.vm.CustomerVM;
import org.junit.Ignore;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

@Ignore
public class CustomerChangerTest {

    private final String TEST = "TEST";

    @Test
    public void toCustomerVM() {
        CustomerEntity customerEntity = new CustomerEntity();
        customerEntity.setName(TEST);

        final CustomerVM customerVM = CustomerChanger.toCustomerVM(customerEntity);

        assertEquals(customerEntity.getName(), customerVM.getName());
    }

    @Test
    public void toCustomerEntity() {
        CustomerVM customerVM = new CustomerVM();
        customerVM.setName(TEST);

        final CustomerEntity customerEntity = CustomerChanger.toCustomerEntity(customerVM);

        assertEquals(customerEntity.getName(), customerVM.getName());
    }
}