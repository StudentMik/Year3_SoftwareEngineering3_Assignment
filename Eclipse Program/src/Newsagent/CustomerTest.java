package Newsagent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

class CustomerTest 
{
    @Test
    void testCustomer() 
    {
        // Test if a customer can be created with valid inputs
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            assertNotNull(customer);  // Check if the customer object is created
            assertEquals("John", customer.getName());  // Check the name
            assertEquals("123 Elm Street", customer.getAddress());  // Check the address
            assertEquals("1234567", customer.getPhoneNumber());  // Check the phone number
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown for valid inputs");  // Fail if an exception occurs
        }
    }

    @Test
    void testGetId() 
    {
        // Test if we can get the customer's ID
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            customer.setId(101);  // Set the ID
            assertEquals(101, customer.getId());  // Check if the ID is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testSetId() 
    {
        // Test if we can set the customer's ID
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            customer.setId(202);  // Set a new ID
            assertEquals(202, customer.getId());  // Check if the new ID is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testGetName() 
    {
        // Test if we can get the customer's name
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            assertEquals("John", customer.getName());  // Check if the name is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testSetName() 
    {
        // Test if we can set the customer's name
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            customer.setName("Jane");  // Change the name
            assertEquals("Jane", customer.getName());  // Check if the new name is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testGetAddress() 
    {
        // Test if we can get the customer's address
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            assertEquals("123 Elm Street", customer.getAddress());  // Check if the address is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testSetAddress() 
    {
        // Test if we can set the customer's address
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            customer.setAddress("456 Oak Avenue");  // Change the address
            assertEquals("456 Oak Avenue", customer.getAddress());  // Check if the new address is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testGetPhoneNumber() 
    {
        // Test if we can get the customer's phone number
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            assertEquals("1234567", customer.getPhoneNumber());  // Check if the phone number is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testSetPhoneNumber() 
    {
        // Test if we can set the customer's phone number
        try 
        {
            Customer customer = new Customer("John", "123 Elm Street", "1234567");
            customer.setPhoneNumber("9876543");  // Change the phone number
            assertEquals("9876543", customer.getPhoneNumber());  // Check if the new phone number is correct
        } 
        catch (CustomerExceptionHandler e) 
        {
            fail("Exception should not have been thrown");  // Fail if an exception occurs
        }
    }

    @Test
    void testAddToDatabase() 
    {
        // Placeholder test for adding a customer to the database
        fail("Database interaction not implemented in this test");  // Mark as incomplete
    }

    @Test
    void testReadCustomer() 
    {
        // Placeholder test for reading a customer from the database
        fail("Database interaction not implemented in this test");  // Mark as incomplete
    }

    @Test
    void testCreateCustomer() 
    {
        // Placeholder test for creating a customer
        fail("Method not implemented in this test");  // Mark as incomplete
    }

    @Test
    void testUpdateCustomer() 
    {
        // Placeholder test for updating a customer
        fail("Method not implemented in this test");  // Mark as incomplete
    }

    @Test
    void testDeleteCustomer() 
    {
        // Placeholder test for deleting a customer
        fail("Method not implemented in this test");  // Mark as incomplete
    }

    @Test
    void testMain() 
    {
        // Placeholder test for the main method
        fail("Main method integration not implemented in this test");  // Mark as incomplete
    }

    @Test
    void testInit_db() 
    {
        // Placeholder test for initializing the database
        fail("Database initialization not implemented in this test");  // Mark as incomplete
    }
}
