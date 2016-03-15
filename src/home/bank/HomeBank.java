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
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
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
        } while(!correctCredentials);
        
        //Load user input username into role as activeUser:
        if(correctCredentials){
            activeUser = query.getUserObject(query.lookupUserID(userInput[0]));
            System.out.println("User object for " + activeUser.getUserName());
        }
        
    }
    
    public String[] login(boolean lastLoginFailed) {
        //JAVAFX GUI-Based login screen:
        
        String[] userInput = new String[2];
        
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
        
        loginButton.setOnAction(new EventHandler<ActionEvent> () {
            
            @Override
            public void handle(ActionEvent e) {
                userInput[0] = userNameInput.getText();
                userInput[1] = passwordInput.getText();
                loginScreen.close();
            }           
        });
        
        String text = "Please enter your username and password";
        String incorrect = "Incorrect username or password, please try again.";
        
        loadingOutput.setText(text);
        grid.add(loadingOutput,     1, 1, 2, 1);
        grid.add(userName,          1, 2, 1, 1);
        grid.add(userNameInput,     2, 2, 1, 1);
        grid.add(password,          1, 3, 1, 1);
        grid.add(passwordInput,     2, 3, 1, 1);
        grid.add(loginButton,       1, 4, 2, 1);
        grid.setAlignment(Pos.CENTER);
        loginScreen.setScene(new Scene(grid, 320, 240));
        
        //Display "incorrect username... if last login failed:
        incorrectCredentials.setText(incorrect);
        if(lastLoginFailed == true){
            grid.add(incorrectCredentials, 1, 5, 3, 1);
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
}
    

