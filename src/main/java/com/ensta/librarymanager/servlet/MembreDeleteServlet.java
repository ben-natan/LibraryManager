package com.ensta.librarymanager.servlet;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/membre_delete")
public class MembreDeleteServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/membre_delete.jsp");

        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            int idMembre = Integer.parseInt(request.getParameter("id"));
            Membre membre = membreService.getById(idMembre);

            request.setAttribute("membre", membre);

            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        } 
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int idMembre = Integer.parseInt(request.getParameter("id"));
        MembreServiceImpl membreService = MembreServiceImpl.getInstance();

        try {
            membreService.delete(idMembre);
        } catch (ServiceException e) {
            throw new ServletException(e);
        }

        response.sendRedirect("/LibraryManager/membre_list");
    }
}
