package Newsagent;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;


class OrderTest {

    // Equivalence partitioning applied to Create Order tests
    @Test
    void testValidOrderCreation() throws OrderExceptionHandler {
        Order order = new Order("Magazines", 50.0, 10);
        assertNotNull(order, "Order object should be created successfully with valid inputs.");
    }

    @Test
    void testInvalidPublicationNameTooShort() {
        Exception exception = assertThrows(orderExceptionHandler.class, () -> {
            new Order("Mag", 50.0, 10);
        });
        assertEquals("Invalid Publication Name: must be between 7 and 15 characters.", exception.getMessage());
    }

    @Test
    void testInvalidPublicationNameTooLong() {
        Exception exception = assertThrows(orderExceptionHandler.class, () -> {
            new Order("SuperLongPublicationName", 50.0, 10);
        });
        assertEquals("Invalid Publication Name: must be between 7 and 15 characters.", exception.getMessage());
    }

    @Test
    void testInvalidPublicationPriceZero() {
        Exception exception = assertThrows(orderExceptionHandler.class, () -> {
            new Order("Magazines", 0.0, 10);
        });
        assertEquals("Invalid Publication Price: must be a positive number.", exception.getMessage());
    }

    @Test
    void testInvalidPublicationPriceNegative() {
        Exception exception = assertThrows(orderExceptionHandler.class, () -> {
            new Order("Magazines", -50.0, 10);
        });
        assertEquals("Invalid Publication Price: must be a positive number.", exception.getMessage());
    }

    @Test
    void testInvalidQuantityZero() {
        Exception exception = assertThrows(orderExceptionHandler.class, () -> {
            new Order("Magazines", 50.0, 0);
        });
        assertEquals("Invalid Quantity: must be an integer greater than 0.", exception.getMessage());
    }

    @Test
    void testInvalidQuantityNegative() {
        Exception exception = assertThrows(orderExceptionHandler.class, () -> {
            new Order("Magazines", 50.0, -10);
        });
        assertEquals("Invalid Quantity: must be an integer greater than 0.", exception.getMessage());
    }

    // Tests for database insertion


    // Update Order Tests
    @Test
    void testValidOrderUpdate() {
        MyDatabase.init_db();
        // Assuming the order exists with ID 1
        assertDoesNotThrow(() -> MyDatabase.updateOrder(1, 15, 60.0, "UpdatedName"),
                "Valid order update should not throw exceptions.");
    }

    @Test
    void testUpdateOrderInvalidOrderId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MyDatabase.updateOrder(-1, 10, 50.0, "UpdatedName");
        });
        assertEquals("Invalid Order ID", exception.getMessage());
    }

    @Test
    void testUpdateOrderEmptyPublicationName() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MyDatabase.updateOrder(1, 10, 50.0, "");
        });
        assertEquals("Publication name cannot be empty", exception.getMessage());
    }

    @Test
    void testUpdateOrderInvalidQuantity() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MyDatabase.updateOrder(1, -5, 50.0, "ValidName");
        });
        assertEquals("Quantity cannot be negative", exception.getMessage());
    }

    // Delete Order Tests
    @Test
    void testDeleteOrderValidId() {
        MyDatabase.init_db();
        // Assuming order ID 1 exists
        assertTrue(MyDatabase.deleteOrder(1), "Order deletion with a valid ID should succeed.");
    }

    @Test
    void testDeleteOrderInvalidId() {
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            MyDatabase.deleteOrder(-1);
        });
        assertEquals("Invalid Order ID", exception.getMessage());
    }

    // Database Connection Tests
    @Test
    void testDatabaseConnection() {
        assertDoesNotThrow(MyDatabase::init_db, "Database should connect without throwing exceptions");
    }

    @Test
    void testDatabaseCloseConnection() {
        assertDoesNotThrow(MyDatabase::close_db, "Database should close without throwing exceptions");
    }

    // Mocking MyDatabase as an inner static class for the purpose of testing
    private static class MyDatabase {
        public static void init_db() {
            // Mock database initialization
        }

        public static void close_db() {
            // Mock database closure
        }

        public static boolean deleteOrder(int orderId) {
            if (orderId < 0) throw new IllegalArgumentException("Invalid Order ID");
            return true; // Simulate successful deletion
        }

        public static void updateOrder(int orderId, int quantity, double price, String publicationName) {
            if (orderId < 0) throw new IllegalArgumentException("Invalid Order ID");
            if (quantity < 0) throw new IllegalArgumentException("Quantity cannot be negative");
            if (publicationName == null || publicationName.isEmpty())
                throw new IllegalArgumentException("Publication name cannot be empty");
        }
    }

    private class orderExceptionHandler extends Exception {
    }
}
