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
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.time.LocalDateTime;
import static java.time.temporal.TemporalQueries.localDate;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.beans.Observable;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 * FXML Controller class
 *
 * @author yuling
 */
public class EditBookingController implements Initializable {

    @FXML
    private Button home;

    @FXML
    private ComboBox checkInComboBox;

    @FXML
    private ComboBox checkOutComboBox;

    @FXML
    private Label editBookingText;
    @FXML
    private Button save;
    @FXML
    private Button back;
    @FXML
    private Label bookingnamelabel;
    @FXML
    private Label guestslabel;
    @FXML
    private Label roomlabel;
    @FXML
    private Label checkinlabel;
    @FXML
    private Label checkoutlabel;
    @FXML
    private Label deposit;
    @FXML
    private Label referenceCodeTextField;
    @FXML
    private TextField bookingnametextfield;
    @FXML
    private TextField gueststextfield;
    @FXML
    private TextField roomtextfield;
    @FXML
    private Label referenceCode;
    @FXML
    private DatePicker checkindatepicker;
    @FXML
    private DatePicker checkoutdatepicker;
    @FXML
    private TextField depositTextField;
    
    @FXML
    private TableColumn dayTableColumn;
    
    @FXML
    private TableView<Pair<String, Object>> breakfastTableView = new TableView<>();
        
    @FXML
    private TableColumn<Pair<String, Object>, String> dateTableColumn;
    
    @FXML
    private TableColumn<Pair<String, Object>, Object> breakfastTableColumn;


        public ObservableList<Pair<String, Object>> data = FXCollections.observableArrayList();
      
       private Pair<String, Object> pair(String name, Object value) {
        return new Pair<>(name, value);
    }
       
