/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

/**
 *
 * @author Administrator
 */
public class Account {
    
    String accountName = "standard";
    
    String createAccountTableQuery =
                 "CREATE table " + accountName + ".ACCOUNTS (\n"
                + "ID          INTEGER NOT NULL, \n"
                + "HOLDERID    INTEGER NOT NULL, \n"
                + "MGRID       INTEGER NOT NULL, \n"
                + "BALANCE     FLOAT, \n"
                + "DATECREATED DATE \n"
                + ")";
    
}
