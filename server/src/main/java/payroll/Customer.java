package payroll;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Data
@Entity
class Customer {

	private @Id @GeneratedValue Long id;
	private String firstName;
	private String lastName;

	Customer() {}

	Customer(String firstName, String lastName) {
		this.firstName = firstName;
		this.lastName = lastName;
	}

	public String getName() {
		return this.firstName + " " + this.lastName;
	}

	public void setName(String name) {
		String[] parts =name.split(" ");
		this.firstName = parts[0];
		this.lastName = parts[1];
	}
}
