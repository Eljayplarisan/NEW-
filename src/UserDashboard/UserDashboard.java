package UserDashboard;

import Config.Config;
import Main.Main;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class UserDashboard {

    // ---------------- Borrow Books ----------------
    public static void borrow_Books() {
      int continueChoice = 1;

    while (continueChoice == 1) {
        System.out.print("Enter Book ID to Borrow: ");
        String bib = Main.lp.nextLine();

        if (bib.isEmpty()) {
            System.out.println("Book ID cannot be empty!");
            continue;
        } else if (!bib.matches("\\d+")) {
            System.out.println("Invalid Book ID! Please enter numbers only.");
            continue;
        }

        int bookId = Integer.parseInt(bib);

        System.out.print("Enter borrower name: ");
        String borrower = Main.lp.nextLine();
        if (borrower.isEmpty()) {
            System.out.println("Borrower name cannot be empty!");
            continue;
        }

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {

            // Check if book exists and get status
            String checkQuery = "SELECT b_title, status FROM tbl_storagebook WHERE book_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Book ID does not exist!");
                continue;
            }

            String bookTitle = rs.getString("b_title");
            String currentStatus = rs.getString("status");
            if ("Borrowed".equalsIgnoreCase(currentStatus)) {
                System.out.println("The book \"" + bookTitle + "\" is already borrowed!");
                continue; // restart loop so user can enter another book
            }

            // Update book status to Borrowed
            String updateQuery = "UPDATE tbl_storagebook SET status = 'Borrowed' WHERE book_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setInt(1, bookId);
            int rows = updateStmt.executeUpdate();

            if (rows > 0) {
                System.out.println("You have successfully borrowed \"" + bookTitle + "\"!");

                // Record in tbl_borrowed
                String insertBorrow = "INSERT INTO tbl_borrowed (book_id, borrower_name, date_borrowed) VALUES (?, ?, datetime('now'))";
                PreparedStatement borrowStmt = conn.prepareStatement(insertBorrow);
                borrowStmt.setInt(1, bookId);
                borrowStmt.setString(2, borrower);
                borrowStmt.executeUpdate();

                System.out.println("Borrow record saved!");
            } else {
                System.out.println("Error borrowing the book.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }

        // Ask if user wants to borrow another book
        System.out.print("Do you want to borrow another book? (1 = Yes, 0 = Return to Dashboard): ");
        try {
            continueChoice = Integer.parseInt(Main.lp.nextLine());
        } catch (NumberFormatException e) {
            continueChoice = 0; // invalid input exits loop
        }
    }

    System.out.println("Returning to Dashboard...");
}

    // ---------------- Return Books ----------------
    public static void return_books() {
        System.out.print("Enter Book ID to Return: ");
        String bir = Main.lp.nextLine();

        if (bir.isEmpty()) {
            System.out.println("Book ID cannot be empty!");
            return;
        } else if (!bir.matches("\\d+")) {
            System.out.println("Invalid Book ID! Numbers only.");
            return;
        }

        int bookId = Integer.parseInt(bir);

        try (Connection conn = DriverManager.getConnection("jdbc:sqlite:library.db")) {

            // Check if book exists
            String checkQuery = "SELECT status FROM tbl_storagebook WHERE book_id = ?";
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, bookId);
            ResultSet rs = checkStmt.executeQuery();

            if (!rs.next()) {
                System.out.println("Book ID does not exist!");
                return;
            }

            String currentStatus = rs.getString("status");
            if ("Available".equalsIgnoreCase(currentStatus)) {
                System.out.println("This book is not borrowed!");
                return;
            }

            // Update book status to Available
            String updateQuery = "UPDATE tbl_storagebook SET status = 'Available' WHERE book_id = ?";
            PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
            updateStmt.setInt(1, bookId);
            int rows = updateStmt.executeUpdate();

            if (rows > 0) {
                System.out.println("Book successfully returned!\n");

                // Display the tbl_borrowed table
                String borrowedQuery = "SELECT * FROM tbl_borrowed";
                PreparedStatement borrowedStmt = conn.prepareStatement(borrowedQuery);
                ResultSet borrowedRs = borrowedStmt.executeQuery();

                System.out.println("Borrowed Books Table:");
                System.out.println("ID | Borrower Name | Book ID | Date Borrowed");
                System.out.println("-------------------------------------------");

                while (borrowedRs.next()) {
                    System.out.println(
                        borrowedRs.getInt("id") + " | " +
                        borrowedRs.getString("borrower_name") + " | " +
                        borrowedRs.getInt("book_id") + " | " +
                        borrowedRs.getString("date_borrowed")
                    );
                }

                System.out.println("\nPress ENTER to continue...");
                Main.lp.nextLine();

            } else {
                System.out.println("Error returning the book.");
            }

        } catch (SQLException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // ---------------- View Books ----------------
    public static void View() {
        
        Config con = new Config();
        String UserQuery = "SELECT * FROM tbl_storagebook";
        String[] UserHeaders = {"ID","Book Title", "Book Author", "Book Publisher", "Book Published", "status"};
        String[] UserColumns = {"book_id","b_title", "b_author", "b_publisher", "b_yearpublished", "status"};

        con.viewRecords(UserQuery, UserHeaders, UserColumns);
    }

     // Method to ensure tbl_borrowed exists
    public static void setupBorrowedTable() {
        String createTableQuery = "CREATE TABLE IF NOT EXISTS tbl_borrowed ("
                + "borrow_id INTEGER PRIMARY KEY AUTOINCREMENT, "
                + "book_id INTEGER NOT NULL, "
                + "book_title TEXT NOT NULL, "
                + "borrower_name TEXT NOT NULL, "
                + "date_borrowed TEXT NOT NULL"
                + ");";

        try (Connection conn = connect();
             Statement stmt = conn.createStatement()) {

            stmt.execute(createTableQuery);
            System.out.println("Table tbl_borrowed is ready.");

        } catch (SQLException e) {
            System.out.println("Error creating tbl_borrowed: " + e.getMessage());
        }
    }

    // Connection method
    public static Connection connect() {
        Connection conn = null;
        try {
            String url = "jdbc:sqlite:C:/Users/Eljay/your_database.db"; // adjust your DB path
            conn = DriverManager.getConnection(url);
        } catch (SQLException e) {
            System.out.println("Connection failed: " + e.getMessage());
        }
        return conn;
    }

    // Display borrowed books
    public static void viewBorrowedBooks() {
        Config con = new Config();
        String BorrowedQuery = "SELECT * FROM tbl_borrowed";
        String[] BorrowedHeaders = {"Borrow ID", "Book ID", "Book Title", "Borrower Name", "Date Borrowed"};
        String[] BorrowedColumns = {"borrow_id", "book_id", "book_title", "borrower_name", "date_borrowed"};

        con.viewRecords(BorrowedQuery, BorrowedHeaders, BorrowedColumns);
    }
  
}
