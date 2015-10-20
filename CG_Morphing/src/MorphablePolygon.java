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
	
	//get the transition polygon between stateA & stateB
	//maxSteps : the maximum number of transition steps.
	//step     : the current step no. for the requested transition point.
	//           if transition is going from stateB to stateA:
	//              step 0            == exactly at stateB
	//              step maxSteps + 1 == exactly at stateA
	//toStateA : the direction of the transition.
	//           true if it's going from stateB to stateA, false otherwise.
	public Polygon transitionPolygon(int maxSteps, int step, boolean toStateA) {
		Polygon tPolygon = new Polygon();
		
		if (null != points && points.size() > 0) {
			for (int i = 0; i < points.size(); i++) {
				Point2D tPoint = points.get(i)
					.transitionPoint(maxSteps, step, toStateA);
				tPolygon.addPoint(
					Math.round(tPoint.x), Math.round(tPoint.y));
			}
		}
		
		return tPolygon;
	}
	
	//check whether a point is in a less position relative
	//to the center in a counter clockwise direction
	private int pointCcwLess(Point2D p1, Point2D p2) {
		int isTrue   =  1;
		int isFalse  = -1;
		int areEqual =  0;
	
		if (p1.x - center.x >= 0 && p2.x - center.x < 0)
			return isTrue;
		if (p1.x - center.x < 0 && p2.x - center.x >= 0)
			return isFalse;
		if (p1.x - center.x == 0 && p2.x - center.x == 0) {
			if (p1.y - center.y >= 0 || p2.y - center.y >= 0)
				return p1.y > p2.y ? isTrue : isFalse;
			return p2.y > p1.y ? isTrue : isFalse;
		}
		
		//get determinant
		float det = ((p1.x - center.x) * (p2.y - center.y)) -
			((p2.x - center.x) * (p1.y - center.y));
		if (det < 0) return isTrue;
		if (det > 0) return isFalse;
		
		//p1 & p2 are on the same line from the center
		return areEqual;
	}
}
