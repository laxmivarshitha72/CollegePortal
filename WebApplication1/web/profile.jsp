
<%@ page import="java.sql.Connection, java.sql.DriverManager, java.sql.PreparedStatement, java.sql.ResultSet, java.sql.SQLException" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <title>Student Profile</title>
    <style>
        body {
            margin: 0;
            padding: 0;
            font-family: Arial, sans-serif;
            background-color: #ffffff; 
        }

        .rect {
            padding: 10px;
            background: #e6f2ff;
            color: #000000;
            margin-bottom: 10px;
        }

        h2, h3 {
            font-weight: normal;
        }

        .rect h3 {
            margin: 0;
            font-size: 18px;
            font-weight: normal;
        }

        .rect h2 {
            margin: 0;
            font-size: 16px;
            font-weight: normal;
            color: rgb(7, 48, 100);
        }

        .rect table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 10px;
            margin-bottom: 10px;
        }

        .rect th, .rect td {
            border: 1px solid #ccc;
            padding: 8px;
            text-align: left;
        }

        .rect th {
            background-color: #f2f2f2;
        }

        .rect .toggle-link {
            color: rgb(7, 48, 100);
            cursor: pointer;
            text-decoration: none;
            font-size: 16px;
        }

        .rect .toggle-link:hover {
            text-decoration: underline;
        }

        .rect .toggle-content {
            background-color: white; /* Set toggle content background color to white */
            display: none;
        }

        .button {
            width: 150px;
            height: 30px;
            background-color: #70a9df;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
        }

        .button1 {
            width: 150px;
            height: 30px;
            background-color: #70a9df;
            color: white;
            border: none;
            border-radius: 5px;
            cursor: pointer;
            margin-top: 10px;
            margin-left: 10px;
        }
    </style>
    <script>
        function toggleTable(tableId) {
            var table = document.getElementById(tableId);
            var currentDisplay = table.style.display;

            // If the table is currently hidden, show it; otherwise, hide it
            table.style.display = (currentDisplay === 'none') ? 'table' : 'none';
        }

        function displayForm() {
            alert("Functionality to print or export data goes here.");
        }
    </script>
</head>
<body>
    <h3 style="color: rgb(7, 48, 100);">STUDENT PROFILE</h3>
    <h2>---------------------------------------------------------------------------------------------------------------------------------------------------------</h2>
    
    <% 
        Connection conn = null;
        PreparedStatement pstmt = null;
        ResultSet rs = null;
        
        try {
            // Establish database connection
            Class.forName("com.mysql.cj.jdbc.Driver");
            conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/jobhunt", "root", "1234");
            
            // Retrieve username from session
            String username = (String) session.getAttribute("username");

            // Prepare SQL query to fetch user details from admission table
            String query = "SELECT * FROM users WHERE username = ?";
            pstmt = conn.prepareStatement(query);
            pstmt.setString(1, username); // Set the username parameter
            rs = pstmt.executeQuery();
            
            // Display user details
            if (rs.next()) {
                %>
                <div class="rect">
                    <h3><span class="toggle-link" onclick="toggleTable('personalDetails')">PROFILE</span></h3>
                    <table id="personalDetails" class="toggle-content">
                        
                       <tr>
                            <th>Username</th>
                            <td><%= rs.getString("username") %></td>
                        </tr>
                        <tr>
                            <th>First-name</th>
                            <td><%= rs.getString("firstname") %></td>
                        </tr>
                        <tr>
                            <th>Last-name</th>
                            <td><%= rs.getString("lastname") %></td>
                        </tr>
                        <tr>
                            <th>Email-id</th>
                            <td><%= rs.getString("emailid") %></td>
                        </tr>
                        <tr>
                            <th>Country code</th>
                            <td><%= rs.getString("countrycode") %></td>
                        </tr>
                        <tr>
                            <th>Phone num</th>
                            <td><%= rs.getString("phonenum") %></td>
                        </tr>
                        <tr>
                            <th>Gender</th>
                            <td><%= rs.getString("gender") %></td>
                        </tr>
                        <tr>
                            <th>Skills</th>
                            <td><%= rs.getString("skills") %></td>
                        </tr>
                    </table>
                </div>
                <%
            } else {
                out.println("<p>No records found.</p>");
            }

//            // Fetch attendance details
//            String attendanceQuery = "SELECT * FROM users WHERE username = ?";
//            pstmt = conn.prepareStatement(attendanceQuery);
//            pstmt.setString(1, username);
//            ResultSet rsAttendance = pstmt.executeQuery();
            %>

            <%
        } catch (ClassNotFoundException | SQLException e) {
            out.println("Error retrieving data: " + e.getMessage());
        } finally {
            // Close resources
            try {
                if (rs != null) rs.close();
                if (pstmt != null) pstmt.close();
                if (conn != null) conn.close();
            } catch (SQLException e) {
                out.println("Error closing resources: " + e.getMessage());
            }
        }
    %>
</body>
</html>