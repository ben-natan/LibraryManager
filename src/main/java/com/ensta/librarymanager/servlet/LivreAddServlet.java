package com.ensta.librarymanager.servlet;

import java.io.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/livre_add")
public class LivreAddServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_add.jsp");

        try {
            dispatcher.forward(request, response);
        } catch (ServletException | IOException e) {
            throw new ServletException(e);
        } 
    }


    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String titre = request.getParameter("titre");
        String auteur = request.getParameter("auteur");
        String isbn = request.getParameter("isbn");
        int id;

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

        try {
            id = livreService.create(titre, auteur, isbn);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect(("/LibraryManager/livre_details?id="+id));
    }

}
