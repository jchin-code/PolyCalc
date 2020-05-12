import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.filechooser.*;
import java.io.*;
/**
 * The main class for the calculator. The main method is located here, and
 * this calculator has been implemented using the Graphical User Interface
 * (GUI).
 *
 * @author Jordan Chin
 * @version 1.0
 */
public class PolyCalc
{   /**
     * The main button handler for the buttons in the main method
     */
    private static class ButtonHandler implements ActionListener{
        /**
         * Performs the actions for the 3 buttons in the main menu of the
         * GUI
         * @param e the ActionEvent
         */
        public void actionPerformed(ActionEvent e){
            if (e.getActionCommand().equals("Exit"))
                System.exit(0);
            else if (e.getActionCommand().equals("Interact Mode"))
                interactMode();
            else if (e.getActionCommand().equals("File Mode"))
                fileMode();
        }
    }

    /**
     * Runs the full calculator as a GUI. Both a File Mode and Interactive
     * Mode are available to the user.
     * @param args the arguments
     */
    public static void main(String[] args){
        JFrame window = new JFrame("Polynomial Calculator"); 
        window.setSize(450, 150);
        window.setLocationRelativeTo(null);
        JPanel content = new JPanel();
        JLabel welcome = new JLabel("Welcome to the Polynomial Calculator!");
        JLabel select = 
         new JLabel("Please select a mode, or press Exit to quit the program.");
        JButton exitButton = new JButton("Exit");
        JButton interactButton = new JButton("Interactive Mode");
        JButton fileButton = new JButton("File Mode");
        ActionListener al = new ButtonHandler();
        exitButton.addActionListener(al);
        interactButton.addActionListener(al);
        fileButton.addActionListener(al);
        content.add(welcome);
        content.add(select);
        content.add(interactButton);
        content.add(fileButton);
        content.add(exitButton);
        window.setContentPane(content);
        window.setVisible(true);
        window.toFront();

        return;
    }

    /**
     * The method for the interactive mode. Offers a field to instantly
     * calculate Polynomials. Interactive mode and file mode do not share
     * the same memory.
     */
    private static void interactMode(){
        JFrame window = 
            new JFrame("Polynomial Calculator - Interactive Mode"); 
        window.setSize(450, 150);
        window.setLocationRelativeTo(null);
        JPanel content = new JPanel();
        JLabel enter = 
            new JLabel("Enter an expression you would like to be evaluated");
        JTextField field = new JTextField("", 15);
        JButton eval = new JButton("Evaluate");
        JLabel res = new JLabel("");
        LineRead lr = new LineRead();
        eval.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    String poly = field.getText(), output = lr.lineRead(poly);
                    if (output!=null)
                        res.setText("Result: " + output);
                    else
                        res.setText("Please enter a valid expression.");    
                }
            });
        content.add(enter);
        content.add(field);
        content.add(eval);
        content.add(res);
        window.setContentPane(content);
        window.setVisible(true);
        window.toFront();
        return;
    }

    /**The method for the file mode. Offers a button to browse for a file the
     * user would like to run. Interactive mode and file mode do not share
     * the same memory.
     */
    private static void fileMode(){
        JFrame window = 
            new JFrame("Polynomial Calculator - File Mode"); 
        window.setSize(640, 480);
        window.setLocationRelativeTo(null);
        JTextArea results = new JTextArea(24, 50);
        JPanel content = new JPanel();
        JButton choose = new JButton("Choose File");
        JLabel select = new JLabel("Please choose a file:");
        choose.addActionListener(new ActionListener(){
                public void actionPerformed(ActionEvent e){
                    JFileChooser fc = new JFileChooser();
                    FileNameExtensionFilter filter =
                        new FileNameExtensionFilter("Text files", "txt");
                    fc.setFileFilter(filter);

                    int retVal = fc.showOpenDialog(window);
                    if (retVal ==  JFileChooser.APPROVE_OPTION){
                        StringBuilder sb = new StringBuilder();
                        File file = fc.getSelectedFile();
                        try{
                            BufferedReader r = 
                                new BufferedReader(new FileReader(file));

                            String line;
                            LineRead lr = new LineRead();
                            while ((line = r.readLine()) != null){
                                if (line.length()!=0){
                                    sb.append(line + "\n");
                                    if (lr.lineRead(line)==null)
                                        sb.append("Invalid expression");
                                    else
                                        sb.append(lr.lineRead(line));
                                    sb.append("\n\n");
                                }
                            }
                            results.setText(sb.toString());
                            r.close();
                        } catch (IOException io) {System.err.println(io);}
                    }
                }
            });

        content.add(select);
        content.add(choose);
        content.add(results);
        window.setContentPane(content);
        window.setVisible(true);
        window.toFront();
        return;
    }
}
