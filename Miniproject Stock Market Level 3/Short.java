
/*Shorts are a version of stock, but when you purchase one you gain money and sell one you lose money(an implementation of the idea of a real short,
 * where you can sell a short you don't actually own, but you have to return the short that you bought back to its owner within a certain time frame, 
 * whether the value has gone up or down. Here instead of selling, you purchase at negative value and your short will be automatically sold at term end.)
 */
public class Short extends Stock
{
    // instance variables - replace the example below with your own
    private int term;
    private int time;
    
    public Short(double value, String name, double riskFactor, String sector, String tendency,int term)
    {
        super(value,name + " (Short)",riskFactor,sector,tendency);
        this.term = term;
        time = 0;
    }
    
    //The instruemnt will be sold automatically after a time period
    public boolean autoSell(){
        if(getTime() > getTerm()){
            int numberOwned = getFIOwned();
            for(int i = 0; i<numberOwned; i++){
                sellFI();
            }
            return true;
        }
        return false;
    }
    
    public double getCurrentValue(){
        return -(super.getCurrentValue()); //Overriden to get negative value
    }
   
    public String printInstrumentDetails(){
        return super.printInstrumentDetails() + "The term is : " + getTerm() + " days.\n";
    }
    
    public int getTerm(){
        return term;
    }
    
    //Changing the value now increases the time counter by 1.
    public void changeFinancialValueDaily(){
        super.changeFinancialValueDaily();
        time++;
    }
    
    public int getTime(){
        return time;
    }
    
    //Time is set to 0 on purchase
    public boolean purchaseFI(){
        super.purchaseFI();
        if(getFIOwned() == 1){
        time = 0;
        }
        return true;
    }
    
    //Time is set to 0 on sell.
    public void sellFI(){
        super.sellFI();
        time = 0;
    }
          
}
