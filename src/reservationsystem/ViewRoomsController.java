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
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author yuling
 */
public class ViewRoomsController implements Initializable {

    @FXML
    private Button Back;
    @FXML
    private Button homeButton;
    @FXML
    private DatePicker arriveDatePicker;
    @FXML
    private DatePicker departureDatePicker;
    @FXML
    private Label departureDate;
    @FXML
    private Label arrivalDate;
    @FXML
    private TableView<Rooms> roomsTableView;
    @FXML
    private TextField roomTypeTextField;
    @FXML
    private Label roomTypeLabel;
    @FXML
    private TableColumn<Rooms, String> roomType;
    @FXML
    private TableColumn<Rooms, String> roomNumber;
    @FXML
    private TableColumn<Rooms, String> Cost;
    @FXML
    private TableColumn<Rooms, String> capacity;

    private ObservableList<Rooms> roomData = FXCollections.observableArrayList(); //create the data

    PageSwitch ps = new PageSwitch();
    public Connection conn;
    Methods m = new Methods();

    /**
     * Initializes the controller class.
     *
     * @param ad
     * @param dd
     * @param url
     * @param rb
     * @return
     * @throws java.sql.SQLException
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        description.setVisible(false);

        try {
            m.createLog("User viewed rooms available");
            generateTable();
            insertDates();
        } catch (SQLException ex) {
            Logger.getLogger(ViewRoomsController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertDates() throws SQLException {
        openConnection();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        System.out.println("starting to generate the table");
        java.sql.Statement statement = conn.createStatement();
        System.out.println("still good");
        ResultSet RS = statement.executeQuery("SELECT CHECKIN, CHECKOUT FROM APP.FROM_BOOKING");
        if (RS.next()) {
            arriveDatePicker.setValue(LocalDate.parse(RS.getString(1), formatter));
            departureDatePicker.setValue(LocalDate.parse(RS.getString(2), formatter));
        } else {
            arriveDatePicker.setValue(null);
            departureDatePicker.setValue(null);
        }

        RS.close();
        String query = "TRUNCATE TABLE APP.FROM_BOOKING";
        System.out.println("Inserting/n" + query);
        Methods m = new Methods();
        m.insertStatement(query);
        //truncate
        System.out.println("skipped everything cos java sucks");
    }

    @FXML
    private Label description;

    public void generateTable() throws SQLException {

// Add to the data any time, and the table will be updated
        System.out.println("generate Room method has begun...");

        roomType.setCellValueFactory(cellData -> cellData.getValue().ROOM_TYPEProperty());
        roomNumber.setCellValueFactory(cellData -> cellData.getValue().ROOM_NOProperty());
        capacity.setCellValueFactory(cellData -> cellData.getValue().CAPACITYProperty());
        Cost.setCellValueFactory(cellData -> cellData.getValue().COSTProperty());
        // Available.setCellValueFactory(cellData -> cellData.getValue().AVAILABLEProperty());

        roomsTableView.setRowFactory(cellData -> {
            TableRow<Rooms> row = new TableRow<>();
            row.setOnMouseClicked(event -> {
                if (event.getClickCount() == 2 && (!row.isEmpty())) {
                    Rooms rowData = row.getItem();
                    System.out.println(rowData.getROOM_NO());
                    try {
                        Methods m = new Methods();

                        java.sql.Statement statement1 = conn.createStatement();
                        ResultSet RS = statement1.executeQuery("SELECT * FROM APP.FROM_ROOM");

                        if (RS.next()) {
                            m.insertStatement("UPDATE APP.FROM_ROOM SET ROOM_NO = " + Integer.parseInt(rowData.getROOM_NO()));
                        } else {
                            m.insertStatement("INSERT INTO APP.FROM_ROOM(ROOM_NO) VALUES(" + Integer.parseInt(rowData.getROOM_NO()) + ")");
                        }
                        ps.newBooking(event);
                    } catch (IOException ex) {
                        Logger.getLogger(ViewRoomsController.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (SQLException ex) {
                        Logger.getLogger(ViewRoomsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            });

            row.hoverProperty().addListener((observable) -> {
                Rooms room = row.getItem();
                if (row.isHover() && room != null) {
                    try {
                        openConnection();
                        String roomType = room.getROOM_TYPE();
                        java.sql.Statement statement = conn.createStatement();
                        ResultSet viewRooms = statement.executeQuery("SELECT DESCRIPTION FROM APP.ROOM_TYPES"
                                + " WHERE ROOM_TYPE = '" + room.getROOM_TYPE() + "'");
                        while (viewRooms.next()) {
                            description.setVisible(true);
                            System.out.println(viewRooms.getString(1));
                            description.setText(viewRooms.getString(1));
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ViewRoomsController.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    description.setVisible(false);
                }
            });

            return row;
        });

        System.out.println("cell value factory stuff...attempting to read from db now");

        openConnection();
        java.sql.Statement statement = conn.createStatement();
        ResultSet viewRooms = statement.executeQuery("SELECT ROOM_NO, ROOM_TYPE ,COST FROM APP.ROOM");

        while (viewRooms.next()) {

            String a = viewRooms.getString(1);
            String b = viewRooms.getString(2);
            String c = viewRooms.getString(3);
            String d;

            if (b.contains("Single")) {
                d = "1";
            } else if (b.contains("King")) {
                d = "2";
            } else if (b.contains("Twin") || b.contains("-")) {
                d = "4";
            } else if (b.contains("Suite")) {
                d = "12";
            } else {
                d = "2";
            }

            System.out.println(a + " " + b + " " + c + " " + d);
            Rooms rooms = new Rooms(a, b, d, c);
            roomData.add(rooms);
            roomsTableView.setItems(roomData);
        }

        System.out.println("wrapping the observable list in a filtered list");

        // 1. Wrap the ObservableList in a FilteredList (initially display all data).
        FilteredList<Rooms> filteredData = new FilteredList<>(roomData, p -> true);

        // 2. Set the filter Predicate whenever the filter changes.
        roomTypeTextField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Rooms -> {
                // If filter text is empty, display all persons.
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                // Compare first name and last name of every person with filter text.
                String lowerCaseFilter = newValue.toLowerCase();

                int i = 0;

                if (departureDatePicker.getValue() != null && arriveDatePicker.getValue() != null) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate startDate = arriveDatePicker.getValue();
                        LocalDate endDate = departureDatePicker.getValue();
                        System.out.println(startDate.compareTo(endDate));

                        java.sql.Statement statement5 = conn.createStatement();
                        ResultSet viewBookings = statement5.executeQuery("SELECT * FROM APP.BOOKING WHERE ROOM = " + Integer.parseInt(Rooms.getROOM_NO()));
                        System.out.println("query created");

                        while (viewBookings.next()) {
                            LocalDate bookingStart = LocalDate.parse(viewBookings.getString(6), formatter);
                            LocalDate bookingEnd = LocalDate.parse(viewBookings.getString(7), formatter);
                            //bookingStart.compareTo(bookingEnd);

                            if (Rooms.getROOM_TYPE().toLowerCase().contains(lowerCaseFilter)) {

                                if (startDate.compareTo(bookingEnd) > 0) {
                                    return true;
                                } else if (endDate.compareTo(bookingStart) < 0) {
                                    return true;
                                } else {
                                    i++;
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }

                        if (i > 0) {
                            return false;
                        } else {
                            return true;
                        }

                    } catch (SQLException ex) {
                        Logger.getLogger(ViewRoomsController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("I have registered the event");
                    if (Rooms.getROOM_TYPE().toLowerCase().contains(lowerCaseFilter)) {
                        return true; // Filter matches first name.
                    } else if (Rooms.getROOM_NO().toLowerCase().contains(lowerCaseFilter)) {
                        return false;
                    } else if (Rooms.getCAPACITY().toLowerCase().contains(lowerCaseFilter)) {
                        return false;
                    } else if (Rooms.getCOST().toLowerCase().contains(lowerCaseFilter)) {
                        return false;
                    }
                }

                return false;

            });
        });

        arriveDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Rooms -> {
                int i = 0;
                if (newValue == null) {
                    return true;
                }
                if (departureDatePicker.getValue() != null) {
                    try {
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                        LocalDate startDate = arriveDatePicker.getValue();
                        LocalDate endDate = departureDatePicker.getValue();
                        System.out.println(startDate.compareTo(endDate));

                        java.sql.Statement statement5 = conn.createStatement();
                        ResultSet viewBookings = statement5.executeQuery("SELECT * FROM APP.BOOKING WHERE ROOM = " + Integer.parseInt(Rooms.getROOM_NO()));
                        System.out.println("query created");

                        while (viewBookings.next()) {
                            LocalDate bookingStart = LocalDate.parse(viewBookings.getString(6), formatter);
                            LocalDate bookingEnd = LocalDate.parse(viewBookings.getString(7), formatter);
                            //bookingStart.compareTo(bookingEnd);

                            if (startDate.compareTo(bookingEnd) > 0) {
                                return true;
                            } else if (endDate.compareTo(bookingStart) < 0) {
                                return true;
                            } else {
                                i++;
                                return false;
                            }
                        }
                    } catch (SQLException ex) {
                        Logger.getLogger(ViewRoomsController.class
                                .getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    System.out.println("I have registered the event");
                }
                if (i > 0) {
                    return false;
                } else {
                    return true;
                }
            });
        });

        departureDatePicker.valueProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(Rooms -> {
                int i = 0;
                if (newValue == null) {
                    return true;
                }

                try {
                    DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                    LocalDate startDate = arriveDatePicker.getValue();
                    LocalDate endDate = departureDatePicker.getValue();

                    System.out.println(startDate.compareTo(endDate));

                    java.sql.Statement statement5 = conn.createStatement();
                    ResultSet viewBookings = statement5.executeQuery("SELECT * FROM APP.BOOKING WHERE ROOM = " + Integer.parseInt(Rooms.getROOM_NO()));
                    System.out.println("query created");

                    while (viewBookings.next()) {
                        LocalDate bookingStart = LocalDate.parse(viewBookings.getString(6), formatter);
                        LocalDate bookingEnd = LocalDate.parse(viewBookings.getString(7), formatter);
                        //bookingStart.compareTo(bookingEnd);

                        if (startDate.compareTo(bookingEnd) > 0) {
                            return true;
                        } else if (endDate.compareTo(bookingStart) < 0) {
                            return true;
                        } else {
                            i++;
                            return false;
                        }
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                if (i > 0) {
                    return false;
                } else {
                    return true;
                }

            });
        });

        // 3. Wrap the FilteredList in a SortedList. 
        SortedList<Rooms> sortedData = new SortedList<>(filteredData);
        // 4. Bind the SortedList comparator to the TableView comparator.
        sortedData.comparatorProperty().bind(roomsTableView.comparatorProperty());
        // 5. Add sorted (and filtered) data to the table.
        roomsTableView.setItems(sortedData);
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
    private void goBack(ActionEvent event) throws IOException {
        System.out.println("You clicked me!");
        ps.homepage(event);
    }

    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        ps.homepage(event);
    }

}
