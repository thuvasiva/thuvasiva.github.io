import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.io.*;

public class GUI extends Frame implements ActionListener
{
    // instance variables
    private TextArea t1,t2,t3;
    private Account myBalance;
    private ArrayList<FinancialInstrument> instruments;
    private ArrayList<Double> movingAverages;
    private ArrayList<ArrayList<Double>> rawData;
    private int pandemicCount = 0;
    private int daysPassed = 0;
    private int companyNumber = 0;
    private List lst;

    public GUI()
    {  
       t1 = new TextArea(20,80);
       add("North",t1);
       
       t2 = new TextArea(10,80);
       add("West",t2);
       
       t3 = new TextArea(5,80);
       add("South",t3);
       t3.append("Key events will be displayed here:\n");
        
       //Initializing variables
       myBalance = readBalanceFromFile();
       instruments = readInstrumentsFromFile();
       rawData = new ArrayList<>();
       for(int i = 0; i<instruments.size(); i++){
           rawData.add(new ArrayList<Double>());
       }
       
       //Generate initial set of moving averages.
       generateMovingAverages();
   
       Panel buttonPanel = new Panel();
       Button b1 = new Button("Simulate 1 Day");       
       buttonPanel.add(b1);
       b1.addActionListener(this);
       Button b2 = new Button("Purchase");
       buttonPanel.add(b2);
       b2.addActionListener(this);
       Button b3 = new Button("Sell");
       buttonPanel.add(b3);
       b3.addActionListener(this);
       Button b4 = new Button("Add Instrument");
       buttonPanel.add(b4);
       b4.addActionListener(this);
       Button b5 = new Button("Quit For Now");
       buttonPanel.add(b5);
       b5.addActionListener(this);
       Button b6 = new Button("End Simulation");
       buttonPanel.add(b6);
       b6.addActionListener(this);
       Button b7 = new Button("Show Moving Averages");
       buttonPanel.add(b7);
       b7.addActionListener(this);
       
       add("Center",buttonPanel);
       
       lst = new List(10,false);
       for(int i = 0; i<instruments.size(); i++){
           lst.add(instruments.get(i).getName());
       }
       add("East",lst);
         
       pack();
       show();
    }
    
    public void addOpeningText(){
       t1.append("To start this stock market simulation press the 'Simulate 1 Day' button. After this all Stock/Bonds/Short/Cash items will be displayed.\n");
       t1.append("These are the assets you can purchase and sell in the simulation. If you want more assets to trade with, you can use the 'Add Instrument' button. \n");
       t1.append("The aim is to buy and sell these assets to have a net profit.\n\n");
       t1.append("The 4 types of items have key differences.\n\n");
       t1.append("Stock are ordinary stock which fluctuate in value.\n\n");
       t1.append("Shorts are stock you can purchase for negative money, simulating selling a stock you do not own. However, shorts have to be bought back to the company!\n");
       t1.append("After a certain timeframe, the short will be sold automatically otherwise.\n");
       t1.append("If you buy more of the same short, you have until the term of the original short expires, to sell ALL shorts.\n\n");
       t1.append("Bonds are investments, which earn interest for as long as you own them.\n");
       t1.append("Bonds, unlike Stock or Shorts, only 1 can be purchased of each. Therefore a second purchase of the same bond will cause an unsuccessful transaction.\n\n");
       t1.append("You start with £1000 of cash as well, which tends to decrease in value over time due to inflation. The remains of your cash will be added back to your balance at the end.\n\n");
       t1.append("If you end this simulation or become bankrupt, this window will close and your results will be displayed on a file named finalresults.txt.\n\n");
       t1.append("Key note: if you press the button 'Quit For Now', all progress will be saved regarding balance, and assets owned.\n");
       t1.append("How to purchase/sell: Press Purchase/Sell -> Select item on list(RHS) -> Press Purchase/Sell.\n");
       t1.append("Good Luck!\n");
    }
    
