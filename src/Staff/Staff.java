package Staff;

import Config.Config;
import Main.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Staff {

    // ---------------- Add Book ----------------
    public static void ADDBOOKS() {
      int continueChoice;

    do {
        System.out.println("-----------------------");
        System.out.println("-------ADD BOOKS-------");
        System.out.println("-----------------------");

        System.out.print("Enter Book Title: ");
        String BT = Main.lp.nextLine();

        System.out.print("Enter Book Author: ");
        String BA = Main.lp.nextLine();

        System.out.print("Enter Book Publisher: ");
        String BP = Main.lp.nextLine();

        System.out.print("Enter Book Year Published: ");
        String BYP = Main.lp.nextLine();

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {

            // Check if book already exists
            String checkQuery = "SELECT * FROM tbl_storagebook WHERE b_title = ? AND b_author = ? AND b_publisher = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setString(1, BT);
            checkStmt.setString(2, BA);
            checkStmt.setString(3, BP);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Book already exists
                System.out.println("Book already exists in the library!");
                System.out.print("Do you want to enter another book? (1 = Yes, 0 = Return to Staff Dashboard): ");
                continueChoice = Main.lp.nextInt();
                Main.lp.nextLine(); // consume newline
                if (continueChoice == 0) {
                    break; // return to dashboard
                } else {
                    continue; // restart input for new book
                }
            }

            // If book does not exist, insert
            String sql = "INSERT INTO tbl_storagebook (b_title, b_author, b_publisher, b_yearpublished) VALUES(?, ?, ?, ?)";
            PreparedStatement insertStmt = conn.prepareStatement(sql);
            insertStmt.setString(1, BT);
            insertStmt.setString(2, BA);
            insertStmt.setString(3, BP);
            insertStmt.setString(4, BYP);

            int rows = insertStmt.executeUpdate();
            if (rows > 0) {
                System.out.println("Book added successfully!");
            } else {
                System.out.println("Error adding the book.");
            }

            // Ask if user wants to add another book
            System.out.print("Do you want to add another book? (1 = Yes, 0 = Return to Staff Dashboard): ");
            continueChoice = Main.lp.nextInt();
            Main.lp.nextLine(); // consume newline

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            continueChoice = 0;
        }

    } while (continueChoice == 1);

    System.out.println("Returning to Staff Dashboard...");
    }

    // ---------------- View Books ----------------
    public static void VIEW() {
        Config con = new Config();
        String UserQuery = "SELECT * FROM tbl_storagebook";
        String[] UserHeaders = {"ID", "Book Title", "Book Author", "Book Publisher", "Book Year Published"};
        String[] UserColumns = {"book_id", "b_title", "b_author", "b_publisher", "b_yearpublished"};

        con.viewRecords(UserQuery, UserHeaders, UserColumns);
    }

    // ---------------- Delete Book ----------------
    public static void Delete() {
        
    while (true) {  // loop for multi-delete

        String input;

        // Validate numeric input
        while (true) {
            System.out.print("Enter book ID to delete: ");
            input = Main.lp.nextLine();

            if (input.isEmpty()) {
                System.out.println("Book ID cannot be empty!");
            } else if (!input.matches("\\d+")) {
                System.out.println("Invalid input! Numbers only.");
            } else {
                break; // valid number
            }
        }

        int did = Integer.parseInt(input);

        Config con = new Config();
        String sqlDelete = "DELETE FROM tbl_storagebook WHERE book_id = ?";
        con.deleteRecord(sqlDelete, did);

        System.out.println("Book deleted successfully (if it existed).");

        // Ask if they want to delete again
        System.out.print("\nDo you want to delete another book? (1 = Yes, 0 = No): ");
        String choice = Main.lp.nextLine();

        if (!choice.equals("1")) {
            System.out.println("Returning to Staff Dashboard...");
            return; // exit → back to staff dashboard
        }

        System.out.println("\n--- Delete Another Book ---\n");
    }
    }
}
