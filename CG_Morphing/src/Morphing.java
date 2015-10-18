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
		setSize(800, 400);
		add("Center", new CvMorphing());

	}
}

class CvMorphing extends Canvas implements MouseListener {
	Point tCenter;
	Point refCenter;
	Polygon targetPolygon = new Polygon();
	Polygon refPolygon = new Polygon();
	boolean targetPolyClosed;
	boolean refPolyClosed;
	boolean MouseClicked;
	String msg;
	double distanceRefrence; // calculating the distance between polygon
								// starting vertex to center

	Point2D dirCP = new Point2D(); // point to find the vector for the reference
									// line
	Point hRefLinePoint, vRefLinePoint;

	public CvMorphing() {
		this.addMouseListener(this);

	}

	// paint method to draw polygons
	public void paint(Graphics g) {
		super.paint(g);
		Dimension d = getSize();
		
		if(!MouseClicked)
		{
				msg = "Please Click to start";
				g.drawString(msg, (d.width - 100)/2, (d.height-100)/2);
		}
		else
		{
				msg = "In order to draw polygon you have to always move in Clockwise Direction";
				g.drawString(msg, 20, 20);
		}
		drawPolygon(targetPolygon, g, Color.BLUE, targetPolyClosed);
		drawPolygon(refPolygon, g, Color.MAGENTA, refPolyClosed);

		// first click on the frame
		if (refCenter != null) {
			g.fillArc(refCenter.x - 2, refCenter.y - 2, 4, 4, 0, 360);
			msg = "Center(" + refCenter.x + "," + refCenter.y + ")";
			g.drawString(msg, refCenter.x - 20, refCenter.y - 20);
		}
		// The first click after our reference polygon is drawn to draw another polygon
		if (tCenter != null) {
			g.fillArc(tCenter.x - 2, tCenter.y - 2, 4, 4, 0, 360);
		}

		
		drawReferenceLines(g);
	}

	// Draw different points on the polygon on every mouse click
	private void drawPolygon(Polygon p, Graphics g, Color color, boolean closePolygon) {
		int npoints = p.npoints; // number of points in polygon
		Color orgColor = g.getColor();
		g.setColor(color);
		if (closePolygon == false) {
			// This handles the condition for drawing a source polygon after we
			// have initialized the center for our polygon.
			for (int i = 0; i < npoints; i++) {

				g.fillRect(p.xpoints[i] - 2, p.ypoints[i] - 2, 4, 4);

				// for the starting point of the polygon mark it with arc
				if (i == 0) {
					g.drawArc(p.xpoints[i] - 5, p.ypoints[i] - 5, 10, 10, 0, 360);

				}
				//start connecting the points of polygon
				if (i > 0)

					g.drawLine(p.xpoints[i - 1], p.ypoints[i - 1], p.xpoints[i], p.ypoints[i]);

			}
		}
		else
		{
			g.fillPolygon(p);  //after polygon is closed, fill the shape with the desired color
		}
		g.setColor(orgColor);
	}

	
	//This handles drawing reference lines on the polygon with respect to the polygon point and Center
	//Two reference line were drawn one in the direction of Center and first polygon point
	// Another in the perpendicular direction of the reference polygon first point.
	private void drawReferenceLines(Graphics g) {
		int npoints;

		if (refCenter != null) {
			if (refPolygon.npoints > 0) {
				npoints = refPolygon.npoints - 1;
				// After the polygon gets closed draw a reference line from the
				// center.
				
				// This calculates the distance bewteen the center of the reference polygon and its point.
				distanceRefrence = Math.sqrt(((refPolygon.xpoints[npoints] - refCenter.x)
						* (refPolygon.xpoints[npoints] - refCenter.y))
						+ ((refPolygon.ypoints[npoints] - refCenter.y) * (refPolygon.ypoints[npoints] - refCenter.y)));

				// unit vector of reference line
				vRefLinePoint = new Point();
				hRefLinePoint = new Point();
				
				//direction  of line from center of reference polygon to the first polygon point
				dirCP.x = (float) (((refPolygon.xpoints[npoints] - refCenter.x) / distanceRefrence));
				dirCP.y = (float) (((refPolygon.ypoints[npoints] - refCenter.y) / distanceRefrence));

				// To draw horizontal and vertical refrence line points with respect to the polygon vertex
				vRefLinePoint.x = Math.round((float) (refPolygon.xpoints[npoints] + distanceRefrence * dirCP.x));
				vRefLinePoint.y = Math.round((float) (refPolygon.ypoints[npoints] + distanceRefrence * dirCP.y));

				hRefLinePoint.x = Math.round((float) (refPolygon.xpoints[npoints] + distanceRefrence * dirCP.y));
				hRefLinePoint.y = Math.round((float) (refPolygon.ypoints[npoints] - distanceRefrence * dirCP.x));

				// Draw a reference line from the  Center to  reference polygon
				g.setColor(Color.red);
				g.drawLine(refCenter.x, refCenter.y, vRefLinePoint.x, vRefLinePoint.y);
				g.drawLine(hRefLinePoint.x, hRefLinePoint.y, refPolygon.xpoints[npoints], refPolygon.ypoints[npoints]);

			}
		}
	}

