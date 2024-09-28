package jtest;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class junit_testing {

    @Test
    void test_valid_customer_name() {
     CustomerName customerName = new CustomerName();
     assertAll("customer name",
             () -> assertEquals("Mike", CustomerName.getFirstName()),
             () -> assertEquals("Doherty", CustomerName.getLastName())
     );
    }

    @Test
    void test_invalid_PhoneNumber() {

        assertEquals(031, "488#erf" ,"integers only");



    }




    @Test
    void test_invalid_Stock() {
        assertEquals("1000", "1000000" ,"1000 stock limit");
    }

    @Test
    void test_valid_expiry_date() {
        assertEquals("todays date", "todays date" ,"1000 stock limit");
    }
}

