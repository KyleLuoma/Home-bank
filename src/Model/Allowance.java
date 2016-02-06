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
public class Allowance extends Account {
    
    double amount = 0;
    
    String frequency;
    
    public Allowance(BankQuery query, int holderID, int managerID, 
            String accountName, String schema, double amount, String frequency) {
    
        super(query, holderID, managerID, accountName, "allowance", schema);
        
        this.amount = amount;
    
    }
    
    public double getAmount() {
        
        return this.amount;
        
    }
    
    public String getFrequency() {
        
        return this.frequency;
        
    }
    
    public void setAmount(double newAmount) {
        
        this.amount = newAmount;
        
    }
    
    public void setFrequency(String newFrequency) {
        
        this.frequency = newFrequency;
        
    }
    
}
