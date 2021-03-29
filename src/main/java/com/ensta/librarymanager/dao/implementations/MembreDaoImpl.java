package com.ensta.librarymanager.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.dao.interfaces.MembreDao;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.exceptions.*;
import com.ensta.librarymanager.persistence.*;

public class MembreDaoImpl implements MembreDao {

    private static MembreDaoImpl instance;

    private MembreDaoImpl(){}

    public static MembreDaoImpl getInstance() {
        if (instance == null) {
            instance = new MembreDaoImpl();
        }
        return instance;
    }

    private String getListQuery = "SELECT id,nom,prenom,adresse,email,telephone,abonnement FROM membre ORDER BY nom,prenom";
    private String getByIdQuery = "SELECT id,nom,prenom,adresse,email,telephone,abonnement FROM membre WHERE id=?";
    private String createQuery = "INSERT INTO membre(nom,prenom,adresse,email,telephone,abonnement) VALUES (?,?,?,?,?,?)";
    private String updateQuery = "UPDATE membre SET nom = ?, prenom = ?, adresse = ?, email = ?, telephone = ?, abonnement = ? WHERE id = ?";
    private String deleteQuery = "DELETE FROM membre WHERE id = ?";
    private String countQuery = "SELECT COUNT(id) AS count FROM membre";

    public List<Membre> getList() throws DaoException {
        List<Membre> memberList = new ArrayList<Membre>();
        try {
            Connection  connection = ConnectionManager.getConnection();
            PreparedStatement getListPreparedStatement = null;
            getListPreparedStatement = connection.prepareStatement(this.getListQuery);
            ResultSet resultSet = getListPreparedStatement.executeQuery();
            
            while (resultSet.next()) {
                Membre member = new Membre();
                member.setId(resultSet.getInt("id"));
                member.setNom(resultSet.getString("nom"));
                member.setPrenom(resultSet.getString("prenom"));
                member.setAdresse(resultSet.getString("adresse"));
                member.setTelephone(resultSet.getString("telephone"));
                member.setEmail(resultSet.getString("email"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                member.setAbonnement(abonnement);
                memberList.add(member);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
        return memberList;
    }
    

    public Membre getById(int id) throws DaoException {
        Membre member = new Membre();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement getByIdPreparedStatement = null;
            getByIdPreparedStatement = connection.prepareStatement(this.getByIdQuery);
            getByIdPreparedStatement.setInt(1,id);
            ResultSet resultSet = getByIdPreparedStatement.executeQuery();
            
            if (resultSet.next()) {
                member.setId(id);
                member.setNom(resultSet.getString("nom"));
                member.setPrenom(resultSet.getString("prenom"));
                member.setAdresse(resultSet.getString("adresse"));
                member.setTelephone(resultSet.getString("telephone"));
                member.setEmail(resultSet.getString("email"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                member.setAbonnement(abonnement);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return member;
    }

    public int create(String nom, String prenom, String adresse, String email, String telephone) throws DaoException {
        int id = -1;
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement createPreparedStatement = null;
            createPreparedStatement = connection.prepareStatement(this.createQuery, PreparedStatement.RETURN_GENERATED_KEYS);
            createPreparedStatement.setString(1,nom);
            createPreparedStatement.setString(2,prenom);
            createPreparedStatement.setString(3,adresse);
            createPreparedStatement.setString(4,email);
            createPreparedStatement.setString(5,telephone);
            createPreparedStatement.setString(6,Abonnement.BASIC.name());
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

    public void update(Membre membre) throws DaoException {

        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updatePreparedStatement = null;
            updatePreparedStatement = connection.prepareStatement(this.updateQuery);
            updatePreparedStatement.setString(1, membre.getNom());
            updatePreparedStatement.setString(2, membre.getPrenom());
            updatePreparedStatement.setString(3, membre.getAdresse());
            updatePreparedStatement.setString(4, membre.getEmail());
            updatePreparedStatement.setString(5, membre.getTelephone());
            updatePreparedStatement.setString(6, membre.getAbonnement().name());
            updatePreparedStatement.setInt(7, membre.getId());
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