import javax.servlet.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

@WebServlet("/Signup")
public class Signup extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        String name = req.getParameter("name");
        String email = req.getParameter("email");
        String password = req.getParameter("password"); 
        String confirmPassword = req.getParameter("confirmPassword");
        PrintWriter out = res.getWriter();
        
        if(password.equals(confirmPassword)){
            try {
                Class.forName("com.mysql.cj.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
                
                // Insert user data into the users table
                String insertUserQuery = "INSERT INTO users(name, email, password) VALUES (?, ?, ?)";
                PreparedStatement ps = con.prepareStatement(insertUserQuery);
                ps.setString(1, name);
                ps.setString(2, email);
                ps.setString(3, password);
                int rowsAffected = ps.executeUpdate();
                
                if (rowsAffected > 0) {
                    res.sendRedirect("login.html");
                } else {
                    out.println("<div style=\"text-align: center; background-color: #ccffcc; border: 1px solid #00cc00; padding: 10px; border-radius: 5px;\">");
                    out.println("<p style=\"font-size: 18px; color: #008000;\">Some error occurred! Please try again.</p>");
                    out.println("<a href=\"signup.html\" style=\"text-decoration: none; background-color: #008000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                    out.println("</div>");
                }
                con.close(); // Close connection after use
            }
            catch(ClassNotFoundException e){
                out.println("<font color=red size=18>Error: " + e.getMessage() + "<br>");
                out.println("<a href=signup.html>Try again</a>");
            }
            catch(SQLException e){
                out.println("<div style=\"text-align: center; background-color: #ffcccc; border: 1px solid #cc0000; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #cc0000;\">Error: " + e.getMessage() + "</p>");
                out.println("<a href=\"signup.html\" style=\"text-decoration: none; background-color: #cc0000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");



            }
        }
        else{
            out.println("<font color=red size=18>Passwords don't match.<br>");
            out.println("<a href=signup.html>Try again</a>");
        }
    }
}
