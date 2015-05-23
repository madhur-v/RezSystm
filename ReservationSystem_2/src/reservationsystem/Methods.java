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
                        + "REFERENCECODE VARCHAR(10) NOT NULL,"
                        + "BOOKING_NAME VARCHAR(100) NOT NULL,"
                        + "PEOPLE INT NOT NULL,"
                        + "ROOM INT NOT NULL,"
                        + "CHECKIN DATE NOT NULL,"
                        + "CHECKOUT DATE NOT NULL,"
                        + "CHECKINTIME VARCHAR(20) NOT NULL,"
                        + "CHECKOUTTIME VARCHAR(20) NOT NULL,"
                        + "BREAKFAST BOOLEAN DEFAULT FALSE,"
                        + "EARLYIN VARCHAR(10) DEFAULT 'NO',"
                        + "LATEOUT VARCHAR(10) DEFAULT 'NO', "
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
                        + "PASSWORD, ADMIN) VALUES "
                        + "('user', 'password', true)");
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

            String csvFile = "C:\\Users\\yuling\\Documents\\NetBeansProjects\\ReservationSystem_2\\Rooms.csv";
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

                String csvFile = "C:\\Users\\yuling\\Documents\\NetBeansProjects\\ReservationSystem_2\\Rooms.csv";
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

    public static void createServiceTable() {
        System.out.println("creating service table...");
        PreparedStatement createServiceTable = null;
        ResultSet rs = null;

        openConnection();

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "SERVICE", null);
            if (!rs.next()) {
                createServiceTable = conn.prepareStatement(
                        "CREATE TABLE APP.SERVICE (NAME VARCHAR(50), "
                        + "REFERENCECODE VARCHAR(20),"
                        + "QUANTITY INT,"
                        + "CHARGE_DATE DATE)");
                        
                createServiceTable.execute();
                System.out.println("service table created");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void createItemTable() {
        System.out.println("creating item table...");
        PreparedStatement createItemTable = null;
        PreparedStatement insertItemValues = null;
        ResultSet rs = null;

        openConnection();

        try {
            DatabaseMetaData dbmd = conn.getMetaData();
            rs = dbmd.getTables(null, "APP", "ITEMS", null);
            if (!rs.next()) {
                createItemTable = conn.prepareStatement(
                        "CREATE TABLE APP.ITEMS (ITEM_ID INT, "
                        + "COST INT, "
                        + "NAME VARCHAR(50))");
                createItemTable.execute();
                System.out.println("item table created");

                insertItemValues = conn.prepareStatement(
                        "INSERT INTO APP.ITEMS (ITEM_ID, "
                        + "COST, NAME) VALUES "
                        + "(1, 10, 'Chicken Sandwich')");
                insertItemValues.execute();
                System.out.println("item values inserted");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
