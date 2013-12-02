package eecs285.proj4.Yahtzee;

import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

/*
   A dialog to get the name of the player to display on the GUI
   of the players.
*/
public class PlayerNamesDialog extends JDialog
{
   // text field for input
   private JTextField PlayerNamesTextField;
   // button for dismissing text field
   private JButton okButton;

   /**
    * Constructor for Dialog box uses the mainFrame as it's background frame and
    * sets the name to name
    */
   public PlayerNamesDialog(JFrame mainFrame, String name)
   {
      super(mainFrame, name, true);
      setLayout(new FlowLayout());
      setLocationRelativeTo(null);
      add(new JLabel("Enter your Player name"));
      PlayerNamesTextField = new JTextField(25);
      add(PlayerNamesTextField);
      okButton = new JButton(" OK ");
      okButton.addActionListener(new ActionListener()
      {
         public void actionPerformed(ActionEvent e)
         {
            setVisible(false);
         }
      });
      add(okButton);
      pack();
      setVisible(true);
   }

   /**
    * gets the string associated with the text box
    */
   public String getName()
   {
      return PlayerNamesTextField.getText();
   }
}
