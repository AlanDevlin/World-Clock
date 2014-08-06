import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.util.*;
import javax.swing.event.*;
import java.io.*;
import javax.swing.border.*;


public class WorldTimeView extends JFrame implements ListSelectionListener, ActionListener
{
   private JPanel clockDisplay;
   private Map<String,Double> cities;
   private JList citiesList;
   private JButton addButton, removeButton;
   private WorldTimeModel model;
   private ArrayList<CityTime> clocks;
   private double offset;
   private String cityName;
   private String cityTime;
   private int counter;
   private int cityOffset;
   private int i;
   
    public WorldTimeView(WorldTimeModel model)
    {
        //Create JFrame and sets its size and location, etc.
        super("World Time");
        setSize(400,250);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setResizable(false);
        setLocationRelativeTo(null);
     
        
        //Create container and add JPanels to display buttons, list and and area where the clocks will go
        Container contentPane = getContentPane();
     
    
        JPanel displayArea = new JPanel(new BorderLayout());
      
        
        //JPanel for the List and buttons
        JPanel controllerDisplay = new JPanel(new BorderLayout());
   
        //Another JPanel for the buttons
        JPanel buttonsArea = new JPanel(new FlowLayout());
        buttonsArea.setBackground(Color.BLACK);
        //Create the buttons
        addButton = new JButton("+");
        addButton.addActionListener(this);
        addButton.setBackground(Color.BLACK);
        addButton.setForeground(new Color(31,250,25));
        addButton.setFont(new Font("Britannic Bold",Font.PLAIN, 20));
     
       // Border thickBorder = new LineBorder(Color.WHITE, 1);
        //addButton.setBorder(thickBorder);
       
        removeButton = new JButton("-");
        removeButton.addActionListener(this);
        removeButton.setBackground(Color.BLACK);
        removeButton.setForeground(new Color(31,250,25));
        removeButton.setFont(new Font("Britannic Bold",Font.PLAIN, 20));
        
        buttonsArea.add(addButton);
        buttonsArea.add(removeButton);
        
        //Another JPanel for the list
        JPanel listArea = new JPanel(new FlowLayout());
        listArea.setBackground(Color.WHITE);
        //load the file containing the cities and their offsets
        loadCityTimeOffsets("CityTimeOffsets.txt");
        
        //add the cities and their offsets to the JList
        Object[] cityNames = cities.keySet().toArray();
        citiesList = new JList(cityNames);
        //Set Dublin as the first selected location
        citiesList.setSelectedIndex(48);
        citiesList.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        citiesList.setSize(500,500);
        citiesList.addListSelectionListener(this);
        citiesList.setBackground(Color.BLACK);
        citiesList.setForeground(new Color(31,250,25));
        
        JScrollPane scrollableCitiesList = new JScrollPane(citiesList);
        scrollableCitiesList.setPreferredSize(new Dimension(150,500));
   
        listArea.add(scrollableCitiesList);


        controllerDisplay.add(buttonsArea, BorderLayout.NORTH);
        controllerDisplay.add(listArea, BorderLayout.CENTER);
        
        //Create the JPanel where the clocks will be displayed
        clockDisplay = new JPanel(new FlowLayout());
        clockDisplay.setBackground(Color.BLACK);
        
        displayArea.add(clockDisplay, BorderLayout.CENTER);
        displayArea.add(controllerDisplay, BorderLayout.WEST);
        contentPane.add(displayArea);
        
        //Create a new instance of the controller (Timer)
        new WorldTimeController(model);
        
        //Link to the model
        this.model = model;
        
        //An ArrayList of CityTime's is created
        this.clocks = new ArrayList<CityTime>();
      
        //Create an initial clock which will display Dublin time on the screen
        String name = citiesList.getSelectedValue() + " ";
        String time = toString(model.getCurrentTime(0));
      
        CityTime c = new CityTime(model, name, time, 0);
        clocks.add(c);
        add(clocks);

       pack();

       setVisible(true);
        
        }
        
