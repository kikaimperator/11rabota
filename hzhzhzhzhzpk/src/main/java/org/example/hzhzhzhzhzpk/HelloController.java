package org.example.hzhzhzhzhzpk;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;

public class HelloController {

    @FXML private ResourceBundle resources;
    @FXML private URL location;
    @FXML private AnchorPane archorPane;
    @FXML private TextArea descriptionItem;

    // --- ЛЕВАЯ ТАБЛИЦА (ТВОЙ ПОИСКОВИК) ---
    @FXML private TableView<Product> table;
    @FXML private TableColumn<Product, String> nameItem;
    @FXML private TableColumn<Product, Double> priceItem;
    @FXML private TableColumn<Product, Integer> quantItem;
    @FXML private TextField search;
    @FXML private Button update;

    // --- ПРАВАЯ ТАБЛИЦА (КОРЗИНА) ---
    @FXML private TableView<Product> cartTable;
    @FXML private TableColumn<Product, String> cartNameColumn;
    @FXML private TableColumn<Product, Double> cartPriceColumn;
    @FXML private TableColumn<Product, Integer> cartQuantityColumn;
    @FXML private TextField quantityField; // Поле для ввода числа
    @FXML private Label totalLabel;

    private DBConnector db;
    private ObservableList<Product> cartList = FXCollections.observableArrayList();

    // 1. ТВОЙ ПОИСК (НЕ ТРОГАЕМ)
    @FXML
    void OnActionSearch(ActionEvent event) {
        String searchText = search.getText();
        DBConnector db = new DBConnector();
        ObservableList<Product> results = db.getProducts(searchText);
        table.setItems(results);
    }

    // 2. ТВОЕ ОБНОВЛЕНИЕ (НЕ ТРОГАЕМ)
    @FXML
    void OnActionUpdate(ActionEvent event) {
        search.clear();
        DBConnector db = new DBConnector();
        table.setItems(db.getProducts(""));
    }

    // 3. ДОБАВИТЬ В КОРЗИНУ
    @FXML
    void OnActionAddToCart(ActionEvent event) {
        Product selected = table.getSelectionModel().getSelectedItem();
        String input = quantityField.getText();

        if (selected != null && !input.isEmpty()) {
            try {
                int count = Integer.parseInt(input);
                if (count > 0 && count <= selected.getStock()) {
                    Product cartItem = new Product(
                            selected.getId(), selected.getName(), selected.getDescription(),
                            selected.getPrice(), selected.getUsage(), count
                    );
                    cartList.add(cartItem);
                    updateTotal();
                    quantityField.clear();
                }
            } catch (NumberFormatException e) {
                System.out.println("Введите число!");
            }
        }
    }

    // 4. ОФОРМИТЬ (СПИСАНИЕ ИЗ БАЗЫ)
    @FXML
    void OnActionCheckout(ActionEvent event) {
        if (cartList.isEmpty()) return;
        for (Product p : cartList) {
            int currentStock = 0;
            for (Product m : table.getItems()) {
                if (m.getId() == p.getId()) { currentStock = m.getStock(); break; }
            }
            db.updateStock(p.getId(), currentStock - p.getStock());
        }
        cartList.clear();
        updateTotal();
        OnActionUpdate(null); // Обновляем левую таблицу
    }

    // 5. ОЧИСТИТЬ ВСЁ ИЗ КОРЗИНЫ (БЕЗ СПИСАНИЯ)
    @FXML
    void OnActionClearCart(ActionEvent event) {
        if (cartList != null) {
            cartList.clear(); // Очищаем список товаров в корзине
            updateTotal();    // Обнуляем итоговую сумму на экране
            System.out.println("Корзина успешно очищена!");
        }
    }

    private void updateTotal() {
        double total = 0;
        for (Product p : cartList) { total += p.getPrice() * p.getStock(); }
        if (totalLabel != null) totalLabel.setText("Итого: " + total + " руб.");
    }

    @FXML
    void initialize() {
        this.db = new DBConnector();

        // Привязка ЛЕВОЙ таблицы
        nameItem.setCellValueFactory(new PropertyValueFactory<>("name"));
        priceItem.setCellValueFactory(new PropertyValueFactory<>("price"));
        quantItem.setCellValueFactory(new PropertyValueFactory<>("stock"));

        // Привязка ПРАВОЙ таблицы (Корзины)
        cartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        cartPriceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));
        cartQuantityColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        cartTable.setItems(cartList);

        table.setItems(db.getProducts(""));

        // Твое описание товара при клике
        table.getSelectionModel().selectedItemProperty().addListener((obs, old, newVal) -> {
            if (newVal != null) {
                descriptionItem.setText("ТОВАР: " + newVal.getName() + "\nОПИСАНИЕ: " + newVal.getDescription());
            }
        });
    }
}
