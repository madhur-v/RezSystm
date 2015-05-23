package reservationsystem;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
import java.io.IOException;
import java.net.URL;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.util.Pair;
import static reservationsystem.Methods.conn;
import static reservationsystem.Methods.openConnection;

/**
 * FXML Controller class
 *
 * @author user
 */
public class ChargeServiceController implements Initializable {

    @FXML
    private Button returnhomebutton;
    @FXML
    private Text chargeservicetext;
    @FXML
    private Label roomlabel;
    @FXML
    private Label itemlabel;
    @FXML
    private TextField roomtf;
    @FXML
    private Button addbutton;
    @FXML
    private Button cancelbutton;
    @FXML
    private Label quantitylabel;
    @FXML
    private TextField quantitytf;
    @FXML
    private ComboBox itemcb;

    PageSwitch ps = new PageSwitch();

    public ObservableList<String> items = FXCollections.observableArrayList();
    @FXML
    private Label message;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        openConnection();
        try {
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT NAME FROM ITEMS");
            while (rs.next()) {
                items.add(rs.getString(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        itemcb.setItems(items);
        itemcb.setValue(
                "Select Item");
    }

    @FXML
    private void gotohomepage(ActionEvent event) throws IOException {
        PageSwitch ps = new PageSwitch();
        ps.homepage(event);
    }

    @FXML
    private void addservice(ActionEvent event) throws SQLException {
        openConnection();
        String refcode = null;
        java.util.Date date = null;
        int roomnum = Integer.parseInt(roomtf.getText());
        int quantity = Integer.parseInt(quantitytf.getText());
        String itemname = itemcb.getValue().toString();

        PreparedStatement insertService = null;
        try {
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT ROOM, REFERENCECODE, DATE_BOOKED FROM APP.BOOKING_AVAILABILITIES "
                    + "WHERE ROOM = "
                    + roomnum
                    + " AND DATE_BOOKED = CURRENT_DATE");
            while (rs.next()) {
                refcode = rs.getString("REFERENCECODE");
                date = rs.getDate("DATE_BOOKED");
                System.out.println(refcode);
            }

            insertService = conn.prepareStatement("INSERT INTO APP.SERVICE (NAME, "
                    + "REFERENCECODE, QUANTITY, CHARGE_DATE) VALUES ("
                    + "'" + itemname + "', "
                    + "'" + refcode + "', "
                    + quantity + ", "
                    + "CURRENT_DATE)");
            insertService.execute();

        } catch (Exception ex) {
            System.out.println("error" + ex);
        }

        java.sql.Statement stmt2 = conn.createStatement();
        ResultSet rs2 = stmt2.executeQuery("SELECT * FROM SERVICE");
        while (rs2.next()) {
            System.out.println(rs2.getString(1) + rs2.getString(2) + rs2.getString(3) + rs2.getString(4));
        }
        roomtf.clear();
        quantitytf.clear();
        itemcb.setValue("Select Item");
        message.setText("Service addded.");
    }

    @FXML
    private void gotoservicehome(ActionEvent event) throws IOException {
        ps.homepage(event);
    }

    @FXML
    private void resetmessage(ActionEvent event) {
        message.setText("");
    }

}
