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
public class ParentUser extends User {
    
    ArrayList<Integer> postedJobs = new ArrayList<>();
    
    public ParentUser(String lastName, String firstName, int level,
        BankQuery query, String schema, String password) {
        
        super(lastName, firstName, "parent", level, query, schema, password);
        
        //this.postedJobs = query.getParentJobs(this.ID); //need to create getparentjobs method in query
        
    }
    
}
