/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author lenovo
 */
public class Account {
    //protected int accountId;
    protected String userName;
    protected String mobile;
    protected String password;
    protected String email;
    //private static int numberOfUsers;

    public Account() {
        //accountId = ++numberOfUsers;
        userName = "";
        mobile = "";
        password = "";
        email = "";
    }

    public Account(String name, String mob, String pass, String e) {
        //accountId = ++numberOfUsers;
        userName = name;
        mobile = mob;
        password = pass;
        email = e;
    }

    public void displayAccount() {
        //System.out.println("User Id: " + accountId);
        System.out.println("User Name: " + userName);
        System.out.println("Mobile: " + mobile);
        System.out.println("Password: " + password);
    }

    public void setPassword(String pass) {
        password = pass;
    }

    public String getPassword() {
        return password;
    }

    public void setMobile(String mob) {
        mobile = mob;
    }

    public String getMobile() {
        return mobile;
    }
    
    public String getEmail(){
        return email;
    }
    
    public void setEmail(String e){
        email = e;
    }
    
    public String getUserName(){
        return userName;
    }
    
    public void setUserName(String name){
        userName = name;
    }
}
