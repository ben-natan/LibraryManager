package com.ensta.librarymanager.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.dao.interfaces.LivreDao;
import com.ensta.librarymanager.dao.exceptions.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.persistence.*;

public class LivreDaoImpl implements LivreDao {

    private static LivreDaoImpl instance;

    private LivreDaoImpl(){}

    public static LivreDaoImpl getInstance() {
        if (instance == null) {
            instance = new LivreDaoImpl();
        }
        return instance;
    }

    private String getListQuery = "SELECT id,titre,auteur,isbn FROM livre";
    private String getByIdQuery = "SELECT id,titre,auteur,isbn FROM livre WHERE id=?";
    private String createQuery = "INSERT INTO livre(titre,auteur,isbn) VALUES (?,?,?)";
    private String updateQuery = "UPDATE livre SET titre=?, auteur = ?, isbn=? WHERE id = ?";
    private String deleteQuery = "DELETE FROM livre WHERE id = ?";
    private String countQuery = "SELECT COUNT(id) AS count FROM livre";


    public List<Livre> getList() throws DaoException {
        List<Livre> livreList = new ArrayList<Livre>();

        try {
            Connection  connection = ConnectionManager.getConnection();
            PreparedStatement getListPreparedStatement = null;
            getListPreparedStatement = connection.prepareStatement(this.getListQuery);
            ResultSet resultSet = getListPreparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("id"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
                livreList.add(livre);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
        return livreList;
    }

    public Livre getById(int id) throws DaoException {
        Livre livre = new Livre();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement getByIdPreparedStatement = null;
            getByIdPreparedStatement = connection.prepareStatement(this.getByIdQuery);
            getByIdPreparedStatement.setInt(1,id);
            ResultSet resultSet = getByIdPreparedStatement.executeQuery();
            
            if (resultSet.next()) {
                livre.setId(resultSet.getInt("id"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
       
        return livre;
    }

    public int create(String titre, String auteur, String isbn) throws DaoException {
        int id = -1;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement createPreparedStatement = null;
            createPreparedStatement = connection.prepareStatement(this.createQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            createPreparedStatement.setString(1,titre);
            createPreparedStatement.setString(2,auteur);
            createPreparedStatement.setString(3,isbn);
            createPreparedStatement.executeUpdate();
            ResultSet resultSet = createPreparedStatement.getGeneratedKeys();
        
            if (resultSet.next()) {
                id = resultSet.getInt(1);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
        return id;
    }

    public void update(Livre livre) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updatePreparedStatement = null;
            updatePreparedStatement = connection.prepareStatement(this.updateQuery);
            updatePreparedStatement.setString(1, livre.getTitre());
            updatePreparedStatement.setString(2, livre.getAuteur());
            updatePreparedStatement.setString(3, livre.getIsbn());
            updatePreparedStatement.setInt(4, livre.getId());
            updatePreparedStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void delete(int id) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement deletePreparedStatement = null;
            deletePreparedStatement = connection.prepareStatement(this.deleteQuery);
            deletePreparedStatement.setInt(1, id);
            deletePreparedStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public int count() throws DaoException {
        int count = 0;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement countPreparedStatement = null;
            countPreparedStatement = connection.prepareStatement(this.countQuery);
            ResultSet resultSet = countPreparedStatement.executeQuery();
            
            if (resultSet.next()) {
                count = resultSet.getInt("count");
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
        return count;
    }
}