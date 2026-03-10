package exfour;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class ExFour {
    public static void main(String[] args) {
        // Tạo cửa sổ Frame chính
        JFrame frame = new JFrame("Chuyen doi nhiet đo");
        frame.setSize(350, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(null); // Sử dụng layout tự do để dễ set tọa độ

        // 1. Label hướng dẫn
        JLabel labelC = new JLabel("Nhap nhiet do (C):");
        labelC.setBounds(20, 20, 120, 25);
        frame.add(labelC);

        // 2. TextField để nhập dữ liệu
        JTextField textC = new JTextField();
        textC.setBounds(150, 20, 150, 25);
        frame.add(textC);

        // 3. Button để tính toán
        JButton btnConvert = new JButton("Chuyen sang F");
        btnConvert.setBounds(90, 60, 150, 30);
        frame.add(btnConvert);

        // 4. Label hiển thị kết quả
        JLabel labelResult = new JLabel("Ket qua (F): ");
        labelResult.setBounds(20, 100, 280, 25);
        frame.add(labelResult);

        // Thêm sự kiện click cho nút bấm
        btnConvert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    // Lấy chữ từ TextField và ép kiểu sang Double
                    double c = Double.parseDouble(textC.getText());
                    
                    // 5. Công thức tính
                    double f = c * 9 / 5.0 + 32;
                    
                    // Hiển thị kết quả (làm tròn 2 chữ số thập phân)
                    labelResult.setText(String.format("Ket qua (F): %.2f", f));
                } catch (NumberFormatException ex) {
                    // Cảnh báo nếu nhập chữ cái thay vì số
                    JOptionPane.showMessageDialog(frame, "Vui long nhap mot so hop le!", "Loi nhap lieu", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Hiển thị cửa sổ
        frame.setLocationRelativeTo(null); // Đưa cửa sổ ra giữa màn hình
        frame.setVisible(true);
    }
}