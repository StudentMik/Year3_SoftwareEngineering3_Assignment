package Newsagent;
import java.sql.*;
import java.text.*;
import java.util.Scanner;

public class Deliveries
{
	//private int driverID; int deliveryDArea; //1-24 String deliveryAddress;
	private String deliveryDate;
	private int customerId;
	private int orderId;
	private String publicationName;  
	

//----------Constructor----------//
	public Deliveries(int customerId, int orderId, String publicationName, String deliveryDate) 
	{
		this.customerId = customerId;
		this.orderId = orderId;
		this.publicationName = publicationName;
		this.deliveryDate = deliveryDate;
	}

	
//----------Getters & Setters----------//
	public String getDeliveryDate() 
	{
		return deliveryDate;
	}
	public void setDeliveryDate(String deliveryDate) 
	{
		this.deliveryDate = deliveryDate;
	}
	
	public int getCustomerId() 
	{
		return customerId;
	}
	public void setCustomerId(int customerId) 
	{
		this.customerId = customerId;
	}
	public int getOrderId() 
	{
		return orderId;
	}
	public void setOrderId(int orderId) 
	{
		this.orderId = orderId;
	}
	
	public String getPublicationName() {
		return publicationName;
	}
	public void setPublicationName(String publicationName) 
	{
		this.publicationName = publicationName;
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
            String str = "INSERT INTO delivery_docket (CustomerId, OrderId, PublicationName, DeliveryDate) VALUES (?, ?, ?, ?)";

            // Get delivery docket details from the user
            System.out.println("Please Enter the Customer ID:");
            int customerId = in.nextInt();
            validatecustId(customerId);
            System.out.println("Please Enter the Order ID:");
            int orderId = in.nextInt();
            validateordId(orderId);
            in.nextLine(); // Consume the newline left-over
            System.out.println("Please Enter the Publication Name:");
            String publicationName = in.nextLine();
            validatepubliName(publicationName);
            System.out.println("Please Enter the Delivery Date (YYYY-MM-DD):");
            String deliveryDate = in.nextLine();
            validatedeliveryDate(deliveryDate);
            	
            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setInt(1, customerId);
            pstmt.setInt(2, orderId);
            pstmt.setString(3, publicationName);
            pstmt.setString(4, deliveryDate);

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
            	System.out.println("Customer Id: " + rs.getInt("customerId"));
            	System.out.println("Order Id: " + rs.getInt("orderId"));
            	System.out.println("Publication Name: " + rs.getString("publicationName"));
            	System.out.println("Delivery Date: " + rs.getString("deliveryDate"));
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
            System.out.println("Please Enter the Date of the DeliveryD to delete:");
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
  	public static void validatecustId(int customerId) throws DeliveryExceptionHandler 
  	{
  	    if(customerId <= 0) 
  	    {
  	        throw new DeliveryExceptionHandler("CustomerId not specified or invalid");
  	    }
  	    if(customerId > 100) 
  	    {
  	        throw new DeliveryExceptionHandler("CustomerId exceeds Maximum");
  	    }
  	}
  	
  	public static void validateordId(int orderId) throws DeliveryExceptionHandler 
  	{
  	    if(orderId <= 0) 
  	    {
  	        throw new DeliveryExceptionHandler("OrderId not specified or invalid");
  	    }
  	    if(orderId > 100) 
  	    {
  	        throw new DeliveryExceptionHandler("OrderId exceeds Maximum");
  	    }
  	}
  	
  	public static void validatepubliName(String publicationName) throws DeliveryExceptionHandler
  	{
  		if (publicationName.isBlank() || publicationName.isEmpty())
  		{
  			throw new DeliveryExceptionHandler("Publication Name not specified or invalid");
  		}
  		else if (publicationName.length() < 5)
  		{
  			throw new DeliveryExceptionHandler("Publication Name does not meet minimum length requirements");
  		}
  		else if (publicationName.length() > 50)
  		{
  			throw new DeliveryExceptionHandler("Publication Name does not exceeds maximum length requirements");
  		}
  		
  	}
  	
  	public static void validatedeliveryDate(String deliveryDate) throws DeliveryExceptionHandler 
  	{
  	    if (deliveryDate == null || deliveryDate.isEmpty()) 
  	    {
  	        throw new DeliveryExceptionHandler("Invoice date not specified or invalid");
  	    }
  	    SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-mm-dd");
  	    dateFormat.setLenient(false); 
  	    try 
  	    {
  	        dateFormat.parse(deliveryDate);
  	    } catch (ParseException e) {
  	        throw new DeliveryExceptionHandler("Invoice date is invalid, expected format is yyyy-mm-dd");
  	    }
  	}
  	
  	
}

