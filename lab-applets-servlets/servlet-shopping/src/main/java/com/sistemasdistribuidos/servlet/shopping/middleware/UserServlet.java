/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.sistemasdistribuidos.servlet.shopping.middleware;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.sistemasdistribuidos.servlet.shopping.application.common.JsonUtils;
import com.sistemasdistribuidos.servlet.shopping.application.user.User;
import com.sistemasdistribuidos.servlet.shopping.application.user.UserRepository;

/**
 *
 * @author mariovillacortagarcia
 */
public class UserServlet extends HttpServlet {

    private UserRepository userRepository;

    @Override
    public void init() throws ServletException {
        super.init();
        if (userRepository == null) {
            userRepository = new UserRepository();
        }

    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to perform CRUD operations against Users";
    }

    /**
     * Handles the HTTP <code>GET</code> method. GET /UserServlet -> Returns all
     * users GET /UserServlet?id=xxx -> Returns a specific user by ID
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();
        
        try {
            // Try to get ID from path parameter first, then from query parameter
            String id = extractPathParameter(request);
            if (id == null) {
                id = request.getParameter("id");
            }
            
            if (id != null && !id.isEmpty()) {
                // Get specific user by ID
                User user = userRepository.getUserById(id);
                if (user != null) {
                    out.print(JsonUtils.toJson(user));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"User not found\",\"message\":\"User with ID " + JsonUtils.escapeJson(id) + " does not exist.\"}");
                }
            } else {
                // Get all users
                User[] users = userRepository.getUsers();
                out.print(JsonUtils.toJsonArray(users));
            }
        } finally {
            out.close();
        }
    }
    
    /**
     * Extracts the path parameter from the request URL.
     * For example, /Users/abc123 -> returns "abc123"
     *
     * @param request The HTTP request
     * @return The path parameter or null if not found
     */
    private String extractPathParameter(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo != null && pathInfo.length() > 1) {
            // Remove leading slash
            return pathInfo.substring(1);
        }
        return null;
    }

    /**
     * Handles the HTTP <code>POST</code> method. POST /UserServlet -> Creates a
     * new user (parameter: name)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String name = request.getParameter("name");

            if (name == null || name.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"Name parameter is required.\"}");
            } else {
                User user = userRepository.createUser(name.trim());
                response.setStatus(HttpServletResponse.SC_CREATED);
                out.print(JsonUtils.toJson(user));
            }
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method. PUT /UserServlet -> Updates an
     * existing user (parameters: id, name)
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");

            if (id == null || id.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"ID parameter is required.\"}");
            } else {
                User existingUser = userRepository.getUserById(id);
                if (existingUser == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"User not found\",\"message\":\"User with ID " + JsonUtils.escapeJson(id) + " does not exist.\"}");
                } else {
                    if (name != null && !name.trim().isEmpty()) {
                        existingUser.setName(name.trim());
                    }
                    User updatedUser = userRepository.updateUser(existingUser);
                    if (updatedUser != null) {
                        out.print(JsonUtils.toJson(updatedUser));
                    }
                }
            }
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>DELETE</code> method. DELETE /Users/xxx
     * -> Deletes a user by ID
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("application/json;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Try to get ID from path parameter first, then from query parameter
            String id = extractPathParameter(request);
            if (id == null) {
                id = request.getParameter("id");
            }

            if (id == null || id.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"ID parameter is required.\"}");
            } else {
                User user = userRepository.getUserById(id);
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"User not found\",\"message\":\"User with ID " + JsonUtils.escapeJson(id) + " does not exist.\"}");
                } else {
                    userRepository.deleteUserById(id);
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"message\":\"User deleted successfully\",\"id\":\"" + JsonUtils.escapeJson(id) + "\"}");
                }
            }
        } finally {
            out.close();
        }
    }

}
