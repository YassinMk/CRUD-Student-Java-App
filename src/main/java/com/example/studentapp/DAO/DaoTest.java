package com.example.studentapp.DAO;

import com.example.studentapp.DAO.entities.Student;

import java.util.List;

public class DaoTest {
    public static void main(String[] args) {
        /*Student std = new Student(1234,"omare","mouad","omar@gmail.com","12/02/2024","Marrakech");

        studentDao.create(std);
        */
        StudentDaoImp studentDao = new StudentDaoImp();
        List<Student> students = studentDao.getAll();
        for (Student student : students) {
            System.out.println(student.getId() + " ," + student.getName() + ", " + student.getLastName() + " ," + student.getEmail() + " ," + student.getDate() + " ," + student.getCity());
        }

    }

}
