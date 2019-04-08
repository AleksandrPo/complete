package hello.repository;

import java.util.List;

import hello.model.Customer;
import org.springframework.data.repository.CrudRepository;

public interface CustomerRepository extends CrudRepository<Customer, Long> {
    List<Customer> findAll();
    List<Customer> findByLastName(String lastName);
    List<Customer> findByFirstName(String firstName);
    List<Customer> findByAge(int age);
//    Customer getCustomerById(long id);
    Customer getCustomerByEmail(String email);


    //TODO: Add code that will be necessary to implement all methods in service
}
