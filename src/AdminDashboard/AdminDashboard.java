
package AdminDashboard;

import Config.Config;
import Main.Main;
import static Main.Main.lp;

public class AdminDashboard {
    
    public void adduser(){
          
       System.out.print("Add user name: ");
       String name = Main.lp.nextLine();
        if (name.isEmpty()) {
            System.out.println(" Name cannot be empty!");
            return; 
        }

        System.out.print("Add user email: ");
        String email = Main.lp.nextLine();
        if (email.isEmpty()) {
            System.out.println(" Email cannot be empty!");
            return;
        } else if (!email.contains("@") || !email.contains(".")) {
            System.out.println("Invalid email already exist please create another email!");
            return;
        }

        System.out.print("Add user password: ");
        String pass = Main.lp.nextLine();
        
            if (pass.isEmpty()) {
                System.out.println(" Password cannot be empty!");
                return;
            } else if (pass.length() < 4) {
                System.out.println(" Password must be at least 4 characters!");
                return;
            }

        System.out.println("Choose role (1. Admin, 2. User 3. Staff): ");
        int chooseRole = Main.lp.nextInt();

        String role = "";
            if (chooseRole == 1) {
                role = "Admin";
             } else if (chooseRole == 2) {
                role = "User";
              } else if (chooseRole == 3) {
                role = "Staff";
              } else {
                System.out.println(" Invalid role choice! Defaulting to User.");
                role = "User";
            }

        Config con = new Config();
        String sql = "INSERT INTO tbl_main(u_name, u_email, u_pass, u_role, u_status) VALUES(?, ?, ?, ?, ?)";
        con.addRecord(sql, name, email, pass, role, "Pending");

        System.out.println(" User registered successfully!");
        
    }
     public void viewuser(){
        
        Config con = new Config();
        String UserQuery = "SELECT * FROM tbl_main";
        String[] UserHeaders = {"ID","NAME", "EMAIL", "PASSWORD", "ROLE", "STATUS"};
        String[] UserColumns = {"u_id","u_name", "u_email", "u_pass", "u_role","u_status"};
        
        con.viewRecords(UserQuery, UserHeaders, UserColumns);
    } 
     
      public void updateuser(){
        
        System.out.print("Enter id to update: ");
        int uid = Main.lp.nextInt();
        Main.lp.nextLine();
       System.out.print("Add user name: ");
        String name = Main.lp.nextLine();
        
        System.out.print("Enter user email: ");
        String email = Main.lp.nextLine();
        
        System.out.print("Enter Password: ");
        String pass = Main.lp.nextLine();
        
        Config con = new Config();
        String sqlUpdate = "UPDATE tbl_main SET u_name = ?, u_email = ?, u_pass = ? WHERE u_id = ?";
        con.updateRecord(sqlUpdate, name, email, pass, uid);
    }
      
      
        public void deleteuser(){
        
        System.out.print("Enter id to delete: ");
        int did = Main.lp.nextInt();
        
        Config con = new Config();
        String sqlDelete = "DELETE FROM tbl_main WHERE u_id = ?";
        con.deleteRecord(sqlDelete, did);
        
    }
    
    public void approveuser(){
    
            System.out.println("Enter id to approved");
            int id = lp.nextInt();
            Main.lp.nextLine();
            
            
            Config con = new Config();
            
            String sqlupdate = "UPDATE tbl_main SET u_status = ? WHERE u_id = ?";
            con.updateRecord(sqlupdate, "Approved", id);
    }
    
     public void addbooks(){
     
         System.out.println("Enter the name of book to add: ");
         String nba = Main.lp.nextLine();
         
         System.out.println("Enter the Book author: ");
         String Ba = Main.lp.nextLine();
         
        Config con = new Config();
        String sql = "INSERT INTO tbl_book (b_name, b_author) VALUES(?,?)";
        con.addRecord(sql, nba, Ba);

     
     }
    
}