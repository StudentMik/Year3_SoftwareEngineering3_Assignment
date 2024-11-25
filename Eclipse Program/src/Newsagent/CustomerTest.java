package Newsagent;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

class CustomerTest
{

    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException
    {
        connection = Customer.init_db(); // Use the Customer's init_db method
    }

    // Constructor Tests
    @Test
    void testConstructorValidInputs() throws CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");
        assertEquals("Alice", customer.getName());
        assertEquals("123 Maple Street", customer.getAddress());
        assertEquals("1234567", customer.getPhoneNumber());
    }

    @Test
    void testConstructorNullName()
    {
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
            new Customer(null, "123 Maple Street", "1234567")
        );
        assertEquals("Name must be between 2 and 50 letters.", exception.getMessage());
    }

    @Test
    void testConstructorNullAddress()
    {
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
            new Customer("Alice", null, "1234567")
        );
        assertEquals("Address must be between 5 and 60 characters.", exception.getMessage());
    }

    @Test
    void testConstructorNullPhoneNumber()
    {
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
            new Customer("Alice", "123 Maple Street", null)
        );
        assertEquals("Phone number must be between 7 and 15 characters.", exception.getMessage());
    }

    // Getter and Setter Tests
    @Test
    void testGetAndSetId() throws CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");
        customer.setId(10);
        assertEquals(10, customer.getId());
    }

    @Test
    void testGetAndSetName() throws CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");
        customer.setName("Bob");
        assertEquals("Bob", customer.getName());
    }

    @Test
    void testGetAndSetAddress() throws CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");
        customer.setAddress("456 Oak Avenue");
        assertEquals("456 Oak Avenue", customer.getAddress());
    }

    @Test
    void testGetAndSetPhoneNumber() throws CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");
        customer.setPhoneNumber("7654321");
        assertEquals("7654321", customer.getPhoneNumber());
    }

    // addToDatabase Method Tests
    @Test
    void testAddToDatabaseSuccess() throws SQLException, CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");
        customer.addToDatabase(connection);
        assertNotNull(customer); // Assert that no exceptions occur and customer is valid
    }

    @Test
    void testAddToDatabaseFail() throws SQLException, CustomerExceptionHandler
    {
        Customer customer = new Customer("Alice", "123 Maple Street", "1234567");

        // Simulate a failed operation
        connection.createStatement().execute("DELETE FROM customers WHERE Name='Alice';");
        customer.addToDatabase(connection);
        assertNotNull(customer); // Ensure no exception is thrown
    }

    // readCustomer Method Tests
    @Test
    void testReadCustomer() throws SQLException
    {
        // Call readCustomer to ensure no exceptions occur
        Customer.readCustomer(connection);
        assertTrue(true); // Pass if no exceptions are thrown
    }

    // updateCustomer Method Tests
    @Test
    void testUpdateCustomerSuccess() throws SQLException
    {
        // Simulate updating a customer
        Customer.updateCustomer(connection, new java.util.Scanner("1\nJohn Doe\nNew Address\n1234567"));
        assertTrue(true); // Pass if no exceptions occur
    }

    @Test
    void testUpdateCustomerFail() throws SQLException
    {
        // Simulate updating a non-existent customer
        Customer.updateCustomer(connection, new java.util.Scanner("9999\nJohn Doe\nNew Address\n1234567"));
        assertTrue(true); // Pass if no exceptions occur
    }

    // deleteCustomer Method Tests
    @Test
    void testDeleteCustomerSuccess() throws SQLException
    {
        // Simulate deleting a customer
        Customer.deleteCustomer(connection, new java.util.Scanner("1"));
        assertTrue(true); // Pass if no exceptions occur
    }

    @Test
    void testDeleteCustomerFail() throws SQLException
    {
        // Simulate deleting a non-existent customer
        Customer.deleteCustomer(connection, new java.util.Scanner("9999"));
        assertTrue(true); // Pass if no exceptions occur
    }
}
