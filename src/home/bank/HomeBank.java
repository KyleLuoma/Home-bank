/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import java.sql.Connection;
import DBManager.*;
import Model.*;
import java.sql.ResultSet;


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
        
        //Create a new user:
        User activeUser = new User( "Luoma" , "Cody" , "child" , 1 , query );
        
        String userName = activeUser.getUserName();
        
        query.putUser(activeUser);
        
        //Check if required tables exist and create new tables if none.
        query.createStandardTables(userName);
        
        //Create a new account using dummy data; assigns a new account number to the object
        Account account = new Account(
                query, 2, 2
        );
        
        //Create an account object that retrieves existing data from the database
        Account openAccount = new Account(
                2,
                query.getAccount(2, userName),
                query
        );
        
        System.out.println(query.getHighestID(userName, "TRANSACTIONS"));
        
        Transaction testTransaction = new Transaction(
                query, 4, 4, 4, 4, 100.50, userName        
        );
        
        System.out.println(testTransaction.getID());
        
        //test of method that puts a new transaction into the database
        query.putTransaction(testTransaction); 
        
        //test of method that puts an account instance into the database
        query.putAccount(account);
        
        System.out.println(openAccount.getID());
        
        System.out.println("" + BankQuery.usageCheck() + " instances of BankQuery class.");
        
        System.out.println("" + Connect.usageCheck() + " connections to database");
                

    }
    
}
    

