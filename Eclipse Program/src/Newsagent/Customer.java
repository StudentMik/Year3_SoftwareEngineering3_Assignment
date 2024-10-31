package Newsagent;

import java.util.*;
import java.sql.*;

public class Customer
{
	private int id;
	private String name;
	private String address;
	private int phoneNumber;
	private String subscriptionStatus;
	private boolean pauseActive;
	
	public Customer(String custName, String custAddress, int custPhone, String subscriptionStatus, boolean pauseActive)
	{
		id = 0;
		
		this.name = custName;
		this.address = custAddress;
		this.phoneNumber = custPhone;
		this.subscriptionStatus = subscriptionStatus;
		this.pauseActive = pauseActive;
	}

	public int getId()
	{
		return id;
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public String getName()
	{
		return name;
	}

	public void setName(String name)
	{
		this.name = name;
	}

	public String getAddress()
	{
		return address;
	}

	public void setAddress(String address)
	{
		this.address = address;
	}

	public int getPhoneNumber()
	{
		return phoneNumber;
	}

	public void setPhoneNumber(int phoneNumber)
	{
		this.phoneNumber = phoneNumber;
	}

	public String getSubscriptionStatus()
	{
		return subscriptionStatus;
	}

	public void setSubscriptionStatus(String subscriptionStatus)
	{
		this.subscriptionStatus = subscriptionStatus;
	}

	public boolean isPauseActive()
	{
		return pauseActive;
	}

	public void setPauseActive(boolean pauseActive)
	{
		this.pauseActive = pauseActive;
	}
	
	public static void validateName()
	{
		
	}
	
	public static void validateAddress()
	{
		
	}
	
	public static void validatePhoneNumber()
	{
		
	}
	
	    static Connection con = null;
	    static Statement stmt = null;
	    static ResultSet rs = null;

	    public static void main(String[] args)
	    {
	        Scanner in = new Scanner(System.in);
	        init_db(); // Open the connection to the database
	        try {
	            // SQL insert statement for the customers table
	            String str = "INSERT INTO customers (Name, Address, area, ContactNo) VALUES (?, ?, ?, ?)";

	            // Get customer details from the user
	            System.out.println("Please Enter the Name:");
	            String name = in.nextLine();  // Use nextLine() to capture full name
	            System.out.println("Please Enter the Address:");
	            String address = in.nextLine();
	            System.out.println("Please Enter the Area:");
	            String area = in.nextLine();
	            System.out.println("Please Enter the Contact Number:");
	            String contactNo = in.nextLine();

	            // Prepare the statement
	            PreparedStatement pstmt = con.prepareStatement(str);
	            pstmt.setString(1, name);
	            pstmt.setString(2, address);
	            pstmt.setString(3, area);
	            pstmt.setString(4, contactNo);

	            // Execute the update
	            int rows = pstmt.executeUpdate();

	            // Check if the insert was successful
	            if (rows > 0)
	            {
	                System.out.println("Customer added successfully!");
	            }
	            else
	            {
	                System.out.println("Failed to add customer.");
	            }
	        }
	        catch (SQLException sqle)
	        {
	            System.out.println("Error: " + sqle.getMessage());
	        }
	        
	        finally
	        {
	            // Close the database connection
	            try
	            {
	                if (con != null)
	                {
	                    con.close();
	                }
	            }
	            catch (SQLException sqle)
	            {
	                System.out.println("Error: failed to close the database");
	            }
	        }
	    }

	    public static void init_db()
	    {
	        try
	        {
	            Class.forName("com.mysql.cj.jdbc.Driver");
	            String url = "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC"; // Updated database name
	            con = DriverManager.getConnection(url, "root", "root");
	            stmt = con.createStatement();
	        }
	        catch (Exception e)
	        {
	            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
	        }
	    }
	
}