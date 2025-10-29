
package Staff;
 
import Config.Config;
import Main.Main;

public class Staff {
    
    public static void ADDBOOKS(){
   
        System.out.println("Enter Books Title: ");
        String btitle = Main.lp.nextLine();
        
        System.out.println("Enter Books Author: ");
        String bauhtor = Main.lp.nextLine();
        
        System.out.println("Enter Books Publisher: ");
        String bpublisher = Main.lp.nextLine();
        
        System.out.println("Enter Books Published");
        String bpub = Main.lp.nextLine();
  
  }
    
    public static void VIEW(){
        
        Config con = new Config();
        String UserQuery = "SELECT * FROM tbl_storagebook";
        String[] UserHeaders = {"ID","book title", "book auhtor", "book publisher", "book published" };
        String[] UserColumns = {"book_id","b_title", "b_author", "b_publisher", "b_yearpublished"};
        
        con.viewRecords(UserQuery, UserHeaders, UserColumns);
    
    
    }
    
}
