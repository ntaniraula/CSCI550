import java.awt.*;
import java.awt.Polygon.*;
import java.util.ArrayList;

//class to represent a morphable polygon
public class MorphablePolygon {
	public ArrayList<MorphablePoint> points;
	public Point2D center;
	
	public MorphablePolygon() {}
	public MorphablePolygon(ArrayList<MorphablePoint> points) {
		this.points = points;
	}
	//see setPoints()
	public MorphablePolygon(Polygon pA, Point2D cA, Polygon pB, Point2D cB) {
		setPoints(pA, cA, pB, cB);
	}
	
	//set the points by way of two polygons & two center points
	//pA & pB = polygon A & B respectively
	//cA & cB = center of polygon A & B respectively
	public void setPoints(Polygon pA, Point2D cA, Polygon pB, Point2D cB) {
		center = new Point2D(cA.x, cA.y); //set pA's center as the polygon's center
		float xDiff = cB.x - cA.x;
		float yDiff = cB.y - cA.y;
	
		//set the first element of pA as reference point
		Point2D basePoint = new Point2D(pA.xpoints[0], pA.ypoints[0]);
		
		//TODO!
		//temporary logic
		for (int i = 0; i < pA.xpoints.length; i++) {
			points.add(new MorphablePoint(
				new Point2D(pA.xpoints[i], pA.ypoints[i]),
				new Point2D(0, 0)));
		}
		for (int i = 0; i < pB.xpoints.length; i++) {
			if (i >= pA.xpoints.length)
				points.add(new MorphablePoint(
					new Point2D(pB.xpoints[i], pB.ypoints[i]),
					new Point2D(pB.xpoints[i], pB.ypoints[i])));
			else
				points.add(i, new MorphablePoint(
					new Point2D(pA.xpoints[i], pA.ypoints[i]),
					new Point2D(pB.xpoints[i], pB.ypoints[i])));
		}
		//temporary logic - END
	}
}
