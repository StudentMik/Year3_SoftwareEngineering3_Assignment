import java.sql.*;
import java.util.Scanner;

public class Order {
    private int customerId; // needs customer id to create order
    private long customerPhoneNumber; // need customer phone number to create order
    private double publicationPrice; // price of each publication on the order
    private boolean canSendOrder;

    // Constructor to initialize an Order
    public Order(int customerId, long customerPhoneNumber, double publicationPrice, boolean canSendOrder) throws OrderExceptionHandler {
        // Validate inputs
        validateCustomerId(customerId);
        validateCustomerPhoneNumber(customerPhoneNumber);
        validatePublicationPrice(publicationPrice);
        validateCanSendOrder(canSendOrder);

        // Set attributes
        this.customerId = customerId;
        this.customerPhoneNumber = customerPhoneNumber;
        this.publicationPrice = publicationPrice;
        this.canSendOrder = canSendOrder;
    }

    // Getters and setters
    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

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

    public boolean isCanSendOrder() {
        return canSendOrder;
    }

    public void setCanSendOrder(boolean canSendOrder) {
        this.canSendOrder = canSendOrder;
    }

    //--------------Validation methods------------------//

    private void validateCustomerId(int customerId) throws OrderExceptionHandler {
        if (customerId <= 0) {
            throw new OrderExceptionHandler("Invalid Customer ID. It must be greater than 0.");
        }
    }

    private void validateCustomerPhoneNumber(long customerPhoneNumber) throws OrderExceptionHandler {
        String phoneStr = String.valueOf(customerPhoneNumber); // Convert long to String for validation

        if (phoneStr.isBlank()) {
            throw new OrderExceptionHandler("Customer Phone Number NOT specified.");
        } else if (phoneStr.length() < 7) {
            throw new OrderExceptionHandler("Customer Phone Number does not meet minimum length requirements.");
        } else if (phoneStr.length() > 15) {
            throw new OrderExceptionHandler("Customer Phone Number exceeds maximum length requirements.");
        }
    }

    private void validatePublicationPrice(double publicationPrice) throws OrderExceptionHandler {
        if (publicationPrice <= 0) {
            throw new OrderExceptionHandler("Publication price must be a positive value.");
        }
    }

    private void validateCanSendOrder(boolean canSendOrder) throws OrderExceptionHandler {

    }

    // Placeholder method for generating the bill
    public void bill() {}




    //------ connect to database newsagent-------------//

    static Connection con = null;
    static Statement stmt = null;
    static ResultSet rs = null;

    public static void main(String[] args) {

        Scanner in = new Scanner(System.in);
        init_db(); // Open the connection to the database
        try {
            // SQL insert statement for the customers table
            String str = "INSERT INTO orders (OrderId, CustomerId, OrderTotalPrice,PublicationName" +
                    " ) VALUES (?, ?, ?, ?)";

            // Get customer details from the user
            System.out.println("Please Enter the orderId:");
            String orderId = in.nextLine();  // Use nextLine() to capture full name
            System.out.println("Please Enter the customerId:");
            String customerId = in.nextLine();
            System.out.println("Please Enter the price:");
            String ordertotalprice = in.nextLine();
            System.out.println("Please Enter the Publication name:");
            String PublicationName = in.nextLine();

            // Prepare the statement
            PreparedStatement pstmt = con.prepareStatement(str);
            pstmt.setString(1, orderId);
            pstmt.setString(2, customerId);
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

        } catch (SQLException sqle) {
            System.out.println("Error: " + sqle.getMessage());
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



