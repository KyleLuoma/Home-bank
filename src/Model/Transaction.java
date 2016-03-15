/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;
import java.sql.Date;
import java.sql.Time;

/**
 *
 * @author Administrator
 */
public class Transaction {
    
    private double transactionAmount;
    
    private int creditAccount;
    
    private int debitAccount;
    
    private int transactionID;
    
    private Date transactionDate;
    
    private Time transactionTime;
    
    private String schema;   
    
    public Transaction ( BankQuery query, int creditAcccount, int debitAccount,
                         double transactionAmount, String schema) {
        
        this.transactionID = query.getHighestID("TRANSACTIONS") + 1;
        
        this.creditAccount = creditAcccount;
        
        this.debitAccount = debitAccount;
        
        this.transactionAmount = transactionAmount;
        
        this.transactionDate = new Date(new java.util.Date().getTime());
        
        this.transactionTime = new Time(new java.util.Date().getTime());
        
        this.schema = schema;
        
    }
    
    public int getID() {
        
        return this.transactionID;
        
    }
    
    public Date getDate() {
        
        return this.transactionDate;
        
    }
    
    public Time getTime() {
        
        return this.transactionTime;
        
    }
    
    public String getPutQuery() {
        //generates a SQL instruction to insert transaction data into the database.
        
        String putTransaction 
                = "INSERT INTO " + schema + ".TRANSACTIONS ("                
                    + "\"ID\" , "
                    + "\"CREDITACCOUNTID\" , "
                    + "\"DEBITACCOUNTID\" , "
                    + "\"AMOUNT\" , "
                    + "\"DATE\" , "
                    + "\"TIME\" "
                    + ") "                
                + "VALUES ( "                
                    + this.transactionID + " , " 
                    + this.creditAccount + " , "
                    + this.debitAccount + " , "
                    + this.transactionAmount + " , "
                    + "\'" + this.transactionDate.toString() + "\'" + " , "
                    + "\'" + this.transactionTime.toString() + "\'" 
                    + ")";
        
        System.out.println(putTransaction);
        
        return putTransaction;
        
    }

}
