package hello.servce;

import hello.dto.CustomerDto;
import hello.mapper.CustomerMapper;
import hello.model.Customer;
import hello.repository.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerService {

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    CustomerRepository customerRepository;

    public List<CustomerDto> getAllCustomers() {
        return customerMapper.customerToDtoList(customerRepository.findAll());
    }

    public Customer getCustomerById(long id){
        return customerRepository.findById(id).orElse(new Customer());
    }

    public List<Customer> getCustomerByName(String name, Boolean isFirstName) {
        return isFirstName ? customerRepository.findByFirstName(name) : customerRepository.findByLastName(name);
    }

    public Customer getCustomerByEmail(String email) {
        return customerRepository.getCustomerByEmail(email);
    }

    public List<Customer> getCustomersByAge(int age) {
        return customerRepository.findByAge(age);
    }

    @Transactional
    public Boolean save(CustomerDto dto) {
        if(customerRepository.getCustomerByEmail(dto.getEmail()) == null) {
            Customer customer = customerMapper.dtoToCustomer(dto);
            customerRepository.save(customer);
            return true;
        }
        return false;
    }

    @Transactional
    public void update(CustomerDto dto) {
        if( customerRepository.findById(dto.getId()).isPresent()){
            Customer c = customerMapper.update(customerRepository.findById(dto.getId()).orElse(new Customer()), dto);
            customerRepository.save(c);
        }
    }

    @Transactional
    public void deleteById(long id) {
        if(customerRepository.findById(id).isPresent()) {
            customerRepository.delete(customerRepository.findById(id).orElse(new Customer()));
        }
    }

    @Transactional
    public void deleteByEmail(String email) {
        if(customerRepository.getCustomerByEmail(email) != null) {
            customerRepository.delete(customerRepository.getCustomerByEmail(email));
        }
    }
    //TODO: Implement methods for each controller method. Note that each of them has to call different method from Service.
}
