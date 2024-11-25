package Newsagent;
import java.sql.*;
import java.text.*;
import java.util.Scanner;

public class Deliveries
{
	private int deliveryArea; // Areas = 1-24
	private String deliveryDate; // Format = yyyy-mm-dd
	private int deliveryQuantity;
	private double deliveryValue;
	

//--------------------Constructor--------------------//
	public Deliveries(int deliveryArea, String deliveryDate, int deliveryQuantity, double deliveryValue) 
	{
		this.deliveryArea = deliveryArea;
		this.deliveryDate = deliveryDate;
		this.deliveryQuantity = deliveryQuantity;
		this.deliveryValue = deliveryValue;
	}

	
//--------------------Getters & Setters--------------------//
	public int getDeliveryArea() {
		return deliveryArea;
	}
	public void setDeliveryArea(int deliveryArea) {
		this.deliveryArea = deliveryArea;
	}

	public String getDeliveryDate() {
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) {
		this.deliveryDate = deliveryDate;
	}

	public int getDeliveryQuantity() {
		return deliveryQuantity;
	}
	public void setDeliveryQuantity(int deliveryQuantity) {
		this.deliveryQuantity = deliveryQuantity;
	}

	public double getDeliveryValue() {
		return deliveryValue;
	}
	public void setDeliveryValue(double deliveryValue) {
		this.deliveryValue = deliveryValue;
	}
	
	
//--------------------Database Connection--------------------//
	static Connection con = null;
	static Statement stmt = null;
   	static ResultSet rs = null;

