/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View_Controller;

import Model.Product;
import Model.Inventory;
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
public class AddProductController implements Initializable {

    Product product;
    int productID;
    
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
    @FXML
    TableView<Part> addProductPartTableview;
    @FXML
    private TableColumn<Part, Integer> partIdColumn1;
    @FXML
    private TableColumn<Part, String> partNameColumn1;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn1;
    @FXML
    private TableColumn<Part, Double> partPriceCostColumn1;
    @FXML
    TableView<Part> productPartTableview;
    @FXML
    private TableColumn<Part, Integer> partIdColumn2;
    @FXML
    private TableColumn<Part, String> partNameColumn2;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn2;
    @FXML
    private TableColumn<Part, Double> partPriceCostColumn2;
    

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
            if(partSearched.getName().equals(searchPart)||Integer.toString(partSearched.getId()).equals(searchPart))addProductPartTableview.getSelectionModel().select(partSearched);
        }  
    }

    @FXML
    private void AddButtonHandler(ActionEvent event){        
        if(addProductPartTableview.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected");
            alert.setContentText("You must select a part before you can add it.");
            alert.showAndWait();
        }else{
            Part part = addProductPartTableview.getSelectionModel().getSelectedItem();
            product.addAssociatedParts(part);
            
        }
    }

    @FXML
    private void DeleteButtonHandler(ActionEvent event) {
        if(productPartTableview.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("No Part Selected!");
            alert.setContentText("You must select a part before you can delete it.");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part from your product?");
            alert.setTitle("Deletion Confirmation!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Part selectedPart = productPartTableview.getSelectionModel().getSelectedItem();
                product.deleteAssociatedPart(selectedPart);
            }
        }
    }

    @FXML
    private void SaveButtonHandler(ActionEvent event) throws IOException {
        
        try {
            int id = productID;
            String name = NameText.getText();
            int stock = Integer.parseInt(InvText.getText());
            double price = Double.parseDouble(PriceText.getText());
            int max = Integer.parseInt(MaxText.getText());
            int min = Integer.parseInt(MinText.getText());             
            
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
            }else if(productPartTableview.getItems().isEmpty() == true){
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Invalid Values");
                alert.setContentText("You must add at least 1 part to a product.");
                alert.showAndWait();
            }else {
               product.setId(id);
               product.setName(name);
               product.setPrice(price);
               product.setStock(stock);
               product.setMax(max);
               product.setMin(min);
               Inventory.addProducts(product);
               
               
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
    
    
    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        productID = 0;
        product = new Product(0, null, 0.0, 0, 0, 0);
        for(Product product:Inventory.getAllProducts()){
            if(product.getId() > productID)
                productID = product.getId();
        }
        productID++;
        
        //Top Tableview
        addProductPartTableview.setItems(Inventory.getAllParts());
        partIdColumn1.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn1.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn1.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostColumn1.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        //Bottom Tableview
        productPartTableview.setItems(product.getAssociatedParts());     
        partIdColumn2.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn2.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn2.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostColumn2.setCellValueFactory(new PropertyValueFactory<>("price"));
    }    
}
