import javax.servlet.*;
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

@WebServlet("/ViewCart")
public class ViewCart extends HttpServlet {
    public void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter out = res.getWriter();
        HttpSession session = req.getSession(false);

        String id = "1";//String.valueOf(session.getAttribute("id"));
        ArrayList<Integer> ids = new ArrayList<>();
        ArrayList<Book> books = new ArrayList<>();
        ArrayList<Integer> quantities = new ArrayList<>();

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/bookstore", "root", "");
            String query = "select * from cart_" + id;
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                ids.add(rs.getInt("book_id"));
                quantities.add(rs.getInt("quantity"));
            }

            if (ids.isEmpty()) {
                res.setContentType("application/json");
                res.setCharacterEncoding("UTF-8");
                res.getWriter().write("[]");
                return;
            }

            String sql = "SELECT * FROM books WHERE book_id IN (";
            for (int i = 0; i < ids.size(); i++) {
                sql += (i == 0 ? "?" : ", ?");
            }
            sql += ")";
            PreparedStatement stmt = con.prepareStatement(sql);

            for (int i = 0; i < ids.size(); i++) {
                stmt.setInt(i + 1, ids.get(i));
            }
            ResultSet rs1 = stmt.executeQuery();
            int quantityIndex = 0;
            while (rs1.next()) {
                Book book = new Book();
                book.setId(rs1.getInt("book_id"));
                book.setTitle(rs1.getString("title"));
                book.setAuthor(rs1.getString("author"));
                book.setPrice(rs1.getDouble("price"));
                book.setQuantity(quantities.get(quantityIndex));
                book.setPublishedDate(rs1.getDate("published_date"));
                book.setGenre(rs1.getString("genre"));
                books.add(book);
                quantityIndex++; 
            }

            
            Gson gson = new Gson();
            String json = gson.toJson(books);

           
            res.setContentType("application/json");
            res.setCharacterEncoding("UTF-8");
            res.getWriter().write(json);

            con.close();
        } catch (SQLException e) {
            
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        } catch (ClassNotFoundException e) {
            
            e.printStackTrace();
            res.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }
    }
}
