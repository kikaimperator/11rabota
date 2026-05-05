package org.example.hzhzhzhzhzpk;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import java.sql.*;

public class DBConnector {

    // Данные для подключения (замените на свои)
    private final String URL = "jdbc:mysql://127.0.0.1:3306/magaz";
    private final String USER = "root";
    private final String PASS = "12345";

    // Метод для создания подключения
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASS);
    }

    // ОСНОВНОЙ МЕТОД для поиска и получения данных
    public ObservableList<Product> getProducts(String searchKey) {
        ObservableList<Product> productList = FXCollections.observableArrayList();

        // SQL запрос: выбираем всё, где имя совпадает с поисковым словом
        String query = "SELECT * FROM products WHERE name LIKE ?";

        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {

            // Устанавливаем значение вместо знака вопроса (окружаем % для поиска подстроки)
            pst.setString(1, "%" + searchKey + "%");

            ResultSet rs = pst.executeQuery();

            while (rs.next()) {
                // Здесь создается объект вашего класса Product
                // Порядок данных: ID, Имя, Описание, Цена, Инструкция, Кол-во
                productList.add(new Product(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getString("description"),
                        rs.getDouble("price"),
                        rs.getString("usage"),
                        rs.getInt("stock")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Ошибка при поиске в БД: " + e.getMessage());
        }

        return productList;
    }

    public void updateStock(int productId, int newStock) {
        String query = "UPDATE products SET stock = ? WHERE id = ?";
        try (Connection conn = getConnection();
             PreparedStatement pst = conn.prepareStatement(query)) {
            pst.setInt(1, newStock);
            pst.setInt(2, productId);
            pst.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
