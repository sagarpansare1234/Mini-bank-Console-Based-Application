package services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;

import dao.DaoCode;
import model.Profile;
import util.Colors;
import util.Connectiondb;

public class AccountCreationService {
	    
	
         public  String account_creation(String fname, String mname, String lname, String address, String email, String mobile, BigDecimal balance) throws SQLException
         {
        	 DaoCode d = new DaoCode();
        	 Connection conn = null;
        	 try {
        		 conn = Connectiondb.getConnection();
        		 conn.setAutoCommit(false);
        		 
        		 Profile existing = d.get_id_mes(conn,mobile, email);
        		 if(existing!=null) {
        			 conn.rollback();
        			 return Colors.YELLOW
        				        + "Your account already exists with Account Number : "
        				        + existing.getAcc_no()
        				        + Colors.RESET;

        		 }
        		 
        		 int changes = d.insert_profile(conn, fname, mname, lname, address, email, mobile, balance);
        		 if(changes>0) {	 
        			 Profile p = d.get_id_mes(conn,mobile, email);
        			  conn.commit();
        			 //Deposit EntryAlso here in future
        			  
        			 int res = d.insert_activity(conn, p.getAcc_no(), "DEPOSIT", balance, balance);
        			 if(res>0) {
                     conn.commit();
                     return Colors.GREEN
                    	        + "Your account is successfully created with Account number: "
                    	        + p.getAcc_no()
                    	        + Colors.RESET;
        			 }
        		 }else {
        			 conn.rollback();
        			 return Colors.RED
        				        + "Account creation failed due to technical issues"
        				        + Colors.RESET;

        		 }
        		 
        		 
        	 }catch(Exception e) {
        		 if(conn!=null) conn.rollback();
        		 throw e;
        	 } 
        	finally {if(conn!=null) conn.close();} 
        	 return null;
         }
}
