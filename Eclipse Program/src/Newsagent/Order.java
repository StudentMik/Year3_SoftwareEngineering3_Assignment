import java.sql.*;
import java.util.Scanner;

class OrderExceptionHandler extends Exception {
    public OrderExceptionHandler(String message) {
        super(message);
    }
}

public class Order {
    private double publicationPrice;
    private String publicationName;
    private int orderId;
    private int quantity;
    private boolean orderStatus = false; // Track status per order instance

    // Constructor
    public Order(String publicationName, double publicationPrice, int quantity) throws OrderExceptionHandler {
        validatePublicationName(publicationName);
        validatePublicationPrice(publicationPrice);
        validateQuantity(quantity);

        this.publicationName = publicationName;
        this.publicationPrice = publicationPrice;
        this.quantity = quantity;
        this.orderId = 0;
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

    private void validatePublicationName(String publicationName) throws OrderExceptionHandler {
        if (publicationName == null || publicationName.length() < 7 || publicationName.length() > 15) {
            throw new OrderExceptionHandler("Invalid Publication Name: must be between 7 and 15 characters.");
        }
    }

    // -------------Connect To Database newsagent--------------------//

    static Connection con = null;

    public static void main(String[] args) throws OrderExceptionHandler {
        init_db();
        Scanner in = new Scanner(System.in);

        while (true) {
            System.out.println("Choose an Option:");
            System.out.println("1. Add a new Order");
            System.out.println("2. Display all Orders");
            System.out.println("3. Update an Order");
            System.out.println("4. Delete an Order");
            System.out.println("5. Exit");
            System.out.print(": ");

            int choice = in.nextInt();
            in.nextLine(); // consume newline

            switch (choice) {
                case 1:
                    Order order = createOrder(in);
                    if (order != null) {
                        order.insertIntoDatabase();
                    }
                    break;
                case 2:
                    readOrders();
                    break;
                case 3:
                    updateOrder(in);
                    break;
                case 4:
                    deleteOrder(in);
                    break;
                case 5:
                    System.out.println("Exiting the program.");
                    close_db();
                    in.close();
                    return;
                default:
                    System.out.println("Error: Invalid Choice");
            }
        }
    }

    //---------------------CreateOrder--------------------------//

    private static Order createOrder(Scanner in) {
        while (true) {
            try {
                System.out.println("Please enter the Quantity:");
                int quantity = Integer.parseInt(in.nextLine());
                System.out.println("Please enter the Price:");
                double price = Double.parseDouble(in.nextLine());
                System.out.println("Please enter the Publication Name:");
                String publicationName = in.nextLine();

                return new Order(publicationName, price, quantity);
            } catch (OrderExceptionHandler | NumberFormatException e) {
                System.out.println("Error: " + e.getMessage());
                System.out.println("Press Enter to retry...");
                in.nextLine();
            }
        }
    }

    public void insertIntoDatabase() {
        String sql = "INSERT INTO orders (Quantity, OrderTotalPrice, PublicationName) VALUES (?, ?, ?)";

        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, this.quantity);
            pstmt.setDouble(2, this.publicationPrice);
            pstmt.setString(3, this.publicationName);

            int rows = pstmt.executeUpdate();
            this.orderStatus = rows > 0;

            System.out.println(this.orderStatus ? "Order added successfully!" : "Failed to add order.");
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

    //--------Read Orders from Database-------//
    public static void readOrders() {
        String query = "SELECT * FROM orders";
        try (Statement stmt = con.createStatement(); ResultSet rs = stmt.executeQuery(query)) {
            System.out.println("Orders:");
            while (rs.next()) {
                System.out.println("Order ID: " + rs.getInt("OrderId"));
                System.out.println("Publication Name: " + rs.getString("PublicationName"));
                System.out.println("Quantity: " + rs.getInt("Quantity"));
                System.out.println("Total Price: " + rs.getDouble("OrderTotalPrice"));
                System.out.println("-------------------------------");
            }
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    //----------Update Order Information------------//

    public static void updateOrder(Scanner in) {
        System.out.println("Please enter the Order ID to update:");
        int orderId = Integer.parseInt(in.nextLine());

        System.out.println("Enter new Quantity:");
        int newQuantity = Integer.parseInt(in.nextLine());
        System.out.println("Enter new Price:");
        double newPrice = Double.parseDouble(in.nextLine());
        System.out.println("Enter new Publication Name:");
        String newPublicationName = in.nextLine();

        String sql = "UPDATE orders SET Quantity = ?, OrderTotalPrice = ?, PublicationName = ? WHERE OrderId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, newQuantity);
            pstmt.setDouble(2, newPrice);
            pstmt.setString(3, newPublicationName);
            pstmt.setInt(4, orderId);

            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Order updated successfully!" : "Order not found or update failed.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
    //-----------Delete Order Information-----------//
    public static void deleteOrder(Scanner in) {
        System.out.println("Please enter the Order ID to delete:");
        int orderId = Integer.parseInt(in.nextLine());

        String sql = "DELETE FROM orders WHERE OrderId = ?";
        try (PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setInt(1, orderId);
            int rows = pstmt.executeUpdate();
            System.out.println(rows > 0 ? "Order deleted successfully!" : "Order not found or delete failed.");
        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
