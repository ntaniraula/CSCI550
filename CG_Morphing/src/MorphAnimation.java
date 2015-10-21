import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.image.BufferedImage;

public class MorphAnimation implements Runnable {
	Graphics g;
	Graphics gImage;
	int steps;
	MorphablePolygon mPoly;
	Canvas c;
	Dimension dim;
	BufferedImage image;

	public MorphAnimation(Graphics g, Canvas c) {
		this.g = g;
		this.dim = c.getSize();
		image = new BufferedImage(dim.width, dim.height, BufferedImage.TYPE_INT_RGB);
		this.gImage = image.getGraphics();
	}

	public void setMorphablePolygon(MorphablePolygon poly) {
		this.mPoly = poly;
	}

	public void setMaxSteps(int steps) {
		this.steps = steps;
	}

	@Override
	public void run() {
		int curStep = 0;
		Polygon curPoly;
		while (true) {
			curPoly = mPoly.transitionPolygon(this.steps, curStep, true);
			gImage.setColor(Color.white);
			gImage.fillRect(0, 0, dim.width, dim.height);
			gImage.setColor(Color.blue);
			gImage.fillPolygon(curPoly);
			g.drawImage(image, 0, 0, dim.width, dim.height, this.c);
			// g.fillRect(100, 100, 10, 10);
			if (curStep == this.steps) curStep =0;
			else curStep++;
			try {
				Thread.currentThread().sleep(100);
			} catch (Exception e) {
				System.out.println(e.getStackTrace());
			}
		}

	}

}
