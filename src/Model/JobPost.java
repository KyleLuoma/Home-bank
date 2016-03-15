/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import java.sql.Date;
import DBManager.BankQuery;

/**
 *
 * @author Administrator
 */
public class JobPost {
    
    private int ID;
    
    private int postedByID;
    
    private int acceptedByID;
    
    private String description;
    
    private Date deadline;
    
    private Date openedOn;
    
    private double bidAmount;
    
    private String schema;
    
    public JobPost(BankQuery query, int postedByID, int acceptedByID, String description, 
            Date deadline, Date openedOn, double bidAmount, String schema) {
        //Generates a new job, with a unique ID.
        
        
        this.ID = query.getHighestID("JOBS") + 1;
        
        this.postedByID = postedByID;
        
        this.acceptedByID = acceptedByID;
        
        this.description = description;
        
        this.deadline = deadline;
        
        this.openedOn = openedOn;
        
        this.bidAmount = bidAmount;
        
        this.schema = schema;
        
        System.out.println("Job " + this.ID + " opened on: " 
                + openedOn.toString() + "\nSchema: " + this.schema);
        
    }
    
    //accessor functions:
    public int getID() {
        
        return this.ID;
        
    }
    
    public int getPostedByID() {
        
        return this.postedByID;
        
    }
    
    public int getAcceptedByID() {
        
        return this.acceptedByID;
        
    }
    
    public String getDescription() {
        
        return this.description;
        
    }
    
    public Date getDeadline() {
        
        return this.deadline;
        
    }
    
    public Date getOpenedOn() {
        
        return this.openedOn;
        
    }
    
    public double getBidAmount() {
        
        return this.bidAmount;
        
    }
    
    public String getPutQuery() {
        //generates a SQL instruction to insert a new job into the database
        
        String putJob 
                = "INSERT INTO " + schema +  ".JOBS ("
                    + "\"ID\" , "
                    + "\"POSTEDBYID\" , "
                    + "\"ACCEPTEDBYID\" , "
                    + "\"DESCRIPTION\" , "
                    + "\"DEADLINE\" , "
                    + "\"OPENEDON\" , "
                    + "\"BIDAMOUNT\""
                    + ")"
                + " VALUES ("
                    + this.ID + " , "
                    + this.postedByID + " , "
                    + this.acceptedByID + " , "
                    + "\'" + this.description + "\' , "
                    + "\'" + this.deadline.toString() + "\' , "
                    + "\'" + this.openedOn.toString() + "\' , "
                    + this.bidAmount 
                    + ")";
        
        System.out.println(putJob);
        
        return putJob;
        
    }
    
}
