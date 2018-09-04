package com.mayweather.techtask.rest;

import com.mayweather.techtask.models.PageableApi;
import com.mayweather.techtask.models.Status;
import com.mayweather.techtask.models.domain.OrderEntity;
import com.mayweather.techtask.models.vm.OrderVM;
import com.mayweather.techtask.services.OrderService;
import com.mayweather.techtask.utils.changers.order.OrderChanger;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static com.mayweather.techtask.GeneralConstants.ID;
import static com.mayweather.techtask.GeneralConstants.ID_PATH;
import static com.mayweather.techtask.utils.changers.order.OrderChanger.toOrderEntity;
import static com.mayweather.techtask.utils.changers.order.OrderChanger.toOrderVM;
import static org.springframework.http.HttpStatus.*;

@Api("Order API")
@RestController
@RequestMapping("/orders")
@AllArgsConstructor
@Slf4j
public class OrdersController {

    private final OrderService orderService;

    @ApiOperation(value = "Create an order")
    @PostMapping
    public ResponseEntity<OrderVM> create(@Valid @RequestBody final OrderVM orderVM, BindingResult bindingResult) {
        BindingErrorsResponse errorsResponse = new BindingErrorsResponse();
        HttpHeaders httpHeaders = new HttpHeaders();
        if (bindingResult.hasErrors()) {
            errorsResponse.addAllErrors(bindingResult);
            httpHeaders.add("errors", errorsResponse.toJSON());
            return new ResponseEntity<>(orderVM, httpHeaders, BAD_REQUEST);
        }
        final OrderEntity entity = toOrderEntity(orderVM);
        entity.setStatus(Status.NEW);
        final OrderEntity saved = orderService.save(entity);
        final OrderVM vm = toOrderVM(saved);
        return new ResponseEntity<>(vm, CREATED);
    }

    @ApiOperation(value = "Get an order by ID")
    @GetMapping(value = ID_PATH)
    public ResponseEntity<OrderVM> getById(@PathVariable(ID) final Long id) {
        final OrderEntity orderEntity = orderService.findById(id);
        final OrderVM orderVM = toOrderVM(orderEntity);
        return new ResponseEntity<>(orderVM, OK);
    }

    @ApiOperation(value = "Delete an order by ID")
    @DeleteMapping(ID_PATH)
    public ResponseEntity deleteById(@PathVariable(ID) final Long id) {
        final OrderEntity orderEntity = orderService.findById(id);
        final OrderVM orderVM = toOrderVM(orderEntity);
        orderService.delete(orderVM.getId());
        return new ResponseEntity(OK);

    }

    @ApiOperation(value = "Find all orders")
    @PageableApi
    @GetMapping
    public ResponseEntity<List<OrderVM>> getPerPage(final Pageable pageable) {
        final Page<OrderEntity> orders = orderService.getPerPage(pageable);
        final List<OrderVM> orderVMS = orders.getContent().stream().map(OrderChanger::toOrderVM).collect(Collectors.toList());
        return new ResponseEntity<>(orderVMS, OK);
    }

    @ApiOperation("Confirm an order")
    @PatchMapping(value = (ID_PATH + "/confirm"))
    public ResponseEntity<Boolean> confirmOrder(@PathVariable Long id) {
        orderService.confirm(id);
        return new ResponseEntity<>(OK);
    }

}
