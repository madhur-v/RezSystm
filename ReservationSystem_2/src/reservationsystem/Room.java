/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.util.ArrayList;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yuling
 */
public class Room {
   private SimpleIntegerProperty ROOM_NO;
    private SimpleStringProperty ROOM_TYPE;
    private SimpleIntegerProperty CAPACITY;
    private SimpleIntegerProperty COST;
    private SimpleBooleanProperty AVAILABLE;
    
     
    public Room(int ROOM_NO, String ROOM_TYPE) {
        this.ROOM_NO = new SimpleIntegerProperty(ROOM_NO);
        this.ROOM_TYPE = new SimpleStringProperty(ROOM_TYPE);
    }

    public Integer getROOM_NO() {
        return ROOM_NO.get();
    }

    public String getROOM_TYPE() {
        return ROOM_TYPE.get();
    }
    
    private Integer getCAPACITY() {
        return CAPACITY.get();
    }
    
    private Integer getCOST() {
        return COST.get();
    }
    
    private Boolean getAVAILABLE() {
        return AVAILABLE.get();
    }
        
}
