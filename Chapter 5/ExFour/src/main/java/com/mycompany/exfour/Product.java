package com.mycompany.exfour;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    
    private String name;
    private double price;

    // Hàm khởi tạo rỗng (Bắt buộc phải có cho Hibernate)
    public Product() {
    }

    // Hàm khởi tạo có tham số
    public Product(String name, double price) {
        this.name = name;
        this.price = price;
    }

    // Các hàm Get/Set
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }
}