package com.ensta.librarymanager.dao.implementations;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.List;
import java.util.ArrayList;

import java.time.LocalDate;
import java.sql.Date;

import com.ensta.librarymanager.dao.exceptions.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.persistence.*;
import com.ensta.librarymanager.dao.interfaces.EmpruntDao;

public class EmpruntDaoImpl implements EmpruntDao {

    private static EmpruntDaoImpl instance;

    private EmpruntDaoImpl(){}

    public static EmpruntDaoImpl getInstance() {
        if (instance == null) {
            instance = new EmpruntDaoImpl();
        }
        return instance;
    }

    private String getListQuery = "SELECT e.id AS id,idMembre,nom,prenom,adresse,email,telephone,abonnement,idLivre,titre,auteur,isbn,dateEmprunt,dateRetour " + 
                                  "FROM emprunt AS e " + 
                                  "INNER JOIN membre ON membre.id = e.idMembre " + 
                                  "INNER JOIN livre ON livre.id = e.idLivre " + 
                                  "ORDER BY dateRetour DESC";


    private String getListCurrentQuery = "SELECT e.id AS id,idMembre,nom,prenom,adresse,email,telephone,abonnement,idLivre,titre,auteur,isbn,dateEmprunt,dateRetour " + 
                                    "FROM emprunt AS e " + 
                                    "INNER JOIN membre ON membre.id = e.idMembre " + 
                                    "INNER JOIN livre ON livre.id = e.idLivre " + 
                                    "WHERE dateRetour IS NULL";

    private String getListCurrentByMembreQuery = "SELECT e.id AS id,idMembre,nom,prenom,adresse,email,telephone,abonnement,idLivre,titre,auteur,isbn,dateEmprunt,dateRetour " + 
                                                "FROM emprunt AS e " + 
                                                "INNER JOIN membre ON membre.id = e.idMembre " + 
                                                "INNER JOIN livre ON livre.id = e.idLivre " + 
                                                "WHERE dateRetour IS NULL AND membre.id = ?";

    private String getListCurrentByLivreQuery = "SELECT e.id AS id,idMembre,nom,prenom,adresse,email,telephone,abonnement,idLivre,titre,auteur,isbn,dateEmprunt,dateRetour " + 
                                                "FROM emprunt AS e " + 
                                                "INNER JOIN membre ON membre.id = e.idMembre " + 
                                                "INNER JOIN livre ON livre.id = e.idLivre " + 
                                                "WHERE dateRetour IS NULL AND livre.id = ? ";

    private String getByIdQuery = "SELECT e.id AS id,idMembre,nom,prenom,adresse,email,telephone,abonnement,idLivre,titre,auteur,isbn,dateEmprunt,dateRetour " + 
                                  "FROM emprunt AS e " + 
                                  "INNER JOIN membre ON membre.id = e.idMembre " +
                                  "INNER JOIN livre ON livre.id = e.idLivre " +
                                  "WHERE e.id = ?";

    private String createQuery= "INSERT INTO emprunt(idMembre, idLivre, dateEmprunt, dateRetour) VALUES (?,?,?,?)";

    private String updateQuery= "UPDATE emprunt SET idMembre = ?, idLivre = ?, dateEmprunt = ?, dateRetour = ? WHERE id = ?";

    private String countQuery= "SELECT COUNT(id) AS count FROM emprunt";
                            

