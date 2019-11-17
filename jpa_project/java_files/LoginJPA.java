import java.util.List;

import java.io.*;
import javax.servlet.http.*;
import javax.servlet.*;

import javax.persistence.Access;
import javax.persistence.AccessType;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Query;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Parameter;

@Entity
@Table(name="userData")
@Access(value=AccessType.FIELD)
class UserData implements Serializable{
  
  @Column(name="username")
  private String username;
  @Column(name="password")
  private String password;
  @Column(name="email")
  private String email;

  @Id
  @Column(name = "ID", unique = true, nullable = false)
  private int id;

  public int getId() {
    return this.id;
  }

  public void setId(int id) {
    this.id = id;
  }
  
  public String getEmail(){
    return this.email;
  }

  public void setEmail(String email){
    this.email = email;
  }
  
  public String getPassword(){
    return this.password;
  }
  public void setPassword(String password){
    this.password = password;
  }
  
  public String getUsername(){
    return this.username;
  }
  
  public void setUsername(String username){
    this.username = username;
  }
} 
public class LoginJPA extends HttpServlet {
  private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("UserData");

  protected void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException{
  
    EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
    EntityTransaction transaction = null;

    try {
    
        String username = request.getParameter("username");
        String password = request.getParameter("password");    
    
        // Get a transaction
        transaction = manager.getTransaction();
        // Begin the transaction
        transaction.begin();
        
        String stringQuery = "SELECT count(U) FROM UserData U where U.username= '"+username+"' and U.password= '"+password+"'";
        Query query = manager.createQuery(stringQuery);
        int size = ((Number) query.getSingleResult()).intValue();      
        
        transaction.commit();
        
        if(size> 0) {
          response.setStatus(HttpServletResponse.SC_OK);
        } else {
          response.setStatus(HttpServletResponse.SC_ACCEPTED); 
        }
    } catch (Exception ex) {
        // If there are any exceptions, roll back the changes
        if (transaction != null) {
            transaction.rollback();
        }
        // Print the Exception
        ex.printStackTrace();
    } finally {
        // Close the EntityManager
        manager.close();
    }
  }
}

