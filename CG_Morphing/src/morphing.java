import java.awt.*;
import java.awt.image.BufferedImageOp;
import javax.swing.*;
import java.lang.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;
import java.awt.Graphics.*;
import java.awt.Polygon.*;

public class morphing extends JFrame {
	public static void main(String args[]) {

		morphing m = new morphing();
		m.setVisible(true);
		m.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent we) {
				System.exit(0);
			}
		});
	}

	public morphing() {
		setTitle("Incenter of Triangle");
		setSize(400, 400);
		add("Center", new cvMorphing());

	}
}

class cvMorphing extends Canvas implements MouseListener {
	int PCenterX, PCenterY;
	Polygon polygon = new Polygon();
	// Graphics g ;
	int countClick = 0;
	boolean polygonClosed;

	public cvMorphing() {
		this.addMouseListener(this);
		// this.g = this.getGraphics();

	}

	public void paint(Graphics g) {
		Point p;
		super.paint(g);
		Dimension d = getSize();
		//

		//
		p = new Point();

		// g.fillRect(x, y, width, height);

		if (countClick >= 1) {
			p.x = PCenterX;
			p.y = PCenterY;

			g.fillRect(p.x - 2, p.y - 2, 4, 4);
		}
		if (countClick >= 2) {
			draw_Poly_points(polygon, g);

		}
	}

	// Draw different points on the polygon on every mouse click
	private void draw_Poly_points(Polygon p, Graphics g) {
		int Poly_points = p.npoints;
		for (int i = 0; i < Poly_points; i++) {
			g.fillRect(p.xpoints[i] - 2, p.ypoints[i] - 2, 4, 4);
			if (i > 0)
				g.drawLine(p.xpoints[i - 1], p.ypoints[i - 1], p.xpoints[i], p.ypoints[i]);
			if (i == 0)
				g.drawArc(p.xpoints[i] - 5, p.ypoints[i] - 5, 10, 10, 0, 360);
		}

	}

	@Override
	public void mouseClicked(MouseEvent event) {
		countClick++;
		if (!polygonClosed) {
			if (countClick == 1) {
				PCenterX = event.getX();
				PCenterY = event.getY();

			}
			if (countClick >= 2) {

				if ((countClick > 2) && (event.getX() >= polygon.xpoints[0] - 5)
						&& (event.getX() <= polygon.xpoints[0] + 5) && (event.getY() >= polygon.ypoints[0] - 5)
						&& (event.getY() <= polygon.ypoints[0] + 5)) {
					polygon.addPoint(polygon.xpoints[0], polygon.ypoints[0]);
					polygonClosed = true;
				} else {
					polygon.addPoint(event.getX(), event.getY());

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
