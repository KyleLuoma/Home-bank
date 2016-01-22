/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBManager;

import Model.*;
import java.sql.Array;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * BankQuery class, singleton class used to handle all communications with the
 * bank database.
 * 
 * @author Administrator
 */
public class BankQuery {
    
    private Connection dbConnection = null;
        
    private Statement statement = null;
    
    private ResultSet result = null;
    
    private static int usageCounter = 0; 
    
    public BankQuery(Connection connectionIn) {
        
        try {
        
            dbConnection = connectionIn;
            
            statement = dbConnection.createStatement();
            
            result = statement.getResultSet();
            
            System.out.println("Initializing Query object");
            
        } catch(SQLException e) {
            
            System.out.println(e);
            
        }
        
        usageCounter++;
    
    }
    
    public void putTransaction(Transaction transaction) {
        
        String putTransaction = transaction.getPutQuery();
        
        try {
            
            statement.execute(putTransaction);
            
        } catch (SQLException e) {
            
            System.out.println(e);
            
        }
        
    }

    public void putAccount(Account account) {
        
        String putAccount = account.getPutQuery();
        
        try {
            
            System.out.println("Trying to put a new account into the DB");
            statement.execute(putAccount);
            
        } catch (SQLException e) {
            
            System.out.println("It didn't work: " + e);
            
        }
        
    }
    
    public Account getAccountObject(int accountID, String userName) {
        
        result = this.getAccountData(accountID, userName);
        
        int holderID = 0; 
        
        int managerID = 0;
        
        double balance = 0;
        
        Date dateCreated = null;
        
        try {
            
            if(result.next()) {
                
                holderID = result.getInt(2);
                              
                managerID = result.getInt(3);
                
                balance = result.getDouble(4);
                
                dateCreated = result.getDate(5);
                
                userName = this.lookupUserName(holderID);
                
            }
            
        } catch(SQLException e) {
            
            System.out.println(e);
            
            System.out.println("There was a problem with BankQuery.getAccountObject");
                                   
        }
        
        Account returnAccount = new Account(accountID, holderID, userName, 
        managerID, balance, dateCreated);
        
        return returnAccount;
        
    }
    
    public ResultSet getAccountData(int accountID, String userName) {
        
        String getAccountQuery 
                = "SELECT * FROM " + userName + ".ACCOUNTS WHERE"
                + " ID = " + accountID;
        
        ResultSet accountResult = null;
        
        Statement accountStatement = null;
        
        try {
            
            accountStatement = dbConnection.createStatement();
            
            accountResult = accountStatement.executeQuery(getAccountQuery);
            
        } catch (SQLException e) {
            
            System.out.println(e);
            
        }
        
        return accountResult;
        
    }
    
    public void putUser(User user) {
                
        String putUser = user.getPutQuery();
        
        try {
        
            System.out.println("Tring to put a new user into the DB. \n"
                               + "sending SQL command: " + putUser + " to DB.");
            
            statement.execute(putUser);
            
        } catch(SQLException e) {
            
            System.out.println(e);
            
        }
        
    }
    
    public User getUserObject(int userID) {
        
        result = this.getUserData(userID) ;
        
        User user = null;
                
        try {
            
            if(result.next()) {
                
                user = new User(
                        
                    result.getInt(1),
                    result.getString(2),
                    result.getString(3),
                    result.getString(4),
                    result.getString(5),
                    result.getInt(6)
                );
                                
            } else { 
                
                System.out.println("No user with ID: " + userID + " in DB"); 
                
            }
            
        } catch(SQLException e) {
                    
                    System.out.println(e);
                    
        }
        
        return user;
                
    }
    
    public ResultSet getUserData(int userID) {
        
        String getUserQuery
                = "SELECT * FROM MAIN.USERS WHERE ID = " + userID;
        
        try {
            
            result = statement.executeQuery(getUserQuery);
            
        } catch(SQLException e) {
            
            System.out.println(e);
            
        }
        
        return result;
        
    }
    
    public String lookupUserName(int userID) {
        
        String lookupUserNameQuery 
                = "SELECT USERNAME FROM MAIN.USERS WHERE ID = " + userID;
        
        String userName = "";
        
        try {
            
            result = statement.executeQuery(lookupUserNameQuery);
            
            if(result.next()) {
                
                userName = result.getString(1);
                
            } else {
                
                System.out.println("No users found in database");
                
            }
            
        } catch(SQLException e) {
            
            System.out.println(e);
            
        }
        
        return userName;
        
    }
    