   	public static void init_db() 
   	{
   		try 
		{
   			Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/NewsAgent?useTimezone=true&serverTimezone=UTC"; // Updated database name
            con = DriverManager.getConnection(url, "root", "root");
        } 
    	catch (Exception e) 
		{
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
    	
    
//--------------------Main--------------------//
    public static void main(String[] args) throws DeliveryExceptionHandler, InvoiceExceptionHandler, PublicationExceptionHandler, CustomerExceptionHandler, OrderExceptionHandler
    {
        Scanner in = new Scanner(System.in);
        boolean on = true;
        init_db(); // Open the connection to the database
        	
        while (on) 
        {
        	System.out.println("Choose an Option:");
        	System.out.println("1.	Add a new DeliveryDocket");
        	System.out.println("2.	Display all Deliveries");
        	System.out.println("3.	Update Deliveries");
        	System.out.println("4. 	Delete Delivery");
        	System.out.println("5. 	Exit");
        	System.out.print(": ");

        	int choice = in.nextInt();
        	in.nextLine();

        	switch (choice) 
        	{
        		case 1:
        			addDeliveryD(in);
        			break;
        		case 2:
        			displayDeliveries();
        			break;
        		case 3:
        			updateDeliveries(in);
        			break;
        		case 4:
        			deleteDelivery(in);
        			break;
        		case 5:
        			System.out.println("Exiting Delivery....");
        			MainMenu.main(null);
        			return;
        				
        		default:
        		System.out.println("Error: Invalid Choice");
        	} 
        }
        try 
        {
        	if (con != null) 
        	{
        		con.close(); // Close database connection
                System.out.println("fafaf");
            }
            if (stmt != null) 
            {
                stmt.close(); // Close statement object
            }
            if (rs != null) 
            {
                rs.close(); // Close result set object
            }
        } 
        catch (SQLException sqle) 
        {
        	System.out.println("Error: failed to close the database"); // Handle closing exceptions
        }
    }
    	
        	
//--------------------CREATE: Add a DeliveryDocket to the Database--------------------//
   	public static void addDeliveryD(Scanner in)
   	{
        try 
        {
        	// SQL insert statement for the delivery_docket table
            String str = "INSERT INTO delivery_docket (DeliveryArea, DeliveryDate, DeliveryQuantity, DeliveryValue) VALUES (?, ?, ?, ?)";

            // Get delivery docket details from the user
            System.out.println("Please Enter the Delivery Area: ");
            int deliveryArea = in.nextInt();
            validatedeliveryArea(deliveryArea);
            in.nextLine();
            System.out.println("Please Enter the Delivery Date (YYYY-MM-DD):");
            String deliveryDate = in.nextLine();
            validatedeliveryDate(deliveryDate);
            System.out.println("Please Enter the Delivery Quantity: ");
            int deliveryQuantity = in.nextInt();
            validatedeliveryQuantity(deliveryQuantity);
            in.nextLine();
            System.out.println("Please Enter the Delivery Value: ");
            double deliveryValue = in.nextDouble();
            validatedeliveryValue(deliveryValue);
            in.nextLine();
            validateDelivery(deliveryArea, deliveryDate);	
            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setInt(1, deliveryArea);
            pstmt.setString(2, deliveryDate);
            pstmt.setInt(3, deliveryQuantity);
            pstmt.setDouble(4, deliveryValue);

            // Execute the update
            int rows = pstmt.executeUpdate();
            
            // Check if the insert was successful
            if (rows > 0) 
            {
            	System.out.println("Delivery docket added successfully!");
            } 
            else
            {
            	System.out.println("Failed to add delivery docket.");
            }
            pstmt.close();
        } 
        catch (SQLException sqle) 
        {
        	System.out.println("Error: " + sqle.getMessage());
        }
        catch (DeliveryExceptionHandler e) {
            System.out.println("Error: " + e.getMessage()); // Handle custom exceptions
        }
   }	
   	
   	
//--------------------READ: Display DeliveryDockets from the Database--------------------//
    public static void displayDeliveries() 
    {
    	try 
    	{
    		stmt = con.createStatement();
            String query = "SELECT * FROM delivery_docket";
            rs = stmt.executeQuery(query);
            while (rs.next()) 
            {
            	System.out.println("Delivery Id: " +rs.getInt("deliveryId"));
            	System.out.println("Area: " + rs.getInt("deliveryArea"));
            	System.out.println("Date: " + rs.getString("deliveryDate"));
            	System.out.println("Quantity: " + rs.getInt("deliveryQuantity"));
            	System.out.println("Value: €" + rs.getDouble("deliveryValue"));
            	System.out.println("-------------------------------");
    		}
    	} 
    	catch (SQLException e) 
    	{
    			e.printStackTrace();
    	}
    }
    
    
//--------------------UPDATE: Change a DeliveryDocket in the Database--------------------//
    public static void updateDeliveries(Scanner in)
    {
    	try 
    	{
        	// Ask user for the DeliveryDocket they want to modify
            System.out.println("Please Enter the DeliveryId of the Docket to Update:");
            int deliveryId = in.nextInt();
            in.nextLine();

         // SQL query to find the DeliveryDocket by Id
            String selectStr = "SELECT * FROM delivery_docket WHERE DeliveryId = ?";

         // Prepare statement to execute the query
            PreparedStatement pstmtSelect = con.prepareStatement(selectStr);
            pstmtSelect.setInt(1, deliveryId);

            // Execute the select query
            rs = pstmtSelect.executeQuery();

            // Check if the DeliveryDocket exists
            if (rs.next()) 
            {
            	// Display the current details of the DeliveryDocket
                System.out.println("Delivery Docket found ");
                System.out.println("Current details - DeliveryId: " + rs.getInt("DeliveryId")
                				   + ", Area: " + rs.getInt("DeliveryArea")
                                   + ", Date: " + rs.getString("DeliveryDate")
                                   + ", Quantity: " + rs.getInt("DeliveryQuantity")
                                   + ", Value: €" + rs.getDouble("DeliveryValue"));

                // Ask the user if they want to change the DeliveryDocket
                System.out.println("Do you want to change this Invoice? (yes/no):");
                String changeDeliveryDocket = in.nextLine();

                if (changeDeliveryDocket.equalsIgnoreCase("yes")) 
                {
                	// Ask user for new Delivery Area
                	System.out.println("Enter new Delivery Area (current: " + rs.getInt("DeliveryArea") + "): ");
                	int newDeliveryArea = in.nextInt();
                	validatedeliveryArea(newDeliveryArea);
                	in.nextLine();
        
                	// Ask user for new Delivery Date
                	System.out.println("Enter new Date (current: " + rs.getString("DeliveryDate") + "):");
                	String newDeliveryDate = in.nextLine();
                	validatedeliveryDate(newDeliveryDate);
                	
                	// Ask user for new Delivery Quantity
                	System.out.println("Enter new Quantity (current: " + rs.getInt("DeliveryQuantity") + "):");
                	int newDeliveryQuantity = in.nextInt();
                	validatedeliveryQuantity(newDeliveryQuantity);
                	in.nextLine();
                	
                	// Ask user for new Delivery Value
                	System.out.println("Enter new Value (current: €" + rs.getDouble("DeliveryValue") + "):");
                	double newDeliveryValue = in.nextDouble();
                	validatedeliveryValue(newDeliveryValue);
                	in.nextLine();
                	validateDelivery(newDeliveryArea, newDeliveryDate);
                	
                	// SQL update statement to Update the DeliveryDocket
                	String updateStr = "UPDATE delivery_docket SET DeliveryArea = ?, DeliveryDate = ?, DeliveryQuantity = ?, DeliveryValue = ? Where DeliveryId = ?";

                	// Prepare the update statement
                	PreparedStatement pstmtUpdate = con.prepareStatement(updateStr);
                	pstmtUpdate.setInt(1, newDeliveryArea);
                	pstmtUpdate.setString(2, newDeliveryDate);
                	pstmtUpdate.setInt(3, newDeliveryQuantity);
                	pstmtUpdate.setDouble(4, newDeliveryValue);
                	pstmtUpdate.setInt(5, deliveryId);

                	// Execute the update
                	int rows1 = pstmtUpdate.executeUpdate();

                	// Check if the update was successful
                	if (rows1 > 0) 
                	{
                		System.out.println("Delivery Docket updated successfully!");// Success message
                	} 
                	else 
                	{
                		System.out.println("Failed to update Delivery Docket.");// Failure message
                	}
                	pstmtUpdate.close(); // Close the prepared statement
                } 
                else 
                {
                	System.out.println("Delivery Docket with DeliveryId '" + deliveryId + "' not found."); // If DeliveryDocket does not exist
                }
            }   
    	} 
        catch (SQLException sqle) 
        {
        	System.out.println("Error: " + sqle.getMessage()); // Handle SQL exceptions
        } 
        catch (DeliveryExceptionHandler e) 
        {
            System.out.println("Error: " + e.getMessage());
        }
    }
    
    
//--------------------DELETE: Remove a DeliveryDocket from the Database--------------------//
    public static void deleteDelivery(Scanner in)
    {
    	try {
        	// Ask the user for the date of the Docket to delete
            System.out.println("Please Enter the Id of the DeliveryDocket to delete: ");
            int deliveryId = in.nextInt();
            String deleteStr = "DELETE FROM delivery_docket WHERE DeliveryId = ?";

         // Prepare and execute the delete query
            PreparedStatement pstmt = con.prepareStatement(deleteStr);
            pstmt.setInt(1, deliveryId);
            int rows = pstmt.executeUpdate();

            // Check if the delete was successful
            if (rows > 0) {
                System.out.println("Docket deleted successfully!"); // Success message
            } else {
                System.out.println("Docket not found or delete failed."); // Failure message
            }
            pstmt.close();
        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
        }
    }
    
    
//--------------------Validation--------------------//
  	public static void validatedeliveryArea(int deliveryArea) throws DeliveryExceptionHandler 
  	{
  	    if(deliveryArea <= 0) 
  	    {
  	        throw new DeliveryExceptionHandler("Delivery Area not specified or invalid");
  	    }
  	    if(deliveryArea > 25) 
  	    {
  	        throw new DeliveryExceptionHandler("Delivery Area invalid. Area = 1-24");
  	    }
  	}
  	
  	public static void validatedeliveryDate(String deliveryDate) throws DeliveryExceptionHandler 
  	{
  	    if (deliveryDate == null || deliveryDate.isEmpty()) 
  	    {
  	        throw new DeliveryExceptionHandler("Delivery date not specified or invalid");
  	    }
  	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
  	    dateFormat.setLenient(false); 
  	    try 
  	    {
  	        dateFormat.parse(deliveryDate);
  	    } catch (ParseException e) {
  	        throw new DeliveryExceptionHandler("Delivery date is invalid, expected format is yyyy-mm-dd");
  	    }
  	}
  	
  	public static void validatedeliveryQuantity(int deliveryQuantity) throws DeliveryExceptionHandler 
  	{
  	    if(deliveryQuantity <= 0) 
  	    {
  	        throw new DeliveryExceptionHandler("Delivery Quantity not specified or invalid");
  	    }
  	}
  	
  	public static void validatedeliveryValue(double deliveryValue) throws DeliveryExceptionHandler 
  	{
  		if (deliveryValue < 0) 
  		{
  			throw new DeliveryExceptionHandler("Total cannot be negative");
  		}
  	}
  	
  	public static void validateDelivery(int deliveryArea, String deliveryDate) throws DeliveryExceptionHandler 
  	{
  		try 
  		{
  	        String checkQuery = "SELECT COUNT(*) FROM delivery_docket WHERE DeliveryArea = ? AND DeliveryDate = ?";
  	        PreparedStatement checkStmt = con.prepareStatement(checkQuery);
  	        checkStmt.setInt(1, deliveryArea);
  	        checkStmt.setString(2, deliveryDate);
  	        ResultSet checkResult = checkStmt.executeQuery();
  	        checkResult.next();
  	        int count = checkResult.getInt(1);

  	        if (count > 0) 
  	        {
  	            throw new DeliveryExceptionHandler("Error: Duplicate Docket Exists");
  	        }
  	        
  	        checkStmt.close();
  	    } 
  		catch (SQLException sqle) 
  		{
  			 System.out.println("SQL Error: " + sqle.getMessage());
  	    }
  	}
}

