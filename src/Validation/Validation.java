
package Validation;

import Config.Config;
import Main.Main;


public class Validation {
    
    public void Register(){
        
        Config con = new Config();
        System.out.print("Add user name: ");
        String name = Main.lp.nextLine();
        while(true){
            String qry = "SELECT * FROM tbl_main WHERE u_name = ?";
            java.util.List<java.util.Map<String, Object>> result = con.fetchRecords(qry, name);

            if (result.isEmpty()) {
                break;
            } else {
                System.out.print("Email already exists, Enter other Email: ");
                name = Main.lp.next();
            }
        }
        System.out.print("Add user email: ");
        String email = Main.lp.nextLine();
        System.out.print("Add user password: ");
        String pass = Main.lp.nextLine();
    
        System.out.println("Choose role (1. Admin, 2. User 3. Staff): ");
        int chooseRole = Main.lp.nextInt();
        
        String role = "";
            if(chooseRole == 1){
                role = "Admin";
            }else if (chooseRole == 2){
                role = "User";
            } else if (chooseRole == 3){
                role = "Staff";
            }
       
        String sql = "INSERT INTO tbl_main (u_name, u_email, u_pass, u_role, u_status) VALUES(?, ?, ?, ?, ?)";
        con.addRecord(sql, name, email, pass, role, "Pending");
        
    }
  public void login(){
      
      
        System.out.print("Enter Email: ");
        String email = Main.lp.nextLine();

        System.out.print("Enter Password: ");
        String pass = Main.lp.nextLine();

        Config con = new Config();
        String role = con.login(email, pass);

        if (role != null) {
            if (role.equalsIgnoreCase("Admin")) {
                Main.AdminDAshboard();
            } else {
                Main.Staff();
            }
        }
    
    }   
}
