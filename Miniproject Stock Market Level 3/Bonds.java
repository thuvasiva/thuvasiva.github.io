
//Bonds are investments which earn interest over time by purchasing the bond. The full value can be reclaimed at the end of the bond term.
public class Bonds extends FinancialInstrument
{
    // instance variables - replace the example below with your own
    private double interest;
    private String taxBracket;

    /**
     * Constructor for objects of class Bonds
     */
    public Bonds(double value, String name, double interest, int termlength,String taxBracket)
    {
        // initialise instance variables
        super(value,name + "(Bond)",0.00001,"Same"); //Set so the bond only slightly changes in value and given a 'same' tendency.
        this.interest = interest; 
        this.taxBracket = taxBracket;
    }
    
    //Overrides taxOnSell method by charging no tax for a government bond's sale.
    public double taxOnSell(){
        if(getTaxBracket().equals("Government Bond")){
            return 0;
        }
        return getCurrentValue() * 0.05; //Overrides tax rate as 5% for bonds
    }
    
    //Changes interest value by adding a small random number which can be negative or positive to it.
    public void changeInterest(){
        double originalInterest = getInterest();
        interest = getInterest() + (0.01* Math.random())-0.005;
        if(getInterest()<0){
            interest = 0; //Prevents interest from hitting 0.
        } 
    }
    
    //Generates interest by getting the value of the bond and multipling it by the interest and 0.1, to make it relatively small.
    public double generateInterestPaymentDaily(){
        return getInterest() * getCurrentValue() * 0.1;
    }
     
    //Overrides method to print interest rate and term length as well.
    public String printInstrumentDetails(){
        return super.printInstrumentDetails() + "Interest rate:" + getInterest() + "\n" + "Tax bracket: " + getTaxBracket() + "\n";
    }
    
    //Overrides the purchase method so that only one can be purchased unlike stock.
    public boolean purchaseFI(){
        if(getFIOwned()==0){
            super.purchaseFI();
            return true;
        }
        else{
            return false;
        }
        
    }
    
    public double getInterest(){
        return interest;
    }
    
    public String getTaxBracket(){
        return taxBracket;
    }
    
    //Overrides the getInstrumentValue() method by adding interest rate. (Concise version of printInstrumentValue())
    public String getInstrumentValue()
    {
        return super.getInstrumentValue() + "\n The new interest rate is " + getInterest();
    }
             
}
    
         


