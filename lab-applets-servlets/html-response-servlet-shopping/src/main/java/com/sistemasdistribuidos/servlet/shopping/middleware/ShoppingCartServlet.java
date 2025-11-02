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
     * Returns HTML page with all shopping carts and forms for interaction
     * GET /ShoppingCarts/xxx -> Returns HTML page for a specific shopping cart
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
            // Try to get userId from path parameter first, then from query parameter
            String userId = extractPathParameter(request);
            if (userId == null) {
                userId = request.getParameter("userId");
            }

            if (userId != null && !userId.isEmpty()) {
                // Get specific shopping cart by user ID
                ShoppingCart cart = repository.getShoppingCartByUserId(userId);
                if (cart != null) {
                    out.print(generateCartDetailHtml(cart, request));
                } else {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(generateErrorHtml("Shopping cart not found", "Shopping cart for user ID " + escapeHtml(userId) + " does not exist.", request));
                }
            } else {
                // Get all shopping carts
                ShoppingCart[] carts = repository.getShoppingCarts();
                out.print(generateCartsListHtml(carts, request));
            }
        } finally {
            out.close();
        }
    }
    
    /**
     * Generates HTML page for listing all shopping carts with forms for CRUD operations
     */
    private String generateCartsListHtml(ShoppingCart[] carts, HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Shopping Carts Management</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 1000px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #333; border-bottom: 2px solid #FF9800; padding-bottom: 10px; }\n");
        html.append("        .section { margin: 20px 0; padding: 15px; background: #f9f9f9; border-radius: 5px; }\n");
        html.append("        .section h2 { margin-top: 0; color: #555; }\n");
        html.append("        form { margin: 10px 0; }\n");
        html.append("        label { display: block; margin-bottom: 5px; font-weight: bold; }\n");
        html.append("        input[type=\"text\"], input[type=\"hidden\"] { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }\n");
        html.append("        button { background-color: #FF9800; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px; }\n");
        html.append("        button:hover { background-color: #F57C00; }\n");
        html.append("        button.delete { background-color: #f44336; }\n");
        html.append("        button.delete:hover { background-color: #da190b; }\n");
        html.append("        button.remove { background-color: #FF5722; }\n");
        html.append("        button.remove:hover { background-color: #E64A19; }\n");
        html.append("        table { width: 100%; border-collapse: collapse; margin-top: 15px; }\n");
        html.append("        th, td { padding: 12px; text-align: left; border-bottom: 1px solid #ddd; }\n");
        html.append("        th { background-color: #FF9800; color: white; }\n");
        html.append("        tr:hover { background-color: #f5f5f5; }\n");
        html.append("        .no-carts { color: #999; font-style: italic; }\n");
        html.append("        .product-list { list-style: none; padding: 0; margin: 5px 0; }\n");
        html.append("        .product-list li { padding: 3px 0; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>Shopping Carts Management</h1>\n");
        
        // Section: Add Product to Cart
        html.append("        <div class=\"section\">\n");
        html.append("            <h2>Add Product to Cart</h2>\n");
        html.append("            <form action=\"").append(request.getContextPath()).append("/ShoppingCarts\" method=\"POST\">\n");
        html.append("                <label for=\"userId\">User ID:</label>\n");
        html.append("                <input type=\"text\" id=\"userId\" name=\"userId\" required>\n");
        html.append("                <label for=\"productName\">Product Name:</label>\n");
        html.append("                <input type=\"text\" id=\"productName\" name=\"productName\" required>\n");
        html.append("                <label for=\"productBrand\">Product Brand:</label>\n");
        html.append("                <input type=\"text\" id=\"productBrand\" name=\"productBrand\" required>\n");
        html.append("                <button type=\"submit\">Add Product</button>\n");
        html.append("            </form>\n");
        html.append("        </div>\n");
        
        // Section: Remove Product from Cart
        html.append("        <div class=\"section\">\n");
        html.append("            <h2>Remove Product from Cart</h2>\n");
        html.append("            <form action=\"").append(request.getContextPath()).append("/ShoppingCarts\" method=\"POST\">\n");
        html.append("                <input type=\"hidden\" name=\"_method\" value=\"PUT\">\n");
        html.append("                <label for=\"remove_userId\">User ID:</label>\n");
        html.append("                <input type=\"text\" id=\"remove_userId\" name=\"userId\" required>\n");
        html.append("                <label for=\"remove_productName\">Product Name:</label>\n");
        html.append("                <input type=\"text\" id=\"remove_productName\" name=\"productName\" required>\n");
        html.append("                <label for=\"remove_productBrand\">Product Brand:</label>\n");
        html.append("                <input type=\"text\" id=\"remove_productBrand\" name=\"productBrand\" required>\n");
        html.append("                <button type=\"submit\" class=\"remove\">Remove Product</button>\n");
        html.append("            </form>\n");
        html.append("        </div>\n");
        
        // Section: Shopping Carts List
        html.append("        <div class=\"section\">\n");
        html.append("            <h2>All Shopping Carts</h2>\n");
        if (carts.length == 0) {
            html.append("            <p class=\"no-carts\">No shopping carts found. Add a product to create a cart.</p>\n");
        } else {
            html.append("            <table>\n");
            html.append("                <thead>\n");
            html.append("                    <tr>\n");
            html.append("                        <th>User ID</th>\n");
            html.append("                        <th>Products</th>\n");
            html.append("                        <th>Actions</th>\n");
            html.append("                    </tr>\n");
            html.append("                </thead>\n");
            html.append("                <tbody>\n");
            for (ShoppingCart cart : carts) {
                html.append("                    <tr>\n");
                html.append("                        <td>").append(escapeHtml(cart.getUserId())).append("</td>\n");
                html.append("                        <td>\n");
                Product[] products = cart.getProducts();
                if (products.length == 0) {
                    html.append("                            <em>No products</em>\n");
                } else {
                    html.append("                            <ul class=\"product-list\">\n");
                    for (Product product : products) {
                        html.append("                                <li>").append(escapeHtml(product.getName())).append(" - ").append(escapeHtml(product.getBrand())).append("</li>\n");
                    }
                    html.append("                            </ul>\n");
                }
                html.append("                        </td>\n");
                html.append("                        <td>\n");
                html.append("                            <form action=\"").append(request.getContextPath()).append("/ShoppingCarts/").append(escapeHtml(cart.getUserId())).append("\" method=\"POST\" style=\"display: inline;\">\n");
                html.append("                                <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n");
                html.append("                                <button type=\"submit\" class=\"delete\">Delete Cart</button>\n");
                html.append("                            </form>\n");
                html.append("                            <a href=\"").append(request.getContextPath()).append("/ShoppingCarts/").append(escapeHtml(cart.getUserId())).append("\">\n");
                html.append("                                <button>View Details</button>\n");
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
     * Generates HTML page for a specific shopping cart
     */
    private String generateCartDetailHtml(ShoppingCart cart, HttpServletRequest request) {
        StringBuilder html = new StringBuilder();
        html.append("<!DOCTYPE html>\n");
        html.append("<html>\n");
        html.append("<head>\n");
        html.append("    <meta charset=\"UTF-8\">\n");
        html.append("    <title>Shopping Cart Details</title>\n");
        html.append("    <style>\n");
        html.append("        body { font-family: Arial, sans-serif; margin: 20px; background-color: #f5f5f5; }\n");
        html.append("        .container { max-width: 800px; margin: 0 auto; background: white; padding: 20px; border-radius: 8px; box-shadow: 0 2px 4px rgba(0,0,0,0.1); }\n");
        html.append("        h1 { color: #333; border-bottom: 2px solid #FF9800; padding-bottom: 10px; }\n");
        html.append("        .cart-info { margin: 20px 0; padding: 15px; background: #f9f9f9; border-radius: 5px; }\n");
        html.append("        .cart-info p { margin: 10px 0; }\n");
        html.append("        .cart-info strong { display: inline-block; width: 120px; }\n");
        html.append("        form { margin: 20px 0; padding: 15px; background: #f9f9f9; border-radius: 5px; }\n");
        html.append("        label { display: block; margin-bottom: 5px; font-weight: bold; }\n");
        html.append("        input[type=\"text\"] { width: 100%; padding: 8px; margin-bottom: 10px; border: 1px solid #ddd; border-radius: 4px; box-sizing: border-box; }\n");
        html.append("        button { background-color: #FF9800; color: white; padding: 10px 20px; border: none; border-radius: 4px; cursor: pointer; margin-right: 5px; }\n");
        html.append("        button:hover { background-color: #F57C00; }\n");
        html.append("        button.delete { background-color: #f44336; }\n");
        html.append("        button.delete:hover { background-color: #da190b; }\n");
        html.append("        button.remove { background-color: #FF5722; }\n");
        html.append("        button.remove:hover { background-color: #E64A19; }\n");
        html.append("        a { text-decoration: none; }\n");
        html.append("        ul.product-list { list-style: none; padding: 0; }\n");
        html.append("        ul.product-list li { padding: 8px; margin: 5px 0; background: white; border-radius: 4px; border-left: 3px solid #FF9800; }\n");
        html.append("    </style>\n");
        html.append("</head>\n");
        html.append("<body>\n");
        html.append("    <div class=\"container\">\n");
        html.append("        <h1>Shopping Cart Details</h1>\n");
        html.append("        <div class=\"cart-info\">\n");
        html.append("            <p><strong>User ID:</strong> ").append(escapeHtml(cart.getUserId())).append("</p>\n");
        html.append("            <p><strong>Products:</strong></p>\n");
        Product[] products = cart.getProducts();
        if (products.length == 0) {
            html.append("            <em>No products in cart</em>\n");
        } else {
            html.append("            <ul class=\"product-list\">\n");
            for (Product product : products) {
                html.append("                <li>").append(escapeHtml(product.getName())).append(" - ").append(escapeHtml(product.getBrand())).append("</li>\n");
            }
            html.append("            </ul>\n");
        }
        html.append("        </div>\n");
        
        // Add Product form
        html.append("        <form action=\"").append(request.getContextPath()).append("/ShoppingCarts/").append(escapeHtml(cart.getUserId())).append("\" method=\"POST\">\n");
        html.append("            <input type=\"hidden\" name=\"userId\" value=\"").append(escapeHtml(cart.getUserId())).append("\">\n");
        html.append("            <label for=\"productName\">Product Name:</label>\n");
        html.append("            <input type=\"text\" id=\"productName\" name=\"productName\" required>\n");
        html.append("            <label for=\"productBrand\">Product Brand:</label>\n");
        html.append("            <input type=\"text\" id=\"productBrand\" name=\"productBrand\" required>\n");
        html.append("            <button type=\"submit\">Add Product</button>\n");
        html.append("        </form>\n");
        
        // Remove Product form
        html.append("        <form action=\"").append(request.getContextPath()).append("/ShoppingCarts\" method=\"POST\">\n");
        html.append("            <input type=\"hidden\" name=\"_method\" value=\"PUT\">\n");
        html.append("            <input type=\"hidden\" name=\"userId\" value=\"").append(escapeHtml(cart.getUserId())).append("\">\n");
        html.append("            <label for=\"remove_productName\">Product Name:</label>\n");
        html.append("            <input type=\"text\" id=\"remove_productName\" name=\"productName\" required>\n");
        html.append("            <label for=\"remove_productBrand\">Product Brand:</label>\n");
        html.append("            <input type=\"text\" id=\"remove_productBrand\" name=\"productBrand\" required>\n");
        html.append("            <button type=\"submit\" class=\"remove\">Remove Product</button>\n");
        html.append("        </form>\n");
        
        // Delete Cart form
        html.append("        <form action=\"").append(request.getContextPath()).append("/ShoppingCarts/").append(escapeHtml(cart.getUserId())).append("\" method=\"POST\">\n");
        html.append("            <input type=\"hidden\" name=\"_method\" value=\"DELETE\">\n");
        html.append("            <button type=\"submit\" class=\"delete\">Delete Shopping Cart</button>\n");
        html.append("        </form>\n");
        
        html.append("        <a href=\"").append(request.getContextPath()).append("/ShoppingCarts\">\n");
        html.append("            <button>Back to Shopping Carts List</button>\n");
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
        html.append("        <a href=\"").append(request.getContextPath()).append("/ShoppingCarts\">Back to Shopping Carts List</a>\n");
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
        // Check if this is a PUT or DELETE request via _method parameter
        String method = request.getParameter("_method");
        if ("PUT".equalsIgnoreCase(method)) {
            doPut(request, response);
            return;
        } else if ("DELETE".equalsIgnoreCase(method)) {
            doDelete(request, response);
            return;
        }
        
        // Regular POST - Add product to cart
        response.setContentType("text/html;charset=UTF-8");
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
                out.print(generateErrorHtml("Bad Request", "User ID parameter is required.", request));
            } else if (productName == null || productName.trim().isEmpty()
                    || productBrand == null || productBrand.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(generateErrorHtml("Bad Request", "Product name and brand parameters are required.", request));
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
                // Redirect to GET to show updated cart
                if (userId != null && !userId.isEmpty()) {
                    response.sendRedirect(request.getContextPath() + "/ShoppingCarts/" + userId);
                } else {
                    response.sendRedirect(request.getContextPath() + "/ShoppingCarts");
                }
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
        response.setContentType("text/html;charset=UTF-8");
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
                out.print(generateErrorHtml("Bad Request", "User ID parameter is required.", request));
            } else if (productName == null || productName.trim().isEmpty()
                    || productBrand == null || productBrand.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(generateErrorHtml("Bad Request", "Product name and brand parameters are required.", request));
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
                
                // Redirect to GET to show updated cart
                response.sendRedirect(request.getContextPath() + "/ShoppingCarts/" + userId);
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
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        try {
            // Try to get userId from path parameter first, then from query parameter
            String userId = extractPathParameter(request);
            if (userId == null) {
                userId = request.getParameter("userId");
            }

            if (userId == null || userId.trim().isEmpty()) {
                response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.print(generateErrorHtml("Bad Request", "User ID parameter is required.", request));
            } else {
                ShoppingCart cart = repository.getShoppingCartByUserId(userId);
                if (cart == null) {
                    response.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.print(generateErrorHtml("Shopping cart not found", "Shopping cart for user ID " + escapeHtml(userId) + " does not exist.", request));
                } else {
                    repository.deleteShoppingCartByUserId(userId);
                    response.setStatus(HttpServletResponse.SC_OK);
                    // Redirect to GET to show updated list
                    response.sendRedirect(request.getContextPath() + "/ShoppingCarts");
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
