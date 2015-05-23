/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package reservationsystem;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.geometry.Side;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

/**
 * FXML Controller class
 *
 * @author yuling
 */
public class BackgroundController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    Tab tab1 = new Tab();        
    //tab1.setText("I hope this works");
    Label label = new Label("I hope this works");
    label.setRotate(90);
    //tabPane.setTabMaxHeight(350);
    tabPane.setTabMinHeight(350);
    tabPane.setTabMaxWidth(50);
    tab1.setGraphic(label);
    //tabPane.setTabMinWidth(300);
    
    //tab1.setRotate(90);
    
    tabPane.getTabs().add(tab1);
    tabPane.setSide(Side.LEFT);
    
    
    }    
    
    @FXML 
    private TabPane tabPane;
    
    @FXML
    private Tab tab;


    
    
}
