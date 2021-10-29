package org.diframework.service.impl;

import org.diframework.core.annotations.Autowired;
import org.diframework.core.annotations.Service;
import org.diframework.dao.CustomerDao;
import org.diframework.entity.Customer;
import org.diframework.service.CustomerService;

import java.util.Collection;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Override
    public void create(Customer entity) {
        customerDao.create(entity);
    }

    @Override
    public void update(Customer entity) {

    }

    @Override
    public void delete(Integer id) {

    }

    @Override
    public Customer findById(Integer id) {
        return null;
    }

    @Override
    public Collection<Customer> findAll() {
        return customerDao.findAll();
    }
}
