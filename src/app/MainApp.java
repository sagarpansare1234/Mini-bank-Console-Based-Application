package app;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.ListIterator;
import java.util.Scanner;




import dao.DaoCode;
import model.Passbook;
import services.AccountCreationService;
import services.CheckBalanceService;
import services.HistoryEntryService;
import services.PrintBankStatementsService;
import util.Colors;
import util.Connectiondb;

public class MainApp {
	Scanner sc = new Scanner(System.in);
    public void main_layer() throws SQLException {

    

    	System.out.println(
    	        "Press 1 to Add Account\n" +
    	        "Press 2 to Deposit Money\n" +
    	        "Press 3 to Withdraw Money\n" +
    	        "Press 4 to Check Balance\n" +
    	        "Press 5 to Transfer Money\n" +
    	        "Press 6 to Export Bank Statements\n" +
    	        "Press 7 to Exit"
    	);

    	System.out.print("Enter your choice: ");
    	int choice = sc.nextInt();

    	switch (choice) {
        case 1:
            sub_layer_add_account();
            break;

        case 2:
            sub_layer_deposit_money();
            break;

        case 3:
            sub_layer_withdraw_money();
            break;

        case 4:
            sub_layer_check_balance();
            break;

        case 5:
            sub_layer_transfer_money();
            break;

        case 6:
            sub_layer_print();
            break;

        case 7:
            System.out.println("Thank you for using the Banking System.");
            System.exit(0);
            break;

        default:
            System.out.println("Invalid choice! Please select between 1 to 7.");
    }


      
    }
	/**
	 * @throws SQLException *************************************************************************************************************************************/
	private void sub_layer_print() throws SQLException {
		System.out.println("Printing Feature Coming Soon");
		PrintBankStatementsService pbss = new PrintBankStatementsService();
	     System.out.println("Enter the Account number:");
	        Long acc_no = sc.nextLong();
		System.out.println(pbss.print_statements(acc_no) );
		
	}
	/***************************************************************************************************************************************/
	private void sub_layer_transfer_money() throws SQLException {
     HistoryEntryService hes = new HistoryEntryService();
     System.out.println("Enter the sender's account number:");
        Long from_accno = sc.nextLong();
        
        System.out.println("Enter the receiver's account number:");   
        Long to_accno = sc.nextLong();
        
       
    	BigDecimal amount;
		while(true) {

			System.out.println("ENTER THE AMOUNT");
			amount = sc.nextBigDecimal();
			if(amount.compareTo(BigDecimal.ZERO)<=0) {
				System.out.println(Colors.YELLOW+"Invalid Amount..."+Colors.RESET);
			}else {
				break;
			}
		}
		System.out.println(hes.transfer_money(from_accno, to_accno, amount));
	}
	/**
	 * @throws SQLException *************************************************************************************************************************************/
	private void sub_layer_check_balance() throws SQLException {
	    HistoryEntryService hes = new HistoryEntryService();
	     System.out.println("Enter the account number:");
	        Long acc_no = sc.nextLong();
	        CheckBalanceService cb = new  CheckBalanceService();
	        try {
	            BigDecimal balance = cb.check_balance(acc_no);
	            System.out.println(Colors.GREEN+"Available Balance: ₹ " + balance+Colors.RESET);
	        } catch (IllegalStateException e) {
	            System.out.println(e.getMessage());
	        }

		
	}
	/**
	 * @throws SQLException *************************************************************************************************************************************/
	private void sub_layer_withdraw_money() throws SQLException {
	    HistoryEntryService hes = new HistoryEntryService();
	     System.out.println("Enter the account number:");
	        Long acc_no = sc.nextLong();
	    	BigDecimal amount;
			while(true) {

				System.out.println("ENTER THE AMOUNT");
				amount = sc.nextBigDecimal();
				if(amount.compareTo(BigDecimal.ZERO)<=0) {
					System.out.println(Colors.YELLOW+"Invalid Amount..."+Colors.RESET);
				}else {
					break;
				}
			} 
	      System.out.println( hes.ledger_entry(acc_no,"WITHDRAW",  amount));  
		
	}
	/***************************************************************************************************************************************/
	private void sub_layer_deposit_money() throws SQLException{
	    HistoryEntryService hes = new HistoryEntryService();
	     System.out.println("Enter the account number:");
	        Long acc_no = sc.nextLong();
	    	BigDecimal amount;
			while(true) {

				System.out.println("ENTER THE AMOUNT");
				amount = sc.nextBigDecimal();
				if(amount.compareTo(BigDecimal.ZERO)<=0) {
					System.out.println(Colors.YELLOW+"Invalid Amount..."+Colors.RESET);
				}else {
					break;
				}
			} 
	      System.out.println( hes.ledger_entry(acc_no,"DEPOSIT",  amount)); 
		
	}
	/***************************************************************************************************************************************/
	private void sub_layer_add_account() throws SQLException {
		AccountCreationService ac = new AccountCreationService();
		Scanner sc = new Scanner(System.in);
		System.out.println("******CREATION OF ACCOUNT STARTED*******");
		System.out.println("ENTER THE FIRST NAME");
		String fname= sc.next();
		
		System.out.println("ENTER THE MIDDLE NAME");
		String mname = sc.next();

		System.out.println("ENTER THE LAST NAME");
		String lname = sc.next();

		sc.nextLine(); 

		System.out.println("ENTER THE ADDRESS");
		String address = sc.nextLine();
		String email;
        while(true) {
    		System.out.println("ENTER THE EMAIL");
    		email = sc.nextLine().trim();
    		
        	if(email.contains(".")&&email.contains("@")&&!email.contains(" ")) {
        		break;
        	}else {
        		System.out.println(Colors.YELLOW+"Invalid Email..."+" |Please Try Again"+Colors.RESET);
        	}
        }


		String mobile;
		while(true) {

			System.out.println("ENTER THE MOBILE NUMBER");
			mobile = sc.nextLine();
			if(mobile.length()==10) {
				break;
			}else {
				System.out.println(Colors.YELLOW+"Invalid Mobile Number..."+" |Please Try Again"+Colors.RESET);
			}
			
		}
        
		BigDecimal balance;
		while (true) {
		    System.out.println("ENTER THE INITIAL BALANCE");

		    try {
		        balance = sc.nextBigDecimal();

		        if (balance.compareTo(BigDecimal.ZERO) >= 0) {
		            break;
		        } else {
		            System.out.println(
		                Colors.YELLOW + "Invalid amount | Please try again" + Colors.RESET
		            );
		        }

		    } catch (InputMismatchException e) {
		        System.out.println(
		            Colors.YELLOW + "Invalid input | Please enter numbers only" + Colors.RESET
		        );
		        sc.next(); // 🔴 VERY IMPORTANT: clear wrong input
		    }
		}
		
        System.out.println(ac.account_creation(fname, mname, lname, address, email, mobile, balance));
    	System.out.println("******CREATION OF ACCOUNT ENDS*******");
	}
	public static void main(String[] args) throws SQLException, IOException{
	
    MainApp app = new MainApp();
    
    while(true) {
    	app.main_layer();
    }
   
  

	}

}
