/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author Madhur Verma
 */
public class BookingHomeController implements Initializable {
    @FXML
    private Text homebookingtext;
    @FXML
    private Button newbookingbutton;
    @FXML
    private Button viewbookingsbutton;
    @FXML
    private Button returnhomebutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    

    @FXML
    private void gotonewbooking(ActionEvent event) throws IOException {
        PageSwitch ps = new PageSwitch();
        ps.newBooking(event);
    }

    @FXML
    private void gotoviewbookings(ActionEvent event) throws IOException {
        PageSwitch ps = new PageSwitch();
        ps.viewBooking(event);
    }

    @FXML
    private void gotohomepage(ActionEvent event) throws IOException {
        PageSwitch ps = new PageSwitch();
        ps.homepage(event);
    }
    
}
