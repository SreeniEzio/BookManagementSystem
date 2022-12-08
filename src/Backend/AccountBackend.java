/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;
import java.sql.*;
import java.util.HashMap;
/**
 *
 * @author lenovo
 */
public class AccountBackend {
    public int addAccount(String uname, String pass, String accountType, String mobile, String email){
        String query = "insert into " + accountType.toLowerCase() + " (name, pass, mobile, email) values(\"" + uname + "\",\"" + pass + "\",\"" + mobile + "\",\"" + email + "\");";
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            System.out.println(query);
            st.execute(query);
            return 1;
        } catch (SQLException e) {
            return 0;
        }
        
    }
    
    public int updateAccount(String accountType, HashMap<String,String> data, int id){
        String query = "update " + accountType + " set ";
        int count = 0;
        for(String key: data.keySet()){
            query += key + "=\"" + data.get(key)+"\"";
            if(count != data.size()-1)query += ",";
            count++;
        }
        
        query += " where id="+id+";";
        System.out.println(query);
        try {
            Connection conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            st.execute(query);
            return 1;
        } catch (SQLException e) {
            System.out.println("Exception");
            return 0;
        }
    }
    
    public int changePass(String accountType, String newPass, int id){
        String query = "update " + accountType + " set pass=\"" + newPass + "\" where id=" + id + ";";
        
        try{
            Connection con = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = con.createStatement();
            st.execute(query);
            st.close();
            con.close();
            return 1;
        }catch(SQLException ex){
            ex.printStackTrace();
            return 0;
        }
    }
    
    public int getCustomerCount(){
        Connection conn = null;
        try{
            conn = DriverManager.getConnection("jdbc:sqlite:data/library.db");
            Statement st = conn.createStatement();
            ResultSet rs = st.executeQuery("select count(id) from customer;");
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
}
