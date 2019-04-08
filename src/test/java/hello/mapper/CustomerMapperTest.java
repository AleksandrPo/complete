package hello.mapper;

import hello.dto.CustomerDto;
import hello.model.Customer;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.samePropertyValuesAs;
import static org.junit.Assert.*;

public class CustomerMapperTest {

    private Customer customer;
    private CustomerDto dto;

    CustomerMapper mapper;

    @Before
    public void setUp() throws Exception {
        mapper = new CustomerMapper();
        customer = new Customer("Lena","Lenova","l.lenova@mail.com",20);
        dto = new CustomerDto.Builder()
                .setFirstName(customer.getFirstName())
                .setLastName(customer.getLastName())
                .setEmail(customer.getEmail())
                .setAge(customer.getAge())
                .build();
    }

    @Test
    public void customerToDtoTest() {
        CustomerDto actual = mapper.customerToDto(customer);
        assertThat(actual, samePropertyValuesAs(dto));
    }

    @Test
    public void dtoToCustomerTest() {
        Customer actual = mapper.dtoToCustomer(dto);
        assertThat(actual, samePropertyValuesAs(customer));
    }

    @Test
    public void customerToDtoListTest() {
        List<CustomerDto> actual = mapper.customerToDtoList(Collections.singletonList(customer));
        assertNotNull(actual);
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), samePropertyValuesAs(dto));
    }

    @Test
    public void dtoToCustomerListTest() {
        List<Customer> actual = mapper.dtoToCustomerList(Collections.singletonList(dto));
        assertNotNull(actual);
        assertThat(actual.size(), is(1));
        assertThat(actual.get(0), samePropertyValuesAs(customer));
    }

    @Test
    public void updateTest() {
        Customer actual = mapper.update(new Customer(), dto);
        assertNotNull(actual);
        assertThat(actual, samePropertyValuesAs(customer));
    }
}