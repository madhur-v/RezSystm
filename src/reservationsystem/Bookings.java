/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.sql.Date;
import java.text.SimpleDateFormat;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;

/**
 *
 * @author yuling
 */
public class Bookings {

    private SimpleStringProperty ID;
    private SimpleStringProperty BOOKING_NAME;
    private SimpleIntegerProperty GUESTS;
    private SimpleIntegerProperty ROOM_NUMBER;
    //private SimpleDateFormat checkInDate;
    private SimpleStringProperty checkInDate;
    private SimpleStringProperty checkOutDate;
    //private SimpleDateFormat checkOutDate;
    private SimpleStringProperty breakfast;
    private SimpleStringProperty earlyIn;
    private SimpleStringProperty lateOut;
    private SimpleIntegerProperty deposit;
    private SimpleIntegerProperty remaining;

    public Bookings(String ID, String BOOKING_NAME, int GUESTS, int ROOM_NUMBER, String breakfast, String earlyIn, String lateOut, int deposit, int remaining) {
        this.ID = new SimpleStringProperty(ID);
        this.BOOKING_NAME = new SimpleStringProperty(BOOKING_NAME);
        this.GUESTS = new SimpleIntegerProperty(GUESTS);
        this.ROOM_NUMBER = new SimpleIntegerProperty(ROOM_NUMBER);
        this.breakfast = new SimpleStringProperty(breakfast);
        this.earlyIn = new SimpleStringProperty(earlyIn);
        this.lateOut = new SimpleStringProperty(lateOut);
        this.deposit = new SimpleIntegerProperty(deposit);
        this.remaining = new SimpleIntegerProperty(remaining);
    }

    public Bookings(String ID, String BOOKING_NAME) {
        this.ID = new SimpleStringProperty(ID);
        this.BOOKING_NAME = new SimpleStringProperty(BOOKING_NAME);
    }

    public Bookings(String ID, String BOOKING_NAME, int GUESTS, int ROOM_NUMBER, String checkInDate, String checkOutDate, String breakfast, String earlyIn, String lateOut, int deposit, int remaining) {
        this.ID = new SimpleStringProperty(ID);
        this.BOOKING_NAME = new SimpleStringProperty(BOOKING_NAME);
        this.GUESTS = new SimpleIntegerProperty(GUESTS);
        this.ROOM_NUMBER = new SimpleIntegerProperty(ROOM_NUMBER);
        this.checkInDate = new SimpleStringProperty(checkInDate);
        this.checkOutDate = new SimpleStringProperty(checkOutDate);
        this.breakfast = new SimpleStringProperty(breakfast);
        this.earlyIn = new SimpleStringProperty(earlyIn);
        this.lateOut = new SimpleStringProperty(lateOut);
        this.deposit = new SimpleIntegerProperty(deposit);
        this.remaining = new SimpleIntegerProperty(remaining);
    }

    public String getID() {
        return ID.get();
    }

    public String getBOOKING_NAME() {
        return BOOKING_NAME.get();
    }

    public int getGUESTS() {
        return GUESTS.get();
    }

    public int getROOM_NUMBER() {
        return ROOM_NUMBER.get();
    }

    public String getCheckInDate() {
        return checkInDate.get();
    }

    public String getCheckOutDate() {
        return checkOutDate.get();
    }

    public String getBreakfast() {
        return breakfast.get();
    }

    public String getEarlyIn() {
        return earlyIn.get();
    }

    public String getLateOut() {
        return lateOut.get();
    }

    public int getDeposit() {
        return deposit.get();
    }

    public int getRemaining() {
        return remaining.get();
    }

}
