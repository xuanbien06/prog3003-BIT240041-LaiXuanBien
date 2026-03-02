package com.mycompany.exfour;

import java.util.List;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class ExFour {
    public static void main(String[] args) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                                                    .addAnnotatedClass(Product.class)
                                                    .buildSessionFactory();
        Session session = factory.openSession();

        try {
            String hql = "FROM Product p WHERE p.price > 1000";
            List<Product> products = session.createQuery(hql, Product.class).getResultList();
            
            System.out.println("--- CÁC SẢN PHẨM CÓ GIÁ > 1000 ---");
            for (Product p : products) {
                System.out.println("ID: " + p.getId() + " - Tên: " + p.getName() + " - Giá: " + p.getPrice());
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            session.close();
            factory.close();
        }
    }
}