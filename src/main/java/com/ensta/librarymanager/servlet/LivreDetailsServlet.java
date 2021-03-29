package com.ensta.librarymanager.servlet;

import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/livre_details")
public class LivreDetailsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_details.jsp");

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();

        try {
            int idLivre = Integer.parseInt(request.getParameter("id"));
            Livre livre = livreService.getById(idLivre);
            request.setAttribute("livre", livre);


            List<Emprunt> emprunts = empruntService.getListCurrentByLivre(idLivre);
            request.setAttribute("emprunts", emprunts);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        int id = Integer.parseInt(request.getParameter("id"));
        Livre livre = new Livre();
        livre.setId(id);
        livre.setTitre(titre);
        livre.setAuteur(auteur);
        livre.setIsbn(isbn);

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        try {
            livreService.update(livre);
        } catch(ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("/LibraryManager/livre_details?id="+id);
    }
}
