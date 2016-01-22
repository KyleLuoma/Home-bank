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
    
    private String userName = "";
    
    private ResultSet transactionList = null;
            
    
    public Account (BankQuery query, int holderID, int managerID) {
        //Generates a new account, with a unique ID.
        
        this.userName = query.lookupUserName(holderID);
        
        System.out.println("Account username = " + this.userName);
        
        this.ID = query.getHighestID(userName, "ACCOUNTS") + 1;
        
        this.holderID = holderID;
        
        this.managerID = managerID;
        
        dateCreated = new Date(new java.util.Date().getTime());
        
        System.out.println("Account created on: " + dateCreated.toString());
        
    }
    
    public Account (int accountID, int holderID, String userName, int managerID,
            double balance, Date dateCreated) {
        
        this.ID = accountID;
        
        this.holderID = holderID;
        
        this.userName = userName;
        
        this.managerID = managerID;
        
        this.dateCreated = dateCreated;
        
        this.balance = balance;
        
    }
    
    public Account (int accountID, ResultSet accountInformation, BankQuery query) {
        //Retrieves an existing account from the database.
        
        try {
            
            accountInformation.next();
                    
            this.ID = accountID;
                                    
            this.holderID = accountInformation.getInt(2);
            
            this.userName = query.lookupUserName(holderID);
            
            this.managerID = accountInformation.getInt(3);
            
            this.balance = accountInformation.getDouble(4);
            
            this.dateCreated = accountInformation.getDate(5);
            
        } catch (SQLException e) {
            
            System.out.println("No accounts found!!!");
            
            System.out.println(e);
            
        }
        
        
        
    }
    
    public int getID() {
        
        return this.ID;
        
    }
    
    public String getUserName() {
        
        return this.userName;
        
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
                = "INSERT INTO " + userName + ".ACCOUNTS ("                
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
