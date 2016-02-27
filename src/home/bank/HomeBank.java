/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package home.bank;

import java.sql.Connection;
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
        
        BankQuery query = this.login(); //Connects to the homebank DB
        //Connection connection = homeDB.getConnection();
        //BankQuery query = new BankQuery(connection);
        String schema = "MAIN";
        System.out.println("OUTPUT");
        TestDriver testDriver = new TestDriver();

        
        
    }
    
    public BankQuery login() {
        Stage loginScreen = new Stage();
        loginScreen.setTitle("Home Bank Login");
        GridPane grid = new GridPane();
        Text loadingOutput = new Text();
        Label userName = new Label("User Name:   ");
        Label password = new Label("Password:   ");
        TextField userNameInput = new TextField();
        PasswordField passwordInput = new PasswordField();
        Button loginButton = new Button("Sign In");
        Connect newConnection = new Connect();
        
        loginButton.setOnAction(new EventHandler<ActionEvent> () {
            
            @Override
            public void handle(ActionEvent e) {
                        newConnection.setUserName(userNameInput.getText());
                        newConnection.setPassword(passwordInput.getText());
                        newConnection.resetConnection();
                        System.out.println(newConnection.getConnectionValidity());
                        loginScreen.close();
            }
        });
        
        String text = "Please enter your username and password:";
        
        loadingOutput.setText(text);
        grid.add(loadingOutput,     1, 1, 2, 1);
        grid.add(userName,          1, 2, 1, 1);
        grid.add(userNameInput,     2, 2, 1, 1);
        grid.add(password,          1, 3, 1, 1);
        grid.add(passwordInput,     2, 3, 1, 1);
        grid.add(loginButton,       1, 4, 2, 1);
        grid.setAlignment(Pos.CENTER);
        loginScreen.setScene(new Scene(grid, 320, 240));
        loginScreen.showAndWait();
        
        if (true) {
            Connection established = newConnection.getConnection();
            System.out.println(established.toString());
            BankQuery newQuery = new BankQuery(established);
            return newQuery;
        }
        
        return null;
                
    }
    
}
    
    
