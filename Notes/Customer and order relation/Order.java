import Newsagent.Customer;


import java.sql.*;
import java.util.Scanner;

class OrderExceptionHandler extends Exception {
    public OrderExceptionHandler(String message) {
        super(message);
    }
}
public class Order {
    private long customerPhoneNumber;
    private double publicationPrice;
    private String publicationName;
    private int orderId;
    private int quantity;
    private boolean orderStatus = false; // Track status per order instance
   
    int findCustomerID = Customer.id;  // check CustomerId from customer class

    // Constructor to initialize an Order
    public Order(int orderId, String publicationName, double publicationPrice, int quantity) throws OrderExceptionHandler {

        validateOrderId(orderId);
        validatePublicationName(publicationName);
        validatePublicationPrice(publicationPrice);
        validateQuantity(quantity);
        validateCustomerID(findCustomerID);

        this.orderId = orderId;
        this.publicationName = publicationName;
        this.publicationPrice = publicationPrice;
        this.quantity = quantity;
    }



    private void validateCustomerID(Integer findCustomerID) throws OrderExceptionHandler {

        if (findCustomerID == null || orderId <= 0) {
            throw new OrderExceptionHandler("Invalid Customer ID: must be a positive integer.");
        }
    }

    //---------------------Validation methods----------------------//


    private void validateQuantity(int quantity) throws OrderExceptionHandler {
        if (quantity <= 0) {
            throw new OrderExceptionHandler("Invalid Quantity: must be an integer greater than 0.");
        }
    }

    private void validatePublicationPrice(Double publicationPrice) throws OrderExceptionHandler {
        if (publicationPrice == null || publicationPrice <= 0) {
            throw new OrderExceptionHandler("Invalid Publication Price: must be a positive number.");
        }
    }

    private void validateOrderId(Integer orderId) throws OrderExceptionHandler {
        if (orderId == null || orderId <= 0) {
            throw new OrderExceptionHandler("Invalid Order ID: must be a positive integer.");
        }
    }

    private void validatePublicationName(String publicationName) throws OrderExceptionHandler {
        if (publicationName == null || publicationName.length() < 7 || publicationName.length() > 15) {
            throw new OrderExceptionHandler("Invalid Publication Name: must be between 7 and 15 characters.");
        }
    }




    // -------------Connect To Database newsagent--------------------//

    static Connection con = null;

    public static void main(String[] args) throws OrderExceptionHandler {
        init_db();



        try (Scanner in = new Scanner(System.in)) {
            Order order = createOrder(in);  // Create order with user inputs
            if (order != null) {
                order.insertIntoDatabase();
            }
        } finally {
            close_db();
        }
    }

    private static Order createOrder(Scanner in) {
        while (true) {
            try {
                System.out.println("Please enter the Order ID:");
                int orderId = Integer.parseInt(in.nextLine());
                System.out.println("Please enter the Quantity:");
                int quantity = Integer.parseInt(in.nextLine());
                System.out.println("Please enter the Price:");
                double price = Double.parseDouble(in.nextLine());
                System.out.println("Please enter the Publication Name:");
                String publicationName = in.nextLine();

                return new Order(orderId, publicationName, price, quantity);
            } catch (OrderExceptionHandler | NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press Enter to retry...");
                in.nextLine();
            }
        }
    }

    public void insertIntoDatabase() {
        String sql = "INSERT INTO orders (OrderId, Quantity, OrderTotalPrice, PublicationName) VALUES (?, ?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, this.orderId);
            pstmt.setInt(2, this.quantity);
            pstmt.setDouble(3, this.publicationPrice);
            pstmt.setString(4, this.publicationName);

            int rows = pstmt.executeUpdate();
            this.orderStatus = rows > 0;

            if (this.orderStatus) {
                System.out.println("Order added successfully!");
            } else {
                System.out.println("Failed to add order.");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public static void init_db() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            String url = "jdbc:mysql://localhost:3306/newsagent?useTimezone=true&serverTimezone=UTC";
            con = DriverManager.getConnection(url, "root", "password");
        } catch (Exception e) {
            System.out.println("Error: Failed to connect to database\n" + e.getMessage());
        }
    }

    public static void close_db() {
        try {
            if (con != null) con.close();
        } catch (SQLException e) {
            System.out.println("Error: Failed to close the database");
        }
    }
}
