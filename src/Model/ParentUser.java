/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;

/**
 *
 * @author KYLE
 */
public class ParentUser extends User {
    
    int[] postedJobs = null;
    
    public ParentUser(String lastName, String firstName, int level,
        BankQuery query, String schema, int[] postedJobs) {
        
        super(lastName, firstName, "parent", level, query, schema);
        
        //this.postedJobs = query.getParentJobs(this.ID); //need to create getparentjobs method in query
        
    }
    
}