    public void addReturningText(){
        t1.append("You are carrying on from where you left off previously.\n");
        t1.append("If you do not want to do this, press 'End Simulation'.\n");
        t1.append("The values of all Stock/Short/Bonds/Cash objects will be as last time.\n");
        t1.append("Your account balance will be the same as previously.\n");
        t1.append("All the assets in the simulation will be the same as last time.\n");
        t1.append("However, moving average data and the day counter have been reset.\n");
        t1.append("Press Simulate 1 Day to continue.\n");
    }
 
    public ArrayList<FinancialInstrument> readInstrumentsFromFile(){
        ArrayList<FinancialInstrument> instruments = new ArrayList<>();
        try{
            ObjectInputStream input = new ObjectInputStream(new FileInputStream("Instruments.txt"));
            while(true){
            Object o = input.readObject();
            FinancialInstrument fi = (FinancialInstrument) o;
            instruments.add(fi);
            }
        }
        catch(ClassNotFoundException e){}
        catch(IOException e){}
        if(instruments.size() == 0){ //If the file is empty new instruments are created
             addOpeningText();
             return createInstruments(new Account(10000));
        }
        addReturningText(); 
        return instruments;
    }
    
    public Account readBalanceFromFile(){
        Account myBalance = null;
        try{
             ObjectInputStream input = new ObjectInputStream(new FileInputStream("Account.txt"));
             while(true){
               Object o = input.readObject();
               myBalance = (Account) o;
             }
        }
        catch(ClassNotFoundException e){}
        catch(IOException e){}
        if(myBalance == null){ //If the file is empty a new Account object is created
            myBalance = new Account(9000);
        }
        return myBalance;
    }
    
