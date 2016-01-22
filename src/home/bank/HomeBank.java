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

//test (21 0947 JAN 16)

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
        //User activeUser = new User( "Luoma" , "Cody" , "child" , 1 , query );
        
        User activeUser = query.getUserObject(1);
        
        String userName = activeUser.getUserName();

        //Check if required tables exist and create new tables if none.
        //query.createStandardTables(userName);
        
        //query.putUser(activeUser);
        
        //User managingUser = new User( "Luoma", "Kyle", "parent", 2, query );
        
        //query.putUser(managingUser);
        
        //Create a new account using dummy data; assigns a new account number to the object
        //Account codyAccount = new Account(query, 1, 2);
        
        //query.putAccount(codyAccount);
                
        //Account daddyAccount = new Account( query, 2, 1 );
        
        //query.putAccount(daddyAccount);
        
        //Create an account object that retrieves existing data from the database
        Account openAccount = query.getAccountObject(1, userName);
        
        System.out.println("Test of openAccount object. User = " 
                + openAccount.getUserName() + "\n"
                + "    Date account created = " 
                + openAccount.getDateCreated().toString()
                + "\n    Manager ID: " + openAccount.getManagerID());  
        
        System.out.println("Test of openUser object. ActiveUser = " + activeUser.getUserName());
        
        System.out.println(query.getHighestID(userName, "TRANSACTIONS"));
        
        //Transaction testTransaction = new Transaction(
        //        query, 2, 1, 2, 1, 100.50, userName        
        //);
      
        //test of method that puts a new transaction into the database
        //query.putTransaction(testTransaction); 
                
        System.out.println("" + BankQuery.usageCheck() + " instances of BankQuery class.");
        
        System.out.println("" + Connect.usageCheck() + " connections to database");
                

    }
        
}
    

