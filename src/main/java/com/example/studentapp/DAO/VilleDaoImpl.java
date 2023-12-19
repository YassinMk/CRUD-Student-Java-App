package com.example.studentapp.DAO;

import com.example.studentapp.DAO.entities.Student;
import com.example.studentapp.DAO.entities.Ville;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VilleDaoImpl implements DaoI<Ville ,Integer>{

    @Override
    public void create(Ville ville) {

    }

    @Override
    public void delete(Integer id) {
        Connection connection = DBSingleton.getConnection();
        try{
            PreparedStatement pstm = connection.prepareStatement("DELETE FROM ville WHERE id = ?");
            pstm.setInt(1,id);
            pstm.executeUpdate();
            System.out.println("City deleted successfully");
        }catch (SQLException e){
            throw new RuntimeException(e);
        }

    }

    @Override
    public List<Ville> getAll() {
        //get all the cities from the database
        Connection connection = DBSingleton.getConnection();
        List<Ville> villes = new ArrayList<>();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM ville");
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                Ville ville = new Ville();
                ville.setId(rst.getInt("ID_VILLE"));
                ville.setNom(rst.getString("NOM"));
                villes.add(ville);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
        return villes;
    }

    @Override
    public Ville getById(Integer id) {
        Connection connection = DBSingleton.getConnection();
        Ville ville = new Ville();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM ville WHERE ID_VILLE = ?");
            pstm.setInt(1,id);
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                ville.setId(rst.getInt("id"));
                ville.setNom(rst.getString("nom"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
        return ville;
    }

    @Override
    public List<Ville> searchQuery(String s) {
        return null;
    }

    @Override
    public Ville getByName(String n) {
        Connection connection = DBSingleton.getConnection();
        Ville ville = new Ville();
        try {
            PreparedStatement pstm = connection.prepareStatement("SELECT * FROM ville WHERE NOM = ?");
            pstm.setString(1,n);
            ResultSet rst = pstm.executeQuery();
            while (rst.next()) {
                ville.setId(rst.getInt("ID_VILLE"));
                ville.setNom(rst.getString("NOM"));
            }
        } catch (SQLException e) {
            throw new RuntimeException(e) ;
        }
        return ville;

    }
}
