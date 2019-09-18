/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.InhousePart;
import Model.Inventory;
import Model.OutsourcedPart;
import Model.Part;
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
public class ModifyInhousePartController implements Initializable {

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
    private RadioButton OuthouseRadio;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    private Part selectedPart;
    @FXML
    Label specialTextLabel;
    
            

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
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
        }
    }

    @FXML
    private void OuthouseRadioHandler(ActionEvent event) throws IOException {
        if(OuthouseRadio.isSelected()){
            specialTextLabel.setText("Company Name");
        }
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {
        try {
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
                    InhousePart newInhousePart = new InhousePart(Integer.parseInt(IDText.getText()), NameText.getText(), Integer.parseInt(InvText.getText()), (int) Double.parseDouble(PriceCostText.getText()), Integer.parseInt(MinText.getText()), Integer.parseInt(MaxText.getText()), Integer.parseInt(MachineIDText.getText()));
                    newInhousePart.setId(selectedPart.getId());
                    Inventory.allParts.add(newInhousePart);
                    Inventory.allParts.remove(selectedPart);
                }else if(OuthouseRadio.isSelected()){
                    OutsourcedPart newOutsourcedPart = new OutsourcedPart(Integer.parseInt(IDText.getText()), NameText.getText(), Integer.parseInt(InvText.getText()), (int) Double.parseDouble(PriceCostText.getText()), Integer.parseInt(MinText.getText()), Integer.parseInt(MaxText.getText()), MachineIDText.getText());
                    newOutsourcedPart.setId(selectedPart.getId());
                    Inventory.allParts.add(newOutsourcedPart);
                    Inventory.allParts.remove(selectedPart);
                }
                
                Stage stage;
                Parent root;
                stage=(Stage) SaveButton.getScene().getWindow();

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

    
    public void setInhousePart(Part selectedPart) {
        this.selectedPart = selectedPart;
        if(selectedPart instanceof InhousePart){
            InhouseRadio.setSelected(true);
            specialTextLabel.setText("Machine ID");
            IDText.setText(new Integer(selectedPart.getId()).toString());
            NameText.setText(selectedPart.getName());
            InvText.setText(new Integer(selectedPart.getStock()).toString());
            PriceCostText.setText(new Double(selectedPart.getPrice()).toString());
            MaxText.setText(new Integer(selectedPart.getMax()).toString());
            MinText.setText(new Integer(selectedPart.getMin()).toString());
            MachineIDText.setText(new Integer(((InhousePart) selectedPart).getMachineId()).toString());
        }

        if(selectedPart instanceof OutsourcedPart){
            OuthouseRadio.setSelected(true);
            specialTextLabel.setText("Company Name");
            IDText.setText(new Integer(selectedPart.getId()).toString());
            NameText.setText(selectedPart.getName());
            InvText.setText(new Integer(selectedPart.getStock()).toString());
            PriceCostText.setText(new Double(selectedPart.getPrice()).toString());
            MaxText.setText(new Integer(selectedPart.getMax()).toString());
            MinText.setText(new Integer(selectedPart.getMin()).toString());
            MachineIDText.setText(((OutsourcedPart) selectedPart).getCompanyName());

        }
    }
}
