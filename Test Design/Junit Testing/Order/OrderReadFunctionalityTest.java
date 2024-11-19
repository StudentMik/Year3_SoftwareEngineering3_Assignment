package jtest;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class OrderReadFunctionalityTest {

    private final OrderReader orderReader = new OrderReader(); // Mocked or actual implementation

    @Test
    void testInvalidOrderId() {
        // Invalid Order ID
        String invalidOrderId = "invalid123";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderById(invalidOrderId);
        });

        assertEquals("Invalid Order ID: " + invalidOrderId, exception.getMessage(),
                "Expected an IllegalArgumentException for invalid order ID.");
    }

    @Test
    void testEmptyOrderId() {
        // Empty Order ID
        String emptyOrderId = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderById(emptyOrderId);
        });

        assertEquals("Order ID cannot be empty", exception.getMessage(),
                "Expected an IllegalArgumentException for empty order ID.");
    }

    @Test
    void testNullOrderId() {
        // Null Order ID
        String nullOrderId = null;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderById(nullOrderId);
        });

        assertEquals("Order ID cannot be null", exception.getMessage(),
                "Expected an IllegalArgumentException for null order ID.");
    }

    @Test
    void testInvalidPublicationName() {
        // Invalid Publication Name
        String invalidName = "123InvalidName";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderByPublicationName(invalidName);
        });

        assertEquals("Invalid Publication Name: " + invalidName, exception.getMessage(),
                "Expected an IllegalArgumentException for invalid publication name.");
    }

    @Test
    void testEmptyPublicationName() {
        // Empty Publication Name
        String emptyName = "";

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderByPublicationName(emptyName);
        });

        assertEquals("Publication name cannot be empty", exception.getMessage(),
                "Expected an IllegalArgumentException for empty publication name.");
    }

    @Test
    void testInvalidQuantity() {
        // Invalid Quantity
        int invalidQuantity = -5;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderByQuantity(invalidQuantity);
        });

        assertEquals("Quantity cannot be negative: " + invalidQuantity, exception.getMessage(),
                "Expected an IllegalArgumentException for negative quantity.");
    }

    @Test
    void testZeroQuantity() {
        // Zero Quantity
        int zeroQuantity = 0;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderByQuantity(zeroQuantity);
        });

        assertEquals("Quantity must be greater than zero", exception.getMessage(),
                "Expected an IllegalArgumentException for zero quantity.");
    }

    @Test
    void testExcessiveQuantity() {
        // Excessively Large Quantity
        int excessiveQuantity = 1000000;

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            orderReader.readOrderByQuantity(excessiveQuantity);
        });

        assertEquals("Quantity exceeds maximum allowed value: " + excessiveQuantity, exception.getMessage(),
                "Expected an IllegalArgumentException for excessive quantity.");
    }
}
