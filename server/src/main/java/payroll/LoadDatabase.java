package payroll;

import lombok.extern.slf4j.Slf4j;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
@Slf4j
class LoadDatabase {

	@Bean
	CommandLineRunner initDatabase(EmployeeRepository employeeRepository,
								   OrderRepository orderRepository, 
								   CustomerRepository customerRepository) {
		return args -> {
			employeeRepository.save(new Employee("Bilbo", "Baggins", "burglar"));
			employeeRepository.save(new Employee("Frodo", "Baggins", "thief"));

			employeeRepository.findAll().forEach(employee -> {
				log.info("Preloaded " + employee);
			});

			orderRepository.save(new Order("MacBook Pro", Status.COMPLETED));
			orderRepository.save(new Order("iPhone", Status.IN_PROGRESS));

			orderRepository.findAll().forEach(order -> {
				log.info("Preloaded " + order);
			});

			customerRepository.save(new Customer("Naruto", "Uzumaki"));
			customerRepository.save(new Customer("Sasuke", "Uchiha"));
			
			customerRepository.findAll().forEach(customer -> {
				log.info("Preloaded " + customer);
			});

		};
	}
}
