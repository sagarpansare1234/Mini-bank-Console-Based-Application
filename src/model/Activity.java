package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Activity {
	   private long act_id;
	    private long acc_no;
	    private String act_type;   // WITHDRAW / DEPOSIT
	    private BigDecimal amount;
	    private Timestamp act_time;
	    private BigDecimal balance_after;
	    
	    public Activity() {}
	    //write
		public Activity(long acc_no, String act_type, BigDecimal amount) {
			super();
			this.acc_no = acc_no;
			this.act_type = act_type;
			this.amount = amount;
		}
       
		//read
		public Activity(long act_id, long acc_no, String act_type, BigDecimal amount, Timestamp act_time,
				BigDecimal balance_after) {
			super();
			this.act_id = act_id;
			this.acc_no = acc_no;
			this.act_type = act_type;
			this.amount = amount;
			this.act_time = act_time;
			this.balance_after = balance_after;
		}
		
		
		
		public long getAct_id() {
			return act_id;
		}
		public void setAct_id(long act_id) {
			this.act_id = act_id;
		}

		public long getAcc_no() {
			return acc_no;
		}

		public void setAcc_no(long acc_no) {
			this.acc_no = acc_no;
		}

		public String getAct_type() {
			return act_type;
		}

		public void setAct_type(String act_type) {
			this.act_type = act_type;
		}

		public BigDecimal getAmount() {
			return amount;
		}

		public void setAmount(BigDecimal amount) {
			this.amount = amount;
		}

		public Timestamp getAct_time() {
			return act_time;
		}

		public void setAct_time(Timestamp act_time) {
			this.act_time = act_time;
		}

		public BigDecimal getBalance_after() {
			return balance_after;
		}

		public void setBalance_after(BigDecimal balance_after) {
			this.balance_after = balance_after;
		}
	    
}
