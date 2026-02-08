package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Txn {

   private  long t_id;
   private  BigDecimal amount;
   private  long from_accno;
   private  long to_accno;
    private Timestamp t_time;
    private BigDecimal from_balance_after;
    private BigDecimal to_balance_after;
    
    
    public Txn() {
    }

    //write
    public Txn(long from_accno, long to_accno, BigDecimal amount) {
        this.from_accno = from_accno;
        this.to_accno = to_accno;
        this.amount = amount;
    }
    
     //read
	public Txn(long t_id, BigDecimal amount, long from_accno, long to_accno, Timestamp t_time,
			BigDecimal from_balance_after, BigDecimal to_balance_after) {
		super();
		this.t_id = t_id;
		this.amount = amount;
		this.from_accno = from_accno;
		this.to_accno = to_accno;
		this.t_time = t_time;
		this.from_balance_after = from_balance_after;
		this.to_balance_after = to_balance_after;
	}



	public long getT_id() {
		return t_id;
	}

	public void setT_id(long t_id) {
		this.t_id = t_id;
	}

	public BigDecimal getAmount() {
		return amount;
	}

	public void setAmount(BigDecimal amount) {
		this.amount = amount;
	}

	public long getFrom_accno() {
		return from_accno;
	}

	public void setFrom_accno(long from_accno) {
		this.from_accno = from_accno;
	}

	public long getTo_accno() {
		return to_accno;
	}

	public void setTo_accno(long to_accno) {
		this.to_accno = to_accno;
	}

	public Timestamp getT_time() {
		return t_time;
	}

	public void setT_time(Timestamp t_time) {
		this.t_time = t_time;
	}

	public BigDecimal getFrom_balance_after() {
		return from_balance_after;
	}

	public void setFrom_balance_after(BigDecimal from_balance_after) {
		this.from_balance_after = from_balance_after;
	}

	public BigDecimal getTo_balance_after() {
		return to_balance_after;
	}

	public void setTo_balance_after(BigDecimal to_balance_after) {
		this.to_balance_after = to_balance_after;
	}
    
}