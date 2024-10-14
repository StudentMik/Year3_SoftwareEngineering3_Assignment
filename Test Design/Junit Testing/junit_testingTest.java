package jtest;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class junit_testing {

    @Test
    void testValidCustomerName() {
     CustomerName customerName = new CustomerName();
     assertAll("customer name",
             () -> assertEquals("Mike", CustomerName.getFirstName()),
             () -> assertEquals("Doherty", CustomerName.getLastName())
     );
    }

    @Test
    void testInvalidPhoneNumber() {

        assertEquals(031, "488#erf" ,"integers only");



    }

    @Test
    void testInvalidId() {

        assertEquals(031, "488#erf" ,"integers only");


    }

    @Test
    void testInvalidPrice() {

        assertEquals(22.01,  2 ,"doubles only");

    }

    @Test
    void testInvalidBill() {

        assertEquals(100, -100 ,"error: cannot be < 0");



    }




}

