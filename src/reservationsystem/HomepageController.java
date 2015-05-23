/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Madhur Verma
 */
public class HomepageController implements Initializable {

    @FXML
    private VBox leftvbox;
    @FXML
    private Button bookingbutton;
    @FXML
    private Text hometext;
    @FXML
    private VBox leftvbox1;

    @FXML
    private Button viewRoomsButton;
    @FXML
    private Button logOutButton;

    @FXML
    private Button payments;
    
    @FXML
    private Button newBooking;
    
    @FXML
    private Button editbooking;

    @FXML
    private Button historyLog;
    
    @FXML
    private Button test;

    public PageSwitch ps = new PageSwitch();

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }
    
    @FXML
    private void goToTest(ActionEvent event) throws IOException {
             System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("Background.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }
    
    @FXML 
    private void gotonewbooking(ActionEvent event) throws IOException {
        ps.newBooking(event);
    }
    
    @FXML
    private void gotoeditbooking(ActionEvent event) throws IOException {
ps.viewBooking(event);
    }
    

    @FXML
    private void gotopayment(ActionEvent event) throws IOException {
        ps.payment(event);
    }

    @FXML
    private void gotohistorylog(ActionEvent event) throws IOException {
        ps.historyLog(event);
    }

    @FXML
    private void gotobookings(ActionEvent event) throws IOException {
        ps.bookings(event);
    }

    @FXML
    private void gotoviewrooms(ActionEvent event) throws IOException {
        ps.viewRooms(event);
    }

    @FXML
    private void logOut(ActionEvent event) throws IOException {
        ps.logOut(event);
    }

}
