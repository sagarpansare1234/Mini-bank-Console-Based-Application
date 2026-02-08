package services;

import java.sql.Connection;
import java.sql.SQLException;

import dao.DaoCode;
import model.Profile;
import util.Connectiondb;

public class SuspendBlockService {
	
	  public static Profile get_status(Connection conn, Long acc_no) throws SQLException {
	        DaoCode d = new DaoCode();
	        return d.get_profile_by_id(conn, acc_no);
	    }

	    /* ************************ SUSPEND ACCOUNT ************************ */
	    public String suspend_acc(Long acc_no) throws SQLException {

	        Connection conn = null;
	        DaoCode d = new DaoCode();

	        try {
	            conn = Connectiondb.getConnection();
	            conn.setAutoCommit(false);

	            Profile p = get_status(conn, acc_no);

	            if (p == null) {
	                return "This Account is not in our bank";
	            }

	            if ("ACTIVE".equals(p.getAcc_status())) {

	                int changes = d.update_acc_status(conn, acc_no, "SUSPENDED");

	                if (changes > 0) {
	                    conn.commit();
	                    return "The Account whose Account Number is " + acc_no + " has been Suspended!";
	                } else {
	                    conn.rollback();
	                    return "Due to some technical issue, account cannot be suspended.";
	                }

	            } else if ("SUSPENDED".equals(p.getAcc_status())) {
	                return "This Account is already suspended";

	            } else if ("CLOSED".equals(p.getAcc_status())) {
	                return "This Account is already closed";

	            } else {
	                return "Invalid account status";
	            }

	        } catch (Exception e) {
	            if (conn != null) conn.rollback();
	            return "Technical error";
	        } finally {
	            if (conn != null) conn.close();
	        }
	    }

	    /* ************************ DELETE (CLOSE) ACCOUNT ************************ */
	    public String delete_acc(Long acc_no) throws SQLException {

	        Connection conn = null;
	        DaoCode d = new DaoCode();

	        try {
	            conn = Connectiondb.getConnection();
	            conn.setAutoCommit(false);

	            Profile p = get_status(conn, acc_no);

	            if (p == null) {
	                return "This Account is not in our bank";
	            }

	            if ("ACTIVE".equals(p.getAcc_status()) || "SUSPENDED".equals(p.getAcc_status())) {

	                int changes = d.update_acc_status(conn, acc_no, "CLOSED");

	                if (changes > 0) {
	                    conn.commit();
	                    return "The Account whose Account Number is " + acc_no + " has been Closed!";
	                } else {
	                    conn.rollback();
	                    return "Due to some technical issue, account cannot be closed.";
	                }

	            } else if ("CLOSED".equals(p.getAcc_status())) {
	                return "This Account is already closed";

	            } else {
	                return "Invalid account status";
	            }

	        } catch (Exception e) {
	            if (conn != null) conn.rollback();
	            return "Technical error";
	        } finally {
	            if (conn != null) conn.close();
	        }
	    }

	    /* ************************ ACTIVATE ACCOUNT ************************ */
	    public String active_acc(Long acc_no) throws SQLException {

	        Connection conn = null;
	        DaoCode d = new DaoCode();

	        try {
	            conn = Connectiondb.getConnection();
	            conn.setAutoCommit(false);

	            Profile p = get_status(conn, acc_no);

	            if (p == null) {
	                return "This Account is not in our bank";
	            }

	            if ("ACTIVE".equals(p.getAcc_status())) {
	                return "Your Account is already Active";

	            } else if ("SUSPENDED".equals(p.getAcc_status())) {

	                int changes = d.update_acc_status(conn, acc_no, "ACTIVE");

	                if (changes > 0) {
	                    conn.commit();
	                    return "The Account whose Account Number is " + acc_no + " has been Activated!";
	                } else {
	                    conn.rollback();
	                    return "Due to some technical issue, account cannot be activated.";
	                }

	            } else if ("CLOSED".equals(p.getAcc_status())) {
	                return "This Account is closed and cannot be activated";

	            } else {
	                return "Invalid account status";
	            }

	        } catch (Exception e) {
	            if (conn != null) conn.rollback();
	            return "Technical error";
	        } finally {
	            if (conn != null) conn.close();
	        }
	    }
}
