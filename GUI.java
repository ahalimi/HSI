import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
/**
 *  Reads the dataset from a file and displays in a window the value of HSI and
 *  hstilda or HSE, hstilda and heco.
 *  @author Anisa Halimi
**/
public class GUI 
{
   private JFrame mainFrame;
   private JLabel headerLabel;
   private JLabel statusLabel;
   private JPanel controlPanel;
   
   public GUI()
   {
       try {
           prepareGUI();
       } catch (IOException ex) {
           Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
       }
   }

   public static void main(String[] args)
   {
       Component parent = null;
       String input=promptForFolder(parent);
       GUI  swingControlDemo = new GUI();      
       swingControlDemo.showButtonDemo(input);
   }
   
   public static String promptForFolder( Component parent )
    {
        JFileChooser fc = new JFileChooser();
        fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

        if(fc.showOpenDialog(parent)==JFileChooser.APPROVE_OPTION)
            return fc.getSelectedFile().getAbsolutePath();
        return null;
    }
    
   private void prepareGUI() throws IOException
   {
      mainFrame = new JFrame("Host Specificity Index");
      mainFrame.setSize(400,400);
      mainFrame.getContentPane().setBackground(Color.CYAN);
      mainFrame.setLayout(new GridLayout(3, 1));
      mainFrame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent windowEvent){
            System.exit(0);
         }        
      });    
      headerLabel = new JLabel("", JLabel.CENTER);        
      statusLabel = new JLabel("",JLabel.CENTER);    

      statusLabel.setSize(350,150);

      controlPanel = new JPanel();
      controlPanel.setLayout(new FlowLayout());
      controlPanel.setBackground(Color.CYAN);

      mainFrame.add(headerLabel);
      mainFrame.add(controlPanel);
      mainFrame.add(statusLabel);
      mainFrame.setVisible(true);  
   } 

   private void showButtonDemo(String input)
   {
       In in = new In(input);
       Graph G = new Graph(in);
       final HSI hsi=new HSI(G);
       JButton hs=new JButton("Calculate HS");
       hs.setBackground(Color.BLUE);
       JButton hse=new JButton("Calculate HSE");
       hse.setBackground(Color.BLUE);
       
       hs.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            String out="HS: "+hsi.HS()+" Hstilda: "+hsi.hstilda();
            statusLabel.setText(out);
         }          
      });
       
     hse.addActionListener(new ActionListener(){
         public void actionPerformed(ActionEvent e) {
            String out="HSE: "+hsi.HSE()+" Hstilda: "+hsi.hstilda()+" Heco: "+hsi.heco();
            statusLabel.setText(out);
         }          
      });

      controlPanel.add(hs);
      controlPanel.add(hse);      
      mainFrame.setVisible(true);  
   }
}