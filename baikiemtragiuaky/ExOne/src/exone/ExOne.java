package exone;

import java.util.List;

public class ExOne {

    public static void main(String[] args) {

        StudentManager<Student> manager = new StudentManager<>();

        manager.add(new Student("SV01", "Nguyen Van A", 4.0));
        manager.add(new Student("SV02", "Tran Thi B", 3.2));
        manager.add(new Student("SV03", "Le Van C", 3.0));

        List<Student> studentList = manager.getAll();
        System.out.println("Danh sach sinh vien:");
        for (Student student : studentList) {
            System.out.println(student.toString());
        }
    }
}