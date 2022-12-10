/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Backend;

/**
 *
 * @author lenovo
 */
public class Customer extends Account{
    public int customerId;
    public int fine;
    public Customer(){
        super();
    }
    
    public Customer(int id, String name, String pass, String mob, String email, int f){
        super(name, mob, pass, email);
        customerId = id;
        fine = f;
    }
    
    public void displayDebug(){
        System.out.println("ID: " + customerId);
        System.out.println("Name: " + userName);
        System.out.println("Pass: " + password);
        System.out.println("Email: " + email);
        System.out.println("Mobile: " + mobile);
        System.out.println("Fine: " + fine);
    }
}
