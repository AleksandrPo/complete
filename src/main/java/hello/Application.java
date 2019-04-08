package hello;

import hello.model.Customer;
import hello.repository.CustomerRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class Application {

	private static final Logger log = LoggerFactory.getLogger(Application.class);

	public static void main(String[] args) {
		//TODO: Don't forget about tests
		SpringApplication.run(Application.class);
	}

	@Bean
	public CommandLineRunner demo(CustomerRepository repository) {
		return (args) -> {
			repository.save(new Customer("Jack", "Bauer", "j.bauer@accenture.com", 36));
			repository.save(new Customer("Chloe", "O'Brian", "c.obrian@accenture.com", 29));
			repository.save(new Customer("Kim", "Bauer", "k.bauer@accenture.com", 20));
			repository.save(new Customer("David", "Palmer", "d.palmer@accenture.com", 38));
			repository.save(new Customer("Michele", "Dessler", "m.dessler@accenture.com", 27));

			repository.findAll().forEach(c -> {
				log.info("Customer: {}", c);
			});
		};
	}

}
