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
import View.*;
import javafx.stage.Stage;

/**
 *
 * @author KYLE
 */
public class TestDriver {
    
    public JobPost newJobObject(String schema, BankQuery query) {
        
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
 
}
