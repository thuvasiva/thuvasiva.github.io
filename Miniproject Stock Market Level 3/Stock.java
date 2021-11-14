import java.util.Random;

//Stock is a collection of shares(1 or more) They have all properties of a financial instrument along with a sector.
public class Stock extends FinancialInstrument{
    private String sector;

    public Stock(double value, String name, double riskFactor, String sector, String tendency){
        super(value,name,riskFactor,tendency);
        this.sector = sector;
        if(sector.equals("Automotive")){
            setRiskFactor(getRiskFactor() + 0.05); //The automotive sector increases risk factor.
        }
        
    }
    
    public void changeFinancialValueDaily(){
        super.changeFinancialValueDaily();
        if(getCurrentValue()>=100000 & getCurrentValue()<200000){
            changeTendency("Same"); // Overriden to encourage the shares from rapidly increasing beyond 100000.
        }
        else if(getCurrentValue()>200000){
            setValue(10000 * Math.random() + 150000); //Overriden to stop shares from going over 200000. Here to stop impossible stock prices.
        }
        //A company may change its business plans upon getting poor to save itself, and so gets assigned to a new sector.
        else if(getCurrentValue()<1000){
            if(Math.random() > 0.5){
                sector = "High Street";
            }
            else{
                sector = "Technology";
            }
        }
    }
   
    public String getSector(){
        return sector;
    }
    
    public double taxOnSell(){
        if(getSector().equals("High Street")){
            return getCurrentValue() * 0.02;   //Overrides with lower rate of tax for High Street sector.
        }
        else if(getSector().equals("Technology")){
            return getCurrentValue() * 0.15;    //Overrides with higher rate of tax for Technology sector.
        }
        return super.taxOnSell();
    }
    
    //Changes the tendencies of companies according to sector in a pandemic situation.
    public void sectorToTendencyInPandemic(){
        if(getSector().equals("Technology"))
        {
            changeTendency("XUp");
        }
        else if(getSector().equals("High Street")){
            changeTendency("XDown");
        }
        else if(getSector().equals("Automotive")){
            changeTendency("Down");
        }
        else if(getSector().equals("Supermarket")){
            changeTendency("XUp");
        }
        tendencyToNumber();
  
    }
    
    //Changes the tendencies after a pandemic.
    public void sectorAfterPandemic(){
        if(getSector().equals("Technology"))
        {
            changeTendency("Down");
        }
        else if(getSector().equals("High Street")){
            changeTendency("Up");
        }
        else if(getSector().equals("Automotive")){
            changeTendency("Up");
        }
        else if(getSector().equals("Supermarket")){
            changeTendency("Same");
        }
    }
    
    //Overrides the method by adding the sector.
    public String printInstrumentDetails(){
        return super.printInstrumentDetails() + " The sector of it is " + sector + ".\n";
    }
    
}
    
    
    

