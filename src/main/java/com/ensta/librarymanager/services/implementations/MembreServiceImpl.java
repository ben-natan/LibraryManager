package com.ensta.librarymanager.services.implementations;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.services.exceptions.*;
import com.ensta.librarymanager.services.interfaces.MembreService;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.exceptions.DaoException;
import com.ensta.librarymanager.dao.implementations.*;

public class MembreServiceImpl implements MembreService {
    private static MembreServiceImpl instance;

    private MembreDaoImpl membreDao;

    public MembreServiceImpl() {
        this.membreDao = MembreDaoImpl.getInstance();
    }

    public static MembreServiceImpl getInstance() {
        if (instance == null) {
            instance = new MembreServiceImpl();
        }
        return instance;
    }

    public List<Membre> getList() throws ServiceException {
        try {
            return membreDao.getList();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        
    }

    public List<Membre> getListMembreEmpruntPossible() throws ServiceException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try {
            List<Membre> membreList = this.membreDao.getList();
            List<Membre> membreEmpruntPossibleList = new ArrayList<Membre>();
            for (Membre membre: membreList) {
                if (empruntService.isEmpruntPossible(membre)) {
                    membreEmpruntPossibleList.add(membre);
                }
            }
            return membreEmpruntPossibleList;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public Membre getById(int id) throws ServiceException {
        try {
            return membreDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

    public int create(String nom, String prenom, String adresse, String email, String telephone) throws ServiceException {
        if (nom == "" || prenom == "") {
            throw new ServiceException("Nom ou prénom vide");
        }
        try {
            return membreDao.create(nom.toUpperCase(), prenom, adresse, email, telephone);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public void update(Membre membre) throws ServiceException {
        if (membre.getNom() == "" || membre.getPrenom() == "") {
            throw new ServiceException("Nom ou prénom vide");
        }
        try {
            membre.setNom(membre.getNom().toUpperCase());
            membreDao.update(membre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


	public void delete(int id) throws ServiceException {
        try {
            membreDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public int count() throws ServiceException {
        try {
            return membreDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


}
