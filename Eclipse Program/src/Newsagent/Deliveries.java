package Newsagent;
import java.sql.*;
import java.text.*;
import java.util.Scanner;

public class Deliveries
{
	private int deliveryArea; //1-24
	private String deliveryDate;
	private int orderQuantity;
	private double deliveryValue;
	

//----------Constructor----------//
	public Deliveries(int deliveryArea, String deliveryDate, int orderQuantity, double deliveryValue) 
	{
		this.deliveryArea = deliveryArea;
		this.deliveryDate = deliveryDate;
		this.orderQuantity = orderQuantity;
		this.deliveryValue = deliveryValue;
	}

	
//----------Getters & Setters----------//
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

	public int getOrderQuantity() {
		return orderQuantity;
	}
	public void setOrderQuantity(int orderQuantity) {
		this.orderQuantity = orderQuantity;
	}

	public double getDeliveryValue() {
		return deliveryValue;
	}
	public void setDeliveryValue(double deliveryValue) {
		this.deliveryValue = deliveryValue;
	}
	
//----------Database----------//
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
        			updateDeliveries();
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
    	
        	
//----------Create: Add DeliveryD----------//
   	public static void addDeliveryD(Scanner in)
   	{
        try 
        {
        	// SQL insert statement for the delivery_docket table
            String str = "INSERT INTO delivery_docket (DeliveryArea, DeliveryDate, OrderQuantity, DeliveryValue) VALUES (?, ?, ?, ?)";

            // Get delivery docket details from the user
            System.out.println("Please Enter the Delivery Area: ");
            int deliveryArea = in.nextInt();
            validatedeliveryArea(deliveryArea);
            in.nextLine();
            System.out.println("Please Enter the Delivery Date (YYYY-MM-DD):");
            String deliveryDate = in.nextLine();
            validatedeliveryDate(deliveryDate);
            System.out.println("Please Enter the Delivery Quantity: ");
            int orderQuantity = in.nextInt();
            validatedeliveryQuantity(orderQuantity);
            in.nextLine();
            System.out.println("Please Enter the Delivery Value: ");
            double deliveryValue = in.nextDouble();
            validatedeliveryValue(deliveryValue);
            in.nextLine();
            	
            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setInt(1, deliveryArea);
            pstmt.setString(2, deliveryDate);
            pstmt.setInt(3, orderQuantity);
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
   	
   	
//----------Read: Display Deliveries----------//
    public static void displayDeliveries() 
    {
    	try 
    	{
    		stmt = con.createStatement();
            String query = "SELECT * FROM delivery_docket";
            rs = stmt.executeQuery(query);
            while (rs.next()) 
            {
            	System.out.println("Delivery Area: " + rs.getInt("deliveryArea"));
            	System.out.println("Delivery Date: " + rs.getString("deliveryDate"));
            	System.out.println("Delivery Quantity: " + rs.getInt("orderQuantity"));
            	System.out.println("Delivery Value: €" + rs.getDouble("deliveryValue"));
            	System.out.println("-------------------------------");
    		}
    	} 
    	catch (SQLException e) 
    	{
    			e.printStackTrace();
    	}
    }
    
    
//----------Update: Change a Delivery info----------//
    public static void updateDeliveries()
    {
    	
    }
    
    
//----------Delete: Remove a Delivery----------//
    public static void deleteDelivery(Scanner in)
    {
    	try {
        	// Ask the user for the date of the Docket to delete
            System.out.println("Please Enter the Date of the DeliveryDocket to delete:");
            String name = in.nextLine();
            String deleteStr = "DELETE FROM delivery_docket WHERE DeliveryDate = ?";

         // Prepare and execute the delete query
            PreparedStatement pstmt = con.prepareStatement(deleteStr);
            pstmt.setString(1, name);
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
    
    
  //----------Validation----------//
  	public static void validatedeliveryArea(int deliveryArea) throws DeliveryExceptionHandler 
  	{
  	    if(deliveryArea <= 0) 
  	    {
  	        throw new DeliveryExceptionHandler("Delivery Area not specified or invalid");
  	    }
  	    if(deliveryArea > 25) 
  	    {
  	        throw new DeliveryExceptionHandler("Delivery Area exceeds Maximum");
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
}

