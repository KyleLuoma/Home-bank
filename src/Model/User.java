/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class User {
    
    private int ID;
    
    private String userName;
    
    private String lastName;
    
    private String firstName;
    
    private String role;
    
    private int level;
    
    private Date dateCreated;
    
    private String schema;
    
    public User(String lastName, String firstName, String role, int level, 
            BankQuery query, String schema) {
        
        //Initialization method used to generate a new user
        
        this.lastName = lastName;
        
        this.firstName = firstName;
        
        this.role = role;
        
        this.userName = "" + firstName.substring(0, 1).toUpperCase() 
                + lastName.toUpperCase();
        
        this.ID = query.getHighestID(schema, "USERS") + 1;
        
        this.dateCreated = new Date(new java.util.Date().getTime());
        
        this.level = level;
        
    }
    
    public User(int userID, String userName, String lastName, String firstName, 
        String role, int level) {
        //Retrieves an existing user from the database.

            
        this.ID = userID;

        this.userName = userName;

        this.lastName = lastName;

        this.firstName = firstName;

        this.role = role;

        this.level = level;       
        
    }
    
    public String getUserName() {
        
        return this.userName;
        
    }
    
    public String getPutQuery() {
        
        String putUser 
                = "INSERT INTO " + schema + ".USERS ("
                + "\"ID\" , "
                + "\"USERNAME\" , "
                + "\"LASTNAME\" , "
                + "\"FIRSTNAME\" , "
                + "\"ROLE\" , "
                + "\"LEVEL\""
                + ")"
            + " VALUES ("
                + this.ID + " , "
                + "\'" + this.userName + "\'" + " , "
                + "\'" + this.lastName + "\'" + " , "
                + "\'" + this.firstName + "\'" + " , "
                + "\'" + this.role + "\'" + " , "
                + this.level 
                + ") ";
        
        System.out.println("generating SQL command: " + putUser);
        
        return putUser;
        
    }
    
}
