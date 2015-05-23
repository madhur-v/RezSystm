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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TablePosition;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 *
 * @author yuling
 */
public class CheckCell extends TableCell<Breakfast, Boolean> {

    public CheckBox checkbox = new CheckBox();
    int selectedIndex = CheckCell.this.getIndex();

    public CheckCell() {
        this.checkbox = new CheckBox(); 
        //Action when the button is pressed
        checkbox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // get Selected Item
                Breakfast currentBreakfast = (Breakfast) CheckCell.this.getTableView().getItems().get(CheckCell.this.getIndex());
                //remove selected item from the table list

                System.out.println("current booking: " + currentBreakfast.getDate() + " is now: " + checkbox.isSelected());
                NewBookingController.addBreakfast(currentBreakfast.getDate(), checkbox.isSelected());
               
                
                
                Breakfast test = (Breakfast) CheckCell.this.getTableView().getItems().get(0);
                System.out.println("test date: " + test.getDate() + "test breakfast is: " + checkbox.isSelected());
                /**
                ObservableList selectedCells = FXCollections.observableArrayList();
                TablePosition tablePosition = (TablePosition) CheckCell.this.getTableView().getItems().get(CheckCell.this.getIndex());

                tablePosition.getRow();
                System.out.println(tablePosition.getRow());
                * */
            }
        });
    }
    
     public CheckCell(boolean value) {
         this.checkbox.setSelected(value);
        //Action when the button is pressed
        checkbox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // get Selected Item
                Breakfast currentBreakfast = (Breakfast) CheckCell.this.getTableView().getItems().get(CheckCell.this.getIndex());
                //remove selected item from the table list

                System.out.println("current booking: " + currentBreakfast.getDate() + " is now: " + checkbox.isSelected());
                NewBookingController.addBreakfast(currentBreakfast.getDate(), checkbox.isSelected());
               
                
                
                Breakfast test = (Breakfast) CheckCell.this.getTableView().getItems().get(0);
                System.out.println("test date: " + test.getDate() + "test breakfast is: " + checkbox.isSelected());
                /**
                ObservableList selectedCells = FXCollections.observableArrayList();
                TablePosition tablePosition = (TablePosition) CheckCell.this.getTableView().getItems().get(CheckCell.this.getIndex());

                tablePosition.getRow();
                System.out.println(tablePosition.getRow());
                * */
            }
        });
    }
    public void setSelected(boolean check) {
        this.checkbox.setSelected(true);
    }
    /**
    
    public void addEntry(String startDate, String endDate) {
        

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar c = Calendar.getInstance();
            //c.setTime(sdf.parse(endDate));
            c.add(Calendar.DATE, 1);  // number of days to add
            endDate = sdf.format(c.getTime());

 
            while (!startDate.equals(endDate)) {
                System.out.println("startDate is now : " + startDate);
                //System.out.println("startDate is initially: " + startDate);

                Breakfast test = (Breakfast) CheckCell.this.getTableView().getItems().get(0);
                System.out.println("test date: " + test.getDate() + "test breakfast is: " + checkbox.isSelected());
                if(checkbox.isSelected()== true) {
                    //test.setBreakfast(true);
                    NewBookingController.addBreakfast(test.getDate(), true);
                    
                }
     
                String query2 = "INSERT INTO APP.BOOKING_AVAILABILITIES "
                        + "(ROOM, "
                        + " DATE_BOOKED,"
                        + "BREAKFAST) VALUES "
                        + "(2, "
                        + " '" + startDate + "', "
                        + "FALSE)";
                System.out.println("Inserting/n" + query2);
                insertStatement(query2);

               // c.setTime(sdf.parse(startDate));
                c.add(Calendar.DATE, 1);  // number of days to add
                startDate = sdf.format(c.getTime());
            }

        }

    */
    

    private void gotobookinghome(ActionEvent t) throws IOException {
        System.out.println("You clicked me!");
        Parent parent = FXMLLoader.load(getClass().getResource("EditBooking.fxml"));

        Scene scene = new Scene(parent);
        // Stage stage = new Stage();

        Stage stage = (Stage) ((Node) t.getSource()).getScene().getWindow();
        stage.setScene(scene);
        stage.show();
    }

    //Display button if the row is not empty
    @Override
    protected void updateItem(Boolean t, boolean empty) {
        super.updateItem(t, empty);
        if (!empty) {
            setGraphic(checkbox);
        }
    }

    public Connection conn;

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

}

/**
 * private class ButtonCell extends TableCell<Record, Boolean> { final Button
 * cellButton = new Button("Edit");
 *
 * ButtonCell(final TableView tblView){
 *
 * cellButton.setOnAction(new EventHandler<ActionEvent>(){
 *
 * @Override public void handle(ActionEvent t) { int selectdIndex =
 * getTableRow().getIndex();
 *
 * //Create a new table show details of the selected item Record selectedRecord
 * = (Record)tblView.getItems().get(selectdIndex); ObservableList<SubRecord>
 * subDataList = FXCollections.observableArrayList( new SubRecord("ID",
 * selectedRecord.getId()), new SubRecord("Monday",
 * selectedRecord.getValue_0()), new SubRecord("Tuesday",
 * selectedRecord.getValue_1()), new SubRecord("Wednesday",
 * selectedRecord.getValue_2()), new SubRecord("Thursday",
 * selectedRecord.getValue_3()), new SubRecord("Friday",
 * selectedRecord.getValue_4()));
 *
 * TableColumn columnfield = new TableColumn("Field");
 * columnfield.setCellValueFactory( new
 * PropertyValueFactory<Record,String>("fieldSubRecordName"));
 *
 * TableColumn columnValue = new TableColumn("Value");
 * columnValue.setCellValueFactory( new
 * PropertyValueFactory<SubRecord,Integer>("fieldSubRecordValue"));
 *
 * TableView<SubRecord> subTableView = new TableView<>();
 * subTableView.setItems(subDataList);
 * subTableView.getColumns().addAll(columnfield, columnValue);
 *
 * Stage myDialog = new Stage(); myDialog.initModality(Modality.WINDOW_MODAL);
 *
 * Scene myDialogScene = new Scene(VBoxBuilder.create() .children(subTableView)
 * .alignment(Pos.CENTER) .padding(new Insets(10)) .build());
 *
 * myDialog.setScene(myDialogScene); myDialog.show(); } }); }
 *
 * //Display button if the row is not empty
 * @Override protected void updateItem(Boolean t, boolean empty) {
 * super.updateItem(t, empty); if(!empty){ setGraphic(cellButton); } } }
    *
 */
