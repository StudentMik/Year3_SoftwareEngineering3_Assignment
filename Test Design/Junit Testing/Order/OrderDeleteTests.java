package jtest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class DatabaseTests {

    // Test Case 1: Delete Existing Column by Order ID
    @Test
    public void testDeleteColumnByOrderId() {
        // Input setup
        String orderId = "123"; // Example input for Order ID

        // Execute the method under test
        boolean isDeleted = MyDatabase.deleteColumn("OrderId", orderId);

        // Assert the expected result
        assertTrue(isDeleted, "Order ID column should be deleted successfully");
    }

    // Test Case 2: Delete Existing Column by Publication Name
    @Test
    public void testDeleteColumnByPublicationName() {
        // Input setup
        String publicationName = "SamplePublication"; // Example input for Publication Name

        // Execute the method under test
        boolean isDeleted = MyDatabase.deleteColumn("PublicationName", publicationName);

        // Assert the expected result
        assertTrue(isDeleted, "Publication Name column should be deleted successfully");
    }

    // Test Case 3: Handle Null Input
    @Test
    public void testDeleteColumnWithNullInput() {
        // Input setup
        String input = null;

        // Execute the method under test and assert
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MyDatabase.deleteColumn("ColumnName", input);
        });

        assertEquals("Please enter a valid command", exception.getMessage());
    }

    // Test Case 4: Test Database Connection
    @Test
    public void testDatabaseConnection() {
        // Execute the connection check
        String result = MyDatabase.checkConnection();

        // Assert the expected error message
        assertEquals("Error: could not find table orders", result,
                "Should return an error for missing 'orders' table");
    }
}

