import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import com.google.gson.Gson;



@WebServlet("/GetBookDetails")
public class GetBookDetails extends HttpServlet {
    protected void doPost(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        int id = Integer.parseInt(req.getParameter("book_id"));
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","");
            String query = "select * from books where book_id=?";
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                
                Book book = new Book(
                    rs.getInt("book_id"),
                    rs.getString("title"),
                    rs.getString("author"),
                    rs.getDouble("price"),
                    rs.getInt("quantity"),
                    rs.getDate("published_date"), // Use parsed date
                    rs.getString("genre")
                );

                // Convert Book object to JSON
                Gson gson = new Gson();
                String json = gson.toJson(book);
                //out.println(formattedDate );
                //Send JSON response
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().write(json);

                con.close();
            } else {
                out.println("<div style=\"text-align: center; background-color: #ccffcc; border: 1px solid #00cc00; padding: 10px; border-radius: 5px;\">");
                out.println("<p style=\"font-size: 18px; color: #008000;\">Some error occurred! Please try again.</p>");
                out.println("<a href=\"signup.html\" style=\"text-decoration: none; background-color: #008000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
                out.println("</div>");
            }
            con.close();
        }
        catch (SQLException e) {
           out.println("<div style=\"text-align: center; background-color: #ffcccc; border: 1px solid #cc0000; padding: 10px; border-radius: 5px;\">");
           out.println("<p style=\"font-size: 18px; color: #cc0000;\">Error: " + e.getMessage() + "</p>");
           out.println("<a href=\"booklist.html\" style=\"text-decoration: none; background-color: #cc0000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Try Again</a>");
           out.println("</div>");
       }
       catch (ClassNotFoundException e) {
           // Handle the exception here
           e.printStackTrace(); // or any other handling mechanism
       }
       
    }
}
