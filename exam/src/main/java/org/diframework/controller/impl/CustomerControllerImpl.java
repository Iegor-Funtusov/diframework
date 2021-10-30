package org.diframework.controller.impl;

import org.diframework.controller.CustomerController;
import org.diframework.core.annotations.Autowired;
import org.diframework.core.annotations.Service;
import org.diframework.core.crud.controller.*;
import org.diframework.dto.CustomerRequestDto;
import org.diframework.dto.CustomerResponseDto;
import org.diframework.facade.CustomerFacade;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Collection;

@Service
public class CustomerControllerImpl implements CustomerController {

    @Autowired
    private CustomerFacade customerFacade;

    @Create
    public boolean create(BufferedReader reader) {
        try {
            System.out.println("Please, enter your first name");
            String firstName = reader.readLine();
            System.out.println("Please, enter your last name");
            String lastName = reader.readLine();

            CustomerRequestDto dto = new CustomerRequestDto();
            dto.setFirstName(firstName);
            dto.setLastName(lastName);

            customerFacade.create(dto);
            return true;
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return false;
    }

    @Update
    public boolean update(BufferedReader reader) {
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            System.out.println("Please, enter your name");
            String name = reader.readLine();
            System.out.println("Please, enter your age");
            String ageString = reader.readLine();
            int age = Integer.parseInt(ageString);
            CustomerRequestDto dto = new CustomerRequestDto();
            customerFacade.update(dto, Integer.parseInt(id));
            return true;
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return false;
    }

    @Delete
    public boolean delete(BufferedReader reader) {
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            Integer idd = Integer.parseInt(id);
            customerFacade.delete(idd);
            return true;
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return false;
    }

    @Find
    public CustomerResponseDto findById(BufferedReader reader) {
        try {
            System.out.println("Please, enter id");
            String id = reader.readLine();
            Integer idd = Integer.parseInt(id);
            return customerFacade.findById(idd);
        } catch (IOException e) {
            System.out.println("problem: = " + e.getMessage());
        }
        return null;
    }

    @Find
    public Collection<CustomerResponseDto> findAll(BufferedReader reader) {
        return customerFacade.findAll();
    }
}
