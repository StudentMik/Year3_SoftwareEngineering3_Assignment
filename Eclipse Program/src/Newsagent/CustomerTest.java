package Newsagent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class CustomerTest 
{
    // Test cases for validating the customer's name

    @Test
    void testValidName() throws CustomerExceptionHandler 
    {
        // Name within valid range
        Customer customer = new Customer("John", "123 Elm Street", "1234567");
        assertEquals("John", customer.getName());
    }

    @Test
    void testNameTooShort() 
    {
        // Name shorter than 2 characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("J", "123 Elm Street", "1234567");
        });
        assertEquals("Name must be between 2 and 50 letters.", exception.getMessage());
    }

    @Test
    void testNameTooLong() 
    {
        // Name longer than 50 characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("A".repeat(51), "123 Elm Street", "1234567");
        });
        assertEquals("Name must be between 2 and 50 letters.", exception.getMessage());
    }

    @Test
    void testNameWithInvalidCharacters() 
    {
        // Name containing non-alphabetic characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("John123", "123 Elm Street", "1234567");
        });
        assertEquals("Name must be between 2 and 50 letters.", exception.getMessage());
    }

    // Test cases for validating the customer's address

    @Test
    void testValidAddress() throws CustomerExceptionHandler 
    {
        // Address within valid range
        Customer customer = new Customer("John", "123 Elm Street", "1234567");
        assertEquals("123 Elm Street", customer.getAddress());
    }

    @Test
    void testAddressTooShort() 
    {
        // Address shorter than 5 characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("John", "123", "1234567");
        });
        assertEquals("Address must be between 5 and 60 characters.", exception.getMessage());
    }

    @Test
    void testAddressTooLong() 
    {
        // Address longer than 60 characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("John", "A".repeat(61), "1234567");
        });
        assertEquals("Address must be between 5 and 60 characters.", exception.getMessage());
    }

    // Test cases for validating the customer's phone number

    @Test
    void testValidPhoneNumber() throws CustomerExceptionHandler 
    {
        // Phone number within valid range
        Customer customer = new Customer("John", "123 Elm Street", "1234567");
        assertEquals("1234567", customer.getPhoneNumber());
    }

    @Test
    void testPhoneNumberTooShort() 
    {
        // Phone number shorter than 7 characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("John", "123 Elm Street", "123456");
        });
        assertEquals("Phone number must be between 7 and 15 characters.", exception.getMessage());
    }

    @Test
    void testPhoneNumberTooLong() 
    {
        // Phone number longer than 15 characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("John", "123 Elm Street", "1".repeat(16));
        });
        assertEquals("Phone number must be between 7 and 15 characters.", exception.getMessage());
    }

    @Test
    void testPhoneNumberWithInvalidCharacters() 
    {
        // Phone number containing non-numeric characters
        Exception exception = assertThrows(CustomerExceptionHandler.class, () -> 
        {
            new Customer("John", "123 Elm Street", "123ABC");
        });
        assertEquals("Phone number must be between 7 and 15 characters.", exception.getMessage());
    }
}
