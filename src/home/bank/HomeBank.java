/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import java.sql.Connection;
import DBManager.*;
import Model.Account;
import Model.User;
import java.util.ArrayList;
import javafx.application.Application;
import javafx.beans.binding.BooleanBinding;
import javafx.beans.property.SimpleStringProperty;
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

        //testDriver.checkUserPassword("KLUOMA", "password", schema, query);
       
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
        } else {
            activeUser = null;
        }
        
        if(activeUser.getRole().toLowerCase().equals("parent")) {
            //open the parent view
            parentView();
        } 
        
        
        
        if(activeUser.getRole().toLowerCase().equals("child")) {
            //open the child view
            childView(activeUser, query);
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
            userInput[0] = userNameInput.getText().toUpperCase();
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
        grid.add(newUserButton,     2, 4, 2, 1);
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
        
        System.out.println("Verifying username and password combination.");
        
        boolean correct = false;
        
        int userID = query.lookupUserID(userName);
        String hashIn = query.lookupUserHash(userID);
        
        if(User.hashPassword(password, userName).equals(hashIn)) {
            System.out.println("Password for " + userName + " is correct.");
            correct = true;
        } else {
            System.out.println("Incorrect credentials");
            correct = false;
        }
        
        return correct;
    }
    
    User createNewUser(String schema, BankQuery query, Connection connection) {
        
        User newUser = new User("DEFAULT", "DEFAULT", "DEFAULT", 0, query, schema, "");
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
        Label passwordMismatch = new Label(" Passwords do not match.");
        Label requiredFields = new Label(" Please fill out all fields including "
                + "a password.");
        requiredFields.setVisible(false);
        Label generatedUserName = new Label("");
        
        //Combo boxes:
        ComboBox roles = new ComboBox();
        roles.getItems().addAll("Make a selection", "Parent", "Child");
        roles.getSelectionModel().select(0);
        
        //Fields:
        TextField firstNameField = new TextField();
        TextField lastNameField = new TextField();
        PasswordField passwordField = new PasswordField();
        PasswordField confirmPasswordField = new PasswordField();
        
        //Buttons:
        Button createUserButton = new Button("Create user");
        Button cancelButton = new Button("Cancel");
        
        //Properties:
        SimpleStringProperty passwordOne = new SimpleStringProperty();
        SimpleStringProperty passwordTwo = new SimpleStringProperty();
        BooleanBinding passwordsNotMatch;
        
        //Bindings:
        passwordsNotMatch = passwordOne.isNotEqualTo(passwordTwo);
        passwordOne.bind(passwordField.textProperty());
        passwordTwo.bind(confirmPasswordField.textProperty());
        passwordMismatch.visibleProperty()
                       .bind(passwordsNotMatch);
        generatedUserName.textProperty().bind(
                firstNameField.textProperty().concat(lastNameField.textProperty())
            );
        
        //Events:
        
        cancelButton.setOnAction((event) -> { userCreationScreen.close(); });
        
        createUserButton.setOnAction((event) ->{
           //Check to ensure all fields are populated:
           if(passwordsNotMatch.get() == false           &
                   !firstNameField.getText().equals("")  &
                   !lastNameField.getText().equals("")   &
                   roles.getSelectionModel()
                        .getSelectedIndex() != 0         &
                   !passwordField.getText().equals("")
                   ) {
               //Apply input from fields to newUser object:
               newUser.setFirstName(firstNameField.getText());
               newUser.setLastName(lastNameField.getText());
               newUser.setUserName(newUser.getFirstName().toUpperCase() 
                       + newUser.getLastName().toUpperCase());
               newUser.setRole(
                   roles.getSelectionModel()
                       .getSelectedItem()
                       .toString()
               );
               newUser.setLevel(1); //Arbitrary at this point
               newUser.setPasswordHash(passwordField.getText());
               if(query.putUser(newUser)) {
                   userCreationScreen.close();
               } else {
                   System.out.println("The user was not correctly added "
                           + "to the database");
               }
           } else {
               requiredFields.visibleProperty().set(true);
               System.out.println(roles.getSelectionModel().getSelectedItem());
           }
        });
        
        //Grid setup:
        grid.setAlignment(Pos.CENTER);
        grid.add(instructionsText,      1, 1, 4, 1);
        grid.add(firstNameLabel,        1, 2, 1, 1);
        grid.add(firstNameField,        3, 2, 1, 1);
        grid.add(lastNameLabel,         1, 3, 1, 1);
        grid.add(lastNameField,         3, 3, 1, 1);
        grid.add(userNameLabel,         1, 4, 1, 1);
        grid.add(generatedUserName,     3, 4, 1, 1);
        grid.add(roleLabel,             1, 5, 1, 1);
        grid.add(roles,                 3, 5, 1, 1);
        grid.add(passwordLabel,         1, 6, 1, 1);
        grid.add(passwordField,         3, 6, 1, 1);
        grid.add(confirmPasswordLabel,  1, 7, 1, 1);
        grid.add(confirmPasswordField,  3, 7, 1, 1);
        grid.add(passwordMismatch,       4, 7, 1, 1);
        
        grid.add(createUserButton,      1, 10, 1, 1);
        grid.add(cancelButton,          2, 10, 2, 1);
        grid.add(requiredFields,        1, 11, 1, 1);
        
        //Display:
        userCreationScreen.setScene(new Scene(grid, 640, 480));
        userCreationScreen.showAndWait();
        
        return newUser;
        
    }
    
    void parentView() {
        //Placeholder for parent dashboard view
        System.out.println("Parent view loading");
    }
    
    void childView(User activeUser, BankQuery query) {
        //Placeholder for child dashboard view
        System.out.println("Child view loading");
        
        Stage childView = new Stage();
        childView.setTitle("Welcome, " + activeUser.getFirstName());
        GridPane grid = new GridPane();
        
        //Account information retrieval:
        ArrayList<Account> activeAccounts = query.getUserAccounts(activeUser.getID());
        
        //Text:
        Text userAccountInfo = new Text();
        userAccountInfo.setText("" + activeUser.getFirstName() + " " + activeUser.getLastName()
                + " has " + activeAccounts.size() + " accounts:\n"
        );
        
        //Grid setup:
        grid.setAlignment(Pos.CENTER);
        grid.add(userAccountInfo,      1, 1, 4, 1);
        
        childView.setScene(new Scene(grid, 640, 480));
        childView.showAndWait();
        
    }
    
}
    

