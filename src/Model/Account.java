/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.ResultSet;
import DBManager.BankQuery;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author Administrator
 */
public class Account {
      
    private final int ID;    
    private final int holderID;
    private final int managerID;
    private double balance = 0;  
    private final Date dateCreated;    
    private final ResultSet transactionList = null;    
    private String schema = "";    
    private String accountName = "";    
    private String type = "";         
    private final SimpleStringProperty accountNameProperty;
    private final SimpleStringProperty typeProperty;
    private final SimpleDoubleProperty balanceProperty;
    
    public Account (BankQuery query, int holderID, int managerID, 
            String accountName, String type, String schema) {
        //Generates a new account, with a unique ID.
        
        this.ID = query.getHighestID("ACCOUNTS") + 1;
        this.holderID = holderID;        
        this.managerID = managerID;       
        dateCreated = new Date(new java.util.Date().getTime());       
        this.accountName = accountName;        
        this.type = type;       
        this.schema = schema;
        this.accountNameProperty = new SimpleStringProperty(accountName);
        this.typeProperty = new SimpleStringProperty(type);
        this.balanceProperty = new SimpleDoubleProperty(balance);
        
        System.out.println("Account created on: " + dateCreated.toString());
        
    }
    
    public Account (int accountID, int holderID, int managerID,
            double balance, Date dateCreated, String accountName, 
            String type, String schema) {
        //Retrieves account from database
        
        this.ID = accountID;        
        this.holderID = holderID;        
        this.managerID = managerID;        
        this.dateCreated = dateCreated;       
        this.balance = balance;        
        this.accountName = accountName;        
        this.type = type;        
        this.schema = schema;
        this.accountNameProperty = new SimpleStringProperty(accountName);
        this.typeProperty = new SimpleStringProperty(type);
        this.balanceProperty = new SimpleDoubleProperty(balance);
        
    }
    
       
    public int getID() {       
        return this.ID;      
    }
    
    public double getBalance() {
        //System.out.println("Returning only double value of account balance from"
        //        + "account.");
        return this.balanceProperty.get();        
    }
    
    public SimpleDoubleProperty balanceProperty() {
        //System.out.println("Returning balanceProperty from account.");
        return this.balanceProperty;
    }
    
    public Date getDateCreated() {        
        return this.dateCreated;        
    }
    
    public int getHolderID() {        
        return this.holderID;    
    }
    
    public int getManagerID() {       
        return this.managerID;        
    }
    
    public String getType() {       
        return this.typeProperty.get();        
    }
    
    public SimpleStringProperty accountNameProperty() {
        //System.out.println("Returning accountNameProperty from account.");
        return this.accountNameProperty;
    }
    
    public String getAccountName() {
        //System.out.println("Returning only accountName string from account.");
        return this.accountNameProperty.get();
    }
    
    public void setType(String type) {        
        typeProperty.set(type);        
    }
    
    public void setBalance(double newBalance) {       
        balanceProperty.set(newBalance);
        this.balance = newBalance;
    }
    
    
    
    public String getPutQuery() {
        //generates a SQL instruction to insert a new account into the database.
        
        String putAccount 
                = "INSERT INTO " + schema + ".ACCOUNTS ("                
                    + "\"ID\" , "
                    + "\"HOLDERID\" , "
                    + "\"MGRID\" , "
                    + "\"BALANCE\" , "
                    + "\"DATECREATED\" , "
                    + "\"ACCOUNTNAME\" , "
                    + "\"TYPE\""
                    + ")"                
                + " VALUES ("                
                    + this.ID + " , " 
                    + this.holderID + " , "
                    + this.managerID + " , "
                    + this.balance + " , "
                    + "\'" + this.dateCreated.toString() + "\'" + " , "
                    + "\'" + this.accountName + "\'" + " , "
                    + "\'" + this.type + "\'"
                    + ") ";
        
        System.out.println(putAccount);
        
        return putAccount;
        
    }
        
}
