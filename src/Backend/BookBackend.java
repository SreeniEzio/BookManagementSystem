/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 *
 * @author lenovo
 */
public class BookBackend {
    public ArrayList<Object[]> retrieveAllBooks(){
        Connection conn = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from bookCollection;");
            while(rs.next()){
                result.add(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6)
                });
            }
            conn.close();
            st.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public ArrayList<Object[]> retrieveBorrowedBooks(int customerId){
        Connection conn = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from bookCollection where borrowedBy=" + customerId + ";");
            while(rs.next()){
                result.add(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6)
                });
            }
            conn.close();
            st.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public int addBook(String name, String author, String genre){
        Connection conn = null;
        String query = "insert into bookCollection (name, author, genre) values(\"" + name + "\",\"" +author + "\",\"" + genre + "\");"; 
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            System.out.println(query);
            st.execute(query);
            conn.close();
            st.close();
            return 1;
        }catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    public ArrayList<Object[]> retrieveAvailableBooks(int customerId){
        Connection conn = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from bookCollection where borrowedBy is NULL;");
            while(rs.next()){
                result.add(new Object[]{
                    rs.getInt(1),
                    rs.getString(2),
                    rs.getString(3),
                    rs.getString(4),
                    rs.getInt(5),
                    rs.getString(6)
                });
            }
            conn.close();
            st.close();
        }catch(SQLException ex){
            ex.printStackTrace();
        }
        return result;
    }
    
    public int borrowBook(int bookId, int customerId){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dateToday = LocalDate.now();
            LocalDate dueDate = dateToday.plusDays(20);
            String due = dueDate.format(dtf);
            //System.out.println("update bookCollection set borrowedBy=" + customerId + ",dueDate=\"" + due +"\" where id=" + bookId + ";");
            st.execute("update bookCollection set borrowedBy=" + customerId + ",dueDate=\"" + due +"\" where id=" + bookId + ";");
            conn.close();
            st.close();
            return 1;
        }catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int returnBook(int bookId){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            System.out.println("update bookCollection set borrowedBy=null,dueDate=null where id=" + bookId + ";");
            st.execute("update bookCollection set borrowedBy=null, dueDate=null where id=" + bookId + ";");
            conn.close();
            st.close();
            return 1;
        }catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int removeBook(int bookId){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            st.execute("delete from bookCollection where id=" + bookId + ";");
            conn.close();
            st.close();
            return 1;
        }catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }    
    }
    
    public int getBookCount(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(id) from bookCollection;");
            rs.next();
            int count = rs.getInt(1);
            conn.close();
            st.close();
            return count;
        }catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        } 
    }
    
    public void updateBorrow(int customerId){
        ArrayList<Object[]> books = retrieveBorrowedBooks(customerId);
        for(Object[] book: books){
            LocalDate today = LocalDate.now();
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            LocalDate dueDate = LocalDate.parse((String)book[5], dtf);
            if(today.isAfter(dueDate)){
                borrowBook((int)book[0], customerId);
            }
        }
    }
    
    public int updateBook(int bookId, String name, String author, String genre){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            String query = "update bookCollection set name=\"" + name + "\",author=\"" + author + "\",genre=\"" + genre + "\" where id=" + bookId + ";";
            st.execute(query);
            return 1;
        }catch(SQLException e){
            e.printStackTrace();
        }
        return 0;
    }
}
