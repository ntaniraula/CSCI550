import java.awt.*;
import java.awt.Polygon.*;
import java.util.ArrayList;

//class to represent a morphable polygon
public class MorphablePolygon {
	public ArrayList<MorphablePoint> points;
	public Point center;
	
	public MorphablePolygon() { points = new ArrayList<MorphablePoint>(); }
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
		
		//Merge points
		points = new ArrayList<MorphablePoint>();
		for (int i = 0; i < pA.npoints; i++) {
			points.add(new MorphablePoint(
				new Point(pA.xpoints[i], pA.ypoints[i]), null));
		}
		for (int i = 0; i < pB.npoints; i++) {
			points.add(new MorphablePoint(
				null, new Point(pB.xpoints[i] - xDiff, pB.ypoints[i] - yDiff)));
		}
		
		sortPoints();
		setProjections();
		
		printPoints();
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
	public int pointCcwLess(MorphablePoint m1, MorphablePoint m2) {
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
				return p1.y > p2.y ? isFalse : isTrue;
			return p2.y > p1.y ? isFalse : isTrue;
		}
		
		//get determinant
		float det = ((p1.x - center.x) * (p2.y - center.y)) -
			((p2.x - center.x) * (p1.y - center.y));
		if (det < 0) return isFalse;
		if (det > 0) return isTrue;
		
		//p1 & p2 are on the same line from the center
		return areEqual;
	}
	
	public void sortPoints() {
		points = quickSort(points);
	}
	
	public void setProjections() {
		for (int i = 0; i < points.size(); i++) {
			MorphablePoint mp = points.get(i);
			if (mp.stateA == null || mp.stateB == null) {
				boolean baseIsStateA = mp.stateA != null;
				
				if (baseIsStateA) {
					mp.stateB = getIntersection(
						getNeighbor(i, false), getNeighbor(i, true),
						center, mp.stateA);
					mp.stateB = mp.stateB != null ? mp.stateB : mp.stateA; //compromise
				} else {
					mp.stateA = getIntersection(
						getNeighbor(i, false), getNeighbor(i, true),
						center, mp.stateB);
					mp.stateA = mp.stateA != null ? mp.stateA : mp.stateB; //compromise
				}
				points.set(i, mp);
			}
		}
	}
	
	public Point getNeighbor(int index, boolean down) {
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
	
	public Point getIntersection(Point a1, Point a2, Point b1, Point b2) {
		float d = (b2.y - b1.y) * (a2.x - a1.x) - (b2.x - b1.x) * (a2.y - a1.y);
		if (d == 0.0f) return null; //lines are parallel
		
		float ua = ((b2.x - b1.x) * (a1.y - b1.y) - (b2.y - b1.y) * (a1.x - b1.x))/d;
		float ub = ((a2.x - a1.x) * (a1.y - b1.y) - (a2.y - a1.y) * (a1.x - b1.x))/d;
		
		if (ua >= 0.0f && ua <= 1.0f &&
			ub >= 0.0f && ub <= 1.0f) {
			return new Point(
				Math.round(a1.x + ua * (a2.x - a1.x)),
				Math.round(a1.y + ua * (a2.y - a1.y)));
		}
		
		return null;
	}
	
	private ArrayList<MorphablePoint> quickSort(ArrayList<MorphablePoint> mPoints) {
		if (mPoints.size() <= 1) return mPoints;
		else {
			ArrayList<MorphablePoint> result = new ArrayList<MorphablePoint>();
			
			MorphablePoint pivot = mPoints.get(0);
			ArrayList<MorphablePoint> left   = new ArrayList<MorphablePoint>();
			ArrayList<MorphablePoint> right  = new ArrayList<MorphablePoint>();
			
			for (int i = 1; i < mPoints.size(); i++) {
				int isLess = pointCcwLess(pivot, mPoints.get(i));
				if (isLess == -1) left.add(mPoints.get(i));
				else if (isLess == 1) right.add(mPoints.get(i));
				else { //merged
					pivot.stateA = null != pivot.stateA ? pivot.stateA : mPoints.get(i).stateA;
					pivot.stateB = null != pivot.stateB ? pivot.stateB : mPoints.get(i).stateB;
				}
			}
			
			result.addAll(quickSort(left));
			result.add(pivot);
			result.addAll(quickSort(right));
			
			return result;
		}
	}
	
	private void printPoints() {
		System.out.println("Size: " + Integer.toString(points.size()));
		for (int i = 0; i < points.size(); i++) {
			MorphablePoint p = points.get(i);
			System.out.println("index : " + Integer.toString(i) +
				" [(" + (p.stateA != null 
					? Integer.toString(p.stateA.x) + ", " + Integer.toString(p.stateA.y) : "NULL") +
				"),(" + (p.stateB != null 
					? Integer.toString(p.stateB.x) + ", " + Integer.toString(p.stateB.y) : "NULL") +
				")]");
		}
	}
	
	private void printPoints(ArrayList<MorphablePoint> mPoints) {
		String result = "";
		for (int i = 0; i < mPoints.size(); i++) {
			result = result + mPoints.get(i).toString();
			result += i == mPoints.size() - 1 ? "" : ", ";
		}
		System.out.println(result);
	}
}
