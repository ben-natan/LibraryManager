package com.ensta.librarymanager.services.implementations;

import java.util.List;
import java.util.ArrayList;

import com.ensta.librarymanager.services.exceptions.*;
import com.ensta.librarymanager.services.interfaces.LivreService;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.dao.exceptions.DaoException;
import com.ensta.librarymanager.dao.implementations.*;

public class LivreServiceImpl implements LivreService {
    private static LivreServiceImpl instance;

    private LivreDaoImpl livreDao;

    public LivreServiceImpl() {
        this.livreDao = LivreDaoImpl.getInstance();
    }

    public static LivreServiceImpl getInstance() {
        if (instance == null) {
            instance = new LivreServiceImpl();
        }
        return instance;
    }

    public List<Livre> getList() throws ServiceException {
        try {
            return this.livreDao.getList();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


	public List<Livre> getListDispo() throws ServiceException {
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        try {
            List<Livre> livreList = this.livreDao.getList();
            List<Livre> livreDispoList = new ArrayList<Livre>();
            for (Livre livre: livreList) {
                if (empruntService.isLivreDispo(livre.getId())) {
                    livreDispoList.add(livre);
                }
            }
            return livreDispoList;
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public Livre getById(int id) throws ServiceException {
        try {
            return this.livreDao.getById(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }


	public int create(String titre, String auteur, String isbn) throws ServiceException {
        if (titre == "") {
            throw new ServiceException("Titre vide");
        }
        try {
            return this.livreDao.create(titre, auteur, isbn);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public void update(Livre livre) throws ServiceException {
        if (livre.getTitre() == "") {
            throw new ServiceException("Titre vide");
        }
        try {
            this.livreDao.update(livre);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public void delete(int id) throws ServiceException {
        try {
            this.livreDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }

	public int count() throws ServiceException {
        try {
            return this.livreDao.count();
        } catch (DaoException e) {
            throw new ServiceException(e.getMessage());
        }
    }
}
