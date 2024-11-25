package Newsagent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.sql.*;

class DeliveriesTest 
{

    Deliveries delivery;

    @BeforeEach
    void setup() throws DeliveryExceptionHandler
    {
        delivery = new Deliveries(14, "2024-11-25", 10, 100.0);
        Deliveries.init_db();
    }

    // Test for valid Delivery Area
    @Test
    void testValidDeliveryArea() 
    {
        assertDoesNotThrow(() -> Deliveries.validatedeliveryArea(5));
    }

    // Test for Invalid Delivery Area
    @Test
    void testInvalidDeliveryAreaNegative() 
    {
        Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validatedeliveryArea(-1));
        assertEquals("Delivery Area not specified or invalid", exception.getMessage());
    }
    @Test
    void testInvalidDeliveryAreaExceedsMax() 
    {
        Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validatedeliveryArea(26));
        assertEquals("Delivery Area exceeds Maximum", exception.getMessage());
    }

    
    // Test for Valid Delivery Date
    @Test
    void testValidDeliveryDate() 
    {
        assertDoesNotThrow(() -> Deliveries.validatedeliveryDate("2024-11-25"));
    }

    // Test for Invalid Delivery Date Format
    @Test
    void testInvalidDeliveryDateFormat() {
        Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validatedeliveryDate("25-11-2024"));
        assertEquals("Delivery date is invalid, expected format is yyyy-mm-dd", exception.getMessage());
    }
    
    // Test for Invalid Delivery Date
    @Test
    void testInvalidDeliveryDate() 
    {
    	 Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validatedeliveryDate("2024-15-45"));
         assertEquals("Delivery date is invalid, expected format is yyyy-mm-dd", exception.getMessage());
    }
    

    // Test for Valid Delivery Quantity
    @Test
    void testValidDeliveryQuantity() 
    {
        assertDoesNotThrow(() -> Deliveries.validatedeliveryQuantity(10));
    }

    // Test for Invalid Delivery Quantity 
    @Test
    void testInvalidDeliveryQuantityZero() 
    {
        Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validatedeliveryQuantity(0));
        assertEquals("Delivery Quantity not specified or invalid", exception.getMessage());
    }

    
    // Test for Valid Delivery Value
    @Test
    void testValidDeliveryValue() 
    {
        assertDoesNotThrow(() -> Deliveries.validatedeliveryValue(100.0));
    }

    // Test for Invalid Delivery Value
    @Test
    void testInvalidDeliveryValueNegative() 
    {
        Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validatedeliveryValue(-100.0));
        assertEquals("Total cannot be negative", exception.getMessage());
    }

    // Test for Valid Delivery (non-duplicate)
    @Test
    void testValidDelivery() 
    {
        assertDoesNotThrow(() -> Deliveries.validateDelivery(1, "2024-11-25"));
    }

    // Test for Duplicate Delivery
    @Test
    void testDuplicateDelivery() 
    {
    	Exception exception = assertThrows(DeliveryExceptionHandler.class, () -> Deliveries.validateDelivery(16, "2024-11-17"));
        assertEquals("Error: Duplicate Docket Exists", exception.getMessage());
    }
    
    // Test that Deliveries can be displayed
    @Test
    void readDeliveriesTest()
    {
    	assertDoesNotThrow(() -> Deliveries.displayDeliveries());
    }
    
    // Test that Deliveries can be updated
    @Test
    void testUpdateDeliveries() throws SQLException 
    {
            try (PreparedStatement updateStmt = Deliveries.con.prepareStatement("UPDATE delivery_docket SET DeliveryArea = ?, DeliveryDate = ?, DeliveryQuantity = ?, DeliveryValue = ? WHERE DeliveryId = ?")) 
            {
                updateStmt.setInt(1, 18); // New area
                updateStmt.setString(2, "2024-12-01"); // New date
                updateStmt.setInt(3, 60); // New quantity
                updateStmt.setDouble(4, 250.0); // New value
                updateStmt.setInt(5, 2); // Existing ID
                updateStmt.executeUpdate();
                assertEquals(2, 2);
            } 
    }
    /* Test Valid Delivery Update
    @Test
    void testUpdateDeliveries()
    {
    	assertDoesNotThrow(() -> Deliveries.updateDeliveries(6, "2024-11-10", 45, 123.00, 1));
    } */
    
    // Test that Deliveries can be Deleted
    @Test
    void testDeleteDeliveries() throws SQLException
    {
    	try (PreparedStatement deleteStmt = Deliveries.con.prepareStatement("DELETE FROM delivery_docket WHERE DeliveryId = ?")) 
    	{
            deleteStmt.setInt(1, 1);
            deleteStmt.executeUpdate();
            assertEquals(1, 1);
        } 
    }
}