    public int getHighestID(String accountName, String accountType) {
        
        // Returns highest transaction ID. Use for generating new transaction to
        // avoid collisions.
        
        Array resultString = null;
        
        String findHighestIDQuery 
                = "SELECT ID FROM " + accountName + "." + accountType + " ORDER BY ID DESC";
        
        int accountID = 0;
        
        try {
        
            result = statement.executeQuery(findHighestIDQuery);
            
            if(result.next()) {
                
                accountID = result.getInt(1);
                
            } else {
                
                System.out.println("Could not find an ID in table " 
                                    + accountName + "." + accountType);
                
                accountID = 0;
                
            }
            
            } catch(SQLException e) {
                    
                System.out.println(e);

            }
        
        return accountID;
        
    }
    
    public ResultSet getAccountTransactions(String userName) {
        
        String getTransactionQuery 
                = "SELECT * FROM " + userName + ".TRANSACTIONS"
                + "ORDER BY ID";
        
        try {
            
            result = statement.executeQuery(getTransactionQuery);
            
        } catch (SQLException e) {
            
            System.out.println(e);
            
        }
        
        return result;
        
    } 
    
    public void createStandardTables(String userName) {
        // checks for existence of required tables and creates new tables if none exist.
        // This method is currently a test bed for the javadb API. Needs to be 
        // reconfigured for it's actual purpose.
              
        String createUserTableQuery
                = "CREATE table MAIN.USERS (\n"
                + "ID          INTEGER NOT NULL, \n"
                + "USERNAME    VARCHAR(30), \n"
                + "LASTNAME    VARCHAR(30), \n"
                + "FIRSTNAME   VARCHAR(30), \n"
                + "ROLE        VARCHAR(10), \n"
                + "LEVEL       INTEGER )";
        
        String createAccountTableQuery
                = "CREATE table " + userName + ".ACCOUNTS (\n"
                + "ID          INTEGER NOT NULL, \n"
                + "HOLDERID    INTEGER NOT NULL, \n"
                + "MGRID       INTEGER NOT NULL, \n"
                + "BALANCE     FLOAT, \n"
                + "DATECREATED DATE \n"
                + ")";

        String createTransactionTableQuery 
                = "CREATE table " + userName + ".TRANSACTIONS (\n"
                + "ID               INTEGER NOT NULL, \n"
                + "CREDITACCOUNTID    INTEGER, \n"
                + "DEBITACCOUNTID      INTEGER, \n"
                + "AMOUNT           FLOAT,   \n"
                + "DATE             DATE,    \n"
                + "TIME             TIME    \n"
                + ")";
        
        Statement statement = null;
        
        //boolean test = checkTable("USER", dbConnection, accountName);
        
        //System.out.println("check table works is " + test);
        
        try {
            
            if (checkTable("USERS", dbConnection, "MAIN") == false) {

                System.out.println("Creating user table");
                statement = dbConnection.createStatement();
                statement.execute(createUserTableQuery);
            
            } else {System.out.println("Did not create user table, already exists");}
        
        } catch (SQLException ut) {
            
            System.out.println("There was a problem creating the users table.");
            System.out.println(ut);
            
        }
            
        try {
        
            if (checkTable("ACCOUNTS", dbConnection, userName) == false) {
                
                System.out.println("Creating account table");
                statement = dbConnection.createStatement();
                statement.execute(createAccountTableQuery);
                
            } else {System.out.println("Did not create account table, already exists");}
            
        } catch (SQLException at) {
            
            System.out.println("There was a problem creating the accounts table");
            System.out.println(at);
            
        }
            
        try {
        
            if (checkTable("TRANSACTIONS", dbConnection, userName) == false) {
                
                System.out.println("Creating transactions table");
                statement = dbConnection.createStatement();
                statement.execute(createTransactionTableQuery);
                
            } else {System.out.println("Did not create transactions table, already exists");}
            
        } catch (SQLException tt) {

            System.out.println("There was a problem creating the transactions table");
            System.out.println(tt);

        }
    }
    
    private boolean checkTable(String tableName, Connection connection, String userName) {
        // Checks for the existence of a table within a database
        
        boolean tableExists;
                
        try {
            
            Statement statement = connection.createStatement();
            DatabaseMetaData tableCheck = connection.getMetaData();
            ResultSet tableVerify = tableCheck.getTables(null, userName, tableName, null);
            tableVerify.next();            
            tableExists = tableName.equals(tableVerify.getString("TABLE_NAME"));
            
        } catch (SQLException ctFail) {
            
            System.out.println("There was no table named " + tableName);
            //System.out.println(ctFail);
            tableExists = false;
                        
        }
        
        return tableExists;
        
    }
    
    public static int usageCheck() {
        
        return usageCounter;
        
    }
    
}
