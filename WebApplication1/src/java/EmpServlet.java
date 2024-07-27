import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/EmpServlet")
public class EmpServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String firstname = request.getParameter("firstname");
        String lastname = request.getParameter("lastname");
        String emailid = request.getParameter("emailid");
        String countrycode = request.getParameter("countrycode");
        String phonenum = request.getParameter("phonenum");
        String gender = request.getParameter("gender");
//        String skills = request.getParameter("skills");

        Connection con = null;
        PreparedStatement pstmt = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobhunt", "root", "1234");

            pstmt = con.prepareStatement("INSERT INTO Employee(username, password, firstname, lastname, emailid, countrycode, phonenum, gender) VALUES (?, ?, ?, ?, ?, ?, ?, ?)");
            pstmt.setString(1, username);
            pstmt.setString(2, password); // In a real application, hash the password before storing
            pstmt.setString(3, firstname);
            pstmt.setString(4, lastname);
            pstmt.setString(5, emailid);
            pstmt.setString(6, countrycode);
            pstmt.setString(7, phonenum);
            pstmt.setString(8, gender);
//            pstmt.setString(9, skills);
            pstmt.executeUpdate();

            response.getWriter().println("Registration successful");
            response.sendRedirect("com_login.html"); // Redirect to success page or homepage
        } catch (ClassNotFoundException | SQLException e) {
            response.getWriter().println("ERROR: " + e.getMessage());
        } finally {
            try {
                if (pstmt != null) pstmt.close();
                if (con != null) con.close();
            } catch (SQLException e) {
                response.getWriter().println("ERROR closing resources: " + e.getMessage());
            }
        }
    }
}

