
public class Cash extends FinancialInstrument
{
    private double inflation; //Cash is affected by an inflation rate, leading its value to decrease over time. The level of inflation changes over time,
    
    public Cash(double value,String name, double inflation)
    {
        super(value,name,0.001,"Down"); //Constructs with a very small risk factor, emphasising the safety of cash.
        this.inflation = inflation;
       
    }
    
    //Overrides the method by dividing the changed value by an inflation rate to decrease its value.
    public void changeFinancialValueDaily(){
        super.changeFinancialValueDaily();
        setValue(getCurrentValue()/getInflationRate());
        
    }
    
    //Changes the inflation rate to a new number(the new inflation rate is determined by the events class)
    public void changeInflationRate(double inflationRate){
        inflation = inflationRate;
    }
     
    public double getInflationRate(){
        return inflation;
    }
      
}
