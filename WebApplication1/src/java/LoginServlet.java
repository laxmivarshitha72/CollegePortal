import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("text/html");
        PrintWriter out = response.getWriter();
        
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        
        if (validate(username, password)) {
            //out.println("Login successful");  uncomment to get only simple message
            response.sendRedirect("file.html");  
        } else {
            out.println("Invalid username or password");
            response.sendRedirect("index.html");
        }
        
        out.close();
    }

    private boolean validate(String username, String password) {
        boolean status = false;
        Connection conn = null;
        PreparedStatement pst = null;
        ResultSet rs = null;

        String url = "jdbc:mysql://localhost:3306/jobhunt";
        String dbUsername = "root"; // Your DB username
        String dbPassword = "1234";     // Your DB password
        
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection(url, dbUsername, dbPassword);
            pst = conn.prepareStatement("SELECT * FROM login23 WHERE username=? AND password=?");
            pst.setString(1, username);
            pst.setString(2, password);
            rs = pst.executeQuery();
            status = rs.next();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (rs != null) rs.close();
                if (pst != null) pst.close();
                if (conn != null) conn.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return status;
    }
}