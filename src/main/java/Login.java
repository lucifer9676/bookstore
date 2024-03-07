
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


@WebServlet("/Login")
public class Login extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        try{
            
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","");
            
            String query = "select id,email,role from users where email=? and password=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setString(1,email);
            ps.setString(2,password);
            ResultSet rs = ps.executeQuery();

            if(rs.next()){
                int id = rs.getInt("id");
                String role = rs.getString("role");

                HttpSession session = req.getSession();
                session.setAttribute("email", email);
                session.setAttribute("id", id);
                session.setAttribute("role", role);

        

                //out.println(session.getAttribute("email"));
                String createCartQuery = "CREATE TABLE If not exists cart_"+id+" (book_id INT NOT NULL, quantity INT NOT NULL,price INT NOT NULL, FOREIGN KEY (book_id) REFERENCES books(book_id))";
                Statement stmt = con.createStatement();
                stmt.executeUpdate(createCartQuery);
               res.sendRedirect("index.html");        
             }
            else{
                out.println("<div style=\"text-align: center; background-color: #ccffcc; border: 1px solid #00cc00; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #008000;\">Login credential don't match.</p>");
                out.println("<a href=\"login.html\" style=\"text-decoration: none; background-color: #008000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");
            }
            con.close();
        }
       
        catch(ClassNotFoundException e){
                out.println("<div style=\"text-align: center; background-color: #ffcccc; border: 1px solid #cc0000; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #cc0000;\">Error: " + e.getMessage() + "</p>");
                out.println("<a href=\"signup.html\" style=\"text-decoration: none; background-color: #cc0000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");
            }
        catch(SQLException e){
                out.println("<div style=\"text-align: center; background-color: #ffcccc; border: 1px solid #cc0000; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #cc0000;\">Error: " + e.getMessage() + "</p>");
                out.println("<a href=\"signup.html\" style=\"text-decoration: none; background-color: #cc0000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");
            }

       
            	
    }
}