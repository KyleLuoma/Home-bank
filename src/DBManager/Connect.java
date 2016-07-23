/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DBManager;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Administrator
 */
public class Connect {

    /**
     * @param args the command line arguments Loads the database driver, sets
     * the system directory, creates a database if one doesn't already exist in
     * the system directory and connects to the database. Use as a singleton
     * class, and sole owner of the database connection in the program.
     */
    public static void main(String[] args) {

    }

    private Connection dbConnection = null;

    private static int numberOfConnections = 0;

    public Connect() {

        this.loadDBDriver();

        this.createDB();

        dbConnection = this.connectToDB();

    }

    public Connection getConnection() {

        return dbConnection;

    }

    public static int usageCheck() {

        return numberOfConnections;

    }

    private Connection connectToDB() {
        //connects to database
        Connection dbConnection = null;

        String stringURL = "jdbc:derby:HomeBankDB;user=parent;password=parent";

        try {

            dbConnection = DriverManager.getConnection(stringURL);

            System.out.println("Connected to database");

        } catch (SQLException sqle) {

            System.out.println("Unable to connect to the database. Check to see if it's open.");

        }

        numberOfConnections++;

        System.out.println("Total connections: " + numberOfConnections);

        return dbConnection;

    }

    private void loadDBDriver() {
        //Load JDBC driver

        try {

            Class.forName("org.apache.derby.jdbc.EmbeddedDriver");

            System.out.println("DB driver load complete");

        } catch (ClassNotFoundException e) {

            System.out.println(e);

        }
    }

    private void createDB() {
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

}
