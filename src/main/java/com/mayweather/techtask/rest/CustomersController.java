package com.mayweather.techtask.rest;

import com.mayweather.techtask.models.PageableApi;
import com.mayweather.techtask.models.domain.CustomerEntity;
import com.mayweather.techtask.models.vm.CustomerVM;
import com.mayweather.techtask.models.vm.OrderVM;
import com.mayweather.techtask.services.CustomerService;
import com.mayweather.techtask.utils.changers.customer.CustomerChanger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.mayweather.techtask.GeneralConstants.ID;
import static com.mayweather.techtask.GeneralConstants.ID_PATH;
import static com.mayweather.techtask.utils.changers.customer.CustomerChanger.toCustomerEntity;
import static com.mayweather.techtask.utils.changers.customer.CustomerChanger.toCustomerVM;
import static org.springframework.http.HttpStatus.*;


@Api("Customer API")
@RestController
@RequestMapping(CustomersController.BASE_URL)
@AllArgsConstructor
@Slf4j
public class CustomersController {

    static final String BASE_URL = "/customers";

    private final CustomerService customerService;

    @ApiOperation("Find all customers")
    @PageableApi
    @GetMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<PageImpl<CustomerVM>> getPage(final Pageable pageable) {
        final Page<CustomerEntity> page = customerService.getPerPage(pageable);
        final List<CustomerVM> vms = page.getContent().stream()
                .map(CustomerChanger::toCustomerVM)
                .collect(Collectors.toList());
        final PageImpl<CustomerVM> vmPage = new PageImpl<>(vms, pageable, page.getTotalElements());
        return new ResponseEntity<>(vmPage, OK);
    }


    @ApiOperation("Find a customer by ID")
    @GetMapping(value = ID_PATH)
    public ResponseEntity<CustomerVM> getById(@PathVariable(ID) final Long id) {
        final CustomerEntity entity = customerService.findById(id);
        final CustomerVM vm = toCustomerVM(entity);
        return new ResponseEntity<>(vm, OK);
    }

    @ApiOperation(value = "Create a customer")
    @PostMapping(produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public ResponseEntity<CustomerVM> create(@Valid @RequestBody final CustomerVM vm, BindingResult bindingResult) {
        BindingErrorsResponse errorsResponse = new BindingErrorsResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            errorsResponse.addAllErrors(bindingResult);
            httpHeaders.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(vm, httpHeaders, BAD_REQUEST);
        }
        final CustomerEntity entity = toCustomerEntity(vm);
        final CustomerEntity saved = customerService.save(entity);
        final CustomerVM savedVM = toCustomerVM(saved);
        return new ResponseEntity<>(savedVM, CREATED);
    }

    @ApiOperation(value = "Update a customer")
    @PutMapping(value = ID_PATH)
    public ResponseEntity<CustomerVM> update(@PathVariable Long id, @Valid @RequestBody final CustomerVM vm, BindingResult bindingResult) {
        BindingErrorsResponse errorsResponse = new BindingErrorsResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            errorsResponse.addAllErrors(bindingResult);
            httpHeaders.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(httpHeaders, BAD_REQUEST);
        }

        final CustomerVM customerVM = customerService.saveCustomerByVM(id, vm);
        return new ResponseEntity<>(customerVM, OK);
    }

    @ApiOperation(value = "Find all customer orders ")
    @GetMapping(value = ID_PATH + "/orders")
    public ResponseEntity<List<OrderVM>> getCustomerOrders(@PathVariable(ID) final Long id) {
        final List<OrderVM> orders = customerService.getOrders(id);
        return new ResponseEntity<>(orders, OK);
    }

    @ApiOperation(value = "Create an customer order")
    @PostMapping(value = ID_PATH + "/orders")
    public ResponseEntity createOrder(@PathVariable(ID) final Long id, @Valid @RequestBody OrderVM orderVM) {
        customerService.saveOrder(id, orderVM);
        return new ResponseEntity(OK);
    }


    @ApiOperation(value = "Delete a customer")
    @DeleteMapping(value = ID_PATH)
    public ResponseEntity delete(@PathVariable(ID) final Long id) {
        customerService.delete(id);
        return new ResponseEntity<>(NO_CONTENT);
    }
}
