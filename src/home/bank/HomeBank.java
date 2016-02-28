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
        
        String schema = "MAIN";
        
        TestDriver testDriver = new TestDriver();
        
        //ParentUser testUser = testDriver.newUserObject(schema, query);

        testDriver.checkUserPassword("KLUOMA", "password", schema, query);
        testDriver.checkUserPassword("KLUOMA", "notthepassword", schema, query);
        
    }
        
}
    

