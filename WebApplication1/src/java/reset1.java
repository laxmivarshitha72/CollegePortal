import javax.servlet.*;
import javax.servlet.http.*;
import java.io.*;
import java.sql.*;

public class reset1 extends HttpServlet {
    private static final long serialVersionUID = 1L;

    // JDBC URL, username, and password of MySQL server
    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/jobhunt";
    private static final String JDBC_USER = "root";
    private static final String JDBC_PASSWORD = "Afreen@2108";

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String username = request.getParameter("username");
        String newPassword = request.getParameter("newPassword");

        Connection conn = null;
        PreparedStatement stmt = null;

        try {
            // Step 1: Register JDBC Driver
            Class.forName("com.mysql.jdbc.Driver");

            // Step 2: Open a connection
            conn = DriverManager.getConnection(JDBC_URL, JDBC_USER, JDBC_PASSWORD);

            // Step 3: Update the password for the given username
            String sql = "UPDATE users SET password = ? WHERE username = ?";
            stmt = conn.prepareStatement(sql);
            stmt.setString(1, newPassword);
            stmt.setString(2, username);

            int rowsAffected = stmt.executeUpdate();

            if (rowsAffected > 0) {
                // Password updated successfully
                response.sendRedirect("reset2.html?message=Password updated successfully");
            } else {
                // Invalid username 
                response.sendRedirect("forgot1.html?error=invalid_credentials");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            response.sendRedirect("forgot.html?error=database_error");
        } finally {
            // Step 6: Clean-up environment
            try {
                if (stmt != null) stmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
