
import java.sql.Connection;
import java.sql.*;


public class ConnectionApplication {
    Connection con;
    ConnectionApplication(){
        try{
        String ConnectingURL = "jdbc:mysql://localhost:3306/test";
        String dbuser = "root";
        String dbpass = "Mohit07@";
        
        Class.forName("com.mysql.cj.jdbc.Driver");
        con = DriverManager.getConnection(ConnectingURL, dbuser, dbpass);
        System.out.println("Connection is Success");
        }
        catch(Exception e){
            System.out.println(e);
        }
    }
    
    public static void main(String[]args){
        ConnectionApplication obj = new ConnectionApplication();
    }
}
