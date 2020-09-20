import javax.servlet.annotation.WebServlet;
import java.io.IOException;
import java.io.PrintWriter;

@WebServlet("/miServlet")
public class miServlet extends javax.servlet.http.HttpServlet {

    protected void doPost(javax.servlet.http.HttpServletRequest request,
                          javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

        response.setContentType("text/html");
        PrintWriter out = new PrintWriter (response.getOutputStream());
        out.println("<html>");
        out.println("<head><title>ServletBienvenida</title></head>");
        out.println("<body >");
        String nombresin = request.getParameter("nombresin");
        out.println("Bienvenido a nuestra web, " + nombresin + "<br>");
        out.println("</body></html>");
        out.close();
    }

    protected void doGet(javax.servlet.http.HttpServletRequest request,
                         javax.servlet.http.HttpServletResponse response)
            throws javax.servlet.ServletException, IOException {

    }
}