    public List<Emprunt> getList() throws DaoException {
        List<Emprunt> empruntList = new ArrayList<Emprunt>();
        try {
            Connection  connection = ConnectionManager.getConnection();
            PreparedStatement getListPreparedStatement = null;
            getListPreparedStatement = connection.prepareStatement(this.getListQuery);
            ResultSet resultSet = getListPreparedStatement.executeQuery();

            while (resultSet.next()) {
                Membre membre = new Membre();
                membre.setId(resultSet.getInt("idMembre"));
                membre.setNom(resultSet.getString("nom"));
                membre.setPrenom(resultSet.getString("prenom"));
                membre.setAdresse(resultSet.getString("adresse"));
                membre.setEmail(resultSet.getString("email"));
                membre.setTelephone(resultSet.getString("telephone"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                membre.setAbonnement(abonnement);
    
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("idLivre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
    
                Emprunt emprunt = new Emprunt();
                emprunt.setId(resultSet.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);

                emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt").toLocalDate());
                if (resultSet.getDate("dateRetour") != null) {
                    emprunt.setDateRetour(resultSet.getDate("dateRetour").toLocalDate());
                }
    
                empruntList.add(emprunt);
    
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }   
        return empruntList;
    }

    public List<Emprunt> getListCurrent() throws DaoException {
        List<Emprunt> empruntListCurrent = new ArrayList<Emprunt>();
        try {
            Connection  connection = ConnectionManager.getConnection();
            PreparedStatement getListCurrentPreparedStatement = null;
            getListCurrentPreparedStatement = connection.prepareStatement(this.getListCurrentQuery);
            ResultSet resultSet = getListCurrentPreparedStatement.executeQuery();

            while (resultSet.next()) {
                Membre membre = new Membre();
                membre.setId(resultSet.getInt("idMembre"));
                membre.setNom(resultSet.getString("nom"));
                membre.setPrenom(resultSet.getString("prenom"));
                membre.setAdresse(resultSet.getString("adresse"));
                membre.setEmail(resultSet.getString("email"));
                membre.setTelephone(resultSet.getString("telephone"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                membre.setAbonnement(abonnement);
    
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("idLivre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
    
                Emprunt emprunt = new Emprunt();
                emprunt.setId(resultSet.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt").toLocalDate());
                if (resultSet.getDate("dateRetour") != null) {
                    emprunt.setDateRetour(resultSet.getDate("dateRetour").toLocalDate());
                }
    
                empruntListCurrent.add(emprunt);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
        return empruntListCurrent;
    }

    public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException {
        List<Emprunt> empruntListCurrentByMembre = new ArrayList<Emprunt>();

        try {
            Connection  connection = ConnectionManager.getConnection();
            PreparedStatement getListCurrentByMembrePreparedStatement = null;
            getListCurrentByMembrePreparedStatement = connection.prepareStatement(this.getListCurrentByMembreQuery);
            getListCurrentByMembrePreparedStatement.setInt(1,idMembre);
            ResultSet resultSet = getListCurrentByMembrePreparedStatement.executeQuery();

            while (resultSet.next()) {
                Membre membre = new Membre();
                membre.setId(resultSet.getInt("idMembre"));
                membre.setNom(resultSet.getString("nom"));
                membre.setPrenom(resultSet.getString("prenom"));
                membre.setAdresse(resultSet.getString("adresse"));
                membre.setEmail(resultSet.getString("email"));
                membre.setTelephone(resultSet.getString("telephone"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                membre.setAbonnement(abonnement);
    
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("idLivre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
    
                Emprunt emprunt = new Emprunt();
                emprunt.setId(resultSet.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt").toLocalDate());
                if (resultSet.getDate("dateRetour") != null) {
                    emprunt.setDateRetour(resultSet.getDate("dateRetour").toLocalDate());
                }
    
                empruntListCurrentByMembre.add(emprunt);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }

        return empruntListCurrentByMembre;
    }

    public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException {
        List<Emprunt> empruntListCurrentByLivre = new ArrayList<Emprunt>();
        try {
            Connection  connection = ConnectionManager.getConnection();
            PreparedStatement getListCurrentByLivrePreparedStatement = null;
            getListCurrentByLivrePreparedStatement = connection.prepareStatement(this.getListCurrentByLivreQuery);
            getListCurrentByLivrePreparedStatement.setInt(1,idLivre);
            ResultSet resultSet = getListCurrentByLivrePreparedStatement.executeQuery();
            while (resultSet.next()) {
                Membre membre = new Membre();
                membre.setId(resultSet.getInt("idMembre"));
                membre.setNom(resultSet.getString("nom"));
                membre.setPrenom(resultSet.getString("prenom"));
                membre.setAdresse(resultSet.getString("adresse"));
                membre.setEmail(resultSet.getString("email"));
                membre.setTelephone(resultSet.getString("telephone"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                membre.setAbonnement(abonnement);
    
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("idLivre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
    
                Emprunt emprunt = new Emprunt();
                emprunt.setId(resultSet.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt").toLocalDate());
                if (resultSet.getDate("dateRetour") != null) {
                    emprunt.setDateRetour(resultSet.getDate("dateRetour").toLocalDate());
                }
    
                empruntListCurrentByLivre.add(emprunt);
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
       
        return empruntListCurrentByLivre;
    }

    public Emprunt getById(int id) throws DaoException {
        Emprunt emprunt = new Emprunt();
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement getByIdPreparedStatement = null;
            getByIdPreparedStatement = connection.prepareStatement(this.getByIdQuery);
            getByIdPreparedStatement.setInt(1,id);
            ResultSet resultSet = getByIdPreparedStatement.executeQuery();

            if (resultSet.next()) {
                Membre membre = new Membre();
                membre.setId(resultSet.getInt("idMembre"));
                membre.setNom(resultSet.getString("nom"));
                membre.setPrenom(resultSet.getString("prenom"));
                membre.setAdresse(resultSet.getString("adresse"));
                membre.setEmail(resultSet.getString("email"));
                membre.setTelephone(resultSet.getString("telephone"));
                Abonnement abonnement = Abonnement.valueOf(resultSet.getString("abonnement"));
                membre.setAbonnement(abonnement);
    
                Livre livre = new Livre();
                livre.setId(resultSet.getInt("idLivre"));
                livre.setTitre(resultSet.getString("titre"));
                livre.setAuteur(resultSet.getString("auteur"));
                livre.setIsbn(resultSet.getString("isbn"));
    
                emprunt.setId(resultSet.getInt("id"));
                emprunt.setMembre(membre);
                emprunt.setLivre(livre);
                emprunt.setDateEmprunt(resultSet.getDate("dateEmprunt").toLocalDate());
                if (resultSet.getDate("dateRetour") != null) {
                    emprunt.setDateRetour(resultSet.getDate("dateRetour").toLocalDate());
                }
            }
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
        
        
        
        return emprunt;
    }

    public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement createPreparedStatement = null;
            createPreparedStatement = connection.prepareStatement(this.createQuery);
            createPreparedStatement.setInt(1,idMembre);
            createPreparedStatement.setInt(2,idLivre);
            createPreparedStatement.setDate(3,Date.valueOf(dateEmprunt));
            createPreparedStatement.setNull(4, java.sql.Types.DATE);
            createPreparedStatement.executeUpdate();
        } catch (java.sql.SQLException e) {
            throw new DaoException(e.getMessage());
        }
    }

    public void update(Emprunt emprunt) throws DaoException {
        try {
            Connection connection = ConnectionManager.getConnection();
            PreparedStatement updatePreparedStatement = null;
            updatePreparedStatement = connection.prepareStatement(this.updateQuery);
            updatePreparedStatement.setInt(1, emprunt.getMembre().getId());
            updatePreparedStatement.setInt(2, emprunt.getLivre().getId());
            updatePreparedStatement.setDate(3, Date.valueOf(emprunt.getDateEmprunt()));
            updatePreparedStatement.setDate(4, Date.valueOf(emprunt.getDateRetour()));
            updatePreparedStatement.setInt(5, emprunt.getId());

            updatePreparedStatement.executeUpdate();
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