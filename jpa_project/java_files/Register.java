import java.io.PrintWriter;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.sql.*; 

public class Register extends HttpServlet {
  protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
    try{    
      String username = request.getParameter("username");
      String email = request.getParameter("email");
      String password = request.getParameter("password");

      Connection con=DriverManager.getConnection("jdbc:mysql://localhost:3306/www","root","rootroot");
      String searchQuery = "SELECT * FROM project1 WHERE username='"+username+"'";
      
      Statement st = con.createStatement();
      ResultSet rs = st.executeQuery(searchQuery);
      
      
      rs.last();
      int size = rs.getRow();
      rs.beforeFirst();
      if(size > 0) {
         response.setStatus(HttpServletResponse.SC_ACCEPTED);
      }
      else {
        //Connect to database  
        String query = "INSERT INTO project1(username,email,password) "+" VALUES(?, ?, ?)";

        PreparedStatement preparedStmt = con.prepareStatement(query);
        preparedStmt.setString(1, username);
        preparedStmt.setString(2, email);
        preparedStmt.setString(3, password);

        //Execute the query
        preparedStmt.execute();

        con.close();
        response.setStatus(HttpServletResponse.SC_OK);
      }
   }  
   catch(Exception e){
     System.err.println(e.getMessage()); 
   }

  }
}
