package Main;

import Staff.Staff;
import AdminDashboard.AdminDashboard;
import BorrowerDashboard.BorrowerDashboard;
import Validation.Validation;
import java.util.Scanner;

public class Main {

    public static Scanner lp = new Scanner(System.in);

    public static void main(String[] args) {

  while (true) {
    System.out.println("WELCOME TO MY SYSTEM");
    System.out.println("1. Login");
    System.out.println("2. Register");
    System.out.println("3. Exit");

    System.out.print("Choose an option: ");
    String input = lp.nextLine().trim(); // read input as string

    int option = 0;

    // Validate input: must be exactly one character and a number 1-3
    if (input.length() == 1 && input.matches("[1-3]")) {
        option = Integer.parseInt(input);
    } else {
        System.out.println("Invalid input! Please enter 1, 2, or 3 only.");
        continue; // restart the loop
    }

    Validation ud = new Validation();

      switch (option) {
          case 1:
              ud.login();
              break;
          case 2:
              ud.Register();
              break;
          case 3:
              System.out.println("Exiting system...");
              System.exit(0);
          default:
              break;
      }
}
    }

    // ================= Admin Dashboard =================
    public static void AdminDashboard() {
        
       AdminDashboard ud = new AdminDashboard();

while (true) {
    System.out.println("------------------------------------");
    System.out.println("-- WELCOME TO THE ADMIN DASHBOARD --");
    System.out.println("------------------------------------");
 
    System.out.println("1. Add Books");
    System.out.println("2. View Books");
    System.out.println("3. Update Books");
    System.out.println("4. Delete Books");
    System.out.println("5. Approve");
    System.out.println("6. Exit");

    System.out.print("\nChoose an option: ");
    int option = lp.nextInt();
    lp.nextLine(); // consume newline

    switch (option) {
        case 1:
            ud.add_books();
            break;
        case 2:
            ud.view_books();
            break;
        case 3:
            ud.view_books();
            ud.updatebook();
            break;
        case 4:
            ud.view_books();
            ud.deletebook();
            break;
        case 5:
            ud.viewmain();
            ud.approveuser();
            break;
        case 6:
            System.out.print("Do you want to go back to Main Menu? (1 = Yes, 0 = Stay): ");
            int exitChoice = lp.nextInt();
            lp.nextLine(); 

            if (exitChoice == 1) {
                System.out.println("Returning to Main Menu...");
                return;
            } else {
                System.out.println("Staying in Admin Dashboard...");
            }
            break;
        default:
            System.out.println("Invalid option. Try again.");
       }
     }
   }

    // ================= Staff Dashboard =================
   public static void Staff() {
    while (true) {
    System.out.println("-------------------------");
    System.out.println("---- Staff Dashboard ----");
    System.out.println("-------------------------");

    System.out.println("1. Add Books");
    System.out.println("2. View Books");
    System.out.println("3. Delete Books");
    System.out.println("4. Exit");

    System.out.print("Choose an option: ");
    int option = lp.nextInt();
    lp.nextLine(); // consume newline

    switch (option) {
        case 1:
            Staff.ADDBOOKS();
            break;
        case 2:
            Staff.VIEW();
            break;
        case 3:
            Staff.VIEW();
            Staff.Delete();
            break;
        case 4:
            System.out.print("Do you want to go back to Main Menu? (1 = Yes, 0 = Stay): ");
            int exitChoice = lp.nextInt();
            lp.nextLine(); // consume newline

            if (exitChoice == 1) {
                System.out.println("Returning to Main Menu...");
                return; // exit dashboard to main menu
            } else {
                System.out.println("Staying in Staff Dashboard...");
            }
            break;
        default:
            System.out.println("Invalid choice, try again.");
    }
  }
}

public static void User() {
    while (true) {
    System.out.println("--------------------------");
    System.out.println("----- Borrower DASHBOARD -----");
    System.out.println("--------------------------");

    System.out.println("1. Borrow Book");
    System.out.println("2. Return Book");
    System.out.println("3. Exit");

    System.out.print("Choose an option: ");
    int option = lp.nextInt();
    lp.nextLine(); // consume newline

    BorrowerDashboard user = new BorrowerDashboard();

    switch (option) {
        case 1:
            BorrowerDashboard.View();
            BorrowerDashboard.borrow_Books();
            break;
        case 2:
            BorrowerDashboard.viewBorrowedBooks(); 
            BorrowerDashboard.return_books();
            break;
        case 3:
            System.out.print("Do you want to go back to Main Menu? (1 = Yes, 0 = Stay): ");
            int exitChoice = lp.nextInt();
            lp.nextLine(); // consume newline

            if (exitChoice == 1) {
                System.out.println("Returning to Main Menu...");
                return; // exit dashboard
            } else {
                System.out.println("Staying in User Dashboard...");
            }
            break;
        default:
            System.out.println("Invalid choice, try again.");
     }
   }
 }

    public static void Borrower() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
