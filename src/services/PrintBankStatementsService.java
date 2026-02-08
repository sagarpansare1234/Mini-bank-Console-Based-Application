package services;

import java.io.FileOutputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import dao.DaoCode;
import model.Passbook;
import model.Profile;
import util.Colors;
import util.Connectiondb;

public class PrintBankStatementsService {
	
	DaoCode d = new DaoCode();
	     public int validation(Connection conn,Long acc_no) throws SQLException  {
	    	
	    	 Profile p = d.get_accno_status_balance(conn, acc_no);
	    	 if(p.getAcc_no()!=acc_no) {
	    		 return 1;
	    	 }else if(p.getAcc_status()=="CLOSED") {
	    		 return 2;
	    	 }else if(p.getAcc_no()==acc_no && (p.getAcc_status().equals("SUSPENDED")||p.getAcc_status().equals("ACTIVE"))){
	    		 return 3;
	    	 }else {return 4;}
	    	
	    	 //1 - > Your Account Doesn't exists in our bank
	    	 //2 - > Your Account is Closed.
	    	 //3 - > You can proceed
	    	 //4 - > Technical issues occurred!
	     }
	     public String export_statements(Long acc_no) throws SQLException {
	    	 Connection conn = null;
	    	 
	    		try {
        			conn=Connectiondb.getConnection();
        			Profile p = d.get_profile_by_id(conn, acc_no);
        			String p_name = p.getFname()+" "+p.getMname()+" "+p.getLname();
        			String status = p.getAcc_status();
        			/********************************Inserting List of transaction in a list*************************************/
        			List list = d.bank_statements(conn, acc_no);
        			ListIterator litr = list.listIterator();
        			/*****************************Export Logic********************************/
        			Workbook wb = new XSSFWorkbook();
        			Sheet sheet =wb.createSheet("Transaction");
        			// Row 0 – Account Holder Name
        			Row row0 = sheet.createRow(0);
        			Cell cell0 = row0.createCell(0);
        			cell0.setCellValue("Account Holder Name : "+ p_name);
        			sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 5));
        			// Row 1 – Account Number
        			Row row1 = sheet.createRow(1);
        			Cell cell1 = row1.createCell(0);
        			cell1.setCellValue("Account No : "+acc_no);
        			sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 5));
        			// Row 2 – Status
        			Row row3 = sheet.createRow(2);
        			Cell cell2 = row3.createCell(0);
        			cell2.setCellValue("Status : "+status);
        			sheet.addMergedRegion(new CellRangeAddress(2, 2, 0, 5));
        			//Style Design
        			CellStyle titleStyle = wb.createCellStyle();
        			Font titleFont = wb.createFont();
        			titleFont.setBold(true);
        			titleFont.setFontHeightInPoints((short) 12);
        			titleStyle.setFont(titleFont);

        			cell0.setCellStyle(titleStyle);
        			cell1.setCellStyle(titleStyle);
        			cell2.setCellStyle(titleStyle);
                    ////////////////////////////////////////Table Starts from here..
        			//header
        			Row header = sheet.createRow(4);
        			String headers[] = {"Date", "Time", "Particulars", "Debit", "Credit", "Balance"};
        			
        			//Style Design of header starts from  here
        			CellStyle headerStyle = wb.createCellStyle();
        			Font headerFont = wb.createFont();
        			headerFont.setBold(true);
        			headerStyle.setFont(headerFont);
        			headerStyle.setBorderBottom(BorderStyle.THIN);
        			headerStyle.setBorderTop(BorderStyle.THIN);
        			headerStyle.setBorderLeft(BorderStyle.THIN);
        			headerStyle.setBorderRight(BorderStyle.THIN);
        			headerStyle.setAlignment(HorizontalAlignment.CENTER);
        			//Style Design of header ends  here
        			for(int i=0;i<headers.length;i++) {
        				Cell cell = header.createCell(i);
        				cell.setCellValue(headers[i]);
        				cell.setCellStyle(headerStyle);
        				sheet.autoSizeColumn(i);
        			}
        			sheet.createFreezePane(0, 5); // freeze's  the rows above the table
        			//main data part starts here
        			int  row_index = 5;
        			//format for date
        			CellStyle dateStyle = wb.createCellStyle();
        			dateStyle.setDataFormat(
        			    wb.createDataFormat().getFormat("dd-MM-yyyy")
        			);
        			//format for balance or amount 
        			//////////////////////Rules or format for presenting balance or the money
    				CellStyle amountStyle = wb.createCellStyle();
    				DataFormat df = wb.createDataFormat();
    				amountStyle.setDataFormat(df.getFormat("₹ #,##0.00"));
        			while(litr.hasNext()) {
        				Passbook psb = (Passbook) litr.next();
        				//Data Row
        				Row row = sheet.createRow(row_index++);
        				//Date
        				Timestamp tms = psb.getDate();
        				LocalDate date = tms.toLocalDateTime().toLocalDate();
        				//Time
        				LocalTime time = tms.toLocalDateTime().toLocalTime();
        				
        				//inserting data and time and particulars
        				Cell cell_date = row.createCell(0);
        				cell_date.setCellValue(java.sql.Date.valueOf(date));
        				cell_date.setCellStyle(dateStyle);
        				
        				Cell cell_time = row.createCell(1);
        				cell_time.setCellValue(time.toString());
        				
           				Cell cell_parti = row.createCell(2);
        				cell_parti.setCellValue(psb.getParticulars());
        		
        				
        				//debit, credit, balance
        				if(psb.getDebit()!=null) {
        					Cell cell_debit = row.createCell(3);
        				    cell_debit.setCellValue(psb.getDebit().doubleValue());
        				    cell_debit.setCellStyle(amountStyle);
        				}
        				if(psb.getCredit()!=null) {
        					Cell cell_credit = row.createCell(4);
        				    cell_credit.setCellValue(psb.getCredit().doubleValue());
        				    cell_credit.setCellStyle(amountStyle);
        				}
      					Cell cell_balance = row.createCell(5);
    				    cell_balance.setCellValue(psb.getBalance_after().doubleValue());
    				    cell_balance.setCellStyle(amountStyle);
        				
        				
        				
        			}
        			for (int i = 0; i < 6; i++) {   // 6 columns
        			    sheet.autoSizeColumn(i);
        			}
        			//main data part ends here
                    ////////////////////////////////////////Table Ends here..
        			String file_path = "Transaction_History.xlsx";
        			FileOutputStream fos = new FileOutputStream(file_path);
        			wb.write(fos);
        			wb.close();
        			fos.close();
        			return Colors.GREEN+"Your File has been Created, Please Check!"+Colors.RESET;
        			/*************************************************************/
        		}catch(SQLException e) {
        			if(conn!=null) {
        				e.printStackTrace();
        				return Colors.RED+"DATABASE Problem's Occured! "+Colors.RESET;
        		
        			}}catch(Exception e) {
            			if(conn!=null) {
            				e.printStackTrace();
            				return Colors.RED+"Internal Problem's Occured! "+Colors.RESET;
            			
            			}
        		}finally {
        			if(conn!=null) {
            			conn.close();
            			}
        			
        		}
	    		return "Oops, Something went Wrong!";
	    	 
	     }
         public String print_statements(Long acc_no) throws SQLException {
        	 Connection conn = null;
	    	 
	    		try {
     			conn=Connectiondb.getConnection();
        	 int choice = validation(conn,acc_no);
        	 
        	 switch(choice) {
        	 case 1:
        		 return Colors.YELLOW+"Your Account Doesn't exists in our bank"+Colors.RESET;
        	 case 2:
        		 return Colors.YELLOW+"Your Account is Closed"+Colors.RESET;
        	 case 3:
        /**********************************************************************/
        		 return export_statements(acc_no);
        		 
       /**********************************************************************/
        	 case 4:
        		 return Colors.RED+"Some, Technical error's has occurred!"+Colors.RESET;
        	 }
	    		}catch(SQLException e) {
        			if(conn!=null) {
        				e.printStackTrace();
        				return Colors.RED+"Internal Problem's Occured! "+Colors.RESET;
        		
        			}}catch(Exception e) {
            			if(conn!=null) {
            				e.printStackTrace();
            				return Colors.RED+"Internal Problem's Occured! "+Colors.RESET;
            			
            			}
        		}finally {
        			if(conn!=null) {
            			conn.close();
            			}
      
         }
	    		return Colors.RED+"Somethng Went wrong!"+Colors.RESET;

}
}