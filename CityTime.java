import javax.swing.*;
import java.awt.*;
import javax.swing.border.*;


//Instances of the clocks to be displayed
public class CityTime extends JTextField
{
    private String city;
    String currentTime;
    private long displayTime;
    private WorldTimeModel model;
    private double offset;

   
 
    
    //Constructor which allows the CityTime to be created with city name, time and offset
    public CityTime(WorldTimeModel model,String city, String currentTime, double offset)
    {
       super(15);
       this.city = city;
       this.currentTime = currentTime;
       this.offset = offset;
       this.model = model;
       setEditable(false);
       setFocusable(false);
       setText(this.currentTime);
       setHorizontalAlignment(JTextField.CENTER);
       Font displayFont = new Font("SansSerif", Font.BOLD, 24);
       setFont(displayFont);
       setBackground(Color.BLACK);
       setForeground(new Color(31,250,25));
       Border lineBorder = BorderFactory.createLineBorder(Color.WHITE,4);
       TitledBorder titledBorder = BorderFactory.createTitledBorder(lineBorder, this.city);
       titledBorder.setTitleColor(new Color(31,250,25));
       titledBorder.setTitleFont(new Font(displayFont.getFontName(),Font.BOLD, 15));
       setBorder(titledBorder);
  
     
    }
    
    //Returns the city name of the clock, used when deleting a clock from the display area
    public String getCity()
    {
        return this.city;
    }
    
    //Returns the offset of the selected city, used when adding a clock to the display area
    public double getOffset()
    {
        return this.offset;
    }

  
}
    


