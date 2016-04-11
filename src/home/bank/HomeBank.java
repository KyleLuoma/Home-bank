/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import java.sql.Connection;
import DBManager.*;
import Model.User;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 *
 * @author KYLE
 */
public class HomeBank extends Application {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        launch(args);
        
    }
    
    @Override
    public void start(Stage primaryStage){
        
        //Local variables:
        User activeUser = null;
        String schema = "MAIN";
        
        //Connect to DB and instantiate connection and query objects:
        Connect homeDB = new Connect(); 
        Connection connection = homeDB.getConnection();
        BankQuery query = new BankQuery(connection, schema);
        
        
        TestDriver testDriver = new TestDriver();
        
        //ParentUser testUser = testDriver.newUserObject(schema, query);

        testDriver.checkUserPassword("KLUOMA", "password", schema, query);
       
        //Present login screen, get user input and check login credientials:
        String[] userInput;
        Boolean correctCredentials = false;
        Boolean lastLoginFailed = false;
        do {
            userInput = login(lastLoginFailed);
            correctCredentials = this.checkUserPassword(userInput[0], 
                    userInput[1], schema, query);
            if(correctCredentials == false) {
                lastLoginFailed = true;
            }
            if(userInput[2].equals("USERCLOSED")) {
                break;
            }
            if(userInput[2].equals("NEWUSERSELECTED")) {
                System.out.println("Loading user creation window...");
                createNewUser(schema, query, connection);
                lastLoginFailed = false;
            }
        } while(!correctCredentials);
        
        //Load user input username into role as activeUser:
        if(correctCredentials){
            activeUser = query.getUserObject(query.lookupUserID(userInput[0]));
            System.out.println("User object for " + activeUser.getUserName());
        }
        
    }
    
    public String[] login(boolean lastLoginFailed) {
        //JAVAFX GUI-Based login screen:
        
        String[] userInput = new String[3];
        userInput[0] = userInput[1] = "";
        
        //Stage setup:
        Stage loginScreen = new Stage();
        loginScreen.setTitle("Homebank Login");
        GridPane grid = new GridPane();
        Text loadingOutput = new Text();
        Text incorrectCredentials = new Text();
        Label userName = new Label("User Name:   ");
        Label password = new Label("Password:   ");
        TextField userNameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Sign In");
        Button newUserButton = new Button("Create new user");
        
        //Events:
        loginButton.setOnAction((event) -> {
            userInput[0] = userNameInput.getText();
            userInput[1] = passwordInput.getText();
            userInput[2] = "";
            loginScreen.close();
        });
        
        newUserButton.setOnAction((event) -> {
            userInput[2] = "NEWUSERSELECTED";
            loginScreen.close();
        });
        
        loginScreen.setOnCloseRequest((event) -> { 
            userInput[2] = "USERCLOSED";
        });
        
        String text = "Please enter your username and password";
        String incorrect = "Incorrect username or password, please try again.";
        
        //Grid setup:
        loadingOutput.setText(text);
        grid.add(loadingOutput,     1, 1, 2, 1);
        grid.add(userName,          1, 2, 1, 1);
        grid.add(userNameInput,     2, 2, 1, 1);
        grid.add(password,          1, 3, 1, 1);
        grid.add(passwordInput,     2, 3, 1, 1);
        grid.add(loginButton,       1, 4, 2, 1);
        grid.add(newUserButton,     1, 5, 2, 1);
        grid.setAlignment(Pos.CENTER);
        loginScreen.setScene(new Scene(grid, 320, 240));
        
        //Display "incorrect username... if last login failed:
        incorrectCredentials.setText(incorrect);
        if(lastLoginFailed == true){
            grid.add(incorrectCredentials, 1, 6, 3, 1);
        }
        
        loginScreen.showAndWait();
        
        //Avoid null value runtime error if user does not input anything in fields:
        if(userInput[0].isEmpty()) {
            userInput[0] = "NOENTRY";
        }
        if(userInput[1].isEmpty()) {
            userInput[1] = "NOENTRY";
        }
        
        return userInput;
    }
    
    boolean checkUserPassword(String userName, String password, String schema,
            BankQuery query) {
        //Accesses the database to check a given username and password hash
        
        boolean correct = false;
        
        int userID = query.lookupUserID(userName);
        String hashIn = query.lookupUserHash(userID);
        
        if(User.hashPassword(password, userName).equals(hashIn)) {
            System.out.println("Welcome, user " + userName);
            correct = true;
        } else {
            System.out.println("Incorrect credentials");
            correct = false;
        }
        
        return correct;
    }
    
    void createNewUser(String schema, BankQuery query, Connection connection) {
        
        Stage userCreationScreen = new Stage();
        userCreationScreen.setTitle("Enter new user information");
        GridPane grid = new GridPane();
        
        //Text:
        String instructions = "Please enter your new user information and"
                + " press the \"Create user\" button.";
        Text instructionsText = new Text();
        instructionsText.setText(instructions);
        
        //Labels:
        Label firstNameLabel = new Label("First name:    ");
        Label lastNameLabel = new Label("Last name:    ");
        Label passwordLabel = new Label("Password:    ");
        Label confirmPasswordLabel = new Label("Confirm Password:    ");
        Label roleLabel = new Label("Select role:    ");
        Label userNameLabel = new Label("Username:    ");
        
        //Combo boxes:
        ComboBox roles = new ComboBox();
        roles.getItems().addAll("Parent", "Child");
        
        //Fields:
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        
        //Buttons:
        Button createUserButton = new Button("Create user");         
        
        //Events:
        
        //Grid setup:
        grid.setAlignment(Pos.CENTER);
        grid.add(instructionsText,      1, 1, 3, 1);
        grid.add(firstNameLabel,        1, 2, 1, 1);
        grid.add(firstNameField,        2, 2, 1, 1);
        grid.add(lastNameLabel,         1, 3, 1, 1);
        grid.add(lastNameField,         2, 3, 1, 1);
        grid.add(roleLabel,             1, 4, 1, 1);
        grid.add(roles,                 2, 4, 1, 1);
        grid.add(passwordLabel,         1, 5, 1, 1);
        grid.add(passwordField,         2, 5, 1, 1);
        grid.add(confirmPasswordLabel,  1, 6, 1, 1);
        grid.add(confirmPasswordField,  2, 6, 1, 1);
        
        grid.add(createUserButton,      4, 10, 1, 1);
        
        //Display:
        userCreationScreen.setScene(new Scene(grid, 640, 480));
        userCreationScreen.showAndWait();
        
    }
    
}
    

