/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;

/**
 *
 * @author Administrator
 */
public class Transaction {
    
    private double transactionAmount;
    
    private int creditAccount;
    
    private int debitAccount;
    
    private int fromUser;
    
    private int toUser;
    
    private int transactionID;
    
    private String schema;   
    
    public Transaction ( BankQuery query, int creditAcccount, int debitAccount,
                         int fromUser, int toUser, double transactionAmount,
                         String schema) {
        
        this.transactionID = query.getHighestID(schema, "TRANSACTIONS") + 1;
        
        this.creditAccount = creditAcccount;
        
        this.debitAccount = debitAccount;
        
        this.fromUser = fromUser; 
        
        this.toUser = toUser; 
        
        this.transactionAmount = transactionAmount;
        
        this.schema = schema;
        
    }
    
    public int getID() {
        
        return this.transactionID;
        
    }
    
    public String getPutQuery() {
        //generates a SQL instruction to insert transaction data into the database.
        
        String putTransaction 
                = "INSERT INTO " + schema + ".TRANSACTIONS ("                
                    + "\"ID\" , "
                    + "\"CREDITACCOUNTID\" , "
                    + "\"DEBITACCOUNTID\" , "
                    + "\"AMOUNT\") "                
                + "VALUES ( "                
                    + this.transactionID + " , " 
                    + this.creditAccount + " , "
                    + this.debitAccount + " , "
                    + this.transactionAmount 
                    + ")";
        
        return putTransaction;
        
    }

}
