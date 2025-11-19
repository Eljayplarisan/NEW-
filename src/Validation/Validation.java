
package Validation;

import Config.Config;
import Main.Main;
import java.util.List;
import java.util.Map;


public class Validation {
    
   public void Register(){
       
           Config con = new Config();

           System.out.println("\n\n\n================================================");
           System.out.println("                 --- REGISTER ---");
           System.out.println("================================================");

      
           System.out.print("Enter username: ");
           String name = Main.lp.nextLine();

           while (name.trim().isEmpty()) {
               System.out.print("Username cannot be empty. Enter again: ");
               name = Main.lp.nextLine();
           }

         
           while (true) {
               String qry = "SELECT * FROM tbl_main WHERE u_name = ?";

               List<Map<String, Object>> result = con.fetchRecords(qry, name);

               if (result.isEmpty()) break;

               System.out.print("Username already exists. Enter another username: ");
               name = Main.lp.nextLine();
           }

           System.out.print("Enter Email: ");
           String email = Main.lp.nextLine();

           while (email.trim().isEmpty()) {
               System.out.print("Email cannot be empty. Enter again: ");
               email = Main.lp.nextLine();
           }

           while (true) {
               String qry = "SELECT * FROM tbl_main WHERE u_email = ?";

               List<Map<String, Object>> result = con.fetchRecords(qry, email);

               if (result.isEmpty()) break;

               System.out.print("Email already exists. Enter another email: ");
               email = Main.lp.nextLine();
           }

      
           System.out.print("Enter Password: ");
           String pass = Main.lp.nextLine();

           while (pass.trim().isEmpty()) {
               System.out.print("Password cannot be empty. Enter again: ");
               pass = Main.lp.nextLine();
           }

           int chooseRole = 0;

           while (true) {
               System.out.print("Choose Role (1 = Admin, 2 = Staff, 3 = Borrower): ");

               if (Main.lp.hasNextInt()) {
                   chooseRole = Main.lp.nextInt();
                   Main.lp.nextLine();

                   if (chooseRole >= 1 && chooseRole <= 3) break;

               } else {
                   Main.lp.nextLine(); 
               }

               System.out.println("Invalid choice! Please enter 1, 2, or 3.");
           }

           String role;
           switch (chooseRole) {
               case 1: role = "Admin"; break;
               case 2: role = "Staff"; break;
               case 3: role = "User"; break;
               default: role = "User"; 
           }

          
           String hash = con.hashPassword(pass);

       
           String sql = "INSERT INTO tbl_main (u_name, u_email, u_pass, u_role, u_status) VALUES (?, ?, ?, ?, ?)";
           con.addRecord(sql, name, email, hash, role, "Pending");

           System.out.println("\nRegistration successful! Status: Pending");

}

   
 public void login() {
     
        System.out.print("Enter Email: ");
        String email = Main.lp.nextLine();

        System.out.print("Enter Password: ");
        String pass = Main.lp.nextLine();

        Config con = new Config();
        String role = con.login(email, pass);

            if (role != null) {
                if (role.equalsIgnoreCase("Admin")) {
                    Main.AdminDashboard();
                } else if (role.equalsIgnoreCase("Staff")) {
                    Main.Staff();
                } else if (role.equalsIgnoreCase("User")) {
                    Main.User();
                }
            }
        }

    
    }   


