/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

/**
 *
 * @author yuling
 */
public class Breakfast {
    private SimpleStringProperty date;
    private SimpleBooleanProperty breakfast;
    
    
    public Breakfast(String date, Boolean breakfast) {
        this.date = new SimpleStringProperty(date);
        this.breakfast = new SimpleBooleanProperty(breakfast);
    }
      
    
    public String getDate() {
        return date.get();
    }
    
    public Boolean getBreakfast(){
        return breakfast.get();
    }    

   public boolean isChecked() {
      return breakfast.get();
    }
   
      public void setDate(String date) {
      this.date.set(date);
    }

    public void setBreakfast(boolean checked) {
      this.breakfast.set(checked);
    }
   
   public StringProperty dateProperty() {
      return date;
    }

    public BooleanProperty checkedProperty() {
      return breakfast;
    }      
  
}
