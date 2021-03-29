package com.ensta.librarymanager.servlet;

import java.util.List;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/livre_list")
public class LivreListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/livre_list.jsp");
        LivreServiceImpl livreService = LivreServiceImpl.getInstance();

        try {
            List<Livre> livres = livreService.getList();
            request.setAttribute("livres", livres);

            dispatcher.forward(request, response);
        } catch(ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        }
        
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

}
