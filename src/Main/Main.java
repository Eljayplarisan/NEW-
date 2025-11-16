package Main;

import Staff.Staff;
import AdminDashboard.AdminDashboard;
import UserDashboard.UserDashboard;
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

    if (option == 1) {
        ud.login();
    } else if (option == 2) {
        ud.Register();
    } else if (option == 3) {
        System.out.println("Exiting system...");
        System.exit(0);
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
            System.out.println("4. Delete User");
            System.out.println("5. Approve");
            System.out.println("6. Borrow Books");
            System.out.println("7. Exit");

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
                    ud.updateuser();
                    break;
                case 4:
                    ud.view_books();
                    ud.deleteuser();
                    break;
                case 5:
                    ud.view_books();
                    ud.approveuser();
                    break;
                case 6:
                    ud.addbooks(); // borrow books method
                    break;
                case 7:
                    System.out.println("Returning to main menu...");
                    return; // back to main
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
        System.out.println("2. View Storage Books");
        System.out.println("3. Delete Books");
        System.out.println("4. Exit");

        System.out.print("Choose an option: ");
        int option = lp.nextInt();
        lp.nextLine();

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
                System.out.println("Returning to main menu...");
                return; // exit dashboard
            default:
                System.out.println("Invalid choice, try again.");
        }
    }
}

public static void User() {
    while (true) {
        System.out.println("--------------------------");
        System.out.println("----- USER DASHBOARD -----");
        System.out.println("--------------------------");

        System.out.println("1. Borrow Book");
        System.out.println("2. Return Book");
        System.out.println("3. Exit");

        System.out.print("Choose an option: ");
        int option = lp.nextInt();
        lp.nextLine();

        UserDashboard user = new UserDashboard();
switch (option) {
    case 1:
        UserDashboard.View();
        UserDashboard.borrow_Books();
        break;
    case 2:
        UserDashboard.viewBorrowedBooks(); // fixed typo
        UserDashboard.return_books();
        break;
    case 3:
        System.out.println("Returning to main menu...");
        return; // exit dashboard
    default:
        System.out.println("Invalid choice, try again.");
}
    }
}

}
