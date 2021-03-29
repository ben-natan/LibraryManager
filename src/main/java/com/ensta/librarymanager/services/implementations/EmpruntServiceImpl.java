package com.ensta.librarymanager.services.implementations;

import java.util.List;
import java.time.LocalDate;

import com.ensta.librarymanager.services.exceptions.*;
import com.ensta.librarymanager.services.interfaces.EmpruntService;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.exceptions.DaoException;
import com.ensta.librarymanager.dao.implementations.*;

public class EmpruntServiceImpl implements EmpruntService{
    private static EmpruntServiceImpl instance;

    private EmpruntDaoImpl empruntDao;

    public EmpruntServiceImpl() {
        this.empruntDao = EmpruntDaoImpl.getInstance();
    }

    public static EmpruntServiceImpl getInstance() {
        if (instance == null) {
            instance = new EmpruntServiceImpl();
        }
        return instance;
    }

    public List<Emprunt> getList() throws ServiceException {
        try {
            return this.empruntDao.getList();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public List<Emprunt> getListCurrent() throws ServiceException {
        try {
            return this.empruntDao.getListCurrent();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public List<Emprunt> getListCurrentByMembre(int idMembre) throws ServiceException {
        try {
            return this.empruntDao.getListCurrentByMembre(idMembre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public List<Emprunt> getListCurrentByLivre(int idLivre) throws ServiceException {
        try {
            return this.empruntDao.getListCurrentByLivre(idLivre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public Emprunt getById(int id) throws ServiceException {
        try {
            return this.empruntDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws ServiceException {
        try {
            this.empruntDao.create(idMembre, idLivre, dateEmprunt);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


    // ID DE QUOI? --> ici id de emprunt
	public void returnBook(int id) throws ServiceException {
       LocalDate date = LocalDate.now();
       try {
            Emprunt emprunt = this.empruntDao.getById(id);
            emprunt.setDateRetour(date);
            this.empruntDao.update(emprunt);
       } catch (DaoException e) {
           throw new ServiceException(e.getMessage());
       }
    }

	public int count() throws ServiceException {
        try {
            return this.empruntDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public boolean isLivreDispo(int idLivre) throws ServiceException {
        boolean dispo = true;
        try {
            List<Emprunt> currentEmprunts = this.empruntDao.getListCurrent();
            for (Emprunt emprunt: currentEmprunts) {
                if (emprunt.getLivre().getId() == idLivre) {
                    dispo = false;
                }
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return dispo;
    }

	public boolean isEmpruntPossible(Membre membre) throws ServiceException {
        boolean possible;
        int idMembre = membre.getId();
        try {
            List<Emprunt> currentMembreEmprunts = this.empruntDao.getListCurrentByMembre(idMembre);
            int nbEmprunts = currentMembreEmprunts.size();
            switch(membre.getAbonnement()) {
                case BASIC:
                    possible = (nbEmprunts < 2);
                    break;
                case PREMIUM:
                    possible = (nbEmprunts < 5);
                    break;
                case VIP:
                    possible = (nbEmprunts < 20);
                    break;
                default: 
                    possible = false;
            }
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
        return possible;
    }



}
