package payroll;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// tag::constructor[]
@RestController
class CustomerController {

	private final CustomerRepository repository;

	private final CustomerResourceAssembler assembler;
	private final OrderResourceAssembler orderAssembler;

	CustomerController(CustomerRepository repository,
					   CustomerResourceAssembler assembler,
					   OrderResourceAssembler orderAssembler) {
		
		this.repository = repository;
		this.assembler = assembler;
		this.orderAssembler = orderAssembler;
	}
	// end::constructor[]

	// Aggregate root

	@GetMapping("/customers")
	Resources<Resource<Customer>> all() {

		List<Resource<Customer>> customers = repository.findAll().stream()
			.map(assembler::toResource)
			.collect(Collectors.toList());
		
		return new Resources<>(customers,
			linkTo(methodOn(CustomerController.class).all()).withSelfRel());
	}

	@PostMapping("/customers")
	ResponseEntity<?> newCustomer(@RequestBody Customer newCustomer) throws URISyntaxException {

		Resource<Customer> resource = assembler.toResource(repository.save(newCustomer));

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	// Single item

	@GetMapping("/customers/{id}")
	Resource<Customer> one(@PathVariable Long id) {

		Customer customer = repository.findById(id)
			.orElseThrow(() -> new CustomerNotFoundException(id));
		
		return assembler.toResource(customer);
	}

	@PutMapping("/customers/{id}")
	ResponseEntity<?> replaceCustomer(@RequestBody Customer newCustomer, @PathVariable Long id) throws URISyntaxException {

		Customer updatedCustomer = repository.findById(id)
			.map(customer -> {
				customer.setName(newCustomer.getName());
				return repository.save(customer);
			})
			.orElseGet(() -> {
				newCustomer.setId(id);
				return repository.save(newCustomer);
			});

		Resource<Customer> resource = assembler.toResource(updatedCustomer);

		return ResponseEntity
			.created(new URI(resource.getId().expand().getHref()))
			.body(resource);
	}

	@DeleteMapping("/customers/{id}")
	ResponseEntity<?> deleteCustomer(@PathVariable Long id) {

		repository.deleteById(id);
		
		return ResponseEntity.noContent().build();
	}
}

