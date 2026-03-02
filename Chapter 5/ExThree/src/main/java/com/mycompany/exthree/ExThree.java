package com.mycompany.exthree;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

public class ExThree {

    public static void main(String[] args) {
        System.out.println("Đang cấu hình Hibernate...");
        
        // 1. Khởi tạo SessionFactory từ file hibernate.cfg.xml
        Configuration cfg = new Configuration().configure("hibernate.cfg.xml");
        cfg.setProperty("hibernate.connection.password", "12345");
        SessionFactory factory = cfg.buildSessionFactory();
        
        // 2. Mở 1 phiên làm việc (Session)
        Session session = factory.openSession();
        Transaction tx = null;

        try {
            // 3. Bắt đầu giao dịch
            tx = session.beginTransaction();

            // 4. Tạo đối tượng Product theo yêu cầu đề bài
            Product product = new Product("Laptop", 1500);
            
            // 5. Lưu vào CSDL (Thay vì viết lệnh INSERT INTO)
            session.save(product);
            
            // 6. Chốt giao dịch
            tx.commit();
            
            System.out.println("--- THÀNH CÔNG ---");
            System.out.println("Đã lưu sản phẩm: " + product.getName() + " với giá " + product.getPrice() + " xuống CSDL.");
            
        } catch (Exception e) {
            // Nếu có lỗi thì hoàn tác lại
            if (tx != null) {
                tx.rollback();
            }
            e.printStackTrace();
        } finally {
            // 7. Luôn nhớ đóng session và factory
            session.close();
            factory.close();
        }
    }
}