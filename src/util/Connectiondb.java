package util;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connectiondb {
	 public Connectiondb() {}
    	 private static final String url="jdbc:mysql://localhost:3306/bank";
    	 private static final String uname ="root";
    	 private static final String pwd = "root";

    	 
    	 public static  Connection getConnection() throws SQLException{
    		 Connection con = DriverManager.getConnection(url,uname,pwd);
    		 return con;
    		 
    		 
    	 } 
      }

