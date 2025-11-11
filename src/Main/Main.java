 
package Main;

import static Main.Main.lp;
import Staff.Staff;
import AdminDashboard.AdminDashboard;
import Validation.Validation;
import java.util.Scanner;



public class Main {
    
    public static Scanner lp = new Scanner(System.in);
    
    public static void main(String[] args) {
        
        
        System.out.println("WELCOME TO MY SYSTEM");
        System.out.println("1. login: ");
        System.out.println("2. Register: ");
        System.out.println("3. Exit: ");
        
        System.out.print("Choose an optoin: ");
        int option = lp.nextInt();
        lp.nextLine();
        
        Validation ud = new Validation();

       switch(option){
         
             case 1:
                 ud.login();
                 break;
             
             case 2:
                 ud.Register();
                 break;
 

       }
      
}

public static void AdminDashboard(){
    
         System.out.println("------------------------------------");
         System.out.println("-- WELCOME TO THE ADMIM DASHBOARD --");
         System.out.println("------------------------------------");
         
         System.out.println("1. Add User");
         System.out.println("2. View User");
         System.out.println("3. Update User");
         System.out.println("4. Delete User");
         System.out.println("5. Approve");
         System.out.println("6. Add books");
         System.out.println("7. Exit");

         System.out.print("\nChoose an option: ");
         int option = lp.nextInt();
         lp.nextLine();

        AdminDashboard ud = new AdminDashboard();

       switch(option){
       
       case 1:
          ud.adduser();
          break;
        case 2:
          ud.viewuser();
           break;
        case 3:
            ud.viewuser();
            ud.updateuser();
            break;
        case 4:
            ud.viewuser();
            ud.deleteuser();
            break;
         case 5:
           ud.viewuser();
           ud.approveuser();
            break;
         case 6:
             ud.addbooks();
           break;
         case 7:
             main(null);
             break;
        
       
       }
      

}

  public static void Staff(){
      
      System.out.println("-------------------------");
      System.out.println("---- Staff Dashboard ----");
      System.out.println("-------------------------");
     
       System.out.println("1. Add Books ");
       System.out.println("2. View Storage Books" );
       System.out.println("3. Delete Books ");
       System.out.println("4. View return books ");
       System.out.println("5. Exit ");
       
       System.out.println("Choose an option ");
       int option = Main.lp.nextInt();
       lp.nextLine();
       
       Staff st = new Staff();
       
       switch (option){
         
         case 1:
             Staff.ADDBOOKS();
             break;
         case 2:
             Staff.VIEW();
             break;
     
     
     
     
     }
  
  
  }
  
  public static void User() {

      System.out.println("--------------------------"); 
      System.out.println("----- USER_DASHBAORD -----");
      System.out.println("--------------------------"); 
      
      
      System.out.println("1. Borrow Book ");
      System.out.println("2. Return Book ");
      System.out.println("3. Exit ");
      
      System.out.println("Choose an option");
      int option = Main.lp.nextInt();
      lp.nextLine();
      

       
       switch (option){
         
         case 1:
            
             break;
         case 2:
   
             break;
     
     
     
     
     }
       
   }

}