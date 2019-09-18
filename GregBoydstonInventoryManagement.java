/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gregboydstoninventorymanagement;

import Model.InhousePart;
import Model.Inventory;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

/**
 *
 * @author greg9485
 * 
 * // TODO //
 * 
 *         1. FOR SOME REASON THE INHOUSE AND OUTSOURCED TOGGLE BUTTONS ARENT DEFINED - NEED TO ADJUST SO INHOUSE/OUTSOURCED PART SCREENS ALTERNATE
 *              WHEN RADIO BUTTONS ARE SELECTED
 *         2. NEED TO THROW ERRORS WHEN INPROPPER INPUT IS ENTERED INTO TEXT FIELDS
 *         3. NEED TO CREATE EXIT BUTTON FUNCTIONALITY 
 * 
 */
public class GregBoydstonInventoryManagement extends Application {
    
    
    @Override
    public void start(Stage stage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/View_Controller/Main Screen.fxml"));
        
        Scene scene = new Scene(root);
        
        stage.setTitle("Inventory Management System");
        stage.setScene(scene);
        stage.show();
    }
     

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        launch(args);
    }
    
}
