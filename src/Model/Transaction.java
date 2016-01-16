/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Administrator
 */
public class Transaction {
    
    private double transactionAmount;
    
    private int fromAccount;
    
    private int toAccount;
    
    private int fromUser;
    
    private int toUser;
    
    private int transactionID;
    
    private String userAccount;
    
    
    public Transaction (int lastTransactionID, int fromAccount, int toAccount,
                         int fromUser, int toUser, double transactionAmount,
                         String userAccount) {
        
        this.transactionID = lastTransactionID + 1;
        
        this.fromAccount = fromAccount;
        
        this.toAccount = toAccount;
        
        this.fromUser = fromUser;
        
        this.toUser = toUser;
        
        this.transactionAmount = transactionAmount;
        
        this.userAccount = userAccount;
        
    }
    
    public int getID() {
        
        return this.transactionID;
        
    }
    
    public String getPutQuery() {
        //generates a SQL instruction to insert transaction data into the database.
        
        String putTransaction 
                = "INSERT INTO " + userAccount + ".TRANSACTIONS ("                
                    + "\"ID\" , "
                    + "\"FROMACCOUNTID\" , "
                    + "\"TOACCOUNTID\" , "
                    + "\"AMOUNT\") "                
                + "VALUES ( "                
                    + this.transactionID + " , " 
                    + this.fromAccount + " , "
                    + this.toAccount + " , "
                    + this.transactionAmount 
                    + ")";
        
        return putTransaction;
        
    }

}
