package com.ensta.librarymanager.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/livre_delete")
public class LivreDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_delete.jsp");

        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

        try {
            int idLivre = Integer.parseInt(request.getParameter("id"));
            Livre livre = livreService.getById(idLivre);

            request.setAttribute("livre", livre);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idLivre = Integer.parseInt(request.getParameter("id"));
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

        try {
            livreService.delete(idLivre);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("/LibraryManager/livre_list");
    }
}
