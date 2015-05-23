/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yuling
 */
public class Rooms {
    
    private  StringProperty ROOM_NO;
    private  StringProperty ROOM_TYPE;
    private  StringProperty CAPACITY;
    private  StringProperty COST;
    private  StringProperty AVAILABLE;
    
     
    public Rooms(String ROOM_NO, String ROOM_TYPE, String CAPACITY, String COST, String AVAILABLE) {
        this.ROOM_NO = new SimpleStringProperty(ROOM_NO);
        this.ROOM_TYPE = new SimpleStringProperty(ROOM_TYPE);
        this.CAPACITY = new SimpleStringProperty(CAPACITY);
        this.COST = new SimpleStringProperty(COST);
        this.AVAILABLE = new SimpleStringProperty(AVAILABLE);
    }
   

    public String getROOM_NO() {
        return ROOM_NO.get();
    }

    public String getROOM_TYPE() {
        return ROOM_TYPE.get();
    }
    
    public String getCAPACITY() {
        return CAPACITY.get();
    }
    
    public String getCOST() {
        return COST.get();
    }
    
   public String getAVAILABLE() {
        return AVAILABLE.get();
    }
    
    public void setROOM_NO(String ROOM_NO) {
        this.ROOM_NO.set(ROOM_NO);
    }
    
    public void setROOM_TYPE(String ROOM_TYPE) {
        this.ROOM_TYPE.set(ROOM_TYPE);
    }
    
    //add the rest of the setters
    
    public StringProperty ROOM_NOProperty() {
        return ROOM_NO;
    }
    
    public StringProperty ROOM_TYPEProperty() {
        return ROOM_TYPE;
    }
    
    public StringProperty CAPACITYProperty() {
        return CAPACITY;
    }
    
    public StringProperty COSTProperty() {
        return COST;
    }
    
    public StringProperty AVAILABLEProperty() {
        return AVAILABLE;
    }
    
}

