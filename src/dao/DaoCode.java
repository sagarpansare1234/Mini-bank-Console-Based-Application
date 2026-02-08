package dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.Activity;
import model.Passbook;
import model.Profile;
import model.Txn;
import util.Connectiondb;

public class DaoCode {
	
	
	
	/*********Select  Queries*************/
    //Fetch data by ID's 
	
	//@@@Profile section@@@//
	//Validation for Account numnber and account status 
	public Profile get_accno_status_balance(Connection conn,long id) throws SQLException{
		Profile pf = null;
	
		String query = """
				select acc_no,acc_status,balance from profile where acc_no = ?""";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
            pf=new Profile();
           pf.setAcc_no(rs.getLong(1)); 
           pf.setAcc_status(rs.getString(2));
           pf.setBalance(rs.getBigDecimal(3));
		}
		rs.close();
		pst.close();
		
		return pf;
	}
	
	//Validation for email and mobile 
	public Profile get_id_mes(Connection conn,String mobile,String email) throws SQLException{
		Profile pf = null;
	
		String query = """
				select acc_no, first_name, middle_name, last_name,
				       address, email, mobile, balance, acc_status,
				       created_at, updated_at from profile where (mobile=? or email=?) and acc_status='ACTIVE' """;
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setString(1, mobile);
		pst.setString(2, email);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
			 pf=new Profile(rs.getLong(1),
	            		rs.getString(2),rs.getString(3),
	            		rs.getString(4),rs.getString(5),
	            		rs.getString(6), rs.getString(7),
	            		rs.getBigDecimal(8),
	            		rs.getString(9),
	            		rs.getTimestamp(10),rs.getTimestamp(11));
		}
		rs.close();
		pst.close();
		
		return pf;
	}
	
	public Profile get_profile_by_id(Connection conn,long id) throws SQLException{
		Profile pf = null;
		
		String query = """
				select acc_no, first_name, middle_name, last_name,
				       address, email, mobile, balance, acc_status,
				       created_at, updated_at
				from profile
				where acc_no = ?
				""";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		if(rs.next()) {
            pf=new Profile(rs.getLong(1),
            		rs.getString(2),rs.getString(3),
            		rs.getString(4),rs.getString(5),
            		rs.getString(6), rs.getString(7),
            		rs.getBigDecimal(8),
            		rs.getString(9),
            		rs.getTimestamp(10),rs.getTimestamp(11));
		}
		rs.close();
		pst.close();
	
		return pf;
	}
	//@@@Profile section@@@//
	
	//@@@Account_Activity section@@@//
	public List<Activity> get_activities_by_id(Connection conn,Long id) throws SQLException {
		
		List<Activity> l = new ArrayList<>();
     
       PreparedStatement pst = conn.prepareStatement("select act_id,acc_no,act_type,amount,act_time,balance_after from account_activity where acc_no=?;");
       pst.setLong(1,id);
    
        ResultSet rs = pst.executeQuery();
        Activity a =null;
        while(rs.next()) {
              a = new Activity(rs.getLong(1),rs.getLong(2),
            		  rs.getString(3),rs.getBigDecimal(4),
            		  rs.getTimestamp(5), rs.getBigDecimal(6));

        	l.add(a);
        }
		rs.close();
		pst.close();
		
		return l;
	}
	//@@@Account_Activity section@@@//
	
	//@@@Transaction section@@@//
	public List<Txn> get_transactions_by_id(Connection conn,Long id) throws SQLException{
		List<Txn> l = new ArrayList<>();
		//Connection conn = Connectiondb.getConnection();
		PreparedStatement pst = conn.prepareStatement("select t_id,amount,from_accno,to_accno,t_time,from_balance_after,to_balance_after from txn where from_accno=? OR to_accno=?;");
		pst.setLong(1, id);
		ResultSet rs = pst.executeQuery();
		Txn t = null;
		while(rs.next() ) {
			
			t = new Txn(rs.getLong(1),rs.getBigDecimal(2),
					rs.getLong(3),rs.getLong(4),
					rs.getTimestamp(5),
					rs.getBigDecimal(6),rs.getBigDecimal(7));
	
			l.add(t);
		}
		rs.close();
		pst.close();

		return l;
	}
	//@@@Transaction section@@@//
	
	/*********Insert  Queries*************/
	//Insert Query
	//@@@Profile section@@@//
	//Account Creation
	public int insert_profile(Connection conn,String fname, String mname, String lname, String address, String email, String mobile, BigDecimal balance)throws SQLException {
    
       String query="insert into profile(first_name, middle_name,last_name,address,email,mobile,balance) values(?,?,?,?,?,?,?)";
       PreparedStatement pst =  conn.prepareStatement(query);
       pst.setString(1, fname);
       pst.setString(2, mname);
       pst.setString(3, lname);
       pst.setString(4, address);
       pst.setString(5, email);
       pst.setString(6, mobile);
       pst.setBigDecimal(7, balance);
       int res = pst.executeUpdate();
		pst.close();
		
       
		return res;
		
	}
	
	
	//@@@Profile section@@@//
	
	
	//@@@Activity section@@@//
	//Activity Insert
	public int insert_activity(Connection conn,Long acc_no, String act_type, BigDecimal amount,BigDecimal balance_after) throws SQLException {
		//Connection conn = Connectiondb.getConnection();
		String query = "insert into account_activity(acc_no,act_type, amount, balance_after) values(?,?,?,?)";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setLong(1, acc_no);
		pst.setString(2, act_type);
		pst.setBigDecimal(3, amount);
		pst.setBigDecimal(4, balance_after);
		int res = pst.executeUpdate();		
		pst.close();
	
		return res;
	}
	//@@@Activity section@@@//
	
	//@@@Transaction section@@@//
	//Transaction Insert
	public int insert_txn( Connection conn,BigDecimal amount, Long from_accno, Long to_accno, BigDecimal from_balance_after,BigDecimal to_balance_after) throws SQLException {
		//Connection conn = Connectiondb.getConnection();
		String query = "insert into txn( amount,from_accno, to_accno, from_balance_after, to_balance_after) values(?,?,?,?,?)";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setBigDecimal(1, amount);
		pst.setLong(2, from_accno);
		pst.setLong(3, to_accno);
		pst.setBigDecimal(4, from_balance_after);
		pst.setBigDecimal(5, to_balance_after);
		int res = pst.executeUpdate();		
		pst.close();
	
		return res;
	}
	//@@@Transaction section@@@//
	/*********Update  Queries *************/
	//Update Query 
	//@@@Profile section@@@//
	public int update_balance(Connection conn, Long acc_no, BigDecimal balance) throws SQLException {
		String query ="update profile set balance=? where acc_no=?";
		PreparedStatement pst = conn.prepareStatement(query);
		pst.setBigDecimal(1, balance);
		pst.setLong(2, acc_no);
		int res = pst.executeUpdate();
		pst.close();
		return res;
	}
	
	public int update_acc_status(Connection conn, Long acc_no, String status) throws SQLException{
		String query  = "update profile set acc_status=? where acc_no=?";
		PreparedStatement pst = conn.prepareStatement(query);
		int res = pst.executeUpdate();
		pst.close();
		return res;
	}
	//@@@Profile section@@@//
	
	/******************Pass book Printing **************************/
	 public List bank_statements(Connection conn, Long acc_no) throws SQLException{
		 //Query for fetching bank statements
		 String query = "SELECT *\r\n"
		 		+ "FROM (\r\n"
		 		+ "    \r\n"
		 		+ "    SELECT\r\n"
		 		+ "        aa.act_time AS tx_date,\r\n"
		 		+ "       aa.act_type AS tx_type,\r\n"
		 		+ "        CASE\r\n"
		 		+ "            WHEN aa.act_type = 'DEPOSIT' THEN 'Cash Deposit'\r\n"
		 		+ "            WHEN aa.act_type = 'WITHDRAW' THEN 'Cash Withdrawal'\r\n"
		 		+ "        END AS particulars,\r\n"
		 		+ "        CASE WHEN aa.act_type = 'WITHDRAW' THEN aa.amount ELSE 0 END AS debit,\r\n"
		 		+ "        CASE WHEN aa.act_type = 'DEPOSIT' THEN aa.amount ELSE 0 END AS credit,\r\n"
		 		+ "        aa.balance_after\r\n"
		 		+ "    FROM account_activity aa\r\n"
		 		+ "    WHERE aa.acc_no = ?\r\n"
		 		+ "\r\n"
		 		+ "    UNION ALL\r\n"
		 		+ "\r\n"
		 		+ "   \r\n"
		 		+ "    SELECT\r\n"
		 		+ "        t.t_time AS tx_date,\r\n"
		 		+ "        'TRANSFER' AS tx_type,\r\n"
		 		+ "        CASE\r\n"
		 		+ "            WHEN t.from_accno = ?\r\n"
		 		+ "            THEN CONCAT(\r\n"
		 		+ "                'Transfer to ',\r\n"
		 		+ "                p_to.first_name, ' ', p_to.last_name,\r\n"
		 		+ "                ' (A/C ', p_to.acc_no, ')'\r\n"
		 		+ "            )\r\n"
		 		+ "            ELSE CONCAT(\r\n"
		 		+ "                'Transfer from ',\r\n"
		 		+ "                p_from.first_name, ' ', p_from.last_name,\r\n"
		 		+ "                ' (A/C ', p_from.acc_no, ')'\r\n"
		 		+ "            )\r\n"
		 		+ "        END AS particulars,\r\n"
		 		+ "        CASE WHEN t.from_accno = ? THEN t.amount ELSE 0 END AS debit,\r\n"
		 		+ "        CASE WHEN t.to_accno = ? THEN t.amount ELSE 0 END AS credit,\r\n"
		 		+ "        CASE\r\n"
		 		+ "            WHEN t.from_accno = ? THEN t.from_balance_after\r\n"
		 		+ "            ELSE t.to_balance_after\r\n"
		 		+ "        END AS balance_after\r\n"
		 		+ "    FROM txn t\r\n"
		 		+ "    JOIN profile p_from ON t.from_accno = p_from.acc_no\r\n"
		 		+ "    JOIN profile p_to   ON t.to_accno   = p_to.acc_no\r\n"
		 		+ "    WHERE t.from_accno = ? OR t.to_accno = ?\r\n"
		 		+ ") passbook\r\n"
		 		+ "ORDER BY tx_date DESC;\r\n"
		 		+ "";
		 
		 
		 PreparedStatement pst = conn.prepareStatement(query);
		 pst.setLong(1, acc_no);
		 pst.setLong(2, acc_no);
		 pst.setLong(3, acc_no);
		 pst.setLong(4, acc_no);
		 pst.setLong(5, acc_no);
		 pst.setLong(6, acc_no);
		 pst.setLong(7, acc_no);
		 ResultSet rs = pst.executeQuery();
		 List<Passbook> al = new ArrayList<>();
		 Passbook p = null;
		 while(rs.next()) {
			 p = new Passbook(rs.getTimestamp(1),rs.getString(2),rs.getString(3),rs.getBigDecimal(4),rs.getBigDecimal(5),rs.getBigDecimal(6));
			 al.add(p);
			 
		 }
		 return al;
	 }
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	 
	
}
