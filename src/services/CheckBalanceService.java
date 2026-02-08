package services;

import java.math.BigDecimal;
import java.sql.SQLException;

import model.Profile;
import util.Colors;

public class CheckBalanceService  {
           public BigDecimal check_balance(Long acc_no) throws SQLException {
        	

        		    HistoryEntryService hes = new HistoryEntryService();
        		    int existence = hes.check_status_existence(acc_no);

        		    switch (existence) {

        		        case 1:
        	                  GetAllDataService data = new GetAllDataService();
        	                  Profile p = data.get_profile_by_id(acc_no);
        	                 return  p.getBalance();
        		            
        		        case 2:
        		            throw new IllegalStateException(
        		                Colors.YELLOW + "Your account is suspended. Please contact the bank." + Colors.RESET
        		            );

        		        case 3:
        		            throw new IllegalStateException(
        		                Colors.YELLOW + "Your account is closed." + Colors.RESET
        		            );

        		        case 4:
        		            throw new IllegalStateException(
        		                Colors.YELLOW + "Account number not found." + Colors.RESET
        		            );

        		        default:
        		            throw new IllegalStateException(  Colors.RED + "Something went wrong. Please try again." + Colors.RESET
        		            	    );
        		    }

        		    
        		

           }
}
