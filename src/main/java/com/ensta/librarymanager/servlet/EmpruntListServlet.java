package com.ensta.librarymanager.servlet;

import java.util.List;
import java.util.ArrayList;

import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.WebServlet;

import com.ensta.librarymanager.services.implementations.*;
import com.ensta.librarymanager.models.*;
import com.ensta.librarymanager.services.exceptions.*;

@WebServlet("/emprunt_list")
public class EmpruntListServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("WEB-INF/View/emprunt_list.jsp");
        EmpruntServiceImpl empruntService = EmpruntServiceImpl.getInstance();
        String show = request.getParameter("show");

        try {
            List<Emprunt> emprunts = new ArrayList<Emprunt>();
            if (show != null && show.equals("all")) {
                emprunts = empruntService.getList();
            } else {
                emprunts = empruntService.getListCurrent();
            }
            request.setAttribute("emprunts", emprunts);
            dispatcher.forward(request, response);
        } catch (ServletException | IOException | ServiceException e) {
            throw new ServletException(e);
        }
    }


}
