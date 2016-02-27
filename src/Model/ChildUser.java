/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;
import java.util.ArrayList;

/**
 *
 * @author KYLE
 */
public class ChildUser extends User {
    
    ArrayList<Integer> acceptedJobs = new ArrayList<>();
    
    public ChildUser(String lastName, String firstName, int level, 
            BankQuery query, String schema, String password) {
        
        super(lastName, firstName, "child", level, query, schema, password);
        
        //this.acceptedJobs = query.getAcceptedJobs(ID); //need to create acceptedjobs method in Query
        
        
    }
    
}
