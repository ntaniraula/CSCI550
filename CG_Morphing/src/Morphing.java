import java.awt.*;
import java.awt.image.BufferedImageOp;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.Graphics.*;
import java.awt.Polygon.*;

public class Morphing extends JFrame {
	public static void main(String args[]) {

		Morphing m = new Morphing();
		m.setVisible(true);
		m.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	public Morphing() {
		setTitle("Morphing a Polygon");
		setSize(400, 400);
		add("Center", new cvMorphing());

	}
}

class cvMorphing extends Canvas implements MouseListener {
	Point DCenter;
	Point Scenter ;
	Polygon dest_polygon = new Polygon();
	Polygon src_polygon = new Polygon();
	boolean DestPolyClosed ;
	boolean SrcPolyClosed;
	String msg;
	

	public cvMorphing() {
		this.addMouseListener(this);
		

	}

	public void paint(Graphics g) {
		super.paint(g);
		Dimension d = getSize();
		if(Scenter != null ){
 				g.fillArc(Scenter.x-2, Scenter.y-2, 4, 4,0,360);
 				msg = "Center(" +Scenter.x+ ","+Scenter.y+ ")";
 				g.drawString(msg,Scenter.x-20, Scenter.y-20);
		}
		if(DCenter != null){
	 		 	g.fillArc(DCenter.x-2, DCenter.y-2, 4, 4,0,360);
	 	}
	 	if(SrcPolyClosed == true){
 				draw_DestPoly_points(dest_polygon,g);
 		}
	 			draw_SrcPoly_points(src_polygon,g);

	}

	// Draw different points on the polygon on every mouse click
	private void draw_SrcPoly_points(Polygon sp, Graphics g) {
		int SrcPoly_points = sp.npoints;
		double distanceRefrence;
		Point2D SP = new Point2D();
		Point Ref = new Point();
		
		for (int i = 0; i < SrcPoly_points; i++) {
			g.fillRect(sp.xpoints[i] - 2, sp.ypoints[i] - 2, 4, 4);
			if (i == 0)
				g.drawArc(sp.xpoints[i] - 5, sp.ypoints[i] - 5, 10, 10, 0, 360);
			if (i > 0)
				g.drawLine(sp.xpoints[i - 1], sp.ypoints[i - 1], sp.xpoints[i], sp.ypoints[i]);

			
			//After the polygon gets closed draw a reference line from the center.
			if(SrcPolyClosed == true )
			{
				distanceRefrence = Math.sqrt(((src_polygon.xpoints[0]-Scenter.x)*(src_polygon.xpoints[0]-Scenter.y)) +((src_polygon.ypoints[0]-Scenter.y)*(src_polygon.ypoints[0]-Scenter.y)));
				//unit vector of reference line
				SP.x =(float)(((src_polygon.xpoints[0]-Scenter.x)/distanceRefrence));
				SP.y = (float)(((src_polygon.ypoints[0]-Scenter.y))/distanceRefrence);
				Ref.x = (int)(src_polygon.xpoints[0] + distanceRefrence * SP.x);
				Ref.y = (int)(src_polygon.ypoints[0] + distanceRefrence * SP.y);
				g.drawLine(Scenter.x,Scenter.y,Ref.x,Ref.y);
					
				
			}

		}
	}
		private void draw_DestPoly_points(Polygon dp, Graphics g) {
			int DestPoly_points = dp.npoints;
			
			for(int i = 0; i < DestPoly_points; i++)
			{
				g.fillArc(dp.xpoints[i]-2, dp.ypoints[i]-2, 4, 4, 0, 360);
				if(i==0)
					g.drawArc(dp.xpoints[0]-5,dp.ypoints[0]-5, 10, 10, 0, 360);
				else
				{
					g.drawLine(dp.xpoints[i-1], dp.ypoints[i-1], dp.xpoints[i],dp.ypoints[i]);
				}
			}
			
		}	
		

	@Override
	public void mouseClicked(MouseEvent event) {
		if(Scenter == null)
		{
				Scenter = new Point(event.getX(),event.getY());
		}
	
		else if(!SrcPolyClosed)
		{
			if((event.getX() >= src_polygon.xpoints[0]-5) && (event.getY() >= src_polygon.ypoints[0]-5) && (event.getX() <= src_polygon.xpoints[0]+5) &&
				(event.getY() <= src_polygon.ypoints[0]+5))
					{
						src_polygon.addPoint(src_polygon.xpoints[0], src_polygon.ypoints[0]);
						SrcPolyClosed =true;
					}
					else
					{
						src_polygon.addPoint(event.getX(),event.getY());
					}
					
					
		}
	
		else if(SrcPolyClosed = true &&!DestPolyClosed){
			
				if(DCenter == null)
					{
						DCenter = new Point(event.getX(),event.getY());
					}
				else {
	
			if((event.getX() >= dest_polygon.xpoints[0]-5) && (event.getY() >= dest_polygon.ypoints[0]-5) && (event.getX() <= dest_polygon.xpoints[0]+5) &&
				(event.getY() <= dest_polygon.ypoints[0]+5))
						{
							dest_polygon.addPoint(dest_polygon.xpoints[0], dest_polygon.ypoints[0]);
							DestPolyClosed =true;
						}
				else
					{
							dest_polygon.addPoint(event.getX(),event.getY());
					}
				}
		}
		
		repaint();
	

	}

	public void mouseEntered(MouseEvent arg0) {
	}

	public void mouseExited(MouseEvent arg0) {
	}

	public void mousePressed(MouseEvent arg0) {
	}

	public void mouseReleased(MouseEvent arg0) {
	}
}
