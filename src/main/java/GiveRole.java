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
import com.google.gson.Gson;

@WebServlet("/GiveRole")
public class GiveRole extends HttpServlet{
	public void doGet(HttpServletRequest req, HttpServletResponse res)throws ServletException, IOException{
		PrintWriter out = res.getWriter();
		HttpSession session = req.getSession(false);
		String email = (String)session.getAttribute("email");
		String userRole=null;
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","");
           String query = "select role from users where email=?";
           PreparedStatement ps = con.prepareStatement(query);
           ps.setString(1,email);
           ResultSet rs = ps.executeQuery();

           if(rs.next()){
           	userRole = rs.getString("role");
           }
           Gson gson = new Gson();
           String json = gson.toJson(userRole);

           res.setContentType("application/json");
           res.setCharacterEncoding("UTF-8");
           //out.println(json);
           res.getWriter().write(json);

           con.close();
		}
		catch (SQLException e) {
           out.println("<div style=\"text-align: center; background-color: #ffcccc; border: 1px solid #cc0000; padding: 10px; border-radius: 5px;\">");
           out.println("<p style=\"font-size: 18px; color: #cc0000;\">Error: " + e.getMessage() + "</p>");
           out.println("<a href=\"booklist.html\" style=\"text-decoration: none; background-color: #cc0000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
           out.println("</div>");
       }
       catch (ClassNotFoundException e) {
           e.printStackTrace();         
       }

	}
}