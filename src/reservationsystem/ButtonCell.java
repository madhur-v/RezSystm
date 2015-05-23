/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBoxBuilder;
import javafx.stage.Modality;
import javafx.stage.Stage;

/**
 *
 * @author yuling
 */
public class ButtonCell extends TableCell<Disposer.Record, Boolean> {

    final Button cellButton = new Button("Edit");
    int selectedIndex = ButtonCell.this.getIndex();

    public ButtonCell() {

        //Action when the button is pressed
        cellButton.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // get Selected Item
                Bookings currentBooking = (Bookings) ButtonCell.this.getTableView().getItems().get(ButtonCell.this.getIndex());
                //remove selected item from the table list

                System.out.println("current booking: " + currentBooking.getID() + " " + currentBooking.getCheckInDate());
                try {

                    TextField textfield = new TextField(currentBooking.getID());

                    Methods m = new Methods();

                    m.insertEDIT_BOOKING(currentBooking.getID(), currentBooking.getBOOKING_NAME(), currentBooking.getGUESTS(), currentBooking.getROOM_NUMBER(),
                            currentBooking.getCheckInDate(), currentBooking.getCheckOutDate(), currentBooking.getDeposit());

                    PageSwitch ps = new PageSwitch();
                    ps.editBooking(t);
                   // editBooking(t);
                    

                } catch (IOException ex) {
                    Logger.getLogger(ButtonCell.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }
    
  public void editBooking(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("EditBooking.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
  }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(cellButton);
        }
    }

}
