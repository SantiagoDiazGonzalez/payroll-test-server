package payroll;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class EmployeeTest {

    @Test
    public void TestBasic() {
    	Employee alex = new Employee("alex", "test", "test");
    	assertEquals("alex test", alex.getName());
    }
}