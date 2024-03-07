import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(urlPatterns = {"/*"}, dispatcherTypes = {DispatcherType.REQUEST, DispatcherType.FORWARD})
public class FilterSession implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        String servletPath = request.getServletPath();
        
        HttpSession session = request.getSession(false);

        // login ra sign up page display huna sessions chaidaina so
        if (servletPath.equals("/login.html") || servletPath.equals("/signup.html")||servletPath.equals("/Login") || servletPath.equals("/Signup")) {
            if(session != null){
                response.sendRedirect("index.html");
                return;
            }
            else{
                filterChain.doFilter(request, response);
                return;
            }
            
        }
        if (session != null) {
            filterChain.doFilter(request, response); 
        }
        else {
            response.sendRedirect("login.html");//session chaina vane redirect
        }
       
       
    }

}
