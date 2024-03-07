
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


@WebServlet("/DeleteBook")
public class DeleteBook extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);
        String id = String.valueOf(session.getAttribute("id"));
        String book_id = req.getParameter("book_id");
    
        
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
            
            String Query = "Delete from books where book_id=?";
            PreparedStatement ps = con.prepareStatement(Query);
            ps.setString(1, book_id);
            
            int rowsAffected = ps.executeUpdate();
            if (rowsAffected > 0) {
                out.print("200");
                }
            else{
                out.println("<div style=\"text-align: center; background-color: #ccffcc; border: 1px solid #00cc00; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #008000;\">Some error occurred! Please try again.</p>");
                out.println("<a href=\"booklist.html\" style=\"text-decoration: none; background-color: #008000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");
                }
            con.close();
        }
        catch(ClassNotFoundException e){
                out.println("<font color=red size=18>Error: " + e.getMessage() + "<br>");
                out.println("<a href=signup.html>Try again</a>");
            }
            catch(SQLException e){
                out.println("<div style=\"text-align: center; background-color: #ffcccc; border: 1px solid #cc0000; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #cc0000;\">Error: " + e.getMessage() + "</p>");
                out.println("<a href=\"booklist.html\" style=\"text-decoration: none; background-color: #cc0000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");



            }

       
            	
    }
}