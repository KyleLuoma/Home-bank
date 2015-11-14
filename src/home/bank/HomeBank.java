/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.DatabaseMetaData;

// Simple version control check 112043NOV15
//IT WORKS!!! Sent from surface pro 3
//or does it? Sent from desktop
//YES!!!

/**
 *
 * @author KYLE
 */
class HomeBank {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        HomeBank.loadDBDriver();
        HomeBank.setDBSystemDir();
        HomeBank.createDB();
        Connection connection = HomeBank.connectToDB();
        HomeBank.createStandardTables("TEST", connection);

    }

    public static void loadDBDriver() {
        //Load JDBC driver
        try {
            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");
            System.out.println("DB driver load complete");
        } catch (ClassNotFoundException e) {
            System.out.println(e);
        }
    }

    private static void setDBSystemDir() {
        // Decide on the db system directory: <userhome>/.bankdb/
        String userHomeDir = System.getProperty("user.home", ".");
        String systemDir = userHomeDir + "/.bankdb";

        // Create a db system directory
        new File(systemDir).mkdir();

        // Set the db system directory
        System.setProperty("derby.system.home", systemDir);

        System.out.println("system directory set");
        System.out.println("User Home Directory is " + userHomeDir);
        System.out.println("System Directory is " + systemDir);
    }

    private static void createDB() {
        //create the database
        Connection dbConnection = null;
        String stringURL = "jdbc:derby:HomeBankDB;create=true";
        try {
            dbConnection = DriverManager.getConnection(stringURL);
            System.out.println("Database created");
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    private static Connection connectToDB() {
        //connects to database
        Connection dbConnection = null;
        String stringURL = "jdbc:derby:HomeBankDB;user=parent;password=parent";
        try {
            dbConnection = DriverManager.getConnection(stringURL);
            System.out.println("Connected to database");
        } catch (SQLException sqle) {
            sqle.printStackTrace();
        }
        return dbConnection;
    }

    static void createStandardTables(String accountName, Connection dbConnection) {
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
                + "ID          INTEGER NOT NULL \n"
                + ")";

        String createTransactionTableQuery 
                = "CREATE table " + accountName + ".TRANSACTIONS (\n"
                + "ID          INTEGER NOT NULL \n"
                + ")";
        
        Statement statement = null;
        
        //boolean test = checkTable("USER", dbConnection, accountName);
        
        //System.out.println("check table works is " + test);
        
        try {
            
            if (checkTable("USERS", dbConnection, accountName) == false) {

                System.out.println("Trying to create a user table");
                statement = dbConnection.createStatement();
                statement.execute(createUserTableQuery);
            
            } else {System.out.println("Did not create user table, already exists");}
        
        } catch (SQLException ut) {
            
            System.out.println("There was a problem creating the users table.");
            System.out.println(ut);
            
        }
            
        try {
        
            if (checkTable("ACCOUNTS", dbConnection, accountName) == false) {
                
                System.out.println("Trying to create an account table");
                statement = dbConnection.createStatement();
                statement.execute(createAccountTableQuery);
                
            } else {System.out.println("Did not create account table, already exists");}
            
        } catch (SQLException at) {
            
            System.out.println("There was a problem creating the accounts table");
            System.out.println(at);
            
        }
            
        try {
        
            if (checkTable("TRANSACTIONS", dbConnection, accountName) == false) {
                
                System.out.println("Trying to create a transactions table");
                statement = dbConnection.createStatement();
                statement.execute(createTransactionTableQuery);
                
            } else {System.out.println("Did not create transactions table, already exists");}
            
        } catch (SQLException tt) {

            System.out.println("There was a problem creating the transactions table");
            System.out.println(tt);

        }
    }
    
    public static boolean checkTable(String tableName, Connection connection, String accountName) {
        // Checks for the existence of a table within a database and returns a boolean
        
        boolean tableExists;
                
        try {
            
            Statement statement = connection.createStatement();
            DatabaseMetaData tableCheck = connection.getMetaData();
            ResultSet tableVerify = tableCheck.getTables(null, accountName, tableName, null);
            tableVerify.next();            
            tableExists = tableName.equals(tableVerify.getString("TABLE_NAME"));
            
        } catch (SQLException ctFail) {
            
            System.out.println("Table check method failed");
            System.out.println(ctFail);
            tableExists = false;
                        
        }
        
        return tableExists;
        
    }
    
}
