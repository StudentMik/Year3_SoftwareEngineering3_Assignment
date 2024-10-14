package jtest;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.*;


public class OrderTest {


    //Test #: 1
    //Test Objective: To catch an invalid customer ID
    //	//Inputs:  = "488#erf"
    //	//Expected Output: "error: customer id must be an int"

    @Test
    void testInvalidId() {
        assertEquals(031, "488#erf", "integers only");

    }

    //Test #: 2
    //Test Objective: To catch an invalid publication price
    //	//Inputs:  = "22.01"
    //	//Expected Output: "error: price must be a double"
    @Test
    void testInvalidPrice() {

        assertEquals(22.01, 2, "doubles only");

    }

    //Test #: 3
    //Test Objective: To catch invalid Order ID
    //	//Inputs:  = "A_"
    //	//Expected Output: "error: Order ID must be  a int"
    @Test
    void testInvalidOrderId() {

        assertEquals(5, "A_", "integers only");
    }

    //Test #: 4
    //Test Objective: To catch invalid Phonenumber
    //	//Inputs:  = "jdweodj"
    //	//Expected Output: "error: Phonenumber must be a long"
    @Test
    void testInvalidPhoneNumber() {

        assertEquals(031000, "dweodj", "long only");

    }
    //Test #: 5
    //Test Objective: To  validate CustomerName
    //	//Inputs:  =  custFirstName = "John" custLastName = "Brown"
    //	//Expected Output: "customer name is valid"
    @Test
    void testValidCustomerName() {
        CustomerName customerName = new CustomerName();
        assertAll("customer name",
                () -> assertEquals("Mike", CustomerName.getFirstName()),
                () -> assertEquals("Doherty", CustomerName.getLastName())
        );
    }


    }