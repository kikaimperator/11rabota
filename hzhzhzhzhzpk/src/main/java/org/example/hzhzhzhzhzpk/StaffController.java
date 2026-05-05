package org.example.hzhzhzhzhzpk;

import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

import java.net.URL;
import java.util.ResourceBundle;


public class StaffController {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private TextField amountInput;

    @FXML
    private AnchorPane archorPane;

    @FXML
    private TextArea descriptionItem;

    @FXML
    private TableView<Product> table;

    @FXML
    private Button add;

    @FXML
    private Button minus;

    @FXML
    private TableColumn<Product, String> nameItem; // (String)

    @FXML
    private TableColumn<Product, Double> priceItem; // (Double)

    @FXML
    private TableColumn<Product, Integer> quantItem; // (Integer)


    @FXML
    private TextField search;

    @FXML
    private Button update;
    private DBConnector db;

    @FXML
    void OnActionMinus (ActionEvent event) {

        Product selected = table.getSelectionModel().getSelectedItem();
        String input = amountInput.getText();

        if (selected != null && !input.isEmpty()) {
            try {
                int change = Integer.parseInt(input);
                int newQuantity = selected.getStock() - change; // Вычитаем

                if (newQuantity < 0) {
                    System.out.println("Ошибка: на складе нет столько товара!");
                } else {
                    db.updateStock(selected.getId(), newQuantity);
                    OnActionUpdate(null);
                    amountInput.clear();
                }
            } catch (NumberFormatException e) {
                System.err.println("Введите корректное число!");
            }
        }

    }

    @FXML
    void OnActionAdd (ActionEvent event) {

        Product selected = table.getSelectionModel().getSelectedItem();
        String input = amountInput.getText();

        if (selected != null && !input.isEmpty()) {
            try {
                int change = Integer.parseInt(input); // Берем число из поля
                int newQuantity = selected.getStock() + change; // Прибавляем

                db.updateStock(selected.getId(), newQuantity);
                OnActionUpdate(null); // Обновляем таблицу
                amountInput.clear();  // Очищаем поле после ввода
            } catch (NumberFormatException e) {
                // Если ввели не число, можно вывести ошибку
                System.err.println("Введите корректное число!");
            }
        }

    }

    @FXML
    void OnActionSearch(ActionEvent event) {

        String searchText = search.getText();
        System.out.println("Ищем: " + searchText); // Выведет в консоль IntelliJ

        DBConnector db = new DBConnector();
        ObservableList<Product> results = db.getProducts(searchText);

        System.out.println("Найдено товаров: " + results.size());
        table.setItems(results);


        // Берем текст из search (TextField) и отправляем в базу
        table.setItems(db.getProducts(search.getText()));

    }

    @FXML
    void OnActionUpdate(ActionEvent event) {
        search.clear(); // Очистить поле поиска
        DBConnector db = new DBConnector();
        table.setItems(db.getProducts(""));

    }

    @FXML
    void initialize() {
        this.db = new DBConnector();
        nameItem.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceItem.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantItem.setCellValueFactory(new PropertyValueFactory<>("stock"));
        assert archorPane != null : "fx:id=\"archorPane\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert descriptionItem != null : "fx:id=\"descriptionItem\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert nameItem != null : "fx:id=\"nameItem\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert priceItem != null : "fx:id=\"priceItem\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert quantItem != null : "fx:id=\"quantItem\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert search != null : "fx:id=\"search\" was not injected: check your FXML file 'hello-view.fxml'.";
        assert update != null : "fx:id=\"update\" was not injected: check your FXML file 'hello-view.fxml'.";

        DBConnector db = new DBConnector();
        table.setItems(db.getProducts(""));








    }

    public void OnActionAmountInput(ActionEvent event) {
    }
}
