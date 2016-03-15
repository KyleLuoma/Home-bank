/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;


import DBManager.*;
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

//test (21 0947 JAN 16)

/**
 *
 * @author KYLE
 */
public class HomeBank extends Application{

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {     
        
        launch(args);  
        
    }
    
    @Override
    public void start(Stage primaryStage) {
        
        primaryStage.show();
        
        //show login screen and get username and password:
        String userNamePassword[] = this.login(); //Get username at [0] and password at [1]
        
        //use provided login and password to connecto to database:
        Connect connector = new Connect(userNamePassword[0], userNamePassword[1]); 
        BankQuery query = new BankQuery(connector.getConnection());
        
        //default schema value is MAIN:
        String schema = "MAIN";

        TestDriver testDriver = new TestDriver();
        
    }
    
    public String[] login() {
        //set the stage:
        Stage loginScreen = new Stage();
        loginScreen.setTitle("Home Bank Login");
        GridPane grid = new GridPane();
        //message text:
        Text loadingOutput = new Text();
        String text = "Please enter your username and password:";
        loadingOutput.setText(text);
        //field labels:
        Label userName = new Label("User Name:   ");
        Label password = new Label("Password:   ");
        //field objects:
        TextField userNameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        String[] userInput = new String[2];
        //button(s):
        Button loginButton = new Button("Sign In");
        loginButton.setOnAction(new EventHandler<ActionEvent> () {
            
            @Override
            public void handle(ActionEvent e) {
                        userInput[0] = userNameInput.getText();
                        userInput[1] = passwordInput.getText();
                        loginScreen.close();
            }
        });
        
        //scene build:
        grid.add(loadingOutput,     1, 1, 2, 1);
        grid.add(userName,          1, 2, 1, 1);
        grid.add(userNameInput,     2, 2, 1, 1);
        grid.add(password,          1, 3, 1, 1);
        grid.add(passwordInput,     2, 3, 1, 1);
        grid.add(loginButton,       1, 4, 2, 1);
        grid.setAlignment(Pos.CENTER);
        
        //show the screen, wait for button press:
        loginScreen.setScene(new Scene(grid, 320, 240));
        loginScreen.showAndWait();
        
        return userInput;
   
    }
    
}
    
    
