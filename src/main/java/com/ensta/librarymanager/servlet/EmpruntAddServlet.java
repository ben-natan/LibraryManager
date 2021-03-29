package com.ensta.librarymanager.servlet;

import java.util.List;
// import java.util.ArrayList;

import java.io.*;
import java.time.LocalDate;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/emprunt_add")
public class EmpruntAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/emprunt_add.jsp");

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            List<Livre> livres = livreService.getListDispo();
            List<Membre> membres = membreService.getListMembreEmpruntPossible();

            request.setAttribute("livres", livres);
            request.setAttribute("membres", membres);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("idLivre"));
        int idMembre = Integer.parseInt(request.getParameter("idMembre"));

        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        LocalDate dateEmprunt = LocalDate.now();
        try {
            empruntService.create(idMembre, idLivre, dateEmprunt);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("/LibraryManager/dashboard");
    }

}
