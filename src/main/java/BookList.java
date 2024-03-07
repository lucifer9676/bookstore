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
import java.util.ArrayList;
import com.google.gson.Gson;

@WebServlet("/BookList")
public class BookList extends HttpServlet{
	public void doGet(HttpServletRequest req,HttpServletResponse res)throws ServletException, IOException{
		PrintWriter out = res.getWriter();
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
           Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore","root","");
           String query = "select * from books";
    		PreparedStatement ps = con.prepareStatement(query);
    		ResultSet rs = ps.executeQuery();
    		ArrayList<Book> books = new ArrayList<>();
    		while (rs.next()) {
               Book book = new Book();
               book.setId(rs.getInt("book_id"));
               book.setTitle(rs.getString("title"));
               book.setAuthor(rs.getString("author"));
               book.setPrice(rs.getDouble("price"));
               book.setQuantity(rs.getInt("quantity"));
               book.setPublishedDate(rs.getDate("published_date"));
               book.setGenre(rs.getString("genre"));
               books.add(book);
           }
        
           Gson gson = new Gson();
           String json = gson.toJson(books);

           res.setContentType("application/json");
           res.setCharacterEncoding("UTF-8");
           res.getWriter().write(json);

            con.close();
          	 
          	
       } catch (SQLException e) {
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