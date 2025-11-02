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
     * Handles the HTTP <code>GET</code> method. GET /Users -> Returns HTML page
     * with all users and forms for interaction
     * GET /Users?id=xxx -> Returns HTML page for a specific user
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
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
                    out.print(generateUserDetailHtml(user, request));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(generateErrorHtml("User not found", "User with ID " + escapeHtml(id) + " does not exist.", request));
                }
            } else {
                // Get all users
                User[] users = userRepository.getUsers();
                out.print(generateUsersListHtml(users, request));
            }
        } finally {
            out.close();
        }
    }
    
    /**
     * Generates HTML page for listing all users with forms for CRUD operations
     */
    private String generateUsersListHtml(User[] users, HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Users Management</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #333; border-bottom: 2px solid #4CAF50; padding-bottom: 10px; }\n");
        html.append("        .section { margin: 20px 0; padding: 15px; background: #f9f9f9; border-radius: 5px; }\n");
        html.append("        .section h2 { margin-top: 0; color: #555; }\n");
        html.append("        form { margin: 10px 0; }\n");
        html.append("        label { display: block; margin-bottom: 5px; font-weight: bold; }\n");
        html.append("        input[type=\"text\"], input[type=\"hidden\"] { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }\n");
        html.append("        button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px; }\n");
        html.append("        button:hover { background-color: #45a049; }\n");
        html.append("        button.delete { background-color: #f44336; }\n");
        html.append("        button.delete:hover { background-color: #da190b; }\n");
        html.append("        button.update { background-color: #2196F3; }\n");
        html.append("        button.update:hover { background-color: #0b7dda; }\n");
        html.append("        table { width: 100%; border-collapse: collapse; margin-top: 15px; }\n");
        html.append("        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }\n");
        html.append("        th { background-color: #4CAF50; color: white; }\n");
        html.append("        tr:hover { background-color: #f5f5f5; }\n");
        html.append("        .no-users { color: #999; font-style: italic; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>Users Management</h1>\n");
        
        // Section: Create User
        html.append("        <div class=\"section\">\n");
        html.append("            <h2>Create New User</h2>\n");
        html.append("            <form action=\"").append(request.getContextPath()).append("/Users\" method=\"POST\">\n");
        html.append("                <label for=\"name\">Name:</label>\n");
        html.append("                <input type=\"text\" id=\"name\" name=\"name\" required>\n");
        html.append("                <button type=\"submit\">Create User</button>\n");
        html.append("            </form>\n");
        html.append("        </div>\n");
        
        // Section: Update User
        html.append("        <div class=\"section\">\n");
        html.append("            <h2>Update User</h2>\n");
        html.append("            <form action=\"").append(request.getContextPath()).append("/Users\" method=\"POST\">\n");
        html.append("                <input type=\"hidden\" name=\"_method\" value=\"PUT\">\n");
        html.append("                <label for=\"update_id\">User ID:</label>\n");
        html.append("                <input type=\"text\" id=\"update_id\" name=\"id\" required>\n");
        html.append("                <label for=\"update_name\">New Name:</label>\n");
        html.append("                <input type=\"text\" id=\"update_name\" name=\"name\" required>\n");
        html.append("                <button type=\"submit\" class=\"update\">Update User</button>\n");
        html.append("            </form>\n");
        html.append("        </div>\n");
        
        // Section: Users List
        html.append("        <div class=\"section\">\n");
        html.append("            <h2>All Users</h2>\n");
        if (users.length == 0) {
            html.append("            <p class=\"no-users\">No users found. Create a new user above.</p>\n");
        } else {
            html.append("            <table>\n");
            html.append("                <thead>\n");
            html.append("                    <tr>\n");
            html.append("                        <th>ID</th>\n");
            html.append("                        <th>Name</th>\n");
            html.append("                        <th>Actions</th>\n");
            html.append("                    </tr>\n");
            html.append("                </thead>\n");
            html.append("                <tbody>\n");
            for (User user : users) {
                html.append("                    <tr>\n");
                html.append("                        <td>").append(escapeHtml(user.getId())).append("</td>\n");
                html.append("                        <td>").append(escapeHtml(user.getName())).append("</td>\n");
                html.append("                        <td>\n");
                html.append("                            <form action=\"").append(request.getContextPath()).append("/Users/").append(escapeHtml(user.getId())).append("\" method=\"POST\" style=\"display: inline;\">\n");
                html.append("                                <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n");
                html.append("                                <button type=\"submit\" class=\"delete\">Delete</button>\n");
                html.append("                            </form>\n");
                html.append("                            <a href=\"").append(request.getContextPath()).append("/Users/").append(escapeHtml(user.getId())).append("\">\n");
                html.append("                                <button class=\"update\">View Details</button>\n");
                html.append("                            </a>\n");
                html.append("                        </td>\n");
                html.append("                    </tr>\n");
            }
            html.append("                </tbody>\n");
            html.append("            </table>\n");
        }
        html.append("        </div>\n");
        
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }
    
    /**
     * Generates HTML page for a specific user
     */
    private String generateUserDetailHtml(User user, HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>User Details</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #333; border-bottom: 2px solid #4CAF50; padding-bottom: 10px; }\n");
        html.append("        .user-info { margin: 20px 0; padding: 15px; background: #f9f9f9; border-radius: 5px; }\n");
        html.append("        .user-info p { margin: 10px 0; }\n");
        html.append("        .user-info strong { display: inline-block; width: 100px; }\n");
        html.append("        form { margin: 20px 0; padding: 15px; background: #f9f9f9; border-radius: 5px; }\n");
        html.append("        label { display: block; margin-bottom: 5px; font-weight: bold; }\n");
        html.append("        input[type=\"text\"] { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }\n");
        html.append("        button { background-color: #4CAF50; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px; }\n");
        html.append("        button:hover { background-color: #45a049; }\n");
        html.append("        button.delete { background-color: #f44336; }\n");
        html.append("        button.delete:hover { background-color: #da190b; }\n");
        html.append("        button.update { background-color: #2196F3; }\n");
        html.append("        button.update:hover { background-color: #0b7dda; }\n");
        html.append("        a { text-decoration: none; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>User Details</h1>\n");
        html.append("        <div class=\"user-info\">\n");
        html.append("            <p><strong>ID:</strong> ").append(escapeHtml(user.getId())).append("</p>\n");
        html.append("            <p><strong>Name:</strong> ").append(escapeHtml(user.getName())).append("</p>\n");
        html.append("        </div>\n");
        
        // Update form
        html.append("        <form action=\"").append(request.getContextPath()).append("/Users\" method=\"POST\">\n");
        html.append("            <input type=\"hidden\" name=\"_method\" value=\"PUT\">\n");
        html.append("            <input type=\"hidden\" name=\"id\" value=\"").append(escapeHtml(user.getId())).append("\">\n");
        html.append("            <label for=\"name\">New Name:</label>\n");
        html.append("            <input type=\"text\" id=\"name\" name=\"name\" value=\"").append(escapeHtml(user.getName())).append("\" required>\n");
        html.append("            <button type=\"submit\" class=\"update\">Update User</button>\n");
        html.append("        </form>\n");
        
        // Delete form
        html.append("        <form action=\"").append(request.getContextPath()).append("/Users/").append(escapeHtml(user.getId())).append("\" method=\"POST\">\n");
        html.append("            <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n");
        html.append("            <button type=\"submit\" class=\"delete\">Delete User</button>\n");
        html.append("        </form>\n");
        
        html.append("        <a href=\"").append(request.getContextPath()).append("/Users\">\n");
        html.append("            <button>Back to Users List</button>\n");
        html.append("        </a>\n");
        
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }
    
    /**
     * Generates error HTML page
     */
    private String generateErrorHtml(String title, String message, HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Error</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 600px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #f44336; }\n");
        html.append("        .error { color: #d32f2f; padding: 10px; background: #ffebee; border-radius: 4px; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>").append(escapeHtml(title)).append("</h1>\n");
        html.append("        <div class=\"error\">").append(escapeHtml(message)).append("</div>\n");
        html.append("        <a href=\"").append(request.getContextPath()).append("/Users\">Back to Users List</a>\n");
        html.append("    </div>\n");
        html.append("</body>\n");
        html.append("</html>\n");
        return html.toString();
    }
    
    /**
     * Escapes HTML special characters
     */
    private String escapeHtml(String text) {
        if (text == null) {
            return "";
        }
        return text.replace("&", "&amp;")
                   .replace("<", "&lt;")
                   .replace(">", "&gt;")
                   .replace("\"", "&quot;")
                   .replace("'", "&#39;");
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
        // Check if this is a PUT or DELETE request via _method parameter
        String method = request.getParameter("_method");
        if ("PUT".equalsIgnoreCase(method)) {
            doPut(request, response);
            return;
        } else if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(request, response);
            return;
        }
        
        // Regular POST - Create user
        // After POST, redirect to GET to show HTML response
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String name = request.getParameter("name");

            if (name == null || name.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(generateErrorHtml("Bad Request", "Name parameter is required.", request));
            } else {
                User user = userRepository.createUser(name.trim());
                response.setStatus(HttpServletResponse.SC_CREATED);
                // Redirect to GET to show updated list
                response.sendRedirect(request.getContextPath() + "/Users");
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            String id = request.getParameter("id");
            String name = request.getParameter("name");

            if (id == null || id.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(generateErrorHtml("Bad Request", "ID parameter is required.", request));
            } else {
                User existingUser = userRepository.getUserById(id);
                if (existingUser == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(generateErrorHtml("User not found", "User with ID " + escapeHtml(id) + " does not exist.", request));
                } else {
                    if (name != null && !name.trim().isEmpty()) {
                        existingUser.setName(name.trim());
                    }
                    User updatedUser = userRepository.updateUser(existingUser);
                    if (updatedUser != null) {
                        // Redirect to GET to show updated user or list
                        response.sendRedirect(request.getContextPath() + "/Users/" + id);
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Try to get ID from path parameter first, then from query parameter
            String id = extractPathParameter(request);
            if (id == null) {
                id = request.getParameter("id");
            }

            if (id == null || id.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(generateErrorHtml("Bad Request", "ID parameter is required.", request));
            } else {
                User user = userRepository.getUserById(id);
                if (user == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(generateErrorHtml("User not found", "User with ID " + escapeHtml(id) + " does not exist.", request));
                } else {
                    userRepository.deleteUserById(id);
                    response.setStatus(HttpServletResponse.SC_OK);
                    // Redirect to GET to show updated list
                    response.sendRedirect(request.getContextPath() + "/Users");
                }
            }
        } finally {
            out.close();
        }
    }

}
