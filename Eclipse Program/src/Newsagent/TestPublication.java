package Newsagent;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class TestPublication  {

    @Test
    void testValidatePublicationName_validName() {
        // Valid inputs
        assertDoesNotThrow(() -> Publications.validatePublicationName("Daily News"));
        assertDoesNotThrow(() -> Publications.validatePublicationName("Weekly Times"));
    }

    @Test
    void testValidatePublicationName_invalidName() {
        // Invalid inputs
        Exception exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validatePublicationName("abc"));
        assertEquals("Invalid publication name. Name must be 4-20 characters and contain only letters.", exception.getMessage());

        exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validatePublicationName("ThisNameIsWayTooLongForValidation"));
        assertEquals("Invalid publication name. Name must be 4-20 characters and contain only letters.", exception.getMessage());

        exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validatePublicationName("1234"));
        assertEquals("Invalid publication name. Name must be 4-20 characters and contain only letters.", exception.getMessage());

        exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validatePublicationName("!@#$"));
        assertEquals("Invalid publication name. Name must be 4-20 characters and contain only letters.", exception.getMessage());
    }

    @Test
    void testValidatePrice_validPrice() {
        // Valid inputs
        assertDoesNotThrow(() -> Publications.validatePrice(0.0));
        assertDoesNotThrow(() -> Publications.validatePrice(19.99));
    }

    @Test
    void testValidatePrice_invalidPrice() {
        // Invalid inputs
        Exception exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validatePrice(-1.0));
        assertEquals("Invalid price. Price cannot be negative.", exception.getMessage());
    }

    @Test
    void testValidateschedule_validSchedules() {
        // Valid inputs
        assertDoesNotThrow(() -> Publications.validateschedule("Daily"));
        assertDoesNotThrow(() -> Publications.validateschedule("Weekly"));
        assertDoesNotThrow(() -> Publications.validateschedule("Monthly"));
    }

    @Test
    void testValidateschedule_invalidSchedules() {
        // Invalid inputs
        Exception exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validateschedule("Yearly"));
        assertEquals("Invalid schedule input. Daily, Weekly, or Monthly expected", exception.getMessage());

        exception = assertThrows(PublicationExceptionHandler.class, () ->
                Publications.validateschedule("Random"));
        assertEquals("Invalid schedule input. Daily, Weekly, or Monthly expected", exception.getMessage());
    }
}
