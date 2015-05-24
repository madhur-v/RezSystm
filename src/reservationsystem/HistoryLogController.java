/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import static reservationsystem.Methods.conn;

/**
 * FXML Controller class
 *
 * @author yuling
 */
public class HistoryLogController implements Initializable {

    @FXML
    private Button home;
    @FXML
    private TableView<HistoryLog> historyLogTableView;
    @FXML
    private TableColumn<HistoryLog, String> userTC;
    @FXML
    private TableColumn<HistoryLog, String> dateTC;
    @FXML
    private TableColumn<HistoryLog, String> timeTC;
    @FXML
    private TableColumn<HistoryLog, String> actionTC;

    PageSwitch ps = new PageSwitch();
    private ObservableList<HistoryLog> logData = FXCollections.observableArrayList(); 
    
    public Connection conn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        try {
            generateTableView();
        } catch (SQLException ex) {
            Logger.getLogger(HistoryLogController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void generateTableView() throws SQLException {
        userTC.setCellValueFactory(cellData -> cellData.getValue().USERProperty());
        dateTC.setCellValueFactory(cellData -> cellData.getValue().DATEProperty());
        timeTC.setCellValueFactory(cellData -> cellData.getValue().TIMEProperty());
        actionTC.setCellValueFactory(cellData -> cellData.getValue().ACTIONProperty());


        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet viewlog = statement.executeQuery("SELECT * FROM APP.HISTORYLOG");

        while (viewlog.next()) {
            String user = viewlog.getString(1);
            String dt = viewlog.getString(2);
            String[] splitdatetime = dt.split("\\s+");  
            
            String date = splitdatetime[0];
            String time = splitdatetime[1].substring(0,5);
            String action = viewlog.getString(3);
            

            System.out.println(user + " " + date + " " + time + " " + action);
            HistoryLog HL = new HistoryLog(user, date, time, action);
            logData.add(HL);
            historyLogTableView.setItems(logData);
        }
        historyLogTableView.setItems(logData);
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        ps.homepage(event);
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

}
