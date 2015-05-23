/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.beans.Statement;
import java.io.IOException;
import java.net.URL;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.scene.control.PasswordField;
import javafx.scene.input.KeyCode;
import static javafx.scene.input.KeyCode.ENTER;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;

/**
 *
 * @author Madhur Verma
 */
public class LoginController implements Initializable {

    @FXML
    private AnchorPane anchorPane;

    @FXML
    private Button loginbutton;
    @FXML
    private Label errorlabel;
    @FXML
    private Text logintext;
    @FXML
    private TextField usertextfield;
    @FXML
    private PasswordField passwordtextfield;
    @FXML
    private Label userlabel;
    @FXML
    private Label passwordlabel;

    public Connection conn;

    @FXML
    private void handleButtonAction(ActionEvent event) throws IOException, SQLException {

        Parent homepage_parent = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        Scene homepage_scene = new Scene(homepage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        //ValidPassword method is run to detect if the password is valid, if it is, go to homepage, else reset
        if (ValidPassword()) {
            app_stage.hide();
            app_stage.setScene(homepage_scene);
            app_stage.show();
        } else {
            usertextfield.clear();
            passwordtextfield.clear();
            errorlabel.setText("Incorrect username or password");
        }
    }

    private void installEventHandler() {
    // handler for enter key press / release events, other keys are
        // handled by the parent (keyboard) node handler
        final EventHandler<KeyEvent> keyEventHandler
                = new EventHandler<KeyEvent>() {
                    public void handle(final KeyEvent keyEvent) {
                        if (keyEvent.getCode() == KeyCode.ENTER) {
                            System.out.println("1");
                            setPressed(keyEvent.getEventType()
                                    == KeyEvent.KEY_PRESSED);
                            System.out.println("2");
                            ValidPassword();
                            System.out.println("3");
                            keyEvent.consume();
                            System.out.println("we have registered the key event :D");

                        }
                    }

                    private void setPressed(boolean b) {
                        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.\
                        if (b == true) {
                            ValidPassword();
                        }
                    }
                };

   // keyNode.setOnKeyPressed(keyEventHandler);
        //keyNode.setOnKeyReleased(keyEventHandler);
    }

    private boolean ValidPassword() {
        System.out.println("Attempting Login");
        openConnection();
        String username = usertextfield.getText();
        String password = passwordtextfield.getText();
        String usertrim = username.trim();
        String passtrim = password.trim();
        boolean access = false;
        try {
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rd = stmt.executeQuery("SELECT USERNAME, "
                    + "PASSWORD FROM APP.STAFF WHERE "
                    + "USERNAME = "
                    + "'" + username + "'"
                    + " AND PASSWORD = "
                    + "'" + password + "'");
            //stmt = conn.prepareStatement(
            //        "SELECT USERNAME, PASSWORD FROM APP.STAFF WHERE USERNAME = " + "'" + usertextfield.getText() + "'" + " AND PASSWORD = " + "'" + passwordtextfield.getText() + "'");
            //stmt.execute();

            System.out.println("selected user and pass");
            if (!rd.next()) {
                access = false;
            } else if (!username.equals(usertrim) || !password.equals(passtrim)) {
                access = false;
            } else {
                System.out.println("username = " + usertextfield.getText());
                System.out.println("password = " + passwordtextfield.getText());
                access = true;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
        }
        System.out.println(access);
        return access;
    }

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        installEventHandler();

    }

    public void openConnection() {
        if (conn == null) {
            try {
                conn = DriverManager.getConnection("jdbc:derby:"
                        + System.getProperty("user.dir")
                        + System.getProperty("file.separator")
                        + "ReservationDB;create=true");
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        }
    }

    @FXML
    private void goToNextPage(KeyEvent event) throws IOException {
        System.out.println("event is: " + event);
      
        if(event.getCode() == KeyCode.ENTER){
            System.out.println("yay");
                    Parent homepage_parent = FXMLLoader.load(getClass().getResource("Homepage.fxml"));
        Scene homepage_scene = new Scene(homepage_parent);
        Stage app_stage = (Stage) ((Node) event.getSource()).getScene().getWindow();

        if (ValidPassword()) {
            app_stage.hide();
            app_stage.setScene(homepage_scene);
            app_stage.show();
        } else {
            usertextfield.clear();
            passwordtextfield.clear();
            errorlabel.setText("Incorrect username or password");
        }

    }

}
}
