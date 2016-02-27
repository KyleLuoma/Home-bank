/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Model;

import DBManager.BankQuery;
import java.math.BigInteger;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.sql.Date;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;

/**
 *
 * @author Administrator
 */
public class User {
    
    private int ID;
    
    private String userName;
    
    private String lastName;
    
    private String firstName;
    
    private String role;
    
    private int level;
    
    private Date dateCreated;
    
    private String schema;
    
    private String hashedPassword;
    
    public User(String lastName, String firstName, String role, int level, 
            BankQuery query, String schema, String ptPassword) {
        
        //Initialization method used to generate a new user
        
        this.schema = schema;
        
        this.lastName = lastName;
        
        this.firstName = firstName;
        
        this.role = role;
        
        this.userName = "" + firstName.substring(0, 1).toUpperCase() 
                + lastName.toUpperCase();
        
        this.ID = query.getHighestID(schema, "USERS") + 1;
        
        this.dateCreated = new Date(new java.util.Date().getTime());
        
        this.level = level;
        
        this.hashedPassword = User.hashPassword(ptPassword, userName);
        
    }
    
    public User(int userID, String userName, String lastName, String firstName, 
        String role, int level) {
        //Retrieves an existing user from the database.

            
        this.ID = userID;

        this.userName = userName;

        this.lastName = lastName;

        this.firstName = firstName;

        this.role = role;

        this.level = level;       
        
    }
    
    public String getUserName() {
        return this.userName;   
    }
    
    public String getPasswordHash() {
        return this.hashedPassword;
    }
    
    public String getPutQuery() {
        
        String putUser 
                = "INSERT INTO " + this.schema + ".USERS ("
                + "\"ID\" , "
                + "\"USERNAME\" , "
                + "\"LASTNAME\" , "
                + "\"FIRSTNAME\" , "
                + "\"ROLE\" , "
                + "\"LEVEL\" , "
                + "\"PASSWORD\""
                + ")"
            + " VALUES ("
                + this.ID + " , "
                + "\'" + this.userName + "\'" + " , "
                + "\'" + this.lastName + "\'" + " , "
                + "\'" + this.firstName + "\'" + " , "
                + "\'" + this.role + "\'" + " , "
                + this.level + " , "
                + "\'" + this.hashedPassword + "\'" 
                + ") ";
        
        System.out.println("generating SQL command: " + putUser);
        
        return putUser;
        
    }
    
    public static String hashPassword(String password, String salt) {
    //Password hash method from adambard.com/blog/3-wrong-ways-to-store-a-password
        final int ITERATIONS = 1000;        
        final int KEY_LENGTH = 192; // bits
        
        char[] passwordChars = password.toCharArray();
        byte[] saltBytes = salt.getBytes();
        SecretKeyFactory key = null;
        byte[] hashedPassword = null;
        
        PBEKeySpec spec = new PBEKeySpec(
            passwordChars,
            saltBytes,
            ITERATIONS,
            KEY_LENGTH
        );
        
        try {
            key = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
            hashedPassword = key.generateSecret(spec).getEncoded();
        } catch(NoSuchAlgorithmException e) {
            System.out.println(e);
        } catch(InvalidKeySpecException iv) {
            System.out.println(iv);
        }
        
        return String.format("%x", new BigInteger(hashedPassword));
        
    }
    
}
