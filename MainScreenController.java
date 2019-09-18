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
import javafx.application.Platform;
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
public class MainScreenController implements Initializable {

    @FXML
    private Label MainLabel;
    @FXML
    private Button Exit;
    @FXML
    private Button SearchPartButton;
    @FXML
    private TextField SearchPartText;
    @FXML
    private Button AddPart;
    @FXML
    private Button ModifyPart;
    @FXML
    private Button DeletePart;
    @FXML
    private Button SearchProductButton;
    @FXML
    private TextField SearchProductText;
    @FXML
    private Button DeleteProduct;
    @FXML
    private Button ModifyProduct;
    @FXML
    private Button AddProduct;
    @FXML
    TableView<Part> partTableView;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryLevelColumn;
    @FXML
    private TableColumn<Part, Double> partPriceCostColumn;
    @FXML
    private TableView<Product> productTableview;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryLevelColumn;
    @FXML
    private TableColumn<Product, Double> productPriceColumn;



//ADD PART BUTTON    
    @FXML
    private void AddPartHandler(ActionEvent event) throws IOException {   
        
        Stage stage;
        Parent root;
        stage=(Stage) AddPart.getScene().getWindow();
      
        FXMLLoader loader= new FXMLLoader(getClass().getResource("Add Inhouse Part.fxml"));
      
        root = loader.load();
        
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

//MODIFY PART BUTTON    
    @FXML
    private void ModifyPartHandler(ActionEvent event) throws IOException{
        
        if(Inventory.allParts.isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Whoa nelly! Looks like you're jumping the gun, buddy!");
            alert.setContentText("You must enter a part before you can modify it!");
            alert.showAndWait();
        }else if(partTableView.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hold your horses!");
            alert.setContentText("You need to select a part before you can modify it!");
            alert.showAndWait();
        }else{
            Stage stage;
            Parent root;
            stage=(Stage) ModifyPart.getScene().getWindow();


            FXMLLoader loader= new FXMLLoader(getClass().getResource("Modify Inhouse Part.fxml"));

            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ModifyInhousePartController controller = loader.getController();
            Part selectedInhousePart = partTableView.getSelectionModel().getSelectedItem();
            controller.setInhousePart(selectedInhousePart);
        }
    }

//DELETE PART BUTTON
    @FXML
    private void DeletePartHandler(ActionEvent event) throws IOException{
        if(Inventory.allParts.isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Whoa nelly! Looks like you're jumping the gun, buddy!");
            alert.setContentText("You must enter a part before you can delete it!");
            alert.showAndWait();
        }else if(partTableView.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hold your horses!");
            alert.setContentText("You need to select a part before you can delete it!");
            alert.showAndWait();
        }else{
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this part?");
            alert.setTitle("Deletion Confirmation!");
            Optional<ButtonType> result = alert.showAndWait();

            if(result.isPresent() && result.get() == ButtonType.OK){
            Part selectedPart = (Part)partTableView.getSelectionModel().getSelectedItem();
            Inventory.allParts.remove(selectedPart);
        }
    }
}
    
//ADD PRODUCT BUTTON
    @FXML
    private void AddProductHandler(ActionEvent event) throws IOException{
     
        if(Inventory.allParts.isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hold your horses!");
            alert.setContentText("You must add a part before you can add any products.");
            alert.showAndWait();
        }else{

            Inventory.getAllParts();

            Stage stage;
            Parent root;
            stage=(Stage) AddProduct.getScene().getWindow();

            FXMLLoader loader= new FXMLLoader(getClass().getResource("Add Product.fxml"));

            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();
        }
    }
    
//DELETE PRODUCT BUTTON
    @FXML
    private void DeleteProductHandler(ActionEvent event) throws IOException{
        if(Inventory.allProducts.isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Whoa nelly! Looks like you're jumping the gun, buddy!");
            alert.setContentText("You must enter a product before you can delete it!");
            alert.showAndWait();
        }else if(productTableview.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hold your horses!");
            alert.setContentText("You need to select a part before you can delete it!");
            alert.showAndWait();
        }else{      
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to delete this product?");
            alert.setTitle("Deletion Confirmation!");
            Optional<ButtonType> result = alert.showAndWait();
            if(result.isPresent() && result.get() == ButtonType.OK){
                Product selectedProduct = (Product)productTableview.getSelectionModel().getSelectedItem();
                Inventory.allProducts.remove(selectedProduct);
            }
        }
    }
    
//MODIFY PRODUCT BUTTON    
    @FXML
    private void ModifyProductHandler(ActionEvent event) throws IOException{
        if(Inventory.allProducts.isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Whoa nelly! Looks like you're jumping the gun, buddy!");
            alert.setContentText("You must enter a product before you can modify it!");
            alert.showAndWait();
        }else if(productTableview.getSelectionModel().isEmpty() == true){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Hold your horses!");
            alert.setContentText("You need to select a product before you can modify it!");
            alert.showAndWait();
        }else{
            Stage stage;
            Parent root;
            stage=(Stage) ModifyProduct.getScene().getWindow();

            FXMLLoader loader= new FXMLLoader(getClass().getResource("Modify Product.fxml"));

            root = loader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            stage.show();

            ModifyProductController controller = loader.getController();
            Product selectedProduct = productTableview.getSelectionModel().getSelectedItem();
            controller.setProduct(selectedProduct);
        }
    }
    
    
    @FXML
    private void ExitHandler(ActionEvent event) {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION, "Are you sure you want to close the program?");
        alert.setTitle("Exit Alert!");
        Optional<ButtonType> result = alert.showAndWait();
        if(result.isPresent() && result.get() == ButtonType.OK){
           Platform.exit();
        }       
    }

    @FXML
    private void SearchPartButtonHandler(ActionEvent event) {
        String searchPart = SearchPartText.getText();
        for(Part partSearched:Inventory.getAllParts()){
            if(partSearched.getName().equals(searchPart)||Integer.toString(partSearched.getId()).equals(searchPart))partTableView.getSelectionModel().select(partSearched);
        }
    }

    @FXML
    private void SearchPartTextHandler(ActionEvent event) {
    }

    @FXML
    private void SearchProductButtonHandler(ActionEvent event) {
        String searchProduct = SearchProductText.getText();
        for(Product productSearched:Inventory.getAllProducts()){
            if(productSearched.getName().equals(searchProduct)||Integer.toString(productSearched.getId()).equals(searchProduct))productTableview.getSelectionModel().select(productSearched);
            Product product1=new Product(0,null,0.0,0,0,0);          
        }
    }

    @FXML
    private void SearchProductTextHandler(ActionEvent event) {
    }

    /**
     * Initializes the controller class.
     * @param url
     * @param rb
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO

        partTableView.setItems(Inventory.getAllParts());
        
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        
        
        productTableview.setItems(Inventory.getAllProducts());
        
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryLevelColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
           
    }
}