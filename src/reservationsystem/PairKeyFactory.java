/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Callback;
import javafx.util.Pair;

/**
 *
 * @author yuling
 */
        class PairKeyFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, String>, ObservableValue<String>> {
    @Override
    public ObservableValue<String> call(TableColumn.CellDataFeatures<Pair<String, Object>, String> data) {
        return new ReadOnlyObjectWrapper<>(data.getValue().getKey());
    }
}

class PairValueFactory implements Callback<TableColumn.CellDataFeatures<Pair<String, Object>, Object>, ObservableValue<Object>> {
    @SuppressWarnings("unchecked")
    @Override
    public ObservableValue<Object> call(TableColumn.CellDataFeatures<Pair<String, Object>, Object> data) {
        Object value = data.getValue().getValue();
        return (value instanceof ObservableValue)
                ? (ObservableValue) value
                : new ReadOnlyObjectWrapper<>(value);
    }
}

class PairValueCell extends TableCell<Pair<String, Object>, Object> {
    @Override
    protected void updateItem(Object item, boolean empty) {
        super.updateItem(item, empty);

        if (item != null) {
            if (item instanceof String) {
                setText((String) item);
                setGraphic(null);
            } else if (item instanceof Integer) {
                setText(Integer.toString((Integer) item));
                setGraphic(null);
            } else if (item instanceof Boolean) {
                CheckBox checkBox = new CheckBox();
                checkBox.setAlignment(Pos.CENTER);
                
                
                checkBox.setSelected((boolean) item);
                
                        checkBox.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent t) {
                // get Selected Item
                Pair pear = (Pair) PairValueCell.this.getTableView().getItems().get(PairValueCell.this.getIndex());
              
              //remove selected item from the table list
//+ pear.getDate() +
                System.out.println("current pair: " + pear.getKey()  + " is now: " + checkBox.isSelected());
               // NewBookingController.addBreakfast(currentBreakfast.getDate(), checkbox.isSelected());
                 System.out.println("current booking: " + pear.getKey() + " is now: " + checkBox.isSelected());
                 String date = pear.getKey().toString();
                 
                NewBookingController.addBreakfast(date, checkBox.isSelected());              
                
                
               // Breakfast test = (Breakfast) CheckCell.this.getTableView().getItems().get(0);
               // System.out.println("test date: " + test.getDate() + "test breakfast is: " + checkBox.isSelected());
                /**
                ObservableList selectedCells = FXCollections.observableArrayList();
                TablePosition tablePosition = (TablePosition) CheckCell.this.getTableView().getItems().get(CheckCell.this.getIndex());

                tablePosition.getRow();
                System.out.println(tablePosition.getRow());
                * */
            }
        });
                        
                setGraphic(checkBox);
            } else if (item instanceof Image) {
                setText(null);
                ImageView imageView = new ImageView((Image) item);
                imageView.setFitWidth(100);
                imageView.setPreserveRatio(true);
                imageView.setSmooth(true);
                setGraphic(imageView);
            } else {
                setText("N/A");
                setGraphic(null);
            }
        } else {
            setText(null);
            setGraphic(null);
        }
    }
}