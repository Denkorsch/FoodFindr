import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.sql.*;
import java.util.Scanner;

public class Connector {
    public Connection con;
    public String result;
    ResultSet rs;
    String user;
    String pass;
    public Connector() 
    {
           
        result = "";
        File file = new File("config.txt");
        Scanner sc;

        try 
        {
            sc = new Scanner(file);
            while(sc.hasNext()) 
            {
                if(user == null)
                {
                    user = ""+ sc.nextLine();
                    System.out.println(user);
                }
                else if(pass == null)
                {
                    pass = "" + sc.nextLine();
                    System.out.println(pass);
                }
            } 
        }
            catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    //function to establish connection to db and execute query and return its result set
    public ResultSet exec(String quer)
    {
        try
        {
        
        System.out.println("");

            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/recipe_db", user, pass);
            Statement stmt = con.createStatement();
            rs = stmt.executeQuery(quer);
            //con.close();
            //stmt.closeOnCompletion();
        }
        catch(Exception e)
        {
            System.out.println(e);
        }
        return rs;
        
    }

    public Connection getConnec()
    {
        return con;
    }
}
