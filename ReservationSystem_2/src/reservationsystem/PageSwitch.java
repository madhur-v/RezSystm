/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.io.IOException;
import java.time.LocalDate;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

/**
 *
 * @author yuling
 */
public class PageSwitch {

    public void newBooking(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("NewBooking.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void viewBooking(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("ViewBookings.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void homepage(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void editBooking(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("EditBooking.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void bookings(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("BookingHome.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void viewRooms(ActionEvent event) throws IOException {
        System.out.println("I see you want to view all the rooms!");
        Parent parent = FXMLLoader.load(getClass().getResource("ViewRoom.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void generateinvoice(ActionEvent event) throws IOException {
        System.out.println("I see you want to view all the rooms!");
        Parent parent = FXMLLoader.load(getClass().getResource("GenerateInvoice.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public Stage viewRooms(ActionEvent event,LocalDate ad, LocalDate dd) throws IOException {
        
        FXMLLoader loader = new FXMLLoader(getClass().getResource("ViewRoomsController.fxml"));
        Stage stage = new Stage(StageStyle.DECORATED);
       
       
        stage.setScene(new Scene(loader.load()));
        
        ViewRoomsController VRC = loader.<ViewRoomsController>getController();
        VRC.initData(ad, dd);
        stage.show();
        
        return stage;
        /**
        Parent root = FXMLLoader.load(getClass().getResource("ViewRoom.fxml"));
        Scene scene = new Scene(root);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
        */
    }

    public void logOut(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("Login.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void payment(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("Payments.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void historyLog(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("HistoryLog.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    public void breakfast(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("Breakfast.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void chargeservice(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("ChargeService.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    public void servicehome(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("ServicesHome.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

}
