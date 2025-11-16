package AdminDashboard;

import Config.Config;
import Main.Main;
import static Main.Main.lp;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AdminDashboard {

    // ================= ADD BOOKS WITH LOOP & VALIDATION =================
    public void add_books() {
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

        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db");

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
                System.out.print("Do you want to try again with a different book? (1 = Yes, 0 = Return to Admin Dashboard): ");
                continueChoice = Main.lp.nextInt();
                Main.lp.nextLine(); // consume newline

                if (continueChoice == 0) {
                    conn.close();
                    break; // exit loop
                } else {
                    conn.close();
                    continue; // restart the loop to input new book
                }
            }

            // If book does NOT exist, insert it
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

            conn.close();

            // Ask if user wants to add another book
            System.out.print("Do you want to add another book? (1 = Yes, 0 = Return to Admin Dashboard): ");
            continueChoice = Main.lp.nextInt();
            Main.lp.nextLine(); // consume newline

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
            continueChoice = 0; // stop loop on error
        }

    } while (continueChoice == 1);

    System.out.println("Exiting Add Books. Returning to Admin Dashboard...");
}

    // ================= VIEW BOOKS =================
    public void view_books() {
        Config con = new Config();
        String UserQuery = "SELECT * FROM tbl_storagebook";
        String[] UserHeaders = {"ID", "Book Title", "Book Author", "Book Publisher", "Book Year Published"};
        String[] UserColumns = {"book_id", "b_title", "b_author", "b_publisher", "b_yearpublished"};

        con.viewRecords(UserQuery, UserHeaders, UserColumns);
    }

    // ================= UPDATE BOOK =================
    public void updateuser() {
        System.out.print("Enter id to update: ");
        int uid = Main.lp.nextInt();
        Main.lp.nextLine();

        System.out.print("Add Books Title: ");
        String BT = Main.lp.nextLine();

        System.out.print("Add Book Author: ");
        String BA = Main.lp.nextLine();

        System.out.print("Enter Book Publisher: ");
        String BP = Main.lp.nextLine();

        System.out.print("Enter Book Year Published: ");
        String BYP = Main.lp.nextLine();

        Config con = new Config();
        String sqlUpdate = "UPDATE tbl_storagebook SET b_title = ?, b_author = ?, b_publisher = ?, b_yearpublished = ? WHERE book_id = ?";
        con.updateRecord(sqlUpdate, BT, BA, BP, BYP, uid);
    }

    // ================= DELETE USER =================
    public void deleteuser() {
        System.out.print("Enter id to delete: ");
        int did = Main.lp.nextInt();

        Config con = new Config();
        String sqlDelete = "DELETE FROM tbl_main WHERE u_id = ?";
        con.deleteRecord(sqlDelete, did);
    }

    // ================= APPROVE USER =================
    public void approveuser() {
        System.out.print("Enter id to approve: ");
        int id = Main.lp.nextInt();
        Main.lp.nextLine();

        Config con = new Config();
        String sqlUpdate = "UPDATE tbl_main SET u_status = ? WHERE u_id = ?";
        con.updateRecord(sqlUpdate, "Approved", id);
    }

    // ================= BORROW BOOK (simplified add) =================
    public void addbooks() {
        System.out.print("Enter the name of book to add: ");
        String nba = Main.lp.nextLine();

        System.out.print("Enter the Book author: ");
        String Ba = Main.lp.nextLine();

        Config con = new Config();
        String sql = "INSERT INTO tbl_book (b_name, b_author) VALUES(?, ?)";
        con.addRecord(sql, nba, Ba);
    }

    // ================= VIEW BORROWED BOOKS =================
    public static void viewBorrowedBooksAdmin() {
        String joinQuery = "SELECT s.book_id, s.b_title, s.b_author, s.b_publisher, " +
                           "s.b_yearpublished, s.status, b.borrower_name, b.date_borrowed " +
                           "FROM tbl_storagebook s " +
                           "LEFT JOIN tbl_borrowed b ON s.book_id = b.book_id " +
                           "ORDER BY s.book_id";

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {
            PreparedStatement stmt = conn.prepareStatement(joinQuery);
            ResultSet rs = stmt.executeQuery();

            System.out.println("ID | Title | Author | Publisher | Year | Status | Borrower | Date Borrowed");
            System.out.println("----------------------------------------------------------------------------------");

            while (rs.next()) {
                System.out.println(rs.getInt("book_id") + " | " +
                                   rs.getString("b_title") + " | " +
                                   rs.getString("b_author") + " | " +
                                   rs.getString("b_publisher") + " | " +
                                   rs.getString("b_yearpublished") + " | " +
                                   rs.getString("status") + " | " +
                                   (rs.getString("borrower_name") != null ? rs.getString("borrower_name") : "N/A") + " | " +
                                   (rs.getString("date_borrowed") != null ? rs.getString("date_borrowed") : "N/A"));
            }

            System.out.println("\nPress ENTER to return to Dashboard...");
            Main.lp.nextLine();

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