    public void actionPerformed(ActionEvent evt){
        if(evt.getActionCommand().equals("Simulate 1 Day")){
            for(int i = 0; i<instruments.size(); i++){
            instruments.get(i).changeFinancialValueDaily();
                if(instruments.get(i) instanceof Bonds)
                { 
                    bondInterestPayment(i); //Generates bond interest payment if due
                }
            t1.append(instruments.get(i).printInstrumentDetails()+"\n\n");
            }
            
            //Auto sells short if needed.
            for(int i = 0; i<instruments.size(); i++){ 
                if(instruments.get(i) instanceof Short){ 
                    if(instruments.get(i).getFIOwned() > 0){ //If an object is a Short and is owned and term limit has been passed it is sold automatically.
                        int numberOwned = instruments.get(i).getFIOwned();
                        boolean readyToSell = ((Short)instruments.get(i)).autoSell();
                        if(readyToSell){
                            for(int j = 0; j<numberOwned; j++){
                                myBalance.payInMoney(instruments.get(i).getCurrentValue());
                            }
                            t3.append("Short(s) sold\n");
                            t2.append("Your balance has changed!");
                        }
                    }
                }
            }
            
            
            if(Events.isPandemic() & pandemicCount == 0) //A pandemic may be generated ever so often changing tendencies for stock(or short) objects.
            {   
                pandemicCount++;
                t3.append("Oh no there has been a pandemic. Tendencies have changed.\n");
                for(int i = 0; i<instruments.size();i++)
                {
                    if(instruments.get(i) instanceof Stock)
                    {   
                        ((Stock)instruments.get(i)).sectorToTendencyInPandemic(); 
                     }
            
                }
            
            }
            
            if(Events.pandemicOver() & pandemicCount == 1){ //Ends a pandemic changing tendenices to post pandemic ones.
                pandemicCount++;
                t3.append("The pandemic is over\n");
                for(int i = 0; i<instruments.size();i++){
                    if(instruments.get(i) instanceof Stock)
                    {
                        ((Stock)instruments.get(i)).sectorAfterPandemic();
                    }
                    
                 }
            }
           
            if(Events.isInflationRateChange()) //An inflation rate change may be occur ever so often. 
            {
                double newRate = Events.InflationRateChange();
                ((Cash)instruments.get(9)).changeInflationRate(newRate);
                t3.append("There has been an inflation rate change!\n");
                t3.append("New inflation rate is: " + newRate + "\n");
            }
            
            //If the user is bankrupt the simulation ends.
            if(myBalance.getBalance() < 0){
                printResultsToFile();
                System.exit(0);
            }
            
            t1.append("Days passed since current simulation period "+ daysPassed + "\n");
            generateMovingAverages();
            daysPassed++;
        }
        else if(evt.getActionCommand().equals("Purchase")){
              
             if(lst.getSelectedItems().length == 0){
               for(int i = 0; i<instruments.size(); i++)
                {
                    if(!(instruments.get(i) instanceof Cash)) //If the instrument is a bond or stock or short it is displayed
                    {
                        t2.append((i+1) + ") " +  instruments.get(i).getInstrumentValue() + "\n"); 
                    }
                }
               t2.append("Your balance: " + myBalance.getBalance() + "\n");
               t2.append("To purchase select an item on the list and press purchase again to confirm.\n");
             }
            
             int choice = lst.getSelectedIndex();
             lst.deselect(lst.getSelectedIndex());
                
             if(choice != -1){
                double amount = instruments.get(choice).getCurrentValue(); //Stores the amount to be paid for that particular choice.
                
                if(myBalance.getBalance() > amount){
                    try{        
                        if(completePurchase(choice)){
                            myBalance.payOutMoney(amount);
                            t2.append("Purchase complete\n");
                        }
                        else{
                            t2.append("Only 1 bond can be purchased at maximum.\n");
                        }
                    }
                    catch(CashTradingException e){
                        t2.append("You cannot trade with cash!\n");
                    }
                   
                }//Money paid out
                else{
                    t2.append("Transaction failed. You do not have enough money!\n\n");
                }
                
                
                t2.append("Your balance: " + myBalance.getBalance()+"\n\n");
             }
        }
        
        else if(evt.getActionCommand().equals("Sell")){    
             if(lst.getSelectedItems().length == 0){
                t2.append("Selling mode\n");
                t2.append("These are the available instruments.\n");
                t2.append("If you do not own anything, nothing will be displayed.\n"); 
                
                for(int i = 0; i<instruments.size(); i++)
                {
                   if(instruments.get(i).getFIOwned() > 0){ //If an instrument is owned it is displayed.
                       t2.append((i+1) + ")" + instruments.get(i).getInstrumentValue() + "\n");
                   }
                }
                t2.append("Select an item on the list on the menu and then press sell again to sell.\n");
                }
            
             int choice = lst.getSelectedIndex(); //User chooses which one they want to sell,
             lst.deselect(lst.getSelectedIndex()); //Deselects the user's selection.
                
             if(choice != -1){
                double amount = instruments.get(choice).getCurrentValue(); //Uses the choice to generate the value to be paid in.
                
                if(instruments.get(choice).getFIOwned() > 0){ //If more than 1 of a instrument is owned it can be sold
                    instruments.get(choice).sellFI(); 
                    myBalance.payInMoney(amount);
                    t2.append("Your sale has a tax deduction of " + instruments.get(choice).taxOnSell()+"\n");
                    myBalance.payOutMoney(instruments.get(choice).taxOnSell()); //Tax is deducted, for sale, varies per object.
                }
                else{
                    t2.append("Transaction failed. None owned!\n\n"); 
                }
                
                t2.append("Your balance: " + myBalance.getBalance()+"\n\n");
             }
        }
            
        else if(evt.getActionCommand().equals("Add Instrument")){
             FinancialInstrument newInstrument = addInstrument();
                
             if(instruments.size() < 15){ //Limited total instruments in simulation to 15, to prevent unlimited objects being created.
                instruments.add(newInstrument);
                rawData.add(new ArrayList<Double>());
                lst.add(newInstrument.getName());
                t1.append("\nInstrument Added");
             }
             else{
                 t1.append("\nLimit reached");
             }
             
        }
        
        else if(evt.getActionCommand().equals("Quit For Now")){ //Saves data for restart
             writeInstrumentsToFile();
             writeBalanceToFile();
             System.exit(0);
        }
        
        else if(evt.getActionCommand().equals("End Simulation")){
	     for(int i = 0; i<instruments.size(); i++){
		if(instruments.get(i).getFIOwned()>0){
		    t3.append("WARNING: You cannot end this simulation while owning assets. All assets have to be sold!\n");
                    return;
		}
             }
             myBalance.payInMoney(instruments.get(9).getCurrentValue()); //Cash value is added to balance.
             File instrumentsFile = new File("Instruments.txt");
             File balanceFile = new File("Account.txt");
             if(instrumentsFile.exists()){
                instrumentsFile.delete();
             }
             if(balanceFile.exists()){
                balanceFile.delete();
             }
             printResultsToFile();
             System.exit(0);
        }  
        else if(evt.getActionCommand().equals("Show Moving Averages")){
            for(int i = 0; i<movingAverages.size(); i++){
                t1.append(instruments.get(i).getName() + "'s current average: " + movingAverages.get(i) + "\n" + compareMovingAverageToCurrentValue(i) + "\n\n");
             }
            
        }
    }
    
    public boolean completePurchase(int choice) throws CashTradingException{
        boolean purchaseComplete;
        if(!(instruments.get(choice) instanceof Cash)){
            purchaseComplete = instruments.get(choice).purchaseFI();
        }
        else{
            throw new CashTradingException();
        }
        return purchaseComplete;
    }
    
    public void bondInterestPayment(int i){
         if(((Bonds)instruments.get(i)).getFIOwned() > 0) //If a bond is owned
         {
           double moneyToBePayed = ((Bonds)instruments.get(i)).generateInterestPaymentDaily(); //Interest payment is generated for bond
           myBalance.payInMoney(moneyToBePayed); //Interest payment is paid in to balance.
         }
         ((Bonds)instruments.get(i)).changeInterest(); //Interest level is changed   
    }
    
    public void printResultsToFile(){
        try{
             File file = new File("FinalResults.txt");   
            PrintWriter output = new PrintWriter(new FileWriter(file));
            output.println("Your final balance was " + myBalance.getBalance());
            output.println("You made: " + (myBalance.getBalance()-10000));
            if(myBalance.getBalance() < 0){
                output.println("You became bankrupt!");
            }
            if(myBalance.getBalance() > 10000){
                output.println("Congratulations on making money!");
            }
            if(myBalance.getBalance() > 50000){
                output.println("You are an amazing trader!");
            }
            if(myBalance.getBalance() < 10000){
                output.println("Commiserations!");
            }
            output.close();
            Desktop myDesktop = Desktop.getDesktop(); 
            if(file.exists()){
                myDesktop.open(file); //File is opened automatically.
            }
        }
        catch (IOException e){
            System.out.println("File was not written to");
        }
    }
    
     //Generates a new financial instrument which is only a stock or short to replace one.
    public FinancialInstrument addInstrument(){
        double probability = Math.random();
        String[] tendencies = {"XUp","XDown"}; //The new one can only have 2 tendencies.
        String[] sectors = {"Technology", "High Street","Supermarket","Automotive"};
        FinancialInstrument a;
        companyNumber++;
            if(probability > 0.5){
                a = new Stock(100000*Math.random(),"Start Up Company" + companyNumber,0.05*Math.random(),sectors[(int)(4*Math.random())],tendencies[(int)(2*Math.random())]);
            }
            else{
                a = new Short(100000*Math.random(),"Start Up Company"+ companyNumber,0.05*Math.random(),sectors[(int)(4*Math.random())],tendencies[(int)(2*Math.random())],(int)(365*Math.random() + 100));
            }
        
        return a;
    }
     
