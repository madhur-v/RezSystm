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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

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

import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;

import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;

import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;

import javafx.util.Pair;

//import javafx.scene.control.DatePicker;
/**
 * FXML Controller class
 *
 * @author Madhur Verma
 */
public class NewBookingController implements Initializable {

    @FXML
    private ComboBox checkInTime;
    @FXML
    private ComboBox checkOutTime;
    @FXML
    private Label referenceCode;
    @FXML
    private Text newbookingtext;
    @FXML
    private Button returnhomebutton;
    @FXML
    private Button resetnewbookingbutton;
    @FXML
    private Label bookingnamelabel;
    @FXML
    private Label guestslabel;
    @FXML
    private Label roomlabel;
    @FXML
    private Button availabilitybutton;
    @FXML
    private Label checkinlabel;
    @FXML
    private Label checkoutlabel;
    @FXML
    private TextField bookingnametextfield;
    @FXML
    private TextField gueststextfield;
    @FXML
    private TextField roomtextfield;
    @FXML
    private Button addentrybutton;

    @FXML
    private Button backbutton;
    @FXML
    private Label commentlabel;
    @FXML
    private DatePicker checkindatepicker;
    @FXML
    private DatePicker checkoutdatepicker;

    public Connection conn;

    @FXML
    private Text compulsorytext;
    @FXML
    private Button add;

    @FXML
    private Button bookingAvailabilities;

    @FXML
    private TableView<Pair<String, Object>> breakfastTableView = new TableView<>();

    @FXML
    private TableColumn<Pair<String, Object>, String> dateTableColumn;

    @FXML
    private TableColumn<Pair<String, Object>, Object> breakfastTableColumn;

    public ObservableList<String> options = FXCollections.observableArrayList("Morning", "Afternoon");

    //for key value pair 
    public ObservableList<Pair<String, Object>> data = FXCollections.observableArrayList();
    @FXML
    private TableColumn<?, ?> dayTableColumn;

    private Pair<String, Object> pair(String name, Object value) {
        return new Pair<>(name, value);
    }

    PageSwitch ps = new PageSwitch();

