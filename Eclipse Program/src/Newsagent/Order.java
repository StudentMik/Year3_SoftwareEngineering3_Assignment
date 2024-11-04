import java.sql.*;
import java.util.Scanner;



 class OrderExceptionHandler  extends Exception{

     public OrderExceptionHandler(String message)
     {
         super(message); // Call the parent constructor with the error message
     }


}


public class Order {
   // private int customerId; // needs customer id to create order
    private long customerPhoneNumber; // need customer phone number to create order
    private double publicationPrice; // price of each publication on the order
    private int OrderId;
    private  int  Quantity;
    private String PublicationName;

    // Constructor to initialize an Order
    public Order(int OrderId, int publicationName, double publicationPrice, String Quantity) throws OrderExceptionHandler {

        validatePublicationName(String.valueOf(publicationName));
        validatePublicationPrice(publicationPrice);
        validateQuantity(Integer.valueOf(Quantity));
        validateOrderId(OrderId);

        // Set attributes
        this.OrderId = OrderId;
        this.publicationPrice = publicationPrice;
        this.Quantity = Integer.parseInt(Quantity);

    }

    //---------------- Getters and setters-----------------//

    public long getCustomerPhoneNumber() {
        return customerPhoneNumber;
    }

    public void setCustomerPhoneNumber(long customerPhoneNumber) {
        this.customerPhoneNumber = customerPhoneNumber;
    }

    public double getPublicationPrice() {
        return publicationPrice;
    }

    public void setPublicationPrice(double publicationPrice) {
        this.publicationPrice = publicationPrice;
    }




    //--------------Validation methods------------------//


    private void validateQuantity(Integer Quantity) throws OrderExceptionHandler {
        if (Quantity == null || Quantity < 0 )
        {
            throw new OrderExceptionHandler("Invalid Quantity: must be a an int and greater than 1 .");
        }
    }


    private void validatePublicationPrice(Double publicationPrice) throws OrderExceptionHandler {
        if (publicationPrice == null || publicationPrice == 0 )
        {
            throw new OrderExceptionHandler("Invalid Publication price: must be a double not an int.");
        }
    }

    private void validateOrderId( Integer OrderId) throws OrderExceptionHandler {
        if(OrderId == null  ||  OrderId == 0  || OrderId < 0)
        {
            throw new OrderExceptionHandler("Invalid Order Id: must be an int  and has to be greater than 0");
        }
    }

    private void  validatePublicationName(String publicationName) throws OrderExceptionHandler
    {
        if (publicationName == null  || publicationName.length() < 1 || publicationName.length() > 15)
        {
            throw new OrderExceptionHandler("Invalid Publication name: must be between 7 and 15 characters.");
        }
    }

    // Placeholder method for generating the bill
    public void bill() {}




    //--------- connect to database newsagent-------------//

    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        init_db(); // Open the connection to the database


        while (true)
        {
            try
            {
                System.out.println("Please Enter the orderId:");
                int orderId = Integer.parseInt(in.nextLine());  // Use nextLine() to capture full name
                System.out.println("Please Enter the Quantity:");
                int Quantity = Integer.parseInt(in.nextLine());
                System.out.println("Please Enter the price:");
                Double ordertotalprice = Double.valueOf(in.nextLine());
                System.out.println("Please Enter the Publication name:");
                String PublicationName = in.nextLine();

                Order order = new Order(orderId,Quantity,ordertotalprice,PublicationName);
                break;

            }
            catch (OrderExceptionHandler e)
            {
                System.out.println("Order Error: " + e.getMessage());
                System.out.println("Press Enter to retry...");
                in.nextLine(); // Wait for user to hit Enter before retrying
            }
        }







        try {
            // SQL insert statement for the customers table
            String str = "INSERT INTO orders (OrderId, Quantity, OrderTotalPrice,PublicationName" +
                    " ) VALUES (?, ?, ?, ?)";

            // Get Order details from the user
            System.out.println("Please Enter the orderId:");
            String orderId = in.nextLine();  // Use nextLine() to capture full name
            System.out.println("Please Enter the Quantity:");
            String Quantity = in.nextLine();
            System.out.println("Please Enter the price:");
            String ordertotalprice = in.nextLine();
            System.out.println("Please Enter the Publication name:");
            String PublicationName = in.nextLine();

            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setString(1, orderId);
            pstmt.setString(2, Quantity);
            pstmt.setString(3, ordertotalprice);
            pstmt.setString(4, PublicationName);

            // Execute the update
            int rows = pstmt.executeUpdate();

            // Check if the insert was successful
            if (rows > 0) {
                System.out.println("Order added successfully!");
            } else {
                System.out.println("Failed to add order.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Close the database connection
            try {
                if (con != null) {
                    con.close();
                }
            } catch (SQLException sqle) {
                System.out.println("Error: failed to close the database");
            }
        }
    }

    public static void init_db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC"; // Updated database name
            con = DriverManager.getConnection(url, "root", "password");
            stmt = con.createStatement();
        } catch (Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }
}



