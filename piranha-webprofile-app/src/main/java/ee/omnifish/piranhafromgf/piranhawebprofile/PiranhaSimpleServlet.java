package ee.omnifish.piranhafromgf.piranhawebprofile;

import jakarta.enterprise.context.RequestScoped;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/piranha")
@RequestScoped
public class PiranhaSimpleServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/plain");
        final PrintWriter writer = response.getWriter();
        writer.println("Hello from Piranha Cloud - Web Profile!");
    }

}
