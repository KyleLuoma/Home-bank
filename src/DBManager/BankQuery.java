/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBManager;

import Model.Transaction;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author Administrator
 */
public class BankQuery {
    
    private Connection dbConnection = null;
        
    private Statement statement = null;
    
    private ResultSet result = null;
        
        
    
    public BankQuery(Connection connectionIn) {
        
        try {
        
            dbConnection = connectionIn;
            
            statement = dbConnection.createStatement();
            
            result = statement.getResultSet();
            
            System.out.println("Initializing Query object");
            
        } catch(SQLException e) {
            
            System.out.println(e);
            
        }
    
    }
    
    public void putTransaction(Transaction transaction, String accountName) {
        
        int transactionID = transaction.getID();
        
        String putTransaction = transaction.getPutQuery();
        
        try {
            
            statement.execute(putTransaction);
            
        } catch (SQLException e) {
            
            System.out.println(e);
            
        }
        
    }    
    
    public int getHighestTransactionID(String accountName) {
        
        // Returns highest transaction ID. Use for generating new transaction to
        // avoid collisions.
        
        Array resultString = null;
        
        String findHighestTransactionIDQuery 
                = "SELECT ID FROM TEST.TRANSACTIONS ORDER BY ID DESC";
        
        int accountID = 0;
        
        try {
        
            result = statement.executeQuery(findHighestTransactionIDQuery);
            
            if(result.next()) {
                
                accountID = result.getInt(1);
                
            }
            
            } catch(SQLException e) {
                    
                System.out.println(e);

            }
        
        return accountID;
        
    }
    
    public void createStandardTables(String accountName) {
        // checks for existence of required tables and creates new tables if none exist.
        // This method is currently a test bed for the javadb API. Needs to be 
        // reconfigured for it's actual purpose.
              
        String createUserTableQuery
                = "CREATE table " + accountName + ".USERS (\n"
                + "ID          INTEGER NOT NULL, \n"
                + "LASTNAME    VARCHAR(30), \n"
                + "FIRSTNAME   VARCHAR(30), \n"
                + "ROLE        VARCHAR(10), \n"
                + "LEVEL       INTEGER )";
        
        String createAccountTableQuery
                = "CREATE table " + accountName + ".ACCOUNTS (\n"
                + "ID          INTEGER NOT NULL, \n"
                + "HOLDERID    INTEGER NOT NULL, \n"
                + "MGRID       INTEGER NOT NULL, \n"
                + "BALANCE     FLOAT, \n"
                + "DATECREATED DATE \n"
                + ")";

        String createTransactionTableQuery 
                = "CREATE table " + accountName + ".TRANSACTIONS (\n"
                + "ID               INTEGER NOT NULL, \n"
                + "FROMACCOUNTID    INTEGER, \n"
                + "TOACCOUNTID      INTEGER, \n"
                + "AMOUNT           FLOAT,   \n"
                + "DATE             DATE,    \n"
                + "TIME             TIME    \n"
                + ")";
        
        Statement statement = null;
        
        //boolean test = checkTable("USER", dbConnection, accountName);
        
        //System.out.println("check table works is " + test);
        
        try {
            
            if (checkTable("USERS", dbConnection, accountName) == false) {

                System.out.println("Creating user table");
                statement = dbConnection.createStatement();
                statement.execute(createUserTableQuery);
            
            } else {System.out.println("Did not create user table, already exists");}
        
        } catch (SQLException ut) {
            
            System.out.println("There was a problem creating the users table.");
            System.out.println(ut);
            
        }
            
        try {
        
            if (checkTable("ACCOUNTS", dbConnection, accountName) == false) {
                
                System.out.println("Creating account table");
                statement = dbConnection.createStatement();
                statement.execute(createAccountTableQuery);
                
            } else {System.out.println("Did not create account table, already exists");}
            
        } catch (SQLException at) {
            
            System.out.println("There was a problem creating the accounts table");
            System.out.println(at);
            
        }
            
        try {
        
            if (checkTable("TRANSACTIONS", dbConnection, accountName) == false) {
                
                System.out.println("Creating transactions table");
                statement = dbConnection.createStatement();
                statement.execute(createTransactionTableQuery);
                
            } else {System.out.println("Did not create transactions table, already exists");}
            
        } catch (SQLException tt) {

            System.out.println("There was a problem creating the transactions table");
            System.out.println(tt);

        }
    }
    
    private boolean checkTable(String tableName, Connection connection, String accountName) {
        // Checks for the existence of a table within a database and returns a boolean
        
        boolean tableExists;
                
        try {
            
            Statement statement = connection.createStatement();
            DatabaseMetaData tableCheck = connection.getMetaData();
            ResultSet tableVerify = tableCheck.getTables(null, accountName, tableName, null);
            tableVerify.next();            
            tableExists = tableName.equals(tableVerify.getString("TABLE_NAME"));
            
        } catch (SQLException ctFail) {
            
            System.out.println("There was no table named " + tableName);
            //System.out.println(ctFail);
            tableExists = false;
                        
        }
        
        return tableExists;
        
    }
    
}
