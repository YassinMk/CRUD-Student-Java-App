package com.example.studentapp.DAO;

import com.example.studentapp.DAO.entities.Student;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class StudentDaoImp implements DaoI<Student,Integer>{

    @Override
    public void create(Student std) {
        Connection connection = DBSingleton.getConnection();

        try {
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO student (CIN ,NAME,LAST_NAME,EMAIL,CITY,DATE)"+"VALUES (?,?,?,?,?,?)");
            pstm.setInt(1,std.getId());
            pstm.setString(2,std.getName());
            pstm.setString(3,std.getLastName());
            pstm.setString(4,std.getEmail());
            pstm.setString(5,std.getCity());
            pstm.setString(6,std.getDate());
            pstm.executeUpdate();
            System.out.println("Student added successfully");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Integer id) {
        Connection connection = DBSingleton.getConnection();
        try{
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM student WHERE CIN = ?");
            pstm.setInt(1,id);
            pstm.executeUpdate();
            System.out.println("Student deleted successfully");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }
    }

    @Override
    public List<Student> getAll() {
        Connection connection = DBSingleton.getConnection();
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM student");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                Student std = new Student();
                std.setId(rst.getInt("CIN"));
                std.setName(rst.getString("NAME"));
                std.setLastName(rst.getString("LAST_NAME"));
                std.setEmail(rst.getString("EMAIL"));
                std.setCity(rst.getString("CITY"));
                std.setDate(rst.getString("DATE"));
                students.add(std);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
    public Student getById(Integer id){
        Connection connection = DBSingleton.getConnection();
        Student std = new Student();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM student WHERE CIN = ?");
            pstm.setInt(1,id);
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                std.setId(rst.getInt("CIN"));
                std.setName(rst.getString("NAME"));
                std.setLastName(rst.getString("LAST_NAME"));
                std.setEmail(rst.getString("EMAIL"));
                std.setCity(rst.getString("CITY"));
                std.setDate(rst.getString("DATE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return std;
    }
    public List<Student> searchStudents(String searchTerm) {
        Connection connection = DBSingleton.getConnection();
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM student WHERE CIN LIKE ? OR NAME LIKE ? OR LAST_NAME LIKE ? OR EMAIL LIKE ? OR CITY LIKE ? OR DATE LIKE ?");
            pstm.setString(1, "%" + searchTerm + "%");
            pstm.setString(2, "%" + searchTerm + "%");
            pstm.setString(3, "%" + searchTerm + "%");
            pstm.setString(4, "%" + searchTerm + "%");
            pstm.setString(5, "%" + searchTerm + "%");
            pstm.setString(6, "%" + searchTerm + "%");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                Student std = new Student();
                std.setId(rst.getInt("CIN"));
                std.setName(rst.getString("NAME"));
                std.setLastName(rst.getString("LAST_NAME"));
                std.setEmail(rst.getString("EMAIL"));
                std.setCity(rst.getString("CITY"));
                std.setDate(rst.getString("DATE"));
                students.add(std);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }
}
