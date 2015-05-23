/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import static reservationsystem.Methods.conn;
import static reservationsystem.Methods.openConnection;

/**
 * FXML Controller class
 *
 * @author user
 */
public class GenerateInvoiceController implements Initializable {

    @FXML
    private Button returnhomebutton;
    @FXML
    private Text generateinvoicetext;
    @FXML
    private Label roomlabel;
    @FXML
    private TextField roomtf;
    @FXML
    private Button generatebutton;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

    @FXML
    private void gotohomepage(ActionEvent event) throws IOException {
        PageSwitch ps = new PageSwitch();
        ps.homepage(event);
    }

    @FXML
    private void generateinvoice(ActionEvent event) {
        openConnection();
        int roomnum = Integer.parseInt(roomtf.getText());
        String refcode = null;
        String remainingpayment = null;
        java.util.Date date = null;

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
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }

        try {
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT REFERENCECODE, REMAINING FROM BOOKING "
                    + "WHERE REFERENCECODE = "
                    + "'" + refcode + "'");
            while (rs.next()) {
                remainingpayment = rs.getString("REMAINING");
                System.out.println(remainingpayment);
            }
        } catch (Exception ex) {
            System.out.println("error" + ex);
        }
        ReportItem_Cost(refcode, remainingpayment, roomnum);
        roomtf.clear();
    }

    public static void ReportItem_Cost(String refcode, String remainingpayment, int roomnum) {
        Double total_cost = 0.00;
        //array list 
        ArrayList<String> Quantity = new ArrayList<String>();
        ArrayList<String> Name = new ArrayList<String>();
        ArrayList<java.util.Date> Date = new ArrayList<java.util.Date>();
        ArrayList<String> Cost = new ArrayList<String>();

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

        try {
            java.sql.Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT REFERENCECODE, SERVICE.NAME, QUANTITY, COST, CHARGE_DATE FROM SERVICE "
                    + "INNER JOIN ITEMS ON "
                    + "ITEMS.NAME = SERVICE.NAME "
                    + "WHERE REFERENCECODE = "
                    + "'" + refcode + "'");

            while (rs.next()) {
                String quantity = rs.getString("QUANTITY");
                String cost = rs.getString("COST");
                String name = rs.getString("NAME");
                java.util.Date date = rs.getTimestamp("CHARGE_DATE");
                total_cost = total_cost + Double.valueOf(cost) * Double.valueOf(quantity);
                System.out.println("Total cost is: " + total_cost);

                Name.add(name);
                Quantity.add(quantity);
                Date.add(date);
                Cost.add(cost);
            }
            PDF(Name, Quantity, Date, Cost, total_cost, refcode, remainingpayment, roomnum);

        } catch (Exception ex) {

            System.out.println("error" + ex);

        }

    }

    public static void PDF(ArrayList<String> Name, ArrayList<String> Quantity, ArrayList<java.util.Date> Date, ArrayList<String> Cost, double total, String refcode, String remainingpayment, int roomnum) throws DocumentException, FileNotFoundException, IOException {
        System.out.println("pdf");
        Document document = new Document();
        String b = "#: Item---Quantity-----Date---------------Cost" + "\n";

        for (int i = 0; i < Name.size(); i++) {
            Date date = Date.get(i);
            SimpleDateFormat DATE_FORMAT = new SimpleDateFormat("dd-MM-yyyy");
            String date2 = DATE_FORMAT.format(date);
            String a = i + 1 + ": " + Name.get(i) + "---" + Quantity.get(i) + "------" + date2 + "---------------" + Cost.get(i);
            b = b + "\n" + a;
        }

        System.out.println(b);

        PdfWriter.getInstance(document, new FileOutputStream("Invoice.pdf"));

        document.open();
        Paragraph P = new Paragraph();
        P.add("Services Invoice" + "\n" + "\n" + "\n");
        P.add("Booking number:  " + refcode + "\n");
        P.add("Room number:  " + roomnum + "\n" + "\n");
        P.add(b + "\n" + "\n");
        String t = "Services Total: $" + total + "0" + "\n";
        P.add(t);
        P.add("Booking Payment Remaining: $" + remainingpayment + ".00" + "\n");
        P.add("Total Due: $" + (Double.valueOf(total)+Double.valueOf(remainingpayment)) + "0");
        document.add(P);
        document.close();
        String pdfFile = "Invoice.pdf";
        Runtime.getRuntime().exec("rundll32 url.dll,FileProtocolHandler " + pdfFile);

    }

}
