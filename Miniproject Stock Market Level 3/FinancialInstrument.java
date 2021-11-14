import java.io.*;
import java.util.Random;
/*A financial instrument has value, a riskfactor(indicating by how much the value can vary per simulation), a tendency(showing the prediction
 * in value changes), the tendency as a value, so it can be manipulated, a name and a number storing how many of each financial instrument is owned.
 */
public class FinancialInstrument implements Serializable
{
    // instance variables
    private double value;
    private double riskFactor;
    private String tendency;  
    private double tendencyAsNumber;
    private String name;
    private int numberOfFinancialInstrumentsOwned; //Multiple of the same instrument can be owned.

    public FinancialInstrument(double value,String name, double riskFactor, String tendency)
    {
        // initialise instance variables
        this.value = value;
        this.riskFactor = riskFactor;
        this.name = name;
        this.tendency = tendency;
        tendencyAsNumber = tendencyToNumber(); //Converts the tendency into a number
        numberOfFinancialInstrumentsOwned = 0;
    }
    
    //Changes value by adding a small multiple of the current value, determined by the risk factor, a random number and a tendency.
    public void changeFinancialValueDaily()
    {   
        value = value + (value * riskFactor * (Math.random() - 0.5 + tendencyAsNumber));
        if(value <= 1){
            value = 0;
        }
    }
    
    public void setValue(double val){
        value = val; 
    }
    
    public double getCurrentValue(){
        return value;
    }
    
    //Prints details of instrument with tendency.
    public String printInstrumentDetails(){
        return "The value today of " + name + ", which has a risk factor of: " + riskFactor + " is " + value + "." + "\nIt's tendency is " + tendency.toLowerCase() + ".\n";
    }
    
    //Gets the value of the object with its name and riskfactor. (Shortened version) of printInstrumentDetails() for concise purchasing screen.
    public String getInstrumentValue()
    {
        return "The value of "+ name + " which has a risk factor of " + riskFactor + " is " + value + "."; 
    }
    
    //Changes the tendency and converts it to a number
    public void changeTendency(String tendency){
        this.tendency = tendency;
        tendencyToNumber();
    }
    
    //Converts the worded tendency to a number to be used in the changeFinancialValue methods.
    public double tendencyToNumber(){
        if(tendency.equals("Up")){
            tendencyAsNumber = 0.05;
        }
        else if(tendency.equals("Down")){
            tendencyAsNumber = -0.05;
        }
        else if(tendency.equals("XUp")){
            tendencyAsNumber = 0.15;
        }
        else if(tendency.equals("XDown")){
            tendencyAsNumber = -0.15;
        }
        else if(tendency.equals("Same")){
            tendencyAsNumber = 0;
        }
        return tendencyAsNumber;
    }
    
    //Every sale has a tax figure which is deducted from the money deposited to your account.
    public double taxOnSell(){
        return value * 0.1;
    }
        
    //Purchases financial instrument, increasing number owned
    public boolean purchaseFI(){
        numberOfFinancialInstrumentsOwned++;
        return true;
    }
    
    //Sells a financial instrument
    public void sellFI(){
        numberOfFinancialInstrumentsOwned--;
    }
   
    public int getFIOwned(){
        return numberOfFinancialInstrumentsOwned;
    }
     
    public String getTendency(){
        return tendency;
    }
    
    public void setRiskFactor(double riskFactor){
        this.riskFactor = riskFactor;
    }
    
    public double getRiskFactor(){
        return riskFactor;
    }
    
    public String getName(){
        return name;
    }
    
}
    
    
    
   
        
