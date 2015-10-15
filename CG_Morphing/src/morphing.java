import java.awt.*;
import java.awt.image.BufferedImageOp;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.Graphics.*;

public class morphing extends JFrame
{
	public static void main(String[] args)
	{
		  new morphing();
		
		
		
	}
	
	 morphing()
	{
		super("Morphing-Project1");  //giving the title for the GUI
		setSize(500,500);
		setVisible(true);
		add("Center", new cvMorphing());

		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				System.exit(0);}});
	}
}

class cvMorphing extends Canvas implements MouseListener{
	
 public cvMorphing(){
	 this.addMouseListener(this);
	 repaint();
	 }
 
public void paint(Graphics g)
{
	
	super.paint(g);
	Dimension d = getSize();
	drawButton(g,10,d.height-40,"Click To Morph");
}

//Draw a simulated button at specified location, with given string caption
private void drawButton(Graphics g, int x,int y,String text)
{
	int height=30, width;
	width=text.length()*10;
	
	g.setColor(Color.darkGray);
	g.fillRect(x+2, y+2, width+2, height+2);
	g.setColor(Color.lightGray);
	g.fillRect(x, y, width, height);
	g.setColor(Color.black);
	g.drawString(text.toUpperCase(), (x+(width/2))-((text.length()/2)*7), y+(2*height/3));
	
}
 
 
 
 

@Override
public void mouseClicked(MouseEvent arg0) {
	
	
	
}

public void mouseEntered(MouseEvent arg0) {}

public void mouseExited(MouseEvent arg0) {}

public void mousePressed(MouseEvent arg0) {}

public void mouseReleased(MouseEvent arg0) {}
}
	
