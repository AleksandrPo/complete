package hello.controller;

import hello.dto.CustomerDto;
import hello.model.Customer;
import hello.servce.CustomerService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.context.WebApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@SpringBootTest
@RunWith(SpringRunner.class)
public class CustomerControllerTest {

    private MockMvc mockMvc;
    private List<Customer> customers;

    @Autowired
    protected WebApplicationContext wac;

    @Autowired
    private CustomerController controller;

    @MockBean
    CustomerService serviceMock;

    @Before
    public void setUp() throws Exception {
        this.mockMvc = standaloneSetup(this.controller).build();
        customers = new ArrayList<>();
        customers.add(new Customer("Anna", "Voronina", "a.voronina@inbox.com", 22));
        customers.add(new Customer("Vadim", "Voronin", "v.voronina@inbox.com", 33));
    }

    @Test
    public void helloTest() throws Exception {
        this.mockMvc.perform(get("/")).andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(0)));
    }

    @Test
    public void getCustomersTest() throws Exception {
        when(serviceMock.getAllCustomers()).thenReturn(Arrays.asList(new CustomerDto(), new CustomerDto()));
        this.mockMvc.perform(get("/getCustomers"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", is(notNullValue())))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    public void getCustomerByIdTest() throws Exception {
        when(serviceMock.getCustomerById(1)).thenReturn(customers.get(0));

        this.mockMvc.perform(get("/getCustomerById/{id}", 1))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Anna")))
                .andExpect(jsonPath("$.lastName", is("Voronina")))
                .andExpect(jsonPath("$.email", is("a.voronina@inbox.com")))
                .andExpect(jsonPath("$.age", is(22)));
        verify(serviceMock, times(1)).getCustomerById(1);
    }

    @Test
    public void getCustomersByNameTest() throws Exception {
        when(serviceMock.getCustomerByName("Anna", true)).thenReturn(Collections.singletonList(customers.get(0)));

        this.mockMvc.perform(get("/getCustomersByFirstName/{firstName}", "Anna"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Anna")))
                .andExpect(jsonPath("$[0].lastName", is("Voronina")))
                .andExpect(jsonPath("$[0].email", is("a.voronina@inbox.com")))
                .andExpect(jsonPath("$[0].age", is(22)));
        verify(serviceMock, times(1)).getCustomerByName("Anna", true);
    }

    @Test
    public void getCustomersBySurnameTest() throws Exception {
        when(serviceMock.getCustomerByName("Voronina", false)).thenReturn(Collections.singletonList(customers.get(0)));

        this.mockMvc.perform(get("/getCustomersByLastName/{lastName}", "Voronina"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Anna")))
                .andExpect(jsonPath("$[0].lastName", is("Voronina")))
                .andExpect(jsonPath("$[0].email", is("a.voronina@inbox.com")))
                .andExpect(jsonPath("$[0].age", is(22)));
        verify(serviceMock, times(1)).getCustomerByName("Voronina", false);
    }

    @Test
    public void getCustomerByEmailTest() throws Exception {
        when(serviceMock.getCustomerByEmail("a.voronina@inbox.com")).thenReturn(customers.get(0));

        this.mockMvc.perform(get("/getCustomerByEmail/{email}/", "a.voronina@inbox.com").characterEncoding("utf-8"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.firstName", is("Anna")))
                .andExpect(jsonPath("$.lastName", is("Voronina")))
                .andExpect(jsonPath("$.email", is("a.voronina@inbox.com")))
                .andExpect(jsonPath("$.age", is(22)));
        verify(serviceMock, times(1)).getCustomerByEmail("a.voronina@inbox.com");
    }

    @Test
    public void getCustomersByAgeTest() throws Exception {
        when(serviceMock.getCustomersByAge(22)).thenReturn(Collections.singletonList(customers.get(0)));

        this.mockMvc.perform(get("/getCustomersByAge/{age}", 22))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].firstName", is("Anna")))
                .andExpect(jsonPath("$[0].lastName", is("Voronina")))
                .andExpect(jsonPath("$[0].email", is("a.voronina@inbox.com")))
                .andExpect(jsonPath("$[0].age", is(22)));
        verify(serviceMock, times(1)).getCustomersByAge(22);
    }

    @Test
    public void saveCustomerTest() throws Exception {
        this.mockMvc.perform(post("/saveCustomer/{firstName}/{lastName}/{email}/{age}", "Anna", "Voronina", "a.voronina@inbox.com", 22))
                .andExpect(status().isOk());
        verify(serviceMock, times(1)).save(any());
    }

    @Test
    public void updateCustomerTest() throws Exception {
        this.mockMvc.perform(put("/updateCustomer/{id}/{firstName}/{lastName}/{email}/{age}", "1", "Anna", "Voronina", "a.voronina@inbox.com", 22))
                .andExpect(status().isOk());
        verify(serviceMock, times(1)).update(any());
    }

    @Test
    public void deleteCustomerTest() throws Exception {
        this.mockMvc.perform(post("/delete/{id}", 1L))
                .andExpect(status().isOk());
        verify(serviceMock, times(1)).deleteById(1L);
    }
}