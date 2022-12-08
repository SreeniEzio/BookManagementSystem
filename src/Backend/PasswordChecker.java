/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.sql.*;
import java.util.ArrayList;

/**
 *
 * @author lenovo
 */
public class PasswordChecker {
    public ArrayList<Customer> retrieveCustomer(String name){
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Customer> cust = new ArrayList<Customer>();
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            
            rs = st.executeQuery("SELECT * FROM customer;");//implement using where keyword 
            //System.out.println("SELECT * FROM " + accountType + ";");
            //ArrayList<Customer> cust = new ArrayList<Customer>();
            if(rs == null)System.err.println(" rs is null");
        
            while(rs.next()){
                String uname, pass, mob, email;
                int id;
                id = rs.getInt(1);
                uname = rs.getString(2);
                pass = rs.getString(3);
                mob = rs.getString(4);
                email = rs.getString(5);
                //System.out.println(id + "\t" + uname + "\t" + pass + "\t" + mob + "\t" + email);
                cust.add(new Customer(id, uname, pass, mob, email));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            if(conn != null)try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return cust;
    }
    
    public ArrayList<Librarian> retrieveLibrarian(String name){
        ResultSet rs = null;
        Connection conn = null;
        ArrayList<Librarian> lib = new ArrayList<Librarian>();
        try {
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            
            rs = st.executeQuery("SELECT * FROM librarian;");//implement using where keyword 
            //System.out.println("SELECT * FROM " + accountType + ";");
            //ArrayList<Customer> cust = new ArrayList<Customer>();
            if(rs == null)System.err.println(" rs is null");
            
            while(rs.next()){
                String uname, pass, mob, email;
                int id;
                id = rs.getInt(1);
                uname = rs.getString(2);
                pass = rs.getString(3);
                mob = rs.getString(4);
                email = rs.getString(5);
                //System.out.println(id + "\t" + uname + "\t" + pass + "\t" + mob + "\t" + email);
                lib.add(new Librarian(id, uname, pass, mob, email));

            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }finally{
            if(conn != null)try {
                conn.close();
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
        return lib;
    }
    
    public int verifyLoginDetails(String name, String pass, String userType){
        if(userType.equals("Customer")){
            ArrayList<Customer> cust = retrieveCustomer(name);
            for(Customer c : cust){
                
                if(c.getUserName().equals(name) && c.getPassword().equals(pass)){
                    return c.customerId;
                }
            }
        }else{
            ArrayList<Librarian> lib = retrieveLibrarian(name);
            for(Librarian l : lib){
                if(l.getUserName().equals(name) && l.getPassword().equals(pass))
                    return l.librarianId;
            }
        }
        
        return -1;
    }
    
}
