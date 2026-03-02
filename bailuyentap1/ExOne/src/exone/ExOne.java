package exone;



import java.util.ArrayList;
import java.util.Scanner;

import java.io.PrintStream;
import java.nio.charset.StandardCharsets;


class Student {
    String mssv;
    String name;
    double gpa;

    public Student(String mssv, String name, double gpa) {
        this.mssv = mssv;
        this.name = name;
        this.gpa = gpa;
    }

    @Override
    public String toString() {
        return "MSSV: " + mssv + " | Tên: " + name + " | GPA: " + gpa;
    }
}

public class ExOne {
    static ArrayList<Student> studentList = new ArrayList<>();
    static Scanner scanner = new Scanner(System.in, StandardCharsets.UTF_8);

    public static void main(String[] args) {
        System.setOut(new PrintStream(System.out, true, StandardCharsets.UTF_8));
        int choice;
        do {
            System.out.println("\n--- QUẢN LÝ SINH VIÊN ---");
            System.out.println("1. Thêm sinh viên mới");
            System.out.println("2. Hiển thị danh sách sinh viên");
            System.out.println("3. Tìm kiếm sinh viên theo tên");
            System.out.println("4. Xóa sinh viên theo MSSV");
            System.out.println("0. Thoát");
            System.out.print("Nhập lựa chọn của bạn: ");
            choice = Integer.parseInt(scanner.nextLine());

            switch (choice) {
                case 1: addStudent(); break;
                case 2: displayStudents(); break;
                case 3: searchStudent(); break;
                case 4: deleteStudent(); break;
                case 0: System.out.println("Đã thoát chương trình."); break;
                default: System.out.println("Lựa chọn không hợp lệ!");
            }
        } while (choice != 0);
    }

    static void addStudent() {
        System.out.print("Nhập MSSV: ");
        String mssv = scanner.nextLine();
        System.out.print("Nhập Tên: ");
        String name = scanner.nextLine();
        System.out.print("Nhập điểm GPA: ");
        double gpa = Double.parseDouble(scanner.nextLine());
        
        studentList.add(new Student(mssv, name, gpa));
        System.out.println("Đã thêm sinh viên thành công!");
    }

    static void displayStudents() {
        if (studentList.isEmpty()) {
            System.out.println("Danh sách trống!");
            return;
        }
        for (Student s : studentList) {
            System.out.println(s.toString());
        }
    }

    static void searchStudent() {
        System.out.print("Nhập tên sinh viên cần tìm: ");
        String searchName = scanner.nextLine().toLowerCase();
        boolean found = false;
        for (Student s : studentList) {
            if (s.name.toLowerCase().contains(searchName)) {
                System.out.println(s.toString());
                found = true;
            }
        }
        if (!found) System.out.println("Không tìm thấy sinh viên nào có tên: " + searchName);
    }

    static void deleteStudent() {
        System.out.print("Nhập MSSV cần xóa: ");
        String mssv = scanner.nextLine();
        boolean removed = studentList.removeIf(s -> s.mssv.equals(mssv));
        if (removed) {
            System.out.println("Đã xóa sinh viên có MSSV: " + mssv);
        } else {
            System.out.println("Không tìm thấy MSSV này.");
        }
    }
}