    public ArrayList<FinancialInstrument> createInstruments(Account myBalance){
        //Declares the list of company names, bond-types, sectors and tendencies that are possible.
        String[] companyNames = {"AAG Corp","BAE Corp","CLA Corp","DDR Corp","ELF Corp","FGA Corp","HJL Corp","IJK Corp", "NDW Corp"};
        String[] taxBracket = {"Government Bond","Non-Government Bond"};
        String[] sectors = {"Technology", "High Street","Supermarket","Automotive"};
        String[] tendencies = {"XUp","XDown","Down","Up"};
        
        //Declares an array of financial instruments.
        ArrayList <FinancialInstrument> instruments = new ArrayList <>();
                
        for(int i = 0; i<9; i++){
            double probability = Math.random();
            if(probability <= 0.5){
                instruments.add(new Stock(10000*Math.random(),companyNames[i],0.05*Math.random(),sectors[(int)(4*Math.random())],tendencies[(int)(4*Math.random())]));
            }
            else if(probability <= 0.8){
                instruments.add(new Short(10000*Math.random(),companyNames[i],0.05*Math.random(),sectors[(int)(4*Math.random())],tendencies[(int)(4*Math.random())],(int)(365*Math.random() + 100)));
            }
            else if(probability > 0.8){
                instruments.add(new Bonds(5000*Math.random(),companyNames[i],0.01*Math.random(),(int)(365*Math.random()+100),taxBracket[(int)(2*Math.random())]));
            }
          
        }
           
        FinancialInstrument myCash = new Cash(1000,"Your Cash",1.0001); //Creates a cash instrument using the cash fee.
        
        //Adds cash to array
        instruments.add(myCash);
        
        return instruments;
    }    
    
    public void writeInstrumentsToFile(){
        try{
            File file = new File("Instruments.txt");
            FileOutputStream fileOut = new FileOutputStream(file); 
            ObjectOutputStream output = new ObjectOutputStream(fileOut); 
            for(int i = 0; i<instruments.size(); i++){
            output.writeObject(instruments.get(i));
             }
            output.close();
       }
       catch(IOException e){
           System.out.println("Could not write to file!");
       }
    }
    
    public void writeBalanceToFile(){
       try{
            File file = new File("Account.txt");
            FileOutputStream fileOut = new FileOutputStream(file); 
            ObjectOutputStream output = new ObjectOutputStream(fileOut); 
            output.writeObject(myBalance);
            output.close();
       }
       catch(IOException e){
           System.out.println("Could not write to file!");
       }
    }
    
    public void generateMovingAverages(){
        for(int i = 0; i<instruments.size(); i++){
            rawData.get(i).add(Math.abs(instruments.get(i).getCurrentValue())); //Math.abs as Shorts have negative value.
        }
        
        movingAverages = new ArrayList<>(); //New moving averages each time, so new ArrayList is created each time.
        
        for(int i = 0; i<rawData.size(); i++){
            double sum = 0;
             for(int j = 0; j<rawData.get(i).size(); j++){
                 sum = sum + rawData.get(i).get(j);
             }
            
            sum = sum/rawData.get(i).size(); //Average calculation.
            movingAverages.add(sum);
        }
    }
    
    public String compareMovingAverageToCurrentValue(int count){
        if(movingAverages.get(count) < Math.abs(instruments.get(count).getCurrentValue())){
            return "Current value is higher than average";
        }
        else{
            return "Current value is lower than average";
        }
    }
    
    public static void main(String[] args){
       GUI myGUI = new GUI();
        myGUI.addWindowListener(new WindowAdapter(){
        public void windowClosing(WindowEvent e) {
            System.exit(0); //All data is lost if closed.
        }
      });
     }

}