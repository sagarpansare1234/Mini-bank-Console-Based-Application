package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Profile {
      private long acc_no;
      private String fname;
      private String mname;
      private String lname;
      private String address;
      private String email;
      private String mobile;
      private BigDecimal balance;
      private String acc_status;
      private Timestamp created_at;
      private Timestamp updated_at;
      
      public Profile () {}
       //write 
  	public Profile(String fname, String mname, String lname, String address, String email, String mobile,
			BigDecimal balance) {
		super();
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
		this.balance = balance;
	}
    //read 
	public Profile(long acc_no, String fname, String mname, String lname, String address, String email, String mobile,
			BigDecimal balance, String acc_status, Timestamp created_at, Timestamp updated_at) {
		super();
		this.acc_no = acc_no;
		this.fname = fname;
		this.mname = mname;
		this.lname = lname;
		this.address = address;
		this.email = email;
		this.mobile = mobile;
		this.balance = balance;
		this.acc_status = acc_status;
		this.created_at = created_at;
		this.updated_at = updated_at;
	}
	
    
    
	public long getAcc_no() {
		return acc_no;
	}
	public void setAcc_no(long acc_no) {
		this.acc_no = acc_no;
	}

	public String getFname() {
		return fname;
	}

	public void setFname(String fname) {
		this.fname = fname;
	}

	public String getMname() {
		return mname;
	}

	public void setMname(String mname) {
		this.mname = mname;
	}

	public String getLname() {
		return lname;
	}

	public void setLname(String lname) {
		this.lname = lname;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public BigDecimal getBalance() {
		return balance;
	}

	public void setBalance(BigDecimal balance) {
		this.balance = balance;
	}

	public String getAcc_status() {
		return acc_status;
	}

	public void setAcc_status(String acc_status) {
		this.acc_status = acc_status;
	}
	@Override
	public String toString() {
		return "Profile [acc_no=" + acc_no + ", fname=" + fname + ", mname=" + mname + ", lname=" + lname + ", address="
				+ address + ", email=" + email + ", mobile=" + mobile + ", balance=" + balance + ", acc_status="
				+ acc_status + ", created_at=" + created_at + ", updated_at=" + updated_at + "]";
	}
      
      
}