	//This condition checks weather the clicked point is valid to form a star shaped polygon
	
	private boolean isPointValid(Polygon p, Point c, Point t) {
		int npoints = p.npoints-1;
		boolean result;
		Point2D X = new Point2D();
		Point2D dir = new Point2D();
		float dist;

		if (p.npoints == 0)
			return true;
		else {
			
			dist = (float) Math.sqrt(((p.xpoints[npoints] - c.x)
					* (p.xpoints[npoints] - c.y))
					+ ((p.ypoints[npoints] - c.y) * (p.ypoints[npoints] - c.y)));

			
			dir.x = (float) (((p.xpoints[npoints] - c.x) / dist));
			dir.y = (float) (((p.ypoints[npoints] - c.y) / dist));
			
			float d = (t.x - c.x) * (c.y - p.ypoints[npoints]) - (c.y - t.y) * (p.xpoints[npoints] - c.x);
			// float PerpDistance2 =
			if (d > 0)
				result = true;
			else
				result = false;

			if (result == true) {

				X.x = (float) (p.xpoints[npoints] + (100 * dir.y));
				X.y = (float) (p.ypoints[npoints] - (100 * dir.x));

				d = (t.x - X.x) * (X.y - p.ypoints[npoints]) - (X.y - t.y) * (p.xpoints[npoints] - X.x);

				if ((npoints) % 2 == 0) {
					if ((d > 0) || (d == 0))
						result = false;
					else if (d < 0)
						result = true;
				} else {
					if ((d < 0) || (d == 0))
						result = false;
					else if (d > 0)
						result = true;
				}
			}
			return result;
		}
	}

	@Override
	public void mouseClicked(MouseEvent event) {
		
		MouseClicked = true; 
		if (refCenter == null) {
			refCenter = new Point(event.getX(), event.getY());
		}

		else if (!refPolyClosed) {
			if (isPointValid(refPolygon, refCenter, new Point(event.getX(), event.getY()))) {
				if ((event.getX() >= refPolygon.xpoints[0] - 5) && (event.getY() >= refPolygon.ypoints[0] - 5)
						&& (event.getX() <= refPolygon.xpoints[0] + 5) && (event.getY() <= refPolygon.ypoints[0] + 5)) {
					refPolygon.addPoint(refPolygon.xpoints[0], refPolygon.ypoints[0]);
					refPolyClosed = true;
				} else {
					refPolygon.addPoint(event.getX(), event.getY());
				}
			}

		}

		else if (refPolyClosed == true && !targetPolyClosed) {

			if (tCenter == null) {
				tCenter = new Point(event.getX(), event.getY());
			} else {
				if (isPointValid(targetPolygon, tCenter, new Point(event.getX(), event.getY()))) {

					if ((event.getX() >= targetPolygon.xpoints[0] - 5) && (event.getY() >= targetPolygon.ypoints[0] - 5)
							&& (event.getX() <= targetPolygon.xpoints[0] + 5)
							&& (event.getY() <= targetPolygon.ypoints[0] + 5)) {
						targetPolygon.addPoint(targetPolygon.xpoints[0], targetPolygon.ypoints[0]);
						targetPolyClosed = true;
					} else {
						targetPolygon.addPoint(event.getX(), event.getY());
					}
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
