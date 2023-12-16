package com.example.studentapp.DAO;

import com.example.studentapp.DAO.entities.Student;

import java.util.List;

public interface DaoI <S,I>{
    void create(S std);
    void delete(I id);
    List<S> getAll();
    Student getById(I id);
    List<Student> searchStudents(String s);
}
