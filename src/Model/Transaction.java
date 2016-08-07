/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;
import java.sql.Date;
import java.sql.Time;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;

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
    
    private SimpleDoubleProperty transactionAmountProperty;
    private SimpleIntegerProperty creditAccountProperty;
    private SimpleIntegerProperty debitAccountProperty;
    private SimpleIntegerProperty transactionIDProperty;
    private SimpleObjectProperty<Date> transactionDateProperty;
    private SimpleObjectProperty<Time> transactionTimeProperty;
    
    public Transaction ( BankQuery query, int creditAcccount, int debitAccount,
                         double transactionAmount, String schema) {
        //Create a new transaction
        
        this.transactionID = query.getHighestID("TRANSACTIONS") + 1;        
        this.creditAccount = creditAcccount;        
        this.debitAccount = debitAccount;        
        this.transactionAmount = transactionAmount;       
        this.transactionDate = new Date(new java.util.Date().getTime());        
        this.transactionTime = new Time(new java.util.Date().getTime());        
        this.schema = schema;
        
    }
    
    public Transaction ( int transID, int creditAccount, int debitAccount, 
                        double transactionAmount, Date transactionDate, 
                        Time transactionTime) {
        //Recall an existing transaction from the database
        
        this.transactionID = transID;
        this.transactionIDProperty = new SimpleIntegerProperty(transactionID);
        this.creditAccount = creditAccount;
        this.creditAccountProperty = new SimpleIntegerProperty(creditAccount);
        this.debitAccount = debitAccount;
        this.debitAccountProperty = new SimpleIntegerProperty(debitAccount);
        this.transactionAmount = transactionAmount;
        this.transactionAmountProperty = new SimpleDoubleProperty(transactionAmount);
        this.transactionDate = transactionDate;
        this.transactionDateProperty = new SimpleObjectProperty<Date>(transactionDate);
        this.transactionTime = transactionTime;
        this.transactionTimeProperty = new SimpleObjectProperty<Time>(transactionTime);
        
    }
    
    public int getID() {        
        return this.transactionID;        
    }
    
    public SimpleIntegerProperty transactionIDProperty() {
        //System.out.println("Returning transactionIDProperty from Transaction");
        return this.transactionIDProperty;
    }
    
    public int getCreditAccountID() {
        return this.creditAccount;
    }
    
    public SimpleIntegerProperty creditAccountProperty() {
        //System.out.println("Returning creditAccountProperty from Transaction");
        return this.creditAccountProperty;
    }
    
    public int getDebitAccountID() {
        return this.debitAccount;
    }
    
    public SimpleIntegerProperty debitAccountProperty() {
        //System.out.println("Returning debitAccountProperty from Transaction");
        return this.debitAccountProperty;
    }
    
    public Double getTransactionAmount() {
        return this.transactionAmount;
    }
    
    public SimpleDoubleProperty transactionAmountProperty() {
        //System.out.println("Returning transactionAmountProperty from Transaction");
        return this.transactionAmountProperty;
    }
    
    public Date getDate() {        
        return this.transactionDate;        
    }
    
    public SimpleObjectProperty<Date> transactionDateProperty() {
        //System.out.println("Returning transactionDateProperty from Transaction");
        return this.transactionDateProperty;
    }
    
    public Time getTime() {        
        return this.transactionTime;       
    }
    
    public SimpleObjectProperty<Time> transactionTimeProperty() {
        //System.out.println("Returning transactionTimeProperty from transaction");
        return this.transactionTimeProperty;
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
