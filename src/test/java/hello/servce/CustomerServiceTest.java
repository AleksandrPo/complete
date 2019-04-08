package hello.servce;

import hello.dto.CustomerDto;
import hello.mapper.CustomerMapper;
import hello.model.Customer;
import hello.repository.CustomerRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.isNotNull;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)//or in setup() need to: MockitoAnnotations.initMocks(this);
public class CustomerServiceTest {

    private Customer customer;
    private CustomerDto dto;

    @Mock
    CustomerMapper mapper;

    @Mock
    CustomerRepository repository;

    @InjectMocks
    CustomerService customerService;

    @Before
    public void setUp() throws Exception {
        customer = new Customer("Inna", "Ralli", "email@email.com", 30);
        customer.setId(10L);
        dto = new CustomerDto();
        dto.setId(10L);

        when(repository.findAll()).thenReturn(Collections.emptyList());
        when(repository.findById(10L)).thenReturn(java.util.Optional.ofNullable(customer));
        when(repository.findByFirstName("firstName")).thenReturn(Collections.singletonList(customer));
        when(repository.getCustomerByEmail("email@email.com")).thenReturn(customer);
        when(repository.findByAge(30)).thenReturn(Collections.singletonList(customer));
        when(repository.save(any(Customer.class))).thenReturn(customer);
        when(repository.findById(10L)).thenReturn(java.util.Optional.ofNullable(customer));

        when(mapper.customerToDtoList(any())).thenReturn(Arrays.asList(new CustomerDto(), new CustomerDto()));
        when(mapper.dtoToCustomer(any())).thenReturn(customer);
        when(mapper.update(any(),any())).thenReturn(customer);
    }

    @Test
    public void getAllCustomersTest() {
        //WHEN
        List<CustomerDto> actual = customerService.getAllCustomers();
        //THEN
        assertThat(actual,is(notNullValue()));
        assertThat("Failed to assert list size", actual.size(), is(2));
    }

    @Test
    public void getCustomerByIdTest() {
        Customer c = customerService.getCustomerById(10L);

        assertThat(c, is(notNullValue()));
        assertThat(c.getId(), is(10L));
    }

    @Test
    public void getCustomerByNameTest() {
        List<Customer> cl = customerService.getCustomerByName("firstName", true);

        assertThat(cl, is(notNullValue()));
        assertThat(cl.size(), is(1));
    }

    @Test
    public void getCusterByEmailTest() {
        Customer c = customerService.getCustomerByEmail("email@email.com");

        assertThat(c, is(notNullValue()));
        assertThat(c, equalTo(customer));
    }

    @Test
    public void getCustomersByAgeTest() {
        List<Customer> c = customerService.getCustomersByAge(30);

        assertThat(c, is(notNullValue()));
        assertThat(c.size(), is(1));
    }

    @Test
    public void saveTest() {
        assertThat(customerService.save(new CustomerDto()), is(true));
        verify(repository, times(1)).save(any());

        ArgumentCaptor<Customer> captor = ArgumentCaptor.forClass(Customer.class);
        verify(repository, times(1)).save(captor.capture());

        Customer actual = captor.getValue();
        assertThat(actual, is(customer));
    }

    @Test
    public void updateTest() {
        customerService.update(dto);
        verify(mapper, times(1)).update(any(),any());
        verify(repository, times(1)).save(any());

        ArgumentCaptor<Customer> customerCaptor = ArgumentCaptor.forClass(Customer.class);
        ArgumentCaptor<CustomerDto> dtoCaptor = ArgumentCaptor.forClass(CustomerDto.class);

        verify(mapper, times(1)).update(customerCaptor.capture(), dtoCaptor.capture());

        Customer actual = customerCaptor.getValue();
        assertThat(actual, samePropertyValuesAs(customer));
    }

    @Test
    public void deleteByIdTest() {
        customerService.deleteById(10L);
        verify(repository, times(2)).findById(any());
    }

    @Test
    public void deleteByEmailTest() {
        customerService.deleteByEmail("email@email.com");
        verify(repository, times(2)).getCustomerByEmail(any());
        verify(repository, times(1)).delete(any());
    }
}