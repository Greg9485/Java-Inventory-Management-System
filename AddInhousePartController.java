/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import java.io.IOException;
import java.net.URL;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author greg9485
 */
public class AddInhousePartController implements Initializable {

    @FXML
    private TextField IDText;
    @FXML
    private TextField NameText;
    @FXML
    private TextField InvText;
    @FXML
    private TextField PriceCostText;
    @FXML
    private TextField MaxText;
    @FXML
    private TextField MinText;
    @FXML
    private TextField MachineIDText;
    @FXML
    private RadioButton InhouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private Button InhouseSaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    Label specialTextLabel;

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
    }    

    @FXML
    private void IDTextHandler(ActionEvent event) {
    }

    @FXML
    private void NameTextHandler(ActionEvent event) {
    }

    @FXML
    private void InvTextHandler(ActionEvent event) {
    }

    @FXML
    private void PriceCostTextHandler(ActionEvent event) {
    }

    @FXML
    private void MaxTextHandler(ActionEvent event) {
    }

    @FXML
    private void MinTextHandler(ActionEvent event) {
    }

    @FXML
    private void MachineIDTextHandler(ActionEvent event) {
      
    }

    @FXML
    private void InhouseRadioHandler(ActionEvent event) {
        if(InhouseRadio.isSelected()){
            specialTextLabel.setText("Machine ID");
            MachineIDText.setPromptText("Machine ID");
        }
    }

    @FXML
    private void OutsourcedRadioHandler(ActionEvent event) throws IOException {
     if(OutsourcedRadio.isSelected()){
            specialTextLabel.setText("Company Name");
            MachineIDText.setPromptText("Company Name");
        }
    }


    
    @FXML
    private void InhouseSaveButtonHandler(ActionEvent event) throws IOException {

    try {            
        int id = 0;
        String name = NameText.getText();
        int stock = Integer.parseInt(InvText.getText());
        double price = Double.parseDouble(PriceCostText.getText());
        int max = Integer.parseInt(MaxText.getText());
        int min = Integer.parseInt(MinText.getText());


        if(Integer.parseInt(MinText.getText()) >= Integer.parseInt(MaxText.getText())){
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Invalid Values");
          alert.setContentText("Minium must be less than Maximum parts.");
          alert.showAndWait(); 
        }else if(Integer.parseInt(InvText.getText()) < Integer.parseInt(MinText.getText()) || Integer.parseInt(InvText.getText()) > Integer.parseInt(MaxText.getText())){
          Alert alert = new Alert(Alert.AlertType.WARNING);
          alert.setTitle("Invalid Values");
          alert.setContentText("Inventory level must between the maximum and minimum number of parts.");
          alert.showAndWait();   
        }else{
             if(InhouseRadio.isSelected()){
                int machineID = Integer.parseInt(MachineIDText.getText());    
                Inventory.addPart(new InhousePart(id, name, price, stock, min, max, machineID));
            }    

            if(OutsourcedRadio.isSelected()){
                String companyName = MachineIDText.getText();
                Inventory.addPart(new OutsourcedPart(id, name, price, stock, min, max, companyName));
            }    

            Stage stage;
            Parent root;
            stage=(Stage) InhouseSaveButton.getScene().getWindow();

            FXMLLoader loader= new FXMLLoader(getClass().getResource("Main Screen.fxml"));

            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please enter the correct value for each field! \nName and Company Name must be Strings. \nInv, Max, Min and Machine ID must be an integer. \nPrice/Cost must be a double. \nYour Error is: \n" + e);
            alert.showAndWait();
        }
    }
        
    @FXML
    private void CancelButtonHandler(ActionEvent event) throws IOException {
     
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to cancel? No changes will be saved.");
        alert.setTitle("Exit Alert!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
            Stage stage;
            Parent root;
            stage=(Stage) CancelButton.getScene().getWindow();

            FXMLLoader loader= new FXMLLoader(getClass().getResource("Main Screen.fxml"));

            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();   
        }
    }
}
