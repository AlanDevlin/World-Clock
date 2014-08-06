

public class WorldTimeModel
{
     private WorldTimeView view;
     private final long MILLIS_IN_DAY = (24 * 60 * 60 * 1000);
     private final long MILLIS_IN_HOUR = (60 * 60 * 1000);
 
    public WorldTimeModel()
    {
        //Link to the view when model is created
        this.view = new WorldTimeView(this);
    }
  
    //Get the current time
     public double getCurrentTime(){
        
        double systemTime = (double)(System.currentTimeMillis() % MILLIS_IN_DAY);
        return systemTime;
        
    }
    
    //Get the current time with the offset included
    //Add one to the offset due to daylight saving time
     public double getCurrentTime(double offset){
        double systemTime = (double)((System.currentTimeMillis() % MILLIS_IN_DAY)+((int)MILLIS_IN_HOUR) * (1 + offset));
        return systemTime;
        
    }
    
    //Method which decides what happens each time the Timer ticks
     public void tick()
     {
      //The updateTime method of the View component is called
       view.updateTime();
      
    }
    
}



 

    
  
    
 
    

  
    
    
  