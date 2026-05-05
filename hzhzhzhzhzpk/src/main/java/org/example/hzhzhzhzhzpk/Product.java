package org.example.hzhzhzhzhzpk; // укажите ваш пакет

public class Product {
    private int id;
    private String name;
    private String description;
    private double price;
    private String usage;
    private int stock;

    // Конструктор (именно его вызывает DBConnector)
    public Product(int id, String name, String description, double price, String usage, int stock) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
        this.usage = usage;
        this.stock = stock;
    }

    // Геттеры (они нужны JavaFX, чтобы выводить данные в таблицу)
    public int getId() { return id; }
    public String getName() { return name; }
    public String getDescription() { return description; }
    public double getPrice() { return price; }
    public String getUsage() { return usage; }
    public int getStock() { return stock; }
}
