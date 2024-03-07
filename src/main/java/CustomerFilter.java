import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.PrintWriter;

@WebFilter({"/viewcart.html", "/ViewCart"})
public class CustomerFilter implements Filter {
    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest httpRequest = (HttpServletRequest) request;
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        HttpSession session = httpRequest.getSession(false);

        PrintWriter out = response.getWriter();

        if (session != null && "customer".equals(session.getAttribute("role"))) {
            // User is admin, allow the request to proceed
            chain.doFilter(request, response);
        } else {
            // User is not admin, redirect to an error page or deny access
            out.println("<div style=\"text-align: center; background-color: #ccffcc; border: 1px solid #00cc00; padding: 10px; border-radius: 5px;\">");
            out.println("<p style=\"font-size: 18px; color: #008000;\"only customer can view this page.</p>");
            out.println("<a href=\"index.html\" style=\"text-decoration: none; background-color: #008000; color: #ffffff; padding: 10px 20px; border-radius: 5px;\">Home</a>");
            out.println("</div>");
        }
    }

}
