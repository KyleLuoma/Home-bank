/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;

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
    
    private String userAccount = "";
        
    public Account (int lastAccountID, int holderID, int managerID, String userAccount) {
        
        this.ID = lastAccountID += 1;
        
        this.holderID = holderID;
        
        this.managerID = managerID;
        
        this.userAccount = userAccount;
        
        dateCreated = new Date(new java.util.Date().getTime());
        
        System.out.println("Account created on: " + dateCreated.toString());
        
    }
    
    public int getID() {
        
        return this.ID;
        
    }
    
    public String getPutQuery() {
        //generates a SQL instruction to insert a new account into the database.
        
        String putAccount 
                = "INSERT INTO " + userAccount + ".ACCOUNTS ("                
                    + "\"ID\" , "
                    + "\"HOLDERID\" , "
                    + "\"MGRID\" , "
                    + "\"BALANCE\" , "
                    + "\"DATECREATED\""
                    + ")"                
                + " VALUES ( "                
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
