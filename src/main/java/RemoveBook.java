import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import java.sql.*;

@WebServlet("/RemoveBook")
public class RemoveBook extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);
        if (session == null) {
            res.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            out.println("User not authenticated");
            return;
        }
        
        String id = String.valueOf(session.getAttribute("id"));
        String book_id = req.getParameter("book_id");
        if (book_id == null || book_id.isEmpty()) {
            res.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            out.println("Book ID parameter missing or empty");
            return;
        }
    
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
            
            // Construct the query using prepared statement to prevent SQL injection
            String query = "DELETE FROM cart_" + id + " WHERE book_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1, book_id);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                res.setStatus(HttpServletResponse.SC_OK);
                out.println("Book removed from cart successfully");
            } else {
                res.setStatus(HttpServletResponse.SC_NOT_FOUND);
                out.println("Book not found in the cart");
            }
            
            con.close();
        } catch (ClassNotFoundException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Error: " + e.getMessage());
        } catch (SQLException e) {
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            out.println("Error: " + e.getMessage());
        }
    }
}
