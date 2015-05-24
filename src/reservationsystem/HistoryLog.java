/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author yuling
 */
public class HistoryLog {

    private StringProperty USER;
    private StringProperty DATE;
    private StringProperty TIME;
    private StringProperty ACTION;
    // private ObjectProperty AVAILABLE;
    //private  StringProperty AVAILABLE;

    public HistoryLog(String USER, String DATE, String TIME, String ACTION) {
        this.USER = new SimpleStringProperty(USER);
        this.DATE = new SimpleStringProperty(DATE);
        this.TIME = new SimpleStringProperty(TIME);
        this.ACTION = new SimpleStringProperty(ACTION);
    }

    public String getUSER() {
        return USER.get();
    }

    public String getDATE() {
        return DATE.get();
    }

    public String getTIME() {
        return TIME.get();
    }

    public String getACTION() {
        return ACTION.get();
    }

    public void setUSER(String USER) {
        this.USER.set(USER);
    }

    public void setDATE(String DATE) {
        this.DATE.set(DATE);
    }

    public void setTIME(String TIME) {
        this.TIME.set(TIME);
    }

    public void setACTION(String ACTION) {
        this.ACTION.set(ACTION);
    }
   
    //add the rest of the setters
    public StringProperty USERProperty() {
        return USER;
    }

    public StringProperty DATEProperty() {
        return DATE;
    }

    public StringProperty TIMEProperty() {
        return TIME;
    }

    public StringProperty ACTIONProperty() {
        return ACTION;
    }

    /**
     * public ObjectProperty AVAILABLEProperty() { return AVAILABLE; }
     *
     *
     * public StringProperty AVAILABLEProperty() { return AVAILABLE; }
     */
}


