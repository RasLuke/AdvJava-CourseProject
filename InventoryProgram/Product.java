package InventoryProgram;
//this is just a class to represent products in inventory
public class Product {
    private String name;
    private int quantity;
    private double price;
    //constructor
    public Product(String name, int quantity, double price) {
        this.name = name;
        this.quantity = quantity;
        this.price = price;
    }
    //getters
    public String getName() { return name; }
    public int getQuantity() { return quantity; }
    public double getPrice() { return price; }
}
