/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.sql.*;
import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
/**
 *
 * @author lenovo
 */
public class FineBackend {
    public ArrayList<Object[]> retrieveAllFine(){
        Connection conn = null;
        ArrayList<Object[]> result = new ArrayList<Object[]>();
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from transactionHistory");
            while(rs.next()){
                result.add(new Object[]{
                    rs.getInt("customerId"),
                    rs.getString("customerName"),
                    rs.getInt("fineAmount"),
                    rs.getString("transactionDate")
                });
            }
            
        }catch(SQLException e){
            e.printStackTrace();
        }
        return result;
    }
    
    public int addFine(int customerId, int fineAmount, String transactionDate){
        Connection conn= null;
        
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select * from customer where id="+customerId+";");
            rs.next();
            String customerName = rs.getString("name");
            String query = "insert into transactionHistory (customerId, customerName, fineAmount, transactionDate) values(" + customerId + ",\"" + customerName + "\"," + fineAmount + ",\"" + transactionDate + "\");";
            st.execute(query);
            return 1;
        }catch(SQLException e){
            e.printStackTrace();
            return 0;
        }
    }
    
    public void refreshFines(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            
            ResultSet rs = st.executeQuery("select id from customer;");
            while(rs.next()){
                int fine = 0;
                int customerId = rs.getInt("id");
                ArrayList<Object[]> books = new BookBackend().retrieveBorrowedBooks(customerId);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate dateToday = LocalDate.now();
                for(Object[] book : books){
                    String dueDateString = (String) book[5];
                    if(dueDateString != null){
                        LocalDate dueDate = LocalDate.parse(dueDateString, dtf);
                        long daysBetween = Period.between(dueDate, dateToday).getDays();
                        if(daysBetween > 0)
                            fine += daysBetween * 5;
                    }
                }
                System.out.println("ID: "+customerId + " Fine: " + fine);
                st.execute("update customer set fine=" + fine + " where id=" + customerId + ";");
            }
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
    
    public void removeFine(int customerId){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            st.execute("update customer set fine=null where id=" + customerId + ";");
        }catch(SQLException e){
            e.printStackTrace();
        }
    }
}
