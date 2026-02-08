package model;

import java.math.BigDecimal;
import java.sql.Timestamp;

public class Passbook {
    private Timestamp Date;
    private String type;
    private String particulars;
    private BigDecimal debit;
    private BigDecimal credit;
    private BigDecimal balance_after;
	public Passbook() {
		super();
	}
	public Passbook(Timestamp date, String type, String particulars, BigDecimal debit, BigDecimal credit,
			BigDecimal balance_after) {
		super();
		Date = date;
		this.type = type;
		this.particulars = particulars;
		this.debit = debit;
		this.credit = credit;
		this.balance_after = balance_after;
	}
	public Timestamp getDate() {
		return Date;
	}
	public void setDate(Timestamp date) {
		Date = date;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getParticulars() {
		return particulars;
	}
	public void setParticulars(String particulars) {
		this.particulars = particulars;
	}
	public BigDecimal getDebit() {
		return debit;
	}
	public void setDebit(BigDecimal debit) {
		this.debit = debit;
	}
	public BigDecimal getCredit() {
		return credit;
	}
	public void setCredit(BigDecimal credit) {
		this.credit = credit;
	}
	public BigDecimal getBalance_after() {
		return balance_after;
	}
	public void setBalance_after(BigDecimal balance_after) {
		this.balance_after = balance_after;
	}
	@Override
	public String toString() {
		return "Passbook [Date=" + Date + ", type=" + type + ", particulars=" + particulars + ", debit=" + debit
				+ ", credit=" + credit + ", balance_after=" + balance_after + "]";
	}
	
      
}
