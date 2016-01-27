/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import DBManager.BankQuery;

/**
 *
 * @author Administrator
 */
public class Account {
      
    private int ID;
    
    private int holderID;
    
    private int managerID;
    
    private double balance = 1;
    
    private Date dateCreated;
    
    private ResultSet transactionList = null;
    
    private String schema = "";
            
    
    public Account (BankQuery query, int holderID, int managerID, String schema) {
        //Generates a new account, with a unique ID.
        
        this.ID = query.getHighestID(schema, "ACCOUNTS") + 1;
        
        this.holderID = holderID;
        
        this.managerID = managerID;
        
        dateCreated = new Date(new java.util.Date().getTime());
        
        this.schema = schema;
        
        System.out.println("Account created on: " + dateCreated.toString());
        
    }
    
    public Account (int accountID, int holderID, int managerID,
            double balance, Date dateCreated, String schema) {
        //Retrieves account from database
        
        this.ID = accountID;
        
        this.holderID = holderID;
        
        this.managerID = managerID;
        
        this.dateCreated = dateCreated;
        
        this.balance = balance;
        
        this.schema = schema;
        
    }
    
       
    public int getID() {
        
        return this.ID;
        
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
    
    
    
    public String getPutQuery() {
        //generates a SQL instruction to insert a new account into the database.
        
        String putAccount 
                = "INSERT INTO " + schema + ".ACCOUNTS ("                
                    + "\"ID\" , "
                    + "\"HOLDERID\" , "
                    + "\"MGRID\" , "
                    + "\"BALANCE\" , "
                    + "\"DATECREATED\""
                    + ")"                
                + " VALUES ("                
                    + this.ID + " , " 
                    + this.holderID + " , "
                    + this.managerID + " , "
                    + this.balance + " , "
                    + "\'" + this.dateCreated.toString() + "\'"
                    + ") ";
        
        System.out.println(putAccount);
        
        return putAccount;
        
    }
        
}
