package hello.controller;


import hello.dto.CustomerDto;
import hello.model.Customer;
import hello.servce.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.List;

@RestController
public class CustomerController {

    @Autowired
    CustomerService customerService;

    @GetMapping("/")
    public List<Customer> hello() {
        return Collections.emptyList();
    }

    @GetMapping("/getCustomers")
    public List<CustomerDto> getCustomers() {
        return customerService.getAllCustomers();
    }

    @GetMapping("/getCustomerById/{id}")
    public Customer getCustomerById(@PathVariable long id) {
        return customerService.getCustomerById(id);
    }

    @GetMapping("/getCustomersByFirstName/{firstName}")
    public List<Customer> getCustomersByName(@PathVariable String firstName) {
        return customerService.getCustomerByName(firstName, true);
    }

    @GetMapping("/getCustomersByLastName/{lastName}")
    public List<Customer> getCustomersBySurname(@PathVariable String lastName) {
        return customerService.getCustomerByName(lastName, false);
    }

    @GetMapping("/getCustomerByEmail/{email}/")
    public Customer getCustomerByEmail(@PathVariable String email) {
        return customerService.getCustomerByEmail(email);
    }

    @GetMapping("/getCustomersByAge/{age}")
    public List<Customer> getCustomersByAge(@PathVariable int age) {
        return customerService.getCustomersByAge(age);
    }

    @ResponseBody
    @PostMapping("/saveCustomer/{firstName}/{lastName}/{email}/{age}")
    public void saveCustomer(CustomerDto dto) {
        customerService.save(dto);
    }

    @ResponseBody
    @PutMapping("/updateCustomer/{id}/{firstName}/{lastName}/{email}/{age}")
    public void updateCustomer(CustomerDto dto) {
        customerService.update(dto);
    }

    @PostMapping("/delete/{id}")
    public void deleteCustomer(@PathVariable long id) {
        customerService.deleteById(id);
    }

    @PostMapping("/deleteByEmail/{email}")
    public void deleteCustomer(@PathVariable String email) {
        customerService.deleteByEmail(email);
    }

    //TODO: Create POST method to saves new customer

    //TODO: Create PUT method to update existing customer. Note! If user tries to update not existing customer, throw an exception

    //TODO: Create DELETE method that deletes customer by id

    //TODO: Create DELETE method that deletes customer by any other key

}
