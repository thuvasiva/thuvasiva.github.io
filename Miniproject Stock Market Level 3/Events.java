//Contains a number of static methods, for events such as pandemics or inflation rate changes.
import java.util.Random;
public class Events
{   
    //Returns true if there is a pandemic, which has a 1 in 500 chance.
      public static boolean isPandemic(){
        Random random = new Random();
        if(random.nextInt(500) == 2){
            return true;
        }
        return false;
    }
    
    //Returns true if the pandemic ends, which also has a 1 in 500 chance.
    public static boolean pandemicOver(){
        Random random = new Random();
        if(random.nextInt(500) == 2){
            return true;
        }
        return false;
    }
    
    //Returns true if the inflation rate is due to change, which has a 1 in 20 chance.
    public static boolean isInflationRateChange(){
        Random random = new Random();
        if(random.nextInt(20) == 1){
            return true;
        }
        return false;
    }
    
    //Gives a new value for the inflation rate.
    public static double InflationRateChange(){
        Random random = new Random();
        double newRate = 0.0001 * random.nextDouble() + 1;
        return newRate;
    }
        
}

