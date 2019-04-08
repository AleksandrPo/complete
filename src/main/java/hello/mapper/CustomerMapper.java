package hello.mapper;

import hello.dto.CustomerDto;
import hello.model.Customer;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class CustomerMapper {

    //TODO: Implement method that maps entity to dto
    public CustomerDto customerToDto(Customer customer){
        CustomerDto dto = new CustomerDto();
        if(customer != null) {
            BeanUtils.copyProperties(customer, dto, CustomerDto.class);
        }
        return dto;
    }

    //TODO: Implement method that maps dto to entity
    public Customer dtoToCustomer(CustomerDto dto) {
        if(dto != null) {
            return new Customer(dto.getFirstName(), dto.getLastName(), dto.getEmail(), dto.getAge());
        }
        return null;
    }

    //TODO: Implement method that maps List of entities to List of dto
    public List<CustomerDto> customerToDtoList(List<Customer> customers) {
        return customers.stream().map(this::customerToDto).collect(Collectors.toList());
    }

    //TODO: Implement method that maps List of dto to List of entities
    public List<Customer> dtoToCustomerList(List<CustomerDto> dtos) {
        return dtos.stream().map(i -> dtoToCustomer(i)).collect(Collectors.toList());//i -> dtoToCustomer(i) == this::dtoToCustomer
    }

    //TODO: Implement method that updates entity with dto data
    public Customer update(Customer customer, CustomerDto dto) {
        if(dto != null) {
            customer.setFirstName(dto.getFirstName());
            customer.setLastName(dto.getLastName());
            customer.setEmail(dto.getEmail());
            customer.setAge(dto.getAge());
        }
        return customer;
    }
}
