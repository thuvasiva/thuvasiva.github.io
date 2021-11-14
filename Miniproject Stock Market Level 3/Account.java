import java.io.*;
public class Account implements Serializable
{
    // instance variables - replace the example below with your own
    private double balance;

    public Account(double balance)
    {
        this.balance = balance;
    }
    
    //Adds money to balance
    public void payInMoney(double amount){
        balance = balance + amount;
    }
    
    //Decreases money from balance.
    public void payOutMoney(double amount){
        if(balance > amount){
            balance = balance - amount;
        }
    }
    
    //Gets balance.
    public double getBalance(){
        return balance;
    }
    
 
    
}
