/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import java.sql.Connection;
import DBManager.*;
import Model.Transaction;

/**
 *
 * @author KYLE
 */
class HomeBank {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        Connect homeDB = new Connect(); //Connects to the homebank DB
        
        Connection connection = homeDB.getConnection();
        
        BankQuery query = new BankQuery(connection);
        
        query.createStandardTables("TEST");
        
        System.out.println(query.getHighestTransactionID("TEST"));
        
        Transaction testTransaction = new Transaction(
                query.getHighestTransactionID("TEST"),
                4, 4, 4, 4, 100.50, "TEST"        
        );
        
        System.out.println(testTransaction.getID());
        
        query.putTransaction(testTransaction, "TEST");

    }
    
}
    