    @Override
    public void initialize(URL url, ResourceBundle rb) {
        breakfastTableView.setVisible(false);

        checkInTime.getItems().addAll("Morning", "Afternoon");
        checkInTime.setValue("Check in time");
        checkOutTime.getItems().addAll("Morning", "Afternoon");
        checkOutTime.setValue("Check out time");

        try {
            insertRoom();
        } catch (SQLException ex) {
            Logger.getLogger(NewBookingController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void insertRoom() throws SQLException {
        openConnection();
        System.out.println("starting to generate the table");
        java.sql.Statement statement = conn.createStatement();
        System.out.println("still good");
        ResultSet RS = statement.executeQuery("SELECT ROOM_NO FROM APP.FROM_ROOM");
        if (RS.next()) {
            roomtextfield.setText(RS.getString(1));
        }

        RS.close();
        String query = "TRUNCATE TABLE APP.FROM_ROOM";
        System.out.println("Inserting/n" + query);
        insertStatement(query);
        //truncate
        System.out.println("skipped everything cos java sucks");
    }

    private void generateTable() throws ParseException, SQLException {

        // dateTableColumn.setCellValueFactory (new PropertyValueFactory<Breakfast, String>("date"));
        String endDate = checkoutdatepicker.getValue().toString();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        c.setTime(sdf.parse(endDate));
        c.add(Calendar.DATE, 1);  // number of days to add
        endDate = sdf.format(c.getTime());
        String startDate = checkindatepicker.getValue().toString();

        final List<Breakfast> items = Arrays.asList();

        while (!startDate.equals(endDate)) {
            System.out.println("startDate is now : " + startDate);
            //System.out.println("startDate is initially: " + startDate);
            Breakfast breakfast = new Breakfast(startDate, false);
            //breakfastData.add(pair(startDate, false));
            data.add(pair(startDate, false));

            c.setTime(sdf.parse(startDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            startDate = sdf.format(c.getTime());

        }

        breakfastTableView.getItems().setAll(data);

        dateTableColumn.setCellValueFactory(new PairKeyFactory());
        breakfastTableColumn.setCellValueFactory(new PairValueFactory());

        breakfastTableView.getColumns().setAll(dateTableColumn, breakfastTableColumn);
        breakfastTableColumn.setCellFactory(new Callback<TableColumn<Pair<String, Object>, Object>, TableCell<Pair<String, Object>, Object>>() {
            @Override
            public TableCell<Pair<String, Object>, Object> call(TableColumn<Pair<String, Object>, Object> column) {
                return new PairValueCell();
            }
        });

        System.out.println("finished adding data to the observable arraylist...");
        System.out.println("finished adding the data to the columns...");
        System.out.println("finshed adding the data to the tableview...");

    }

    @FXML
    private void addentry(ActionEvent event) throws ParseException, SQLException {
        System.out.println("check in time " + checkInTime.getValue());
        System.out.println("check out time " + checkOutTime.getValue());
        String time = checkInTime.getValue().toString();
        String date = checkindatepicker.getValue().toString().replaceAll("-", "");
        String reference = "";

        openConnection();

        int guests;
        Integer room;
        if (gueststextfield.getText().equals("")) {
            commentlabel.setText("Guests is empty");
        } else if ((checkindatepicker.getValue().toString().equals(""))) {
            commentlabel.setText("");
            commentlabel.setText("Missing dates");
        } else if (checkInTime.getValue() != "Morning" && checkInTime.getValue() != "Afternoon") {
            commentlabel.setText("Please pick a check in time");
        } else if (checkOutTime.getValue() != "Morning" && checkOutTime.getValue() != "Afternoon") {
            commentlabel.setText("Please pick a check out time");
        } else {
            guests = Integer.parseInt(gueststextfield.getText());
            if (roomtextfield.getText().equals("")) {
                room = null;
            } else {
                room = Integer.parseInt(roomtextfield.getText());
            }

            System.out.println("everything up to here is fine");

            reference = Character.toString(checkInTime.getValue().toString().charAt(0)) + roomtextfield.getText()+ date;
            System.out.println("reference is: " + reference);

            int totalCost = 0;

            System.out.println("cost initialised");

            java.sql.Statement statement = conn.createStatement();

            System.out.println("okay");
            java.sql.Statement calculateCost = conn.createStatement();
            System.out.println("still good");
            ResultSet abce = statement.executeQuery("SELECT * FROM APP.ROOM WHERE ROOM_NO = " + room);

            System.out.println("cost statement has been created");

            while (abce.next()) {
                totalCost = Integer.parseInt(abce.getString(3));
            }

            System.out.println("whiel statement is finished");

            String query = "INSERT INTO APP.BOOKING "
                    + "(BOOKING_NAME, "
                    + "REFERENCECODE,"
                    + "PEOPLE, "
                    + "ROOM, "
                    + "CHECKIN, "
                    + "CHECKOUT,"
                    + "CHECKINTIME,"
                    + "CHECKOUTTIME,"
                    + "REMAINING) "
                    + "VALUES "
                    + "(" + "'" + bookingnametextfield.getText() + "', '"
                    + reference + "',"
                    + guests + ","
                    + room + ",'"
                    + checkindatepicker.getValue() + "','"
                    + checkoutdatepicker.getValue() + "',"
                    + "'" + checkInTime.getValue() + "',"
                    + "'" + checkOutTime.getValue() + "',"
                    + totalCost + ")";
            System.out.println("Inserting/n" + query);
            insertStatement(query);

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
                        System.out.println("DOES NOT CONTAIN ON NO");

                    }
                    System.out.println(count);
                }

                if (count > 0) {
                    String query1 = "INSERT INTO APP.BOOKING_AVAILABILITIES "
                            + "(ROOM, "
                            + "REFERENCECODE,"
                            + " DATE_BOOKED,"
                            + "BREAKFAST) VALUES("
                            + Integer.parseInt(roomtextfield.getText()) + ", '"
                            + reference + "',"
                            + " '" + startDate + "', "
                            + "TRUE)";
                    System.out.println("Inserting/n" + query1);
                    insertStatement(query1);
                } else {
                    String query2 = "INSERT INTO APP.BOOKING_AVAILABILITIES "
                            + "(ROOM, "
                            + "REFERENCECODE,"
                            + " DATE_BOOKED,"
                            + "BREAKFAST) VALUES("
                            + Integer.parseInt(roomtextfield.getText()) + ", '"
                            + reference + "',"
                            + " '" + startDate + "', "
                            + "FALSE)";
                    System.out.println("Inserting/n" + query2);
                    insertStatement(query2);

                }

                c.setTime(sdf.parse(startDate));
                c.add(Calendar.DATE, 1);  // number of days to add
                startDate = sdf.format(c.getTime());
            }

            bookingnametextfield.clear();
            roomtextfield.clear();
            gueststextfield.clear();
            commentlabel.setText("Entry was added.");
            checkindatepicker.getEditor().clear(); //test to see if this works
            checkoutdatepicker.getEditor().clear(); //test to see if this works
            breakfast.clear();
            //SHOW REFERENCE CODE

            referenceCode.setText("SHOW REFERENCE CODE HERE");
            //String time = checkInTime.getValue();

            referenceCode.setVisible(true);
            referenceCode.setText("The reference code is: " + reference);

        }

    }

    static ArrayList<Breakfast> breakfast = new ArrayList<Breakfast>();

    public static void addBreakfast(String date, boolean test) {
        Breakfast lol = new Breakfast(date, test);
        breakfast.add(lol);

        for (Breakfast b : breakfast) {
            System.out.println(" THE DATE IS: " + b.getDate() + "BREAKFAST? " + b.getBreakfast());
        }
    }

    @FXML
    private void showBreakfast(ActionEvent event) throws ParseException, SQLException {

        SimpleDateFormat ft = new SimpleDateFormat("yyyy-MM-dd");
        String checkInDate = ft.format(ft.parse(checkindatepicker.getValue().toString()));
        String checkOutDate = ft.format(ft.parse(checkoutdatepicker.getValue().toString()));

        if (checkInDate != null && checkOutDate != null) {
            breakfastTableView.setVisible(true);
            generateTable();
        } else {
            breakfastTableView.setVisible(false);
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
            closeConnection();
        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }

    }

    @FXML
    private void resetfields(ActionEvent event) {
        bookingnametextfield.clear();
        roomtextfield.clear();
        gueststextfield.clear();
        commentlabel.setText("");
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
    private void addDummyData(ActionEvent event) {

        String query = "INSERT INTO APP.BOOKING "
                + "(REFERENCECODE, "
                + "BOOKING_NAME,"
                + "PEOPLE, "
                + "ROOM, "
                + "CHECKIN, "
                + "CHECKOUT,"
                + "CHECKINTIME,"
                + "CHECKOUTTIME) "
                + "VALUES "
                + "('M20150505','TEST', 2, 3, '2015-05-05', '2015-05-18', 'MORNING', 'AFTERNOON')";

        System.out.println("Inserting/n" + query);
        insertStatement(query);

        String query2 = "INSERT INTO APP.BOOKING_AVAILABILITIES "
                + "(ROOM, "
                + " DATE_BOOKED,"
                + "BREAKFAST, REFERENCE) VALUES"
                + "(2, '2015-05-05', FALSE, 'M20150505')";
        System.out.println("Inserting/n" + query);
        insertStatement(query2);

        commentlabel.setText("Entry was added.");
    }

    /**
     * public Stage useDateFilter(LocalDate AD, LocalDate DD) { FXML loader =
     * (FXML) new
     * FXMLLoader(getClass().getResource("ViewRoomsController.fxml"));
     *
     * Stage stage = new Stage(StageStyle.DECORATED); stage.setScene(new
     * Scene((Pane) loader.load()));
     *
     * ViewRoomsController controller =
     * loader.<ViewRoomsController>getController();
     *
     * controller.initData(AD, DD);
     *
     * stage.show(); return stage;
     *
     * }
     */
    /**
     * try {
     *
     * // Add to the data any time, and the table will be updated
     * System.out.println("generate booking method has begun...");
     *
     * openConnection(); System.out.println("connection opened");
     * java.sql.Statement statement = conn.createStatement(); ResultSet
     * viewBookings = statement.executeQuery("SELECT * FROM
     * APP.BOOKING_AVAILABILITIES");
     *
     * System.out.println("query created");
     *
     * while (viewBookings.next()) { System.out.println("Data from database: " +
     * viewBookings.getString(1) + " " + viewBookings.getString(2) + " " +
     * viewBookings.getString(3));;
     *
     * }
     *
     * } catch (Exception e) { e.printStackTrace(); }
     *
     */
    @FXML
    private void goToBookingAvailabilities(ActionEvent event) throws IOException {
        LocalDate ad = checkindatepicker.getValue();
        LocalDate dd = checkoutdatepicker.getValue();

        /**
         * try {
         *
         * // Add to the data any time, and the table will be updated
         * System.out.println("generate booking method has begun...");
         *
         * openConnection(); System.out.println("connection opened");
         * java.sql.Statement statement = conn.createStatement(); ResultSet
         * viewBookings = statement.executeQuery("SELECT * FROM
         * APP.BOOKING_AVAILABILITIES");
         *
         * System.out.println("query created");
         *
         * while (viewBookings.next()) { System.out.println("Data from database:
         * " + viewBookings.getString(1) + " " + viewBookings.getString(2) + " "
         * + viewBookings.getString(3));;
         *
         * }
         *
         * } catch (Exception e) { e.printStackTrace(); } }
         */

    }

    @FXML
    private void goToViewRooms(ActionEvent event) throws IOException {
        Methods m = new Methods();
        m.insertStatement("INSERT INTO APP.FROM_BOOKING(CHECKIN, CHECKOUT)"
            + "VALUES('" + checkindatepicker.getValue() + "', '"
            + checkoutdatepicker.getValue() + "')");

        ps.viewRooms(event);

    }

}
