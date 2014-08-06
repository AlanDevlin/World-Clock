import javax.swing.Timer;
import java.awt.event.*;

public class WorldTimeController implements ActionListener
{
    private WorldTimeModel model;
    
    public WorldTimeController( WorldTimeModel model)
    {
      //Creates a Timer which causes the clock to update its Current Time each time the Timer has ticked
      this.model = model;
      Timer ticking = new Timer(1000,this);
      ticking.start();
      
    }
    
     public void actionPerformed(ActionEvent e)
    {
        model.tick();
    }
  
}

    
  