        //Method which adds the CityTime to the JPanel
   public void add(ArrayList<CityTime> clocks){
     
       //go through the ArrayList and add each clock to the JPanel
       for(i = 0; i < clocks.size(); i++){
         
       clockDisplay.add(clocks.get(i));
                                           }
     clockDisplay.validate();
    }

    //Method which removes the CityTime from the JPanel
    public void remove(CityTime c){
        //remove the CityTime from the ArrayList
        clocks.remove(c);
        //remove the CityTime from the JPanel
        clockDisplay.remove(c);
        clockDisplay.repaint();
        clockDisplay.validate();
}
    
    public void valueChanged(ListSelectionEvent e)
    { 
        
    }
    
    
    //What happens when the "+" or "-" button is selected
    public void actionPerformed(ActionEvent e)
    {
        //Find the source of the JButton
      JButton source = (JButton) e.getSource();
      
      //If the "+" button was selected
        if(source == addButton){
 
            //Get the values of each list item selected (Allows for multiple selection)
            //Creates an array of these selections
         Object [] selections = citiesList.getSelectedValuesList().toArray();
         
         //Go through the array and find the corresponding name, offset and current time
         for(int j = 0; j < selections.length; j++)
        {
      cityName = selections[j] + " ";
      offset = cities.get(selections[j]);
      cityTime = toString(model.getCurrentTime(offset));
       
      CityTime addedClock = new CityTime(model, cityName, cityTime, offset);
    
      //While the ArrayList has a size less then 8 then add the CityTime to the ArrayList and to the JPanel
       if  (clocks.size() < 8)
       {
       this.clocks.add(addedClock);
        add(clocks);
      
    }
    //else tell the user that they need to remove a clock before they can display another one
    else
    {
            JOptionPane.showMessageDialog(null, "You can only select 8 clocks to be displayed, Remove a clock to add more ", "Too many clocks", 1);
        }
    } 
}

//if the "-" button has been selected
    if(source == removeButton){
        
        //get the values of all the selected locations and place them in an array
        Object [] selections = citiesList.getSelectedValuesList().toArray();
         
        //go through the array and get the name of the selected location
         for(int j = 0; j < selections.length; j++)
            {
        
        
               cityName = selections[j] + " ";
    
      //go through the ArrayList
      //if the name is in the ArrayList then remove it
       for(i = 0; i < clocks.size(); i++)
       {
       if(clocks.get(i).getCity().equals(cityName))           
       {
         remove(clocks.get(i));
 
        }
      }
               }
                               }
}

   //Method which displays the time in a readable format
     public String toString(double currentTime){
        int hh,mm,ss;
        ss = (int)(currentTime/1000);
        mm = ss/60;
        ss = ss%60;
        hh = mm/60;
        mm = mm%60;
        hh = hh%24;
        return String.format("%02d:%02d:%02d", hh, mm, ss);
    }
    
    //Method which updates the time each time the timer is ticked
    public void updateTime(){
      //go through the ArrayList and for each CityTime set the text to the current time(get the offset of the CityTime)
      for(i = 0; i < clocks.size(); i++){  
        clocks.get(i).setText(toString(model.getCurrentTime(clocks.get(i).getOffset())));

    }
        }
      
      //Method which loads in the Cities and their offsets from a file  
   public Map<String, Double>  loadCityTimeOffsets(String fileName) {
        try {
            FileReader aFileReader = new FileReader(fileName);
            BufferedReader aBufferReader = new BufferedReader(aFileReader);

            String cityName = "";
            int separatorPos = 0;
            double offset = 0.0;
           cities = new TreeMap<String,Double>();

            String lineFromFile = aBufferReader.readLine() ;
            while (lineFromFile != null) {
                // Add the word to the word list
                separatorPos = lineFromFile.indexOf(",");
                cityName = lineFromFile.substring(0,separatorPos);
                offset = Double.parseDouble(lineFromFile.substring(separatorPos+1));
                cities.put(cityName,offset) ;
                lineFromFile = aBufferReader.readLine() ;
            }
            aBufferReader.close();
            aFileReader.close();
            return cities;
        }
        catch(IOException x) {
            return null ;
        }
    }
    }


   

     
     
   

