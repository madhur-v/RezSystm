/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import com.sun.prism.impl.Disposer;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
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
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javax.security.auth.callback.Callback;

/**
 * FXML Controller class
 *
 * @author Madhur Verma
 */
public class ViewBookingsController implements Initializable {

    @FXML
    private Button returnhomebutton;
    @FXML
    private Button backbutton;
    @FXML
    private Label commentlabel;
    @FXML
    private Text viewbookingtext;
    @FXML
    private Button deleteentrybutton;
    @FXML
    private Button editentrybutton;

    @FXML
    private TableView<Bookings> bookingsTableView;

    @FXML
    private TableColumn<Bookings, String> ID;

    //@FXML
    //private TableColumn<Bookings, Integer> ID;
    @FXML
    private TableColumn Booking_Name;
    @FXML
    private TableColumn Guests;
    @FXML
    private TableColumn RoomNo;
    @FXML
    private TableColumn checkInDate;
    @FXML
    private TableColumn checkOutDate;
    @FXML
    private TableColumn breakfast;
    @FXML
    private TableColumn Paid;
    @FXML
    private TableColumn Remaining;
    @FXML
    private TableColumn earlyIn;
    @FXML
    private TableColumn lateOut;

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        generateBooking();
//buildData();
    }

//bookingsTableView.getColumns().get(0).setVisible(true);
    private ObservableList<Bookings> bookingData = FXCollections.observableArrayList(); //create the data

    PageSwitch ps = new PageSwitch();

    public void generateBooking() {
        try {

// Add to the data any time, and the table will be updated
            System.out.println("generate booking method has begun...");

            TableColumn col_action = new TableColumn<>("Action");
            bookingsTableView.getColumns().add(col_action);

            col_action.setCellValueFactory(
                    new javafx.util.Callback<TableColumn.CellDataFeatures<Disposer.Record, Boolean>, ObservableValue<Boolean>>() {

                        @Override
                        public ObservableValue<Boolean> call(TableColumn.CellDataFeatures<Disposer.Record, Boolean> p) {
                            return new SimpleBooleanProperty(p.getValue() != null);
                        }
                    });

            //Adding the Button to the cell
            col_action.setCellFactory(
                    new javafx.util.Callback<TableColumn<Disposer.Record, Boolean>, TableCell<Disposer.Record, Boolean>>() {

                        @Override
                        public TableCell<Disposer.Record, Boolean> call(TableColumn<Disposer.Record, Boolean> p) {
                            System.out.println("return new button cell");
                            return new ButtonCell();
                        }

                    });

            ID.setCellValueFactory(new PropertyValueFactory<Bookings, String>("ID"));
            Booking_Name.setCellValueFactory(new PropertyValueFactory<Bookings, String>("BOOKING_NAME"));
            Guests.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("GUESTS"));
            RoomNo.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("ROOM_NUMBER"));
            // checkInDate.setCellValueFactory(new PropertyValueFactory<Bookings, Date>("checkInDate"));
            //checkOutDate.setCellValueFactory(new PropertyValueFactory<Bookings, Date>("checkOutDate"));
            checkInDate.setCellValueFactory(new PropertyValueFactory<Bookings, String>("checkInDate"));
            checkOutDate.setCellValueFactory(new PropertyValueFactory<Bookings, String>("checkOutDate"));
            breakfast.setCellValueFactory(new PropertyValueFactory<Bookings, Boolean>("breakfast"));
            Paid.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("deposit"));
            Remaining.setCellValueFactory(new PropertyValueFactory<Bookings, Integer>("remaining"));
            earlyIn.setCellValueFactory(new PropertyValueFactory<Bookings, Boolean>("earlyIn"));
            lateOut.setCellValueFactory(new PropertyValueFactory<Bookings, Boolean>("lateOut"));
            
            
                      bookingsTableView.setRowFactory(cellData -> {
                TableRow<Bookings> row = new TableRow<>();
                row.setOnMouseClicked(event -> {
                    if (event.getClickCount() == 2 && (!row.isEmpty())) {
                        Bookings currentBooking = row.getItem();
                        System.out.println(currentBooking.getID());
                        
                                         Methods m = new Methods();

                    m.insertEDIT_BOOKING(currentBooking.getID(), currentBooking.getBOOKING_NAME(), currentBooking.getGUESTS(), currentBooking.getROOM_NUMBER(),
                            currentBooking.getCheckInDate(), currentBooking.getCheckOutDate(), currentBooking.getDeposit());

                    PageSwitch ps = new PageSwitch();
                        try {
                            ps.editBooking(event);
                        } catch (IOException ex) {
                            Logger.getLogger(ViewBookingsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                        
                        
                        
                        try {
                            ps.newBooking(event);
                        } catch (IOException ex) {
                            Logger.getLogger(ViewRoomsController.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                });
                return row;
            });

            System.out.println("cell value factory stuff...attempting to read from db now");

            openConnection();
            System.out.println("connection opened");
            java.sql.Statement statement = conn.createStatement();
            ResultSet viewBookings = statement.executeQuery("SELECT REFERENCECODE, BOOKING_NAME, PEOPLE, ROOM, CHECKIN DATE, CHECKOUT DATE, BREAKFAST, EARLYIN, LATEOUT, DEPOSIT, REMAINING FROM APP.BOOKING");

            System.out.println("query created");

            while (viewBookings.next()) {
                System.out.println("Data from database: " + viewBookings.getString(1) + " " + viewBookings.getString(2) + " " + viewBookings.getString(3) + " " + viewBookings.getString(4) + " " + viewBookings.getString(5) + " " + viewBookings.getString(6) + " " + viewBookings.getString(7) + " " + viewBookings.getString(8) + " " + viewBookings.getString(9) + " " + viewBookings.getString(10) + " " + viewBookings.getString(11));;
                System.out.println("while loop has begun");

                String ID = viewBookings.getString(1);
                String booking_name = viewBookings.getString(2);
                int guests = Integer.parseInt(viewBookings.getString(3));
                int room_number = Integer.parseInt(viewBookings.getString(4));

                SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
                String checkInDate = viewBookings.getString(5);
                java.util.Date test = new SimpleDateFormat("dd-MM-yyyy").parse(checkInDate);
                String cry = formatter.format(test);
                System.out.println("The check in date is: " + cry);

                String checkOutdate = viewBookings.getString(6);
                java.util.Date test2 = new SimpleDateFormat("dd-MM-yyyy").parse(checkOutdate);
                String cryagain = formatter.format(test2);
                System.out.println("The check out date is: " + test2);

                String breakfast = viewBookings.getString(7);
                String earlyIn = viewBookings.getString(8);
                String lateOut = viewBookings.getString(9);
                int deposit = Integer.parseInt(viewBookings.getString(10));

                /**
                 * int totalCost = 0;
                 *
                 * java.sql.Statement calculateCost = conn.createStatement();
                 * ResultSet cost = statement.executeQuery("SELECT COST FROM
                 * APP.ROOM WHERE ROOM_NO = " + room_number); while(cost.next())
                 * { totalCost = Integer.parseInt(cost.getString(1)); }
                 */
                //insert cost here
                int remaining = Integer.parseInt(viewBookings.getString(11));

                System.out.println(ID + booking_name + guests + room_number + cry + cryagain + breakfast + earlyIn + lateOut + deposit + remaining);
                Bookings booking = new Bookings(ID, booking_name, guests, room_number, checkInDate, checkOutdate, breakfast, earlyIn, lateOut, deposit, remaining);

                bookingData.add(booking);
                System.out.println("bookingData.add(booking)...");
                bookingsTableView.setItems(bookingData);
                System.out.println("bookingsTableView.setItems(bookingData)....");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public Connection conn;

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

    public void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @FXML
    private void gotohomepage(ActionEvent event) throws IOException {
        ps.homepage(event);
    }

    @FXML
    private void gotobookinghome(ActionEvent event) throws IOException {
        ps.bookings(event);
    }

    @FXML
    private void deleteentry(ActionEvent event) {
    }

    @FXML
    private void editentry(ActionEvent event) throws IOException {
        ps.editBooking(event);

    }

}
