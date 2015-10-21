import java.awt.*;
import java.awt.Polygon.*;
import java.util.ArrayList;

//class to represent a morphable polygon
public class MorphablePolygon {
	public ArrayList<MorphablePoint> points;
	public Point center;
	
	public MorphablePolygon() {}
	public MorphablePolygon(ArrayList<MorphablePoint> points) {
		this.points = points;
	}
	//see setPoints()
	public MorphablePolygon(Polygon pA, Point cA, Polygon pB, Point cB) {
		setPoints(pA, cA, pB, cB);
	}
	
	//set the points by way of two polygons & two center points
	//pA & pB = polygon A & B respectively
	//cA & cB = center of polygon A & B respectively
	public void setPoints(Polygon pA, Point cA, Polygon pB, Point cB) {
		center = new Point(cA.x, cA.y); //set pA's center as the polygon's center
		int xDiff = cB.x - cA.x;
		int yDiff = cB.y - cA.y;
	
		//set the first element of pA as reference point
		Point2D basePoint = new Point2D(pA.xpoints[0], pA.ypoints[0]);
		
		//Merge points
		for (int i = 0; i < pA.xpoints.length; i++) {
			points.add(new MorphablePoint(
				new Point(pA.xpoints[i], pA.ypoints[i]), null));
		}
		for (int i = 0; i < pB.xpoints.length; i++) {
			points.add(new MorphablePoint(
				null, new Point(pA.xpoints[i] - xDiff, pA.ypoints[i] - yDiff)));
		}
		
		sortPoints();
		setProjections();
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
				Point tPoint = points.get(i)
					.transitionPoint(maxSteps, step, toStateA);
				tPolygon.addPoint(
					Math.round(tPoint.x), Math.round(tPoint.y));
			}
		}
		
		return tPolygon;
	}
	
	//check whether a point is in a less position relative
	//to the center in a counter clockwise direction
	private int pointCcwLess(MorphablePoint m1, MorphablePoint m2) {
		int isTrue   =  1;
		int isFalse  = -1;
		int areEqual =  0;
		
		Point2D p1 = m1.stateA != null 
			? new Point2D(m1.stateA.x, m1.stateA.y) 
			: new Point2D(m1.stateB.x, m1.stateB.y);
		Point2D p2 = m2.stateA != null 
			? new Point2D(m2.stateA.x, m2.stateA.y) 
			: new Point2D(m2.stateB.x, m2.stateB.y);
	
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
	
	private void sortPoints() {
		boolean swapped;
		do {
			swapped = false;
			for (int i = 1; i < points.size() - 1; i++) {
				int isLess = pointCcwLess(points.get(i-1), points.get(i));
				if (isLess == -1) {
					swapPoints(i-1, i);
					swapped = true;
				} else if (isLess == 0) { //the points are in the same centerline
					mergePoints(i-1, i);
				}
			}			
		} while (!swapped);
	}
	
	private void swapPoints(int index1, int index2) {
		MorphablePoint temp = points.get(index1);
		points.set(index1, points.get(index2));
		points.set(index2, temp);
	}
	
	private void mergePoints(int index1, int index2) {
		MorphablePoint newPoint = new MorphablePoint();
		newPoint.stateA = null != points.get(index1).stateA
			? points.get(index1).stateA : points.get(index2).stateA;
		newPoint.stateB = null != points.get(index1).stateB
			? points.get(index1).stateB : points.get(index2).stateB;
			
		points.set(index1, newPoint);
		points.remove(index2);
	}
	
	private void setProjections() {
		for (int i = 0; i < points.size(); i++) {
			MorphablePoint mp = points.get(i);
			if (mp.stateA == null || mp.stateB == null) {
				boolean baseIsStateA = mp.stateA != null;
				
				if (baseIsStateA) {
					mp.stateB = getIntersection(
						getNeighbor(i, false), getNeighbor(i, true),
						center, mp.stateA);
				} else {
					mp.stateA = getIntersection(
						getNeighbor(i, false), getNeighbor(i, true),
						center, mp.stateB);
				}
				points.set(i, mp);
			}
		}
	}
	
	private Point getNeighbor(int index, boolean down) {
		int counter = 0;
		int increment = down ? 1 : -1;
		int position = index;
		
		boolean baseIsStateA = points.get(index).stateA != null;
		
		while (counter < points.size()) {
			position += increment;
			if (position < 0) position = points.size() - 1;
			if (position >= points.size()) position = 0;
			
			if (baseIsStateA && points.get(position).stateB != null)
				return points.get(position).stateB;
			if (!baseIsStateA && points.get(position).stateA != null)
				return points.get(position).stateA;
				
			counter++;
		}
		
		return center; //fallback to center
	}
	
	private Point getIntersection(Point a1, Point a2, Point b1, Point b2) {
		MorphablePoint mA = new MorphablePoint(a1, a2);
		MorphablePoint mB = new MorphablePoint(b1, b2);
		
		Point2D uVA = mA.unitVector(false);
		Point2D uVB = mB.unitVector(false);
		
		float dA = uVA.y/uVA.x;
		float rA = dA * a1.x - a1.y;
		
		float dB = uVB.y/uVB.x;
		float rB = dB * b1.x - b1.y;
		
		return new Point(
			Math.round((rA - rB)/(dA - dB)),
			Math.round(((dA * rB) - (dB * rB))/(dB - dA)));
	}
}
