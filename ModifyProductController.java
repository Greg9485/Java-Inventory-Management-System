/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Inventory;
import Model.Part;
import Model.Product;
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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 * FXML Controller class
 *
 * @author greg9485
 */
public class ModifyProductController implements Initializable {

    
    Product product;
    
    @FXML
    private TextField IDText;
    @FXML
    private TextField NameText;
    @FXML
    private TextField InvText;
    @FXML
    private TextField PriceText;
    @FXML
    private TextField MaxText;
    @FXML
    private TextField MinText;
    @FXML
    private Button SearchButton;
    @FXML
    private Button AddButton;
    @FXML
    private Button DeleteButton;
    @FXML
    private Button SaveButton;
    @FXML
    private Button CancelButton;
    @FXML
    private TextField SearchText;
    private Product selectedProduct;


    @FXML
    TableView<Part> modifyProductPartTableview1;
    @FXML
    private TableColumn<Part, String> modifyProductPartIdColumn1;
    @FXML
    private TableColumn<Part, String> modifyProductPartNameColumn1;
    @FXML
    private TableColumn<Part, Integer> modifyProductPartInvLevelColumn1;
    @FXML
    private TableColumn<Part, Double> modifyProductPartPPUColumn1;
    @FXML
    TableView<Part> modifyProductPartTableview2;
    @FXML
    TableColumn<Part, String> modifyProductPartIdColumn2;
    @FXML
    TableColumn<Part, String> modifyProductPartNameColumn2;
    @FXML
    TableColumn<Part, Integer> modifyProductPartInvLevelColumn2;
    @FXML
    TableColumn<Part, Double> modifyProductPartPPUColumn2;
    
    
    
    /**
     * Initializes the controller class.
     */
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
    private void PriceTextHandler(ActionEvent event) {
    }

    @FXML
    private void MaxTextHandler(ActionEvent event) {
    }

    @FXML
    private void MinTextHandler(ActionEvent event) {
    }

    @FXML
    private void SearchButtonHandler(ActionEvent event) {
    String searchPart = SearchText.getText();
            for(Part partSearched:Inventory.getAllParts()){
                if(partSearched.getName().equals(searchPart)||Integer.toString(partSearched.getId()).equals(searchPart))modifyProductPartTableview1.getSelectionModel().select(partSearched);
        }
    }

    @FXML
    private void AddButtonHandler(ActionEvent event) {
        if(modifyProductPartTableview1.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setContentText("You must select a part before you can add it.");
            alert.showAndWait();
        }else{
            Part part = modifyProductPartTableview1.getSelectionModel().getSelectedItem();
            selectedProduct.addAssociatedParts(part);   
        }
    }

    @FXML
    private void DeleteButtonHandler(ActionEvent event) {
        if(modifyProductPartTableview2.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected!");
            alert.setContentText("You must select a part before you can delete it.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part from your product?");
            alert.setTitle("Deletion Confirmation!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Part selectedPart = modifyProductPartTableview2.getSelectionModel().getSelectedItem();
                selectedProduct.deleteAssociatedPart(selectedPart);
            }
        }
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {
        try {
            if(Integer.parseInt(MinText.getText()) >= Integer.parseInt(MaxText.getText())){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Invalid Values");
               alert.setContentText("Minium must be less than Maximum products.");
               alert.showAndWait(); 
           }else if(Integer.parseInt(InvText.getText()) < Integer.parseInt(MinText.getText()) || Integer.parseInt(InvText.getText()) > Integer.parseInt(MaxText.getText())){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Invalid Values");
               alert.setContentText("Inventory level must between the maximum and minimum number of products.");
               alert.showAndWait();   
           }else if(modifyProductPartTableview2.getItems().isEmpty() == true){
               Alert alert = new Alert(Alert.AlertType.WARNING);
               alert.setTitle("Invalid Values");
               alert.setContentText("You must add at least 1 part to a product.");
               alert.showAndWait();
           }else {

               Product newProduct = new Product(Integer.parseInt(IDText.getText()), NameText.getText(), Integer.parseInt(InvText.getText()), (int) Double.parseDouble(PriceText.getText()), Integer.parseInt(MaxText.getText()), Integer.parseInt(MinText.getText()));
               newProduct.setId(selectedProduct.getId());
               Inventory.allProducts.add(newProduct);
               Inventory.allProducts.remove(selectedProduct);

               
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
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error!");
            alert.setContentText("Please enter the correct value for each field! \nName must be a String. \nInv, Max, Min and Machine ID must be an integer. \nPrice must be a double. \nYour Error is: \n" + e);
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

    @FXML
    private void SearchTextHandler(ActionEvent event) {
    }

    void setProduct(Product selectedProduct) {
        this.selectedProduct = selectedProduct;
        IDText.setText(new Integer(selectedProduct.getId()).toString());
        NameText.setText(selectedProduct.getName());
        InvText.setText(new Integer(selectedProduct.getStock()).toString());
        PriceText.setText(new Double(selectedProduct.getPrice()).toString());
        MaxText.setText(new Integer(selectedProduct.getMax()).toString());
        MinText.setText(new Integer(selectedProduct.getMin()).toString());    
        
        modifyProductPartTableview2.setItems(selectedProduct.getAssociatedParts());
        modifyProductPartIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartInvLevelColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPPUColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
        
    }
    
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        
        //Top TableView
        modifyProductPartTableview1.setItems(Inventory.getAllParts());       
        modifyProductPartIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyProductPartNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyProductPartInvLevelColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyProductPartPPUColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
       
        //Bottom TableView        
      
    }    
}
