package exthree;

import java.util.ArrayList;
import java.util.List;

public class ExThree {

    public static void main(String[] args) {
       
        List<Student> studentList = new ArrayList<>();
        studentList.add(new Student("SV01", "Nguyen Van A", 4.0));
        studentList.add(new Student("SV02", "Tran Thi B", 3.2));
        studentList.add(new Student("SV03", "Le Van C", 3.0));

        System.out.println("Danh sach sinh vien co GPA >= 3.2, sap xep giam dan:");

        studentList.stream()
              
                .filter(student -> student.getGpa() >= 3.2)
                
                .sorted((s1, s2) -> Double.compare(s2.getGpa(), s1.getGpa()))
            
                .forEach(student -> System.out.println(student.toString()));
    }
}