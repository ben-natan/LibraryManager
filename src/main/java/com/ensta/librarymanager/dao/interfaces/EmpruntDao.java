package com.ensta.librarymanager.dao.interfaces;

import java.time.LocalDate;
import java.util.List;

import com.ensta.librarymanager.models.Emprunt;
import com.ensta.librarymanager.dao.exceptions.*;

public interface EmpruntDao {
	public List<Emprunt> getList() throws DaoException;
	public List<Emprunt> getListCurrent() throws DaoException;
	public List<Emprunt> getListCurrentByMembre(int idMembre) throws DaoException;
	public List<Emprunt> getListCurrentByLivre(int idLivre) throws DaoException;
	public Emprunt getById(int id) throws DaoException;
	public void create(int idMembre, int idLivre, LocalDate dateEmprunt) throws DaoException;
	public void update(Emprunt emprunt) throws DaoException;
	public int count() throws DaoException;
}
