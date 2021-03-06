package org.diframework.facade.impl;

import org.diframework.core.annotations.Autowired;
import org.diframework.core.annotations.Service;
import org.diframework.dto.CustomerRequestDto;
import org.diframework.dto.CustomerResponseDto;
import org.diframework.entity.Customer;
import org.diframework.facade.CustomerFacade;
import org.diframework.service.CustomerService;

import java.util.Collection;
import java.util.stream.Collectors;

@Service
public class CustomerFacadeImpl implements CustomerFacade {

    @Autowired
    private CustomerService customerService;

    @Override
    public void create(CustomerRequestDto requestDto) {
        Customer customer = new Customer();
        customer.setFirstName(requestDto.getFirstName());
        customer.setLastName(requestDto.getLastName());
        customerService.create(customer);
    }

    @Override
    public void update(CustomerRequestDto requestDto, Integer id) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public CustomerResponseDto findById(Integer id) {
        return null;
    }

    @Override
    public Collection<CustomerResponseDto> findAll() {
        return customerService.findAll().stream().map(CustomerResponseDto::new).collect(Collectors.toList());
    }
}
