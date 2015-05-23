/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.io.BufferedReader;
import java.io.FileReader;
import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author Madhur Verma
 */
public class ReservationSystem extends Application {



    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Login.fxml"));

        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws SQLException {
        Methods.readInRoomsAndCreateRoomsTable();
        Methods.createEDIT_BOOKINGTable();
        Methods.createStaffTable();
        Methods.createBookingTable();
        Methods.createBOOKING_AVAILABILITIESTable();
        Methods.createRoomTypes();
        Methods.createFROM_ROOMTable();
        Methods.createFROM_BOOKINGTable();
        Methods.createHISTORYLOGTable();
        launch(args);

    }



}
