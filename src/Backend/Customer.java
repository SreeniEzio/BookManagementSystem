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
    public Customer(){
        super();
    }
    
    public Customer(int id, String name, String pass, String mob, String email){
        super(name, mob, pass, email);
        customerId = id;
    }
}
