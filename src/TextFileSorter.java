/**
 * Created by Anmol Grewal on 2017-04-02.
 */
//
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import javax.swing.filechooser.FileFilter;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class TextFileSorter extends JFrame {
    //Creates Panel for Border Layout
    private JPanel panel1 = new JPanel();
    private JPanel panel2 = new JPanel();
    private JPanel panel3 = new JPanel();
    private JPanel panel4 = new JPanel();
    private JPanel panel5 = new JPanel();
    private JMenuBar mBar;     // Horizontal menu bar
    private JMenu fileMenu;   // Vertical menu for Files
    private JRadioButton ascendingButton;      // Ascending Button
    private JRadioButton descendingButton;       // Descending Button
    private ButtonGroup radioButtonGroup;  // To group radio buttons
    private JLabel messageLabel; // To reference a label
    // private JTextField fileTextField; // To reference a text field
    private JButton sortButton; // To reference a button
    private final int WINDOW_WIDTH = 275; // Window width
    private final int WINDOW_HEIGHT = 175; // Window height
    private static JFileChooser fileChooser = new JFileChooser(); // File Chooser
    private int choice; //User Selection Between Ascending or Descending
    private static String fileName; // File Name as String
    private static File selectedFile; // File Location
    // private String everything; // The Whole Text File (In Development)
    // private String[] arrayCutOff = new String[1000]; (In Development)

    public TextFileSorter() {
        // Set the window title.
        setTitle("Text File Sorter");

        // Set the size of the window.
        setSize(WINDOW_WIDTH, WINDOW_HEIGHT);

        // Specify what happens when the close button is clicked.
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Add a BorderLayout manager to the content pane.
        setLayout(new BorderLayout());

        // Create a label to display instructions.
        messageLabel = new JLabel("Please Select Text File");

        // Create a text field 100 characters wide.
        //fileTextField = new JTextField(50);

        //Creates Two Items for Vertical Menu called File
        createFileMenu();

        // Creates horizontal menu bar and Vertical Menu to it.
        mBar = new JMenuBar();
        mBar.add(fileMenu);

        setJMenuBar(mBar);

        //Creates the Buttons
        createButtons();

        //Sets up File Chooser
        fileChoiceSetup();

        //Creates the Panels
        panelSetter();

        //Makes it Appear in the center
        Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
        this.setLocation(dim.width / 2 - this.getSize().width / 2, dim.height / 2 - this.getSize().height / 2);

        // Display the window.
        setVisible(true);
    }

    private void fileChoiceSetup(){
        //Sets Initial Directory to User Home
        fileChooser.setCurrentDirectory(new File(System.getProperty("user.home")));

        //File Filters for Java and Text Files to Choose in FileChooser
        FileFilter ft = new FileNameExtensionFilter("Text Files", "txt", "Java Files", "java");
        //FileFilter ft2 = new FileNameExtensionFilter("Java Files", "java");

        //Adds two File Filters Seperately
        fileChooser.addChoosableFileFilter(ft);
        // fileChooser.addChoosableFileFilter(ft2);
    }

    private void panelSetter() {
        // Add the label, text field, and button components to the panel.
        panel1.add(messageLabel, BorderLayout.CENTER);

        //Panel 2
        panel2.add(panel5, BorderLayout.CENTER);
        panel2.add(panel4, BorderLayout.CENTER);

        panel3.add(sortButton, BorderLayout.CENTER);
        // panel4.add(fileTextField, BorderLayout.CENTER);

        //Panel 5
        panel5.add(ascendingButton, BorderLayout.EAST);
        panel5.add(descendingButton, BorderLayout.WEST);

        panel1.setBorder(BorderFactory.createRaisedSoftBevelBorder());
        panel2.setBorder(BorderFactory.createEtchedBorder());

        add(panel1, BorderLayout.NORTH);
        add(panel2, BorderLayout.CENTER);
        add(panel3, BorderLayout.SOUTH);
    }

    //Creates the File Menu with Two Items
    private void createFileMenu() {
        fileMenu = new JMenu("File");
        JMenuItem item;

        item = new JMenuItem("Help");
        item.addActionListener(new MenuListener());
        fileMenu.add(item);

        item = new JMenuItem("Exit");
        item.addActionListener(new MenuListener());
        fileMenu.add(item);
    }

    //Creates the Buttons to Add onto the Panels
    private void createButtons() {
        // Create a button with the caption "Calculate".
        sortButton = new JButton("Sort");


        //Sets Tooltip Text
        sortButton.setToolTipText("Click to sort text file after selecting an option from bove");

        // Adds Listener to calcButton
        sortButton.addActionListener(new SortButtonListener());

        ascendingButton = new JRadioButton("Ascending");
        descendingButton = new JRadioButton("Descending");

        // Group the radio buttons.
        radioButtonGroup = new ButtonGroup();
        radioButtonGroup.add(ascendingButton);
        radioButtonGroup.add(descendingButton);

        // Add action listeners to the radio buttons.
        ascendingButton.addActionListener(new SortButtonListener());
        descendingButton.addActionListener(new SortButtonListener());
    }

    //Menu Action Listener
    private class MenuListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            String actionCommand = e.getActionCommand();
            if (actionCommand.equals("Help")) {
                /* (In Development)
                JOptionPane.showMessageDialog(null, "This Program sorts arrays in ascending" +
                        " or descending order depending on which option you select.\n It will only sort predefined" +
                        " arrays that already have values. It will not sort arrays that are created\n using other" +
                        "methods such as a loop. To use this program, choose if you would like to sort an array\n in" +
                        " ascending or descending order, then please select the file containing" +
                        " an array that you would like\n to rearrange. In your java class file please" +
                        " comment /*sort right before the array so the program \n can find the array. This program" +
                        " rearranges all types of arrays in whichever order you choose. Types\n include Strings, " +
                        "integers, doubles, chars, etc..");
                */
                JOptionPane.showMessageDialog(null, "This program sorts a Text File or .java"
                        + " that has UTF-8 in Ascending or Descending Order based on your selection.\n Currently it " +
                        "only sorts one \"column\" of text. You can have numbers and words mixed together and it will" +
                        "still sort\n it accordingly. Just select the Input File to Sort and an Output File you" +
                        "also create one using .txt at the end.\n Whatever you prefer");
                JOptionPane.showMessageDialog(null, "Example of text file: \nApple" +
                        "\nBanana\nCharlie\nDelta\n55\n66\nHenry\nArthur");
            } else if (actionCommand.equals("Exit")) {
                System.exit(0);
            }
        }
    }

    //ActionListener for Radio Buttons and Sort Put Together
    private class SortButtonListener implements ActionListener {
        public void actionPerformed(ActionEvent e2) {
            if (e2.getSource() == ascendingButton) {
                choice = 1;
            } else if (e2.getSource() == descendingButton) {
                choice = 2;
            }
            if (e2.getSource() == sortButton) {
                if (choice == 1) {
                    try{
                        fileSorter();
                    }
                    catch (NullPointerException e6){
                        //Catches User Canceling FileChooser Window
                    }
                    /* (In Development)
                    try {
                        editFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                } else if (choice == 2) {
                    try{
                        fileSorter();
                    }
                    catch (Exception e6){
                        //Catches User Canceling FileChooser Window
                    }
                    /* (In Development)
                    try {
                        editFile();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    */
                } else {
                    JOptionPane.showMessageDialog(null, "Please Select Ascending or" +
                            " Descending");
                }
            }
        }
    }

    private static String fileChoice() throws NullPointerException {

        int status = fileChooser.showOpenDialog(null);
        if (status == JFileChooser.APPROVE_OPTION) {
            selectedFile = fileChooser.getSelectedFile();
            fileName = selectedFile.getPath();
            JOptionPane.showMessageDialog(null, "You selected " + fileName);
        } else if (status == JFileChooser.CANCEL_OPTION) {
            JOptionPane.showMessageDialog(null, "Canceled");
        }
        return fileName;
    }

    private void fileSorter() {
        BufferedReader reader = null;

        BufferedWriter writer = null;

        //Create an ArrayList object to hold the lines of input file

        ArrayList<String> lines = new ArrayList<String>();

        try
        {
            //Creating BufferedReader object to read the input file
            reader = new BufferedReader(new FileReader(fileChoice()));

            //Reading all the lines of input file one by one and adding them into ArrayList
            String currentLine = reader.readLine();

            while (currentLine != null)
            {
                lines.add(currentLine);

                currentLine = reader.readLine();
            }

            //Sorting the ArrayList
            if (choice == 1){
                Collections.sort(lines, new Comparator<String>() {
                    public int compare(String o1, String o2) {

                        String o1StringPart = o1.replaceAll("\\d", "");
                        String o2StringPart = o2.replaceAll("\\d", "");


                        if(o1StringPart.equalsIgnoreCase(o2StringPart))
                        {
                            return extractInt(o2) - extractInt(o1);
                        }
                        return o2.compareTo(o1);
                    }

                    int extractInt(String s) {
                        String num = s.replaceAll("\\D", "");
                        // return 0 if no digits found
                        return num.isEmpty() ? 0 : Integer.parseInt(num);
                    }
                });
            }
            else if(choice == 2)
            {
                Collections.sort(lines, new Comparator<String>() {
                    public int compare(String o1, String o2) {

                        String o1StringPart = o1.replaceAll("\\d", "");
                        String o2StringPart = o2.replaceAll("\\d", "");


                        if(o1StringPart.equalsIgnoreCase(o2StringPart))
                        {
                            return extractInt(o1) - extractInt(o2);
                        }
                        return o1.compareTo(o2);
                    }

                    int extractInt(String s) {
                        String num = s.replaceAll("\\D", "");
                        // return 0 if no digits found
                        return num.isEmpty() ? 0 : Integer.parseInt(num);
                    }
                });
            }
            //Creating BufferedWriter object to write into output file
            JOptionPane.showMessageDialog(null, "Select Output File");
            writer = new BufferedWriter(new FileWriter(fileChoice()));

            //Writing sorted lines into output file

            for (String line : lines)
            {
                writer.write(line);

                writer.newLine();
            }
        }
        catch (IOException e5)
        {
            System.out.println("Error!");
        }
        finally
        {
            //Closing the resources

            try
            {
                if (reader != null)
                {
                    reader.close();
                }

                if(writer != null)
                {
                    writer.close();
                }
            }
            catch (IOException e5)
            {
                System.out.println("Error!");
            }
        }
    }
}
    /* Future Development in my own free time. This is basically an excessively large
       amount of code that I will have to create if I wish to update a .java file that
       already has a lot of code created. I was unable to find any API online to help
       me with this using Java. There is also a Large Learning Curve for Regex if I want
       to manipulate the .java or .text file how I would like it to be manipulated, Which
       we have not covered in class. I also have to add a counter to keep track of where
       I wish to replace the array elements using a charCount probably.

    private void editFile() throws IOException {
        //Loads the whole Text or java file into a String
        try (BufferedReader br = new BufferedReader(new FileReader(fileChoice()))) {
            StringBuilder sb = new StringBuilder();
            String line = br.readLine();

            while (line != null) {
                sb.append(line);
                sb.append(System.lineSeparator());
                line = br.readLine();
            }
            everything = sb.toString();
        }
        arrayCutOff = everything.split("/\\* sort \\");
        for(int i = 0; i < arrayCutOff.length; i++){
            System.out.println(arrayCutOff[i]);
        }
    }
    */
