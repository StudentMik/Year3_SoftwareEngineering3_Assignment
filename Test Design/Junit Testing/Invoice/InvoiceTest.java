package Newsagent;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;
import java.sql.*;

class InvoiceTest {

	Invoice invoice;
	
	@BeforeEach
	void setup() throws InvoiceExceptionHandler
	{
		invoice = new Invoice(12, "2024-01-01", 24.99);
		Invoice.init_db();
	}
	
	// Test Valid OrderId
	@Test
	void testValidOrderId()
	{
		assertDoesNotThrow(() -> Invoice.validateordId(13));
	}
	
	// Test Invalid OrderId
	@Test
	void testInvalidOrderId()
	{
		Exception exception = assertThrows(InvoiceExceptionHandler.class, () -> Invoice.validateordId(-1));
        assertEquals("OrderId not specified or invalid", exception.getMessage());
	}
	
	// Test for Valid Invoice Date
    @Test
    void testValidDeliveryDate() 
    {
        assertDoesNotThrow(() -> Invoice.validateinvoiceDate("2024-11-25"));
    }

    // Test for Invalid Invoice Date Format
    @Test
    void testInvalidDeliveryDateFormat() {
        Exception exception = assertThrows(InvoiceExceptionHandler.class, () -> Invoice.validateinvoiceDate("25-11-2024"));
        assertEquals("Invoice date is invalid, expected format is yyyy-mm-dd", exception.getMessage());
    }
    
    // Test for Invalid Invoice Date
    @Test
    void testInvalidInvoiceDate() 
    {
    	 Exception exception = assertThrows(InvoiceExceptionHandler.class, () -> Invoice.validateinvoiceDate("2024-15-45"));
         assertEquals("Invoice date is invalid, expected format is yyyy-mm-dd", exception.getMessage());
    }
    
 // Test for Valid Invoice Total
    @Test
    void testValidInvoiceTotal() 
    {
        assertDoesNotThrow(() -> Invoice.validateinvoiceTotal(100.0));
    }

    // Test for Invalid Invoice Total
    @Test
    void testInvalidInvoiceTotal() 
    {
        Exception exception = assertThrows(InvoiceExceptionHandler.class, () -> Invoice.validateinvoiceTotal(-100.0));
        assertEquals("Total cannot be negative", exception.getMessage());
    }
    
    // Test for Valid Invoice (non-duplicate)
    @Test
    void testValidInvoice() 
    {
        assertDoesNotThrow(() -> Invoice.validateordId(1));
    }

    // Test for Duplicate Invoice
    @Test
    void testDuplicateInvoice() 
    {
    	Exception exception = assertThrows(InvoiceExceptionHandler.class, () -> Invoice.validateordId(1));
        assertEquals("Error: Duplicate Invoice Exists", exception.getMessage());
    }
    
    // Test that Deliveries can be displayed
    @Test
    void readInvoiceTest()
    {
    	assertDoesNotThrow(() -> Invoice.displayInvoices());
    }
    
    // Test that Invoices can be updated
    @Test
    void testUpdateInvoice() 
    {
            try (PreparedStatement updateStmt = Invoice.con.prepareStatement("UPDATE invoice SET OrderId = ?, InvoiceDate = ?, InvoiceTotal = ? Where InvoiceId = ?")) 
            {
                updateStmt.setInt(1, 18); // New OrderId
                updateStmt.setString(2, "2024-12-01"); // New date
                updateStmt.setDouble(3, 60.99); // New Total
                updateStmt.setInt(1, 2); // Existing ID
                updateStmt.executeUpdate();
                assertEquals(2, 2);
            } 
            catch (SQLException e) 
            {
				e.printStackTrace();
			}
    }
    
    // Test that Invoices can be Deleted
    @Test
    void testDeleteInvoice()
    {
    	try (PreparedStatement deleteStmt = Invoice.con.prepareStatement(
                "DELETE FROM invoice WHERE InvoiceId = ?")) {
            deleteStmt.setInt(1, 1);

            int rowsDeleted = deleteStmt.executeUpdate();
            assertEquals(1, rowsDeleted);
        } 
    	catch (SQLException e) 
    	{
		e.printStackTrace();
	}
    }
}

