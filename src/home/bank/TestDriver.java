/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import DBManager.BankQuery;
import DBManager.Connect;
import Model.*;
import java.sql.Date;


/**
 *
 * @author KYLE
 */
public class TestDriver {
    
    JobPost newJobObject(String schema, BankQuery query) {
        
        Date openedOn = new Date(new java.util.Date().getTime());
        Date deadline = new Date(12345);
        
        JobPost newJob = new JobPost(query, 1, 0, "Test Description",
            deadline,  openedOn, 20.00, schema);
        
        System.out.println("Testing accessor functions:\n"
                + "   Job ID number: " + newJob.getID() + "\n" 
                + "   Posted by ID:  " + newJob.getPostedByID() + "\n"
                );
        
        return newJob;
        
    }
    
    ParentUser newUserObject(String schema, BankQuery query) {
        
        ParentUser newUser = new ParentUser(
                "Luoma",
                "Kyle",
                1,
                query,
                schema,
                "password"
        );
        
        System.out.println("Username:" + newUser.getUserName());
        System.out.println("PasswordHash: " + newUser.getPasswordHash());
        
        query.putUser(newUser);
        
        return newUser;
        
    }
    
    void checkUserPassword(String userName, String password, 
            String schema, BankQuery query) {
        
        int userID = query.lookupUserID(userName, schema);
        String hashIn = query.lookupUserHash(userID, schema);
        
        if(User.hashPassword(password, userName).equals(hashIn)) {
            System.out.println("Welcome, user " + userName);
        } else {
            System.out.println("Incorrect credentials");
        }
    }    
    
    
}
