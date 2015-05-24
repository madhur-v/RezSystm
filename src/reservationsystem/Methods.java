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
import java.time.LocalDate;
import java.time.LocalTime;

/**
 *
 * @author yuling
 */
public class Methods {

    public static Connection conn;

    public void insertEDIT_BOOKING(String BOOKING_ID, String BOOKING_NAME, int ROOM,
            int PEOPLE, String CHECKIN, String CHECKOUT, int DEPOSIT) {
        String query = "INSERT INTO APP.EDIT_BOOKING "
                + "(BOOKING_ID, "
                + "BOOKING_NAME, "
                + "PEOPLE, "
                + "ROOM, "
                + "CHECKIN, "
                + "CHECKOUT, "
                + "DEPOSIT) "
                + "VALUES ('"
                + BOOKING_ID + "','"
                + BOOKING_NAME + "',"
                + ROOM + ","
                + PEOPLE + ",'"
                + CHECKIN + "','"
                + CHECKOUT + "',"
                + DEPOSIT + ")";

        System.out.println("Inserting/n " + query);
        insertStatement(query);
    }

    public void insertStatement(String insert_query) {

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

    public static void openConnection() {
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

    public static void closeConnection() {
        try {
            if (conn != null) {
                conn.close();
                conn = null;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void createLogInTable() {
        System.out.println("creating booking table...");
        openConnection();
        PreparedStatement createBookingTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "LOGIN", null);
            if (!rl.next()) {
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.LOGIN (USERNAME VARCHAR(20) NOT NULL, STAFF_NAME VARCHAR(20))");
                createBookingTable.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("Booking table created");

    }

    public void createLog(String description) throws SQLException {
        openConnection();
        String user = " ";
        LocalDate date = LocalDate.now();
        LocalTime time = LocalTime.now();
           
        java.sql.Statement statement = conn.createStatement();
        ResultSet RS = statement.executeQuery("SELECT USERNAME FROM APP.LOGIN");
        while(RS.next()){
            user = RS.getString(1);
        } 
        Methods m = new Methods();
        m.insertStatement("INSERT INTO APP.HISTORYLOG (USERNAME, ADESCRIPTION) VALUES ("
            + "'" + user + "',"  
            + "'" + description + "')"); 
               
    }

    public static void createHISTORYLOGTable() {
        System.out.println("creating booking table...");
        openConnection();
        PreparedStatement createBookingTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "HISTORYLOG", null);
            if (!rl.next()) {
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.HISTORYLOG (USERNAME VARCHAR(20) NOT NULL,"
                        + "ATIME TIMESTAMP DEFAULT CURRENT_TIMESTAMP,"
                        + "ADESCRIPTION VARCHAR(100) NOT NULL)");
                createBookingTable.execute();
                System.out.println("YAY WE ANAGED TO CREATE THE HISTORY LOG");
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("history table created");

    }

    public static void createFROM_BOOKINGTable() {
        System.out.println("creating booking table...");
        openConnection();
        PreparedStatement createBookingTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "FROM_BOOKING", null);
            if (!rl.next()) {
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.FROM_BOOKING (CHECKIN DATE NOT NULL, "
                        + "CHECKOUT DATE NOT NULL)");
                createBookingTable.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("Booking table created");

    }

    public static void createFROM_ROOMTable() {
        System.out.println("creating booking table...");
        openConnection();
        PreparedStatement createBookingTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "FROM_ROOM", null);
            if (!rl.next()) {
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.FROM_ROOM (ROOM_NO INT, "
                        + "BOOKING_NAME VARCHAR(100),"
                        + "PEOPLE INT, "
                        + "CHECKINTIME VARCHAR(20),"
                        + "CHECKOUTTIME VARCHAR(20),"
                        + "CHECKIN DATE, "
                        + "CHECKOUT DATE)");
                createBookingTable.execute();

            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("Booking table created");

    }

    public static void createBookingTable() {
        System.out.println("creating booking table...");
        openConnection();
        PreparedStatement createBookingTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "BOOKING", null);
            if (!rl.next()) {
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.BOOKING (BOOKING_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1),"
                        + "REFERENCECODE VARCHAR(20) NOT NULL,"
                        + "BOOKING_NAME VARCHAR(100) NOT NULL,"
                        + "PEOPLE INT NOT NULL,"
                        + "ROOM INT NOT NULL,"
                        + "CHECKIN DATE NOT NULL,"
                        + "CHECKOUT DATE NOT NULL,"
                        + "CHECKINTIME VARCHAR(20) NOT NULL,"
                        + "CHECKOUTTIME VARCHAR(20) NOT NULL,"
                        + "BREAKFAST BOOLEAN DEFAULT FALSE,"
                        + "EARLYIN VARCHAR(10) DEFAULT 'No',"
                        + "LATEOUT VARCHAR(10) DEFAULT 'No', "
                        + "DEPOSIT INT DEFAULT 0, "
                        + "REMAINING INT DEFAULT 0)");
                createBookingTable.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("Booking table created");

    }

    public static void createBOOKING_AVAILABILITIESTable() {
        System.out.println("creating booking availabilities table...");
        openConnection();
        PreparedStatement createBookingAvailabilitiesTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "BOOKING_AVAILABILITIES", null);
            if (!rl.next()) {
                createBookingAvailabilitiesTable = conn.prepareStatement(
                        "CREATE TABLE APP.BOOKING_AVAILABILITIES (ROOM INT NOT NULL, "
                        + "REFERENCECODE VARCHAR(20) NOT NULL,"
                        + "DATE_BOOKED DATE NOT NULL,"
                        + "BREAKFAST VARCHAR(10) NOT NULL) ");
                createBookingAvailabilitiesTable.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("Booking table created");

    }

    public static void createEDIT_BOOKINGTable() {
        System.out.println("creating edit bookings table...");
        openConnection();
        PreparedStatement createBookingTable = null;
        ResultSet rl = null;
        try {
            DatabaseMetaData dbmd2 = conn.getMetaData();
            rl = dbmd2.getTables(null, "APP", "EDIT_BOOKING", null);
            if (!rl.next()) {
                createBookingTable = conn.prepareStatement(
                        "CREATE TABLE APP.EDIT_BOOKING (BOOKING_ID VARCHAR(20),"
                        + "BOOKING_NAME VARCHAR(100),"
                        + "PEOPLE INT,"
                        + "ROOM INT,"
                        + "CHECKIN VARCHAR(20),"
                        + "CHECKOUT VARCHAR(20),"
                        + "DEPOSIT INT)");
                createBookingTable.execute();
            }
        } catch (Exception e) {
            e.printStackTrace();

        }
        System.out.println("Booking table created");

    }

    public static void createStaffTable() {
        System.out.println("creating staff table...");
        PreparedStatement createStaffTable = null;
        PreparedStatement insertStaffValues = null;
        PreparedStatement deletestaff = null;
        ResultSet rs = null;

        openConnection();

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "STAFF", null);
            if (!rs.next()) {

                createStaffTable = conn.prepareStatement(
                        "CREATE TABLE APP.STAFF (STAFF_ID INT NOT NULL GENERATED ALWAYS AS IDENTITY (START WITH 1, INCREMENT BY 1), "
                        + "USERNAME VARCHAR(20) NOT NULL UNIQUE, "
                        + "PASSWORD VARCHAR(20) NOT NULL, "
                        + "STAFF_NAME VARCHAR(20), "
                        + "CREATION_DATE TIMESTAMP DEFAULT CURRENT_TIMESTAMP, "
                        + "ADMIN BOOLEAN NOT NULL)");
                createStaffTable.execute();
                System.out.println("staff table created");

                insertStaffValues = conn.prepareStatement(
                        "INSERT INTO APP.STAFF (USERNAME, "
                        + "PASSWORD, STAFF_NAME, ADMIN) VALUES "
                        + "('user', 'password', 'Chun-Tang Pai', true)");
                insertStaffValues.execute();
                System.out.println("admin staff values inserted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void readInRoomsAndCreateRoomsTable() {
        openConnection();
        System.out.println("reading in rooms...");

        PreparedStatement createRoomsTable = null;
        PreparedStatement dropRoomsTable = null;
        PreparedStatement insertRoomValues = null;

        try {
            DatabaseMetaData dbmd = conn.getMetaData();

            ResultSet room = dbmd.getTables(null, "APP", "ROOM", null);
            System.out.println("got metadata");

            if (!room.next()) {
                System.out.println("creating rooms table");
                // If the table does not already exist we create it
                createRoomsTable = conn.prepareStatement(
                        "CREATE TABLE APP.ROOM (ROOM_NO INT NOT NULL PRIMARY KEY, "
                        + "ROOM_TYPE VARCHAR(20) NOT NULL,"
                        + "COST INT NOT NULL)");
                createRoomsTable.execute();
                System.out.println("rooms table created");
            }

            String csvFile = "Rooms.csv";
            BufferedReader br = null;
            String line = "";
            String cvsSplitBy = ",";

            br = new BufferedReader(new FileReader(csvFile));
            while ((line = br.readLine()) != null) {
                // use comma as separator
                String[] Rooms = line.split(cvsSplitBy);

                if (!"RoomTypes".equals(Rooms[0])) {
                    int a = Integer.parseInt(Rooms[0]);
                    String b = Rooms[1];
                    int c = Integer.parseInt(Rooms[2]);
                    System.out.println(a + " " + b + " " + c);

                    insertRoomValues = conn.prepareStatement("INSERT INTO APP.ROOM(ROOM_NO, ROOM_TYPE, COST)"
                            + " VALUES( " + a + ",'" + b + "', " + c + ")");
                    insertRoomValues.execute();
                    System.out.println("room values inserted");
                }

            }

            java.sql.Statement statement = conn.createStatement();
            ResultSet viewRooms = statement.executeQuery("SELECT ROOM_NO, ROOM_TYPE FROM APP.ROOM");

            while (viewRooms.next()) {
                System.out.println(viewRooms.getString(1) + " " + viewRooms.getString(2) + " " + viewRooms.getString(3));
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

    }

    public static void createRoomTypes() throws SQLException {
        Methods.openConnection();

        PreparedStatement createRoomTypes = null;
        PreparedStatement insertRoomTypes = null;
        PreparedStatement dropRoomsTable = null;

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            ResultSet types = dbmd.getTables(null, "APP", "ROOM_TYPES", null);
            if (!types.next()) {
                createRoomTypes = conn.prepareStatement(
                        "CREATE TABLE APP.ROOM_TYPES(ROOM_TYPE VARCHAR(20) NOT NULL PRIMARY KEY, "
                        + "DESCRIPTION VARCHAR(100) NOT NULL,"
                        + "CAPACITY INT NOT NULL)");
                createRoomTypes.execute();
                System.out.println("room_types table created");

                String csvFile = "Rooms.csv";
                BufferedReader br = null;
                String line = "";
                String cvsSplitBy = ",";

                br = new BufferedReader(new FileReader(csvFile));
                while ((line = br.readLine()) != null) { // use comma as separator 
                    String[] rt = line.split(cvsSplitBy);

                    if ("RoomTypes".equals(rt[0])) {
                        String a = rt[1];
                        String b = rt[3];
                        int c = Integer.parseInt(rt[2]);
                        System.out.println(a + " " + b + " " + c);

                        insertRoomTypes = conn.prepareStatement(
                                "INSERT INTO APP.ROOM_TYPES(ROOM_TYPE,DESCRIPTION, CAPACITY)"
                                + " VALUES( '" + a + "', '" + b + "'," + c + ")");
                        insertRoomTypes.execute();
                        System.out.println("room values inserted");
                    }

                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        java.sql.Statement statement = conn.createStatement();
        ResultSet viewTypes = statement.executeQuery("SELECT * FROM APP.ROOM_TYPES");

        while (viewTypes.next()) {
            System.out.println(viewTypes.getString(1) + "" + viewTypes.getString(2) + " " + viewTypes.getString(3));
        }
    }

}
