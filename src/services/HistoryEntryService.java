package services;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import dao.DaoCode;
import model.Profile;
import util.Colors;
import util.Connectiondb;

public class HistoryEntryService {
	public int balance_update_profile(Connection conn,Long acc_no,BigDecimal balance) throws SQLException {
		DaoCode d = new DaoCode();
		int changes = d.update_balance(conn, acc_no, balance);
		return changes;
	}

	
	public String ledger_entry(Long acc_no, String act_type, BigDecimal amount) throws SQLException {

	    Connection conn = null;
	    DaoCode d = new DaoCode();

	    try {
	        conn = Connectiondb.getConnection();
	        conn.setAutoCommit(false);

	        // Validation
	        Profile pf = d.get_accno_status_balance(conn, acc_no);
	        if (pf == null) {
	            conn.rollback();
	            return Colors.YELLOW
	                    + "Your account is not in our bank. Please create a new account!"
	                    + Colors.RESET;
	        }

	        switch (pf.getAcc_status()) {

	            case "ACTIVE":

	                if (amount.compareTo(BigDecimal.ZERO) > 0) {

	                    /**************** WITHDRAW ****************/
	                    if ("WITHDRAW".equals(act_type)) {

	                        BigDecimal balance_after = pf.getBalance().subtract(amount);

	                        if (balance_after.compareTo(BigDecimal.ONE) >= 0) {

	                            int res = d.insert_activity(conn, acc_no, act_type, amount, balance_after);

	                            if (res == 1) {

	                                int update_changes =
	                                        balance_update_profile(conn, acc_no, balance_after);

	                                if (update_changes <= 0) {
	                                    conn.rollback();
	                                    return Colors.YELLOW
	                                            + "Transaction failed. Balance update unsuccessful."
	                                            + Colors.RESET;
	                                }

	                                conn.commit();
	                                return Colors.GREEN
	                                        + "Rs." + amount + "/- is successfully withdrawn.\n"
	                                        + "Remaining Balance : " + balance_after
	                                        + Colors.RESET;

	                            } else {
	                                conn.rollback();
	                                return Colors.RED
	                                        + "Rs." + amount + "/- is not withdrawn due to technical issues."
	                                        + Colors.RESET;
	                            }

	                        } else {
	                            conn.rollback();
	                            return Colors.YELLOW
	                                    + "You should have a minimum balance of Rs.1 to maintain your account."
	                                    + Colors.RESET;
	                        }
	                    }

	                    /**************** DEPOSIT ****************/
	                    else if ("DEPOSIT".equals(act_type)) {

	                        BigDecimal balance_after = pf.getBalance().add(amount);
	                        int res = d.insert_activity(conn, acc_no, act_type, amount, balance_after);

	                        if (res == 1) {

	                            int update_changes =
	                                    balance_update_profile(conn, acc_no, balance_after);

	                            if (update_changes <= 0) {
	                                conn.rollback();
	                                return Colors.YELLOW
	                                        + "Transaction failed. Balance update unsuccessful."
	                                        + Colors.RESET;
	                            }

	                            conn.commit();
	                            return Colors.GREEN
	                                    + "Rs." + amount + "/- is successfully deposited.\n"
	                                    + "Remaining Balance : " + balance_after
	                                    + Colors.RESET;

	                        } else {
	                            conn.rollback();
	                            return Colors.RED
	                                    + "Rs." + amount + "/- is not deposited due to technical issues."
	                                    + Colors.RESET;
	                        }
	                    }

	                    else {
	                        return Colors.YELLOW
	                                + "Wrong activity type selected."
	                                + Colors.RESET;
	                    }
	                }

	                else {
	                    conn.rollback();
	                    return Colors.YELLOW
	                            + "Invalid amount. Negative or zero values are not allowed."
	                            + Colors.RESET;
	                }

	            case "SUSPENDED":
	                conn.rollback();
	                return Colors.YELLOW
	                        + "Your account is suspended. Please contact the manager."
	                        + Colors.RESET;

	            case "CLOSED":
	                conn.rollback();
	                return Colors.YELLOW
	                        + "Your account is closed. Please open a new account."
	                        + Colors.RESET;

	            default:
	                return Colors.RED
	                        + "Transaction could not be completed."
	                        + Colors.RESET;
	        }

	    } catch (Exception e) {
	        if (conn != null) {
	            conn.rollback();
	        }
	        e.printStackTrace();
	        return Colors.RED
	                + "Due to some technical issues, we can't proceed."
	                + Colors.RESET;
	    }
	    finally {
	        if (conn != null) {
	            conn.close();
	        }
	    }
	}

   
   /**
 * @throws SQLException **********************************************************************************************/
   public Profile data(Long acc_no) throws SQLException {
	   DaoCode d = new DaoCode();
	   Connection conn=null;
	   try {
		  conn=Connectiondb.getConnection(); 
		  conn.setAutoCommit(false);
		  Profile p = d.get_accno_status_balance(conn, acc_no);
		  return p;
		///////////////
	   }catch(Exception e){
		   if(conn!=null) {
			   e.printStackTrace();
			  
		   }
	   }finally {
		   conn.close();
	   }
	   return null;
   }
	   
   
   public int check_status_existence(Long acc_no) throws SQLException{
	   HistoryEntryService his = new HistoryEntryService();
	   
	
		  Profile p = his.data(acc_no);
		  if(p.getAcc_no()==acc_no && p.getAcc_status().equals("ACTIVE")) {
			  return 1;//You are good to enter
		  }else if(p.getAcc_no()==acc_no && p.getAcc_status().equals("SUSPENDED")) {
			  return 2;//Your account is suspended.
		  }else if(p.getAcc_no()==acc_no && p.getAcc_status().equals("CLOSED")) {
			  return 3;//Your account is closed.
		  }else if(p.getAcc_no()!=acc_no) {
			  
			  return 4; //Your account is not in our bank.
		  }else {
			  return 5;
		  }
		  /////////////////////
   }
   
   
   /********************************************************************************************/
   public int checkbalance(Long acc_no,BigDecimal amt)throws SQLException {
	   HistoryEntryService his = new HistoryEntryService();
	   Profile p = his.data(acc_no);
	
	   BigDecimal balance = p.getBalance();
	   if (p == null || p.getBalance() == null) {
	        return 5; // internal error / account not found
	    }
	   if(amt.compareTo(BigDecimal.ZERO) <= 0) {
		   return 1;
	   }else if(amt.compareTo(balance)>0) {
		   return 2;
	   }else if(amt.compareTo(BigDecimal.ZERO)>0&&amt.compareTo(balance)<0) {
			   return 3;
		   
	   }else if(amt.compareTo(balance)==0) {
		   return 6;
	   }
	   return 4;
      
	   /*1==> "Invalid amount. Amount must be greater than 0."
2==> You don't have enough balance.
3==> Success
4==> Some Internal errors occurred!*/
   } 
   /*********************************************************************************************/

