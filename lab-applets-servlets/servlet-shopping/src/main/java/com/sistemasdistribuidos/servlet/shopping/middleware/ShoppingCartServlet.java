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

import com.sistemasdistribuidos.servlet.shopping.application.cart.ShoppingCart;
import com.sistemasdistribuidos.servlet.shopping.application.cart.ShoppingCartRepository;
import com.sistemasdistribuidos.servlet.shopping.application.common.JsonUtils;
import com.sistemasdistribuidos.servlet.shopping.application.product.Product;

/**
 *
 * @author mariovillacortagarcia
 */
public class ShoppingCartServlet extends HttpServlet {

    private ShoppingCartRepository repository;

    @Override
    public void init() throws ServletException {
        super.init();
        if (repository == null) {
            repository = new ShoppingCartRepository();
        }
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Servlet to perform CRUD operations against Shopping Carts";
    }

    /**
     * Handles the HTTP <code>GET</code> method. GET /ShoppingCarts ->
     * Returns all shopping carts GET /ShoppingCarts/xxx -> Returns
     * a specific shopping cart by user ID
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
            // Try to get userId from path parameter first, then from query parameter
            String userId = extractPathParameter(request);
            if (userId == null) {
                userId = request.getParameter("userId");
            }

            if (userId != null && !userId.isEmpty()) {
                // Get specific shopping cart by user ID
                ShoppingCart cart = repository.getShoppingCartByUserId(userId);
                if (cart != null) {
                    out.print(JsonUtils.toJson(cart));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Shopping cart not found\",\"message\":\"Shopping cart for user ID " + JsonUtils.escapeJson(userId) + " does not exist.\"}");
                }
            } else {
                // Get all shopping carts
                ShoppingCart[] carts = repository.getShoppingCarts();
                out.print(JsonUtils.toJsonArray(carts));
            }
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method. POST /ShoppingCarts/xxx ->
     * Adds a product to a shopping cart. Creates the cart if it doesn't exist.
     * Parameters: productName, productBrand
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
            // Try to get userId from path parameter first, then from query parameter
            String userId = extractPathParameter(request);
            if (userId == null) {
                userId = request.getParameter("userId");
            }
            
            String productName = request.getParameter("productName");
            String productBrand = request.getParameter("productBrand");

            if (userId == null || userId.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"User ID parameter is required.\"}");
            } else if (productName == null || productName.trim().isEmpty()
                    || productBrand == null || productBrand.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"Product name and brand parameters are required.\"}");
            } else {
                // Get or create shopping cart
                ShoppingCart cart = repository.getShoppingCartByUserId(userId);
                if (cart == null) {
                    cart = repository.createUserShoppingCart(userId.trim());
                }
                
                // Add product to cart
                Product product = new Product(productName.trim(), productBrand.trim());
                cart.addProduct(product);
                repository.updateShoppingCart(cart);

                response.setStatus(HttpServletResponse.SC_OK);
                out.print(JsonUtils.toJson(cart));
            }
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>PUT</code> method. PUT /ShoppingCarts/xxx ->
     * Modifies a product in a shopping cart (removes it). Creates the cart if it doesn't exist.
     * Parameters: productName, productBrand
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
            // Try to get userId from path parameter first, then from query parameter
            String userId = extractPathParameter(request);
            if (userId == null) {
                userId = request.getParameter("userId");
            }
            
            String productName = request.getParameter("productName");
            String productBrand = request.getParameter("productBrand");

            if (userId == null || userId.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"User ID parameter is required.\"}");
            } else if (productName == null || productName.trim().isEmpty()
                    || productBrand == null || productBrand.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"Product name and brand parameters are required.\"}");
            } else {
                // Get or create shopping cart
                ShoppingCart cart = repository.getShoppingCartByUserId(userId);
                if (cart == null) {
                    cart = repository.createUserShoppingCart(userId.trim());
                }
                
                // Remove product from cart
                Product product = new Product(productName.trim(), productBrand.trim());
                cart.removeProduct(product);
                repository.updateShoppingCart(cart);
                
                out.print(JsonUtils.toJson(cart));
            }
        } finally {
            out.close();
        }
    }

    /**
     * Handles the HTTP <code>DELETE</code> method. DELETE
     * /ShoppingCarts/xxx -> Deletes a shopping cart by user ID
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
            // Try to get userId from path parameter first, then from query parameter
            String userId = extractPathParameter(request);
            if (userId == null) {
                userId = request.getParameter("userId");
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print("{\"error\":\"Bad Request\",\"message\":\"User ID parameter is required.\"}");
            } else {
                ShoppingCart cart = repository.getShoppingCartByUserId(userId);
                if (cart == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print("{\"error\":\"Shopping cart not found\",\"message\":\"Shopping cart for user ID " + JsonUtils.escapeJson(userId) + " does not exist.\"}");
                } else {
                    repository.deleteShoppingCartByUserId(userId);
                    response.setStatus(HttpServletResponse.SC_OK);
                    out.print("{\"message\":\"Shopping cart deleted successfully\",\"userId\":\"" + JsonUtils.escapeJson(userId) + "\"}");
                }
            }
        } finally {
            out.close();
        }
    }
    
    /**
     * Extracts the path parameter from the request URL.
     * For example, /ShoppingCarts/user123 -> returns "user123"
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
}
