package com.example.studentapp.DAO;

import com.example.studentapp.DAO.entities.Student;
import com.example.studentapp.DAO.entities.Ville;

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
            PreparedStatement pstm = connection.prepareStatement("INSERT INTO student (CIN ,NAME,LAST_NAME,EMAIL,DATE,ID_VILLE)"+"VALUES (?,?,?,?,?,?)");
            pstm.setInt(1,std.getId());
            pstm.setString(2,std.getName());
            pstm.setString(3,std.getLastName());
            pstm.setString(4,std.getEmail());
            pstm.setString(5,std.getDate());
            pstm.setString(6, String.valueOf(std.getCity().getId()));
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
            PreparedStatement pstm = connection.prepareStatement("SELECT student.*, ville.NOM FROM student LEFT JOIN ville ON student.ID_VILLE = ville.ID_VILLE");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                Student std = new Student();
                std.setId(rst.getInt("CIN"));
                std.setName(rst.getString("NAME"));
                std.setLastName(rst.getString("LAST_NAME"));
                std.setEmail(rst.getString("EMAIL"));
                Ville city = new Ville();
                city.setId(rst.getInt("ID_VILLE"));
                city.setNom(rst.getString("NOM"));
                std.setCity(city);

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
            PreparedStatement pstm2 = connection.prepareStatement("SELECT * FROM ville WHERE id = ?");
            pstm.setInt(1,id);
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                std.setId(rst.getInt("CIN"));
                std.setName(rst.getString("NAME"));
                std.setLastName(rst.getString("LAST_NAME"));
                std.setEmail(rst.getString("EMAIL"));
                VilleDaoImpl villeDao = new VilleDaoImpl();
                Ville ville = villeDao.getById(rst.getInt("ID_VILLE"));
                std.setCity(ville);
                std.setDate(rst.getString("DATE"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return std;
    }



    @Override
    public List<Student> searchQuery(String searchTerm) {
        Connection connection = DBSingleton.getConnection();
        List<Student> students = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM student LEFT JOIN ville ON student.ID_VILLE = ville.ID_VILLE WHERE student.CIN LIKE ? OR student.NAME LIKE ? OR student.LAST_NAME LIKE ? OR student.EMAIL LIKE ? OR ville.NOM LIKE ? OR student.DATE LIKE ?");
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

                Ville city = new Ville();
                city.setId(rst.getInt("ID_VILLE"));
                city.setNom(rst.getString("NOM"));
                std.setCity(city);

                std.setDate(rst.getString("DATE"));
                students.add(std);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return students;
    }

    @Override
    public Student getByName(String n) {
        return null;
    }
}
