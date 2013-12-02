package eecs285.proj4.Yahtzee;

import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
/*
    A button used to roll the die and animate the roll.
*/
public class DieButton extends JToggleButton implements ActionListener
{

    ImageIcon [] images;
    Timer timer;
    int roll;
    int animationLength, animationSpeed;
    int endFrame;
    
    public DieButton (ImageIcon [] myImages, int startFrame)
    {
        super();
        
        animationSpeed = 35;
        
        images = myImages;
        roll = 0;
        endFrame = 0;
        this.setIcon(images[startFrame-1]);
        timer = new Timer(1000 / animationSpeed, this);
    }
    
    public void animate(int num, int length)
    {
    		animationLength = length;
        endFrame = num - 1;
        timer.start();
    }

    public void actionPerformed(ActionEvent arg0) 
    {
        roll++;
        this.setIcon(images[roll%6]);
        if(roll > animationLength && (roll%6) == endFrame)
        {
            roll = 0;
            timer.stop();
        }
    }
    
    
}
