package com.example.studentapp.DAO;

import com.example.studentapp.DAO.entities.Student;

import java.util.List;

public interface DaoI <S,I>{
    void create(S std);
    void delete(I id);
    List<S> getAll();
    S getById(I id);
    List<S> searchQuery(String s);
    S getByName(String n);
}
