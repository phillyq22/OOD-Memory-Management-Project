import javax.swing.*;
import java.awt.*;
import java.util.Observable;
import java.util.Observer;

public class View implements Observer {
    //This scales down the actual values from the memory into a smaller size
    //that is easier to work with for displaying
    private int divisor = 1024 / 256;

    //This array holds colorable panels that display to the screen
    private JPanel[] memCopy = new JPanel[256];

    //Progress bar can show the total amount of memory being used
    private JProgressBar used = new JProgressBar();

    private int timeStep;
    private JLabel time = new JLabel("Time: 0" );

    private int numProcesses;
    private JLabel processes = new JLabel("Processes in Memory: 0");
    private JLabel info = new JLabel("Starting");



    public View(){

        //Creates the initial frame
        JFrame frame = new JFrame("Memory Visualization");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //Initial size of the frame
        //Do we want it to be resizable or not?
        //frame.setPreferredSize(new Dimension(650,140));

        //Border layout so the different aspects of the view
        //don't interfere with each other
        BorderLayout layout = new BorderLayout();
        frame.setLayout(layout);

        //Center panel that will hold the colored panels
        //representing the memory
        JPanel center = new JPanel();
        center.setPreferredSize(new Dimension(300, 4));

        //FlowLayout with zero horizontal gap so colored panels sit directly next to each other
        FlowLayout centerLayout = new FlowLayout();
        centerLayout.setHgap(0);
        center.setLayout(centerLayout);
        frame.add(center, BorderLayout.CENTER);

        //South panel that currently holds the bar that displays total
        //% memory used
        JPanel south = new JPanel();
        frame.add(south, BorderLayout.SOUTH);

        JPanel right = new JPanel();
        right.setPreferredSize(new Dimension(150,50));
        right.setLayout(new BoxLayout(right, BoxLayout.Y_AXIS));
        frame.add(right, BorderLayout.EAST);
        right.add(time);
        right.add(processes);

        JPanel north = new JPanel();
       //north.setPreferredSize(new Dimension(2, 100));
        frame.add(north, BorderLayout.NORTH);
        frame.add(info, BorderLayout.WEST);
        info.setPreferredSize(new Dimension(100,75));

        //Create the colored panels in the array, initialize color and add
        //to center panel
        for (int i = 0; i<256; i++){
            memCopy[i] = new JPanel();
            memCopy[i].setPreferredSize(new Dimension(1,20));
            memCopy[i].setBackground(Color.green);
            center.add(memCopy[i]);
        }

        //Initialize values for progress bar and add to south panel
        used.setMaximum(256);
        used.setMinimum(0);
        used.setStringPainted(true);
        south.add(used);

        //Pack and display the view
        frame.pack();
        frame.setVisible(true);
    }


    @Override
    //Determine the type of event received and handle appropriately
    public void update(Observable o, Object arg) {
        switch (arg.getClass().getCanonicalName()){
            case "AddEvent":
                handleAdd((AddEvent)arg);
                break;
            case "RemoveEvent":
                handleRemove((RemoveEvent)arg);
                break;
            case "OOMEvent":
                handleOOM((OOMEvent)arg);
                break;
        }
    }


    private void handleAdd(AddEvent event){
        System.out.println("View received an AddEvent");
        //Reduce offset to equivalent value in View's version of memory
        int offset = event.getProcess().getOffset() / divisor;

        //Reduce size to equivalent value in View's version of memory
        int size = event.getProcess().getSize() / divisor;

        //For each panel starting from the offset to offset + size,
        //color the panel to show it is allocated
        for (int i = offset; i < size + offset; i++){
            memCopy[i].setBackground(Color.red);
        }

        //Update the progress bar
        updateFullBar();
        timeStep = event.getTime();
        time.setText("Time: " + timeStep);
        processes.setText("Processes in Memory: " + ++numProcesses);

        System.out.println("View handled AddEvent");
        info.setText("<html>" + "Process of size " + event.getProcess().getSize() + " allocated with offset " + event.getProcess().getOffset() + "</html>");
    }

    private void handleRemove(RemoveEvent event){
        System.out.println("View got a RemoveEvent");
        //Reduce offset to equivalent value in View's version of memory
        int offset = event.getProcess().getOffset() / divisor;

        //Reduce size to equivalent value in View's version of memory
        int size = event.getProcess().getSize() / divisor;

        //For each panel starting from the offset to offset + size,
        //color the panel to show it is allocated
        for (int i = offset; i< size + offset; i++){
            memCopy[i].setBackground(Color.green);
        }

        //Update the progress bar
        updateFullBar();
        timeStep = event.getTime();
        time.setText("Time: " + timeStep);
        processes.setText("Processes in Memory: " + --numProcesses);
        info.setText("<html>" + "Process of size " + event.getProcess().getSize() + " removed from offset " + event.getProcess().getOffset() + "</html>");

        System.out.println("View handled RemoveEvent");
    }

    public void handleOOM(OOMEvent event){
        timeStep = event.getTime();
        time.setText("Time: " + timeStep);

        System.out.println("View received an OOMEvent, but doesn't care");
        info.setText("<html>" + "Could not allocate process of size " + event.getProcess().getSize() + "</html>");
    }

    private void updateFullBar(){
        //Counts the number of panels that have been colored as allocated
        //Sets this number to the value of the progress bar
        double full = 0;
        for (JPanel j : memCopy){
            if (j.getBackground() == Color.red){
                full++;
            }
        }
        used.setValue((int)full);
    }
}