   //Transaction code
   public String transfer_money(Long from_accno, Long to_accno, BigDecimal amt) throws SQLException {

	    DaoCode d = new DaoCode();
	    HistoryEntryService his = new HistoryEntryService();

	    int sender = his.check_status_existence(from_accno);
	    int receiver = his.check_status_existence(to_accno);

	    switch (sender) {

	        case 1:
	            switch (receiver) {

	                case 1:
	                    switch (his.checkbalance(from_accno, amt)) {

	                        case 1:
	                            return Colors.YELLOW
	                                    + "Invalid amount. Amount must be greater than 0."
	                                    + Colors.RESET;

	                        case 2:
	                            return Colors.YELLOW
	                                    + "Sender doesn't have enough balance."
	                                    + Colors.RESET;

	                        case 3:
	                            /************** MAIN LOGIC **************/
	                            Connection conn = Connectiondb.getConnection();
	                            conn.setAutoCommit(false);

	                            try {
	                                BigDecimal sender_balance =
	                                        (his.data(from_accno)).getBalance();
	                                BigDecimal receiver_balance =
	                                        (his.data(to_accno)).getBalance();

	                                BigDecimal after_balance_sender =
	                                        sender_balance.subtract(amt);
	                                BigDecimal after_balance_receiver =
	                                        receiver_balance.add(amt);

	                                if (after_balance_sender.compareTo(BigDecimal.ONE) >= 0) {

	                                    int update_sender =
	                                            d.update_balance(conn, from_accno, after_balance_sender);

	                                    if (update_sender > 0) {

	                                        int update_receiver =
	                                                d.update_balance(conn, to_accno, after_balance_receiver);

	                                        if (update_receiver > 0) {

	                                            int insert_count =
	                                                    d.insert_txn(conn, amt, from_accno, to_accno,
	                                                            after_balance_sender, after_balance_receiver);

	                                            if (insert_count > 0) {
	                                                conn.commit();
	                                                return Colors.GREEN
	                                                        + "Success! Money transferred successfully.\n"
	                                                        + "Sender Balance : " + after_balance_sender + "\n"
	                                                        + "Receiver Balance : " + after_balance_receiver
	                                                        + Colors.RESET;
	                                            } else {
	                                                conn.rollback();
	                                                return Colors.RED
	                                                        + "Due to some problem, transaction is not registered."
	                                                        + Colors.RESET;
	                                            }

	                                        } else {
	                                            conn.rollback();
	                                            return Colors.RED
	                                                    + "Due to some problem, receiver balance cannot be updated."
	                                                    + Colors.RESET;
	                                        }

	                                    } else {
	                                        conn.rollback();
	                                        return Colors.RED
	                                                + "Due to some problem, sender balance cannot be updated."
	                                                + Colors.RESET;
	                                    }
	                                }

	                            } catch (Exception e) {
	                                e.printStackTrace();
	                                return Colors.RED
	                                        + "Some internal error occurred during money transfer."
	                                        + Colors.RESET;
	                            } finally {
	                                conn.close();
	                            }

	                        case 4:
	                            return Colors.RED
	                                    + "Some internal errors occurred!"
	                                    + Colors.RESET;

	                        case 6:
	                            return Colors.YELLOW
	                                    + "You should have minimum balance to maintain your account i.e. Rs.1"
	                                    + Colors.RESET;
	                    }

	                case 2:
	                    return Colors.YELLOW
	                            + "Receiver's account is suspended."
	                            + Colors.RESET;

	                case 3:
	                    return Colors.YELLOW
	                            + "Receiver's account is closed."
	                            + Colors.RESET;

	                case 4:
	                    return Colors.YELLOW
	                            + "Receiver's account is not in our bank."
	                            + Colors.RESET;
	            }

	        case 2:
	            return Colors.YELLOW
	                    + "Sender's account is suspended."
	                    + Colors.RESET;

	        case 3:
	            return Colors.YELLOW
	                    + "Sender's account is closed."
	                    + Colors.RESET;

	        case 4:
	            return Colors.YELLOW
	                    + "Sender's account is not in our bank."
	                    + Colors.RESET;
	    }

	    return Colors.RED
	            + "Sorry, due to some technical issues this money transfer was not possible."
	            + Colors.RESET;
	}

}