       public Connection conn;
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        bookingnametextfield.setText("TEST HOPE THIS WORKS");
        fillData();
        try {
            generateBreakfast();
        } catch (SQLException ex) {
            Logger.getLogger(EditBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public String reference = " ";

    public void fillData() {
        try {

// Add to the data any time, and the table will be updated
            System.out.println("read booking method has begun...");

            openConnection();
            System.out.println("connection opened");
            //java.sql.Statement statement = conn.createStatement();

            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

            System.out.println("query created");

            //String reference = " ";
            String room = " ";
            String checkInDate = " ";
            String checkOutDate = " ";
            
            java.sql.Statement statement = conn.createStatement();
            ResultSet viewBookings = statement.executeQuery("SELECT * FROM APP.EDIT_BOOKING");
            while (viewBookings.next()) {
                reference = viewBookings.getString(1);
                System.out.println("reference is: " + reference);
            }
            System.out.println("finished adding data to the observable arraylist...");

            ResultSet viewBooking = statement.executeQuery("SELECT * FROM APP.BOOKING WHERE REFERENCECODE = '" + reference + "'");
            System.out.println("result set is done! :D");
            while (viewBooking.next()) {
                System.out.println("Data from database: " + viewBooking.getString(1) + " " + viewBooking.getString(2) + " " + viewBooking.getString(3) + " " + viewBooking.getString(5));

                reference = viewBooking.getString(2);
                room = viewBooking.getString(5);
                checkInDate = viewBooking.getString(6);
                checkOutDate = viewBooking.getString(7);

                referenceCode.setText(viewBooking.getString(2));
                bookingnametextfield.setText(viewBooking.getString(3));
                gueststextfield.setText(viewBooking.getString(4));
                roomtextfield.setText(viewBooking.getString(5));
                checkindatepicker.setValue(LocalDate.parse(viewBooking.getString(6), formatter));
                checkoutdatepicker.setValue(LocalDate.parse(viewBooking.getString(7), formatter));
                depositTextField.setText(viewBooking.getString(13));

                String referenceCode = viewBooking.getString(2);
                //int room_No = Integer.parseInt(viewBookings.getString(1));
                System.out.println("STARTING NEW SECTION");
                java.sql.Statement statement1 = conn.createStatement();
                ResultSet viewBreakfast = statement1.executeQuery("SELECT * FROM APP.BOOKING_AVAILABILITIES WHERE REFERENCECODE = '" + referenceCode + "'");

                System.out.println("STATEMENT IS GOOD YAY");

                while (viewBreakfast.next()) {
                    System.out.println("Room is: " + viewBreakfast.getString(1) + " REFERENCE CODE IS " + viewBreakfast.getString(2) + " Date is: " + viewBreakfast.getString(3) + " breakfast? : " + viewBreakfast.getString(4));
                }

            }

            arrangeEarlyCheckIn(reference, room, checkInDate);
            arrangeLateCheckOut(reference, room, checkOutDate);
            //viewBookings.close();
            viewBooking.close();
            String query = "TRUNCATE TABLE APP.EDIT_BOOKING";
            System.out.println("Inserting/n" + query);
            insertStatement(query);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void generateBreakfast() throws SQLException {
        java.sql.Statement statement = conn.createStatement();
        ResultSet viewbreakfast = statement.executeQuery("SELECT * FROM APP.BOOKING_AVAILABILITIES "
                + " WHERE REFERENCECODE = '" + reference + "'");
        while (viewbreakfast.next()) {
            String dateBooked = viewbreakfast.getString(3);
            Boolean bf = Boolean.parseBoolean(viewbreakfast.getString(4));
            System.out.println("datebooked: " + dateBooked + "boolean bf : " + bf);
            Breakfast breakfast = new Breakfast(dateBooked, bf);
            data.add(pair(dateBooked, bf));           
        }
        
        breakfastTableView.getItems().setAll(data);
        
       dateTableColumn.setCellValueFactory(new PairKeyFactoryEditBooking());
        breakfastTableColumn.setCellValueFactory(new PairValueFactoryEditBooking());
 
              breakfastTableView.getColumns().setAll(dateTableColumn, breakfastTableColumn);
        breakfastTableColumn.setCellFactory(new Callback<TableColumn<Pair<String, Object>, Object>, TableCell<Pair<String, Object>, Object>>() {
            @Override
            public TableCell<Pair<String, Object>, Object> call(TableColumn<Pair<String, Object>, Object> column) {
                return new PairValueCellEditBooking();
            }
        });
        
    }


    public void arrangeEarlyCheckIn(String reference, String room, String checkInDate) throws SQLException, ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(checkInDate));
        c.add(Calendar.DATE, -1);  // number of days to add
        checkInDate = sdf.format(c.getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate checkIn = LocalDate.parse(checkInDate, formatter);

        java.sql.Statement statement1 = conn.createStatement();
        ResultSet checkDayBefore = statement1.executeQuery("SELECT * FROM APP.BOOKING WHERE ROOM = " + room
                + " AND CHECKOUT = '" + checkIn + "'");

        if (!checkDayBefore.next()) { // if the query is empty then there is no bookign for the day before
            System.out.println("No data");
            checkInComboBox.getItems().addAll("Yes", "No");
            checkInComboBox.setValue("Available");
        } else {
            checkInComboBox.setValue("Unavailable");
        }
    }

    public void arrangeLateCheckOut(String reference, String room, String checkOutDate) throws ParseException, SQLException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(checkOutDate));
        c.add(Calendar.DATE, 1);  // number of days to add
        checkOutDate = sdf.format(c.getTime());

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        LocalDate checkOut = LocalDate.parse(checkOutDate, formatter);
        java.sql.Statement statement1 = conn.createStatement();
        ResultSet checkDayAfter = statement1.executeQuery("SELECT * FROM APP.BOOKING WHERE ROOM = " + room
                + " AND CHECKIN = '" + checkOut + "'");

        if (!checkDayAfter.next()) { // if the query is empty then there is no bookign for the day before
            System.out.println("No data");
            checkOutComboBox.getItems().addAll("Yes", "No");
            checkOutComboBox.setValue("Available");
        } else {
            checkOutComboBox.setValue("Unavailable");
        }
    }


        
    @FXML
    private void saveBooking(ActionEvent event) throws IOException, SQLException, ParseException {
        String checkIn = "No";
        String checkOut = "No";

        if (checkInComboBox.getValue() == "Yes") {
            checkIn = "Yes";
        }

        if (checkOutComboBox.getValue() == "Yes") {
            checkOut = "Yes";
        }

        int totalCost = 0;
        java.sql.Statement statement1 = conn.createStatement();
        ResultSet cost = statement1.executeQuery("SELECT COST FROM APP.ROOM WHERE ROOM_NO = "
                + Integer.parseInt(roomtextfield.getText()));

        while (cost.next()) {
            totalCost = Integer.parseInt(cost.getString(1));
        }

        totalCost = totalCost - Integer.parseInt(depositTextField.getText());

        String update = "UPDATE APP.BOOKING SET "
                + "BOOKING_NAME = '" + bookingnametextfield.getText() + "', "
                + "PEOPLE = " + gueststextfield.getText() + ", "
                + "ROOM = " + roomtextfield.getText() + ", "
                + "CHECKIN = '" + checkindatepicker.getValue() + "', "
                + "CHECKOUT = '" + checkoutdatepicker.getValue() + "', "
                + "EARLYIN = '" + checkIn + "', "
                + "LATEOUT = '" + checkOut + "', "
                + "DEPOSIT = " + depositTextField.getText() + ", "
                + "REMAINING = " + totalCost
                + " WHERE REFERENCECODE = '" + reference + "'";

        System.out.println("Inserting/n" + update);
        insertStatement(update);
        System.out.println("You clicked me!");
        
                    String endDate = checkoutdatepicker.getValue().toString();

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            c.setTime(sdf.parse(endDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            endDate = sdf.format(c.getTime());

            String startDate = checkindatepicker.getValue().toString();

            while (!startDate.equals(endDate)) {
                System.out.println("startDate is now : " + startDate);
                //System.out.println("startDate is initially: " + startDate);
                CheckCell uhm = new CheckCell();
                //Breakfast test = (Breakfast) uhm.this.getTableView().getItems().get(0);
                //System.out.println("test date: " + test.getDate() + "test breakfast is: " + checkbox.isSelected());

                int count = 0;
                for (Breakfast z : breakfast) {
                    //System.out.println(" THE DATE IS: " + z.getDate() + "BREAKFAST? " + z.getBreakfast());
                    if (z.getDate().equals(startDate) && z.getBreakfast() == true) {
                        System.out.println("CONTAINS YAY");
                        count++;
                    } else if (z.getDate().equals(startDate) && z.getBreakfast() == false) {
                        System.out.println("does not contain");
                        count--;
                    } else {
                        System.out.println("DOES NOT CONTAIN OH NO");
 
                    }
                    System.out.println(count);
                }
                       
                if (count > 0) {
                    String update1 ="UPDATE APP.BOOKING_AVAILABILITIES SET "
                            + "BREAKFAST = TRUE  "
                            + "WHERE REFERENCECODE = '" + reference + "' AND DATE_BOOKED = '" + startDate + "'";
                  
                    System.out.println("Inserting/n" + update1);
                    insertStatement(update1);
                } else {
                        String update2 =" UPDATE APP.BOOKING_AVAILABILITIES SET "
                            + "BREAKFAST = FALSE  "
                            + "WHERE REFERENCECODE = '" + reference + "' AND DATE_BOOKED = '" + startDate + "'";
                    System.out.println("Inserting/n" + update2);
                    insertStatement(update2);

                }


                c.setTime(sdf.parse(startDate));
                c.add(Calendar.DATE, 1);  // number of days to add
                startDate = sdf.format(c.getTime());
            }
   
        data.removeAll();

        Parent parent = FXMLLoader.load(getClass().getResource("ViewBookings.fxml"));
        Scene scene = new Scene(parent);
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();

    }

        static ArrayList<Breakfast> breakfast = new ArrayList<Breakfast>();

    public static void addBreakfast(String date, boolean test) {
        Breakfast lol = new Breakfast(date, test);
        breakfast.add(lol);
        //System.out.println("hm: " + breakfast);
        //insert code to update dates

        //System.out.println("array list is : " + breakfast.get(0));
        for (Breakfast b : breakfast) {
            System.out.println(" THE DATE IS: " + b.getDate() + "BREAKFAST? " + b.getBreakfast());
        }
    }
   
    @FXML
    private void goToHome(ActionEvent event) throws IOException {
        PageSwitch ps = new PageSwitch();
        ps.homepage(event);
    }

    @FXML
    private void goToBack(ActionEvent event) throws IOException {
       PageSwitch ps = new PageSwitch();
       ps.viewBooking(event);

       data.removeAll();
    }

    @FXML
    private void showBreakfast(ActionEvent event) {
    }

    public  void openConnection() {
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



    private void insertStatement(String insert_query) {

        java.sql.Statement stmt = null;
        openConnection();

        try {
            System.out.println("Database opened successfully");
            stmt = conn.createStatement();
            System.out.println("The query was: " + insert_query);
            stmt.executeUpdate(insert_query);
            stmt.close();
            conn.commit();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }
}
