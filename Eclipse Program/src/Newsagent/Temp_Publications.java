package Newsagent;
import java.sql.*;
import java.util.Scanner;
 
public class Publications
{
	private String publicationName;
	//private double publicationStock;
	private double publicationPrice;
	private String schedule;
	
	public Publications(String publicationName, double publicationPrice, String schedule) 
	{
		this.publicationName = publicationName;
		//this.publicationStock = publicationStock;
		this.publicationPrice = publicationPrice;
		this.schedule = schedule;
	}

	
	public String getPublicationName() 
	{
		return publicationName;
	}
	public void setPublicationName(String publicationName) 
	{
		this.publicationName = publicationName;
	}

	/*public double getPublicationStock() 
	{
		return publicationStock;
	}
	public void setPublicationStock(double publicationStock) 
	{
		this.publicationStock = publicationStock;
	}*/

	public double getPublicationPrice() 
	{
		return publicationPrice;
	}
	public void setPublicationPrice(double publicationPrice) 
	{
		this.publicationPrice = publicationPrice;
	}

	public String getSchedule() 
	{
		return schedule;
	}
	public void setSchedule(String schedule) 
	{
		this.schedule = schedule;
	}

	public static void validatepubliName(String publicationName) throws PublicationExceptionHandler
	{
		if (publicationName.isBlank() || publicationName.isEmpty())
		{
			throw new PublicationExceptionHandler("Publication Name not specified or invalid");
		}
		else if (publicationName.length() < 5)
		{
			throw new PublicationExceptionHandler("Publication Name does not meet minimum length requirements");
		}
		else if (publicationName.length() > 50)
		{
			throw new PublicationExceptionHandler("Publication Name does not exceeds maximum length requirements");
		}
	}
	public static void validatepubliPrice(double publicationPrice) throws PublicationExceptionHandler 
	{
		if (publicationPrice < 0) 
		{
	        throw new PublicationExceptionHandler("Price cannot be negative");
	    }
	}
	public static void validateschedule(String schedule) throws PublicationExceptionHandler
	{
		if (!("Daily".equals(schedule) || "Weekly".equals(schedule) || "Monthly".equals(schedule))) 
		{
	        throw new PublicationExceptionHandler("Invalid schedule input. Daily, Weekly, or Monthly expected");
	    }
		/*if (schedule != "Daily" || schedule != "Weekly" || schedule != "Monthly")
		{
			throw new PublicationExceptionHandler("Invalid schedule input. Daily, Weekly or Monthly expected");
		}*/
	}
	
	
	//Connect to Database
		static Connection con = null;
    	static Statement stmt = null;
    	static ResultSet rs = null;

    	public static void main(String[] args) throws PublicationExceptionHandler
    	{
    		Scanner scanner = new Scanner(System.in);
    		Publications publicationsInstance = new Publications("Sample", 0.0, "Daily"); // Example placeholder

        	init_db(); // Open the connection to the database
     
        		System.out.println("Choose an action:");
    			System.out.println("1. Add a new publication");
    			System.out.println("2. Display all publications");
    			System.out.println("3. Exit");
    			System.out.print("Enter your choice: ");

    			int choice = scanner.nextInt();
    			scanner.nextLine();  // Consume newline

    			switch (choice) {
    				case 1:
    					publicationsInstance.addPublication();
    					break;

    				case 2:
    					// Display all publications
    					publicationsInstance.displayAllPublications();
    					break;

    				case 3:
    					// Exit the menu
    					System.out.println("Exiting the program.");
    					scanner.close();
    					close_db();
    					return;
    				
    				default:
    					System.out.println("Invalid choice. Please try again.");
    			} 
        	} 
    	

    	public static void init_db() 
    	{
    		try 
			{
            	Class.forName("com.mysql.cj.jdbc.Driver");
            	String url = "jdbc:mysql://localhost:3306/NewsAgent?useTimezone=true&serverTimezone=UTC"; // Updated database name
            	con = DriverManager.getConnection(url, "root", "Root");
        	} 
    		catch (Exception e) 
			{
            	System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        	}
    	}
    	
    	public static void close_db()
    	{
    		try 
			{
                if (con != null) 
                {
                    con.close();
                    System.out.println("Database Closed");
                }
            } 
            catch (SQLException sqle) 
			{
                System.out.println("Error: failed to close the database");
            }
    	}

    	// Display all publications
    	public void displayAllPublications() {
    		try {
    			stmt = con.createStatement();
                String query = "SELECT * FROM publication";
                rs = stmt.executeQuery(query);
    			while (rs.next()) {
    				System.out.println("Publication Name: " + rs.getString("publicationName"));
    				System.out.println("Price: " + rs.getDouble("publicationPrice"));
    				System.out.println("Schedule: " + rs.getString("schedule"));
    				System.out.println("-------------------------------");
    			}
    			rs.close();
                stmt.close();
                close_db();
    		} catch (SQLException e) {
    			e.printStackTrace();
    		}
    	}

    	public void addPublication() throws PublicationExceptionHandler{
		Scanner in = new Scanner(System.in);
		
			try {
				// SQL insert statement for the publication table
				String str = "INSERT INTO publication (PublicationName, PublicationPrice, Schedule) VALUES (?, ?, ?)";

				// Get publication details from the user
				System.out.println("Please Enter the Publication Name:");
				String publicationName = in.nextLine();  // Use nextLine() to capture full name
				validatepubliName(publicationName);
				System.out.println("Please Enter the Publication Price:");
				double publicationPrice = in.nextDouble();
				validatepubliPrice(publicationPrice);
				in.nextLine(); // Consume the newline left-over
				System.out.println("Please Enter the Schedule:");
				String schedule = in.nextLine();
				validateschedule(schedule);

				// Prepare the statement
				PreparedStatement pstmt = con.prepareStatement(str);
				pstmt.setString(1, publicationName);
				pstmt.setDouble(2, publicationPrice);
				pstmt.setString(3, schedule);

				// Execute the update
				int rows = pstmt.executeUpdate();

				// Check if the insert was successful
				if (rows > 0) 
				{
					System.out.println("Publication added successfully!");
				} 
				else 
				{
					System.out.println("Failed to add publication.");
				}
				pstmt.close();
				}	 
				catch (SQLException sqle) 
				{
					System.out.println("Error: " + sqle.getMessage());
				} 
			close_db();
    		}
		}	
