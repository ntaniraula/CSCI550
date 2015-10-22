import java.awt.Point;
import java.util.ArrayList;

public class MorphablePolygon_Test {
	public static void main(String args[]) {
		testPointCcwLess();
		testSortPoints();
		testSetProjections();
		testGetNeighbor();
		testGetIntersection();
	}
	
	static void testPointCcwLess() {
		Point center;
		MorphablePoint m1, m2;
		int result;
		
		MorphablePolygon mPolygon = new MorphablePolygon();
		System.out.println("Point Ccw Less Test");
		
		center = new Point(200, 200);
		mPolygon.center = center;
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(null, new Point(400, 200));
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(new Point(300, 100), null);
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(null, new Point(200, 0));
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(new Point(100, 100), null);
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(null, new Point(200, 400));
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(new Point(100, 300), null);
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 300), null);
		m2 = new MorphablePoint(null, new Point(0, 200));
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		m1 = new MorphablePoint(new Point(300, 100), null);
		m2 = new MorphablePoint(null, new Point(400, 200));
		result = mPolygon.pointCcwLess(m1, m2);
		System.out.println("m1    : " + m1.toString());
		System.out.println("m2    : " + m2.toString());
		System.out.println("result: " + Integer.toString(result));
		
		System.out.println();
	}
	
	static void testSortPoints() {
		Point center;
		ArrayList<MorphablePoint> points;
		
		MorphablePolygon mPolygon = new MorphablePolygon();
		System.out.println("Sort Points Test");
		
		center = new Point(200, 200);
		points = new ArrayList<MorphablePoint>();
		points.add(new MorphablePoint(new Point(300, 300), null));
		points.add(new MorphablePoint(new Point(300, 100), null));
		points.add(new MorphablePoint(new Point(100, 100), null));
		points.add(new MorphablePoint(new Point(100, 300), null));
		points.add(new MorphablePoint(null, new Point(400, 200)));
		points.add(new MorphablePoint(null, new Point(200, 0  )));
		points.add(new MorphablePoint(null, new Point(0  , 200)));
		points.add(new MorphablePoint(null, new Point(200, 400)));
		mPolygon.center = center;
		mPolygon.points = points;
		
		mPolygon.sortPoints();
		printMorphablePolygon(mPolygon, "Sort Points Test");
		
		System.out.println();
	}
	
	static void testSetProjections() {
		Point center;
		ArrayList<MorphablePoint> points;
		
		MorphablePolygon mPolygon = new MorphablePolygon();
		System.out.println("Set Projections Test");
		
		center = new Point(200, 200);
		points = new ArrayList<MorphablePoint>();
		points.add(new MorphablePoint(null, new Point(200,   0)));
		points.add(new MorphablePoint(new Point(300, 100), null));
		points.add(new MorphablePoint(null, new Point(400, 200)));
		points.add(new MorphablePoint(new Point(300, 300), null));
		points.add(new MorphablePoint(null, new Point(200, 400)));
		points.add(new MorphablePoint(new Point(100, 300), null));
		points.add(new MorphablePoint(null, new Point(0  , 200)));
		points.add(new MorphablePoint(new Point(100, 100), null));
		
		mPolygon.center = center;
		mPolygon.points = points;
		
		mPolygon.setProjections();
		printMorphablePolygon(mPolygon, "Set Projections Test");
		
		System.out.println();
	}
	
	static void testGetNeighbor() {
		Point center;
		ArrayList<MorphablePoint> points;
		
		MorphablePolygon mPolygon = new MorphablePolygon();
		System.out.println("Get Neighbor Test");
		
		Point result;
		
		center = new Point(200, 200);
		points = new ArrayList<MorphablePoint>();
		points.add(new MorphablePoint(null, new Point(200,   0)));
		points.add(new MorphablePoint(new Point(300, 100), null));
		points.add(new MorphablePoint(null, new Point(400, 200)));
		points.add(new MorphablePoint(new Point(300, 300), null));
		points.add(new MorphablePoint(null, new Point(200, 400)));
		points.add(new MorphablePoint(new Point(100, 300), null));
		points.add(new MorphablePoint(null, new Point(0  , 200)));
		points.add(new MorphablePoint(new Point(100, 100), null));
		
		mPolygon.center = center;
		mPolygon.points = points;
		
		printMorphablePolygon(mPolygon, "Get Neighbor Test");
		System.out.println();
		
		int index;
		boolean down;
		
		index = 0;
		down = false;
		result = mPolygon.getNeighbor(index, down);
		System.out.println("Input     : " + mPolygon.points.get(index).toString());
		System.out.println("Direction : " + (down ? "down" : "up"));
		System.out.println("Output    : " + getPointString(result));
		System.out.println();
		
		index = 0;
		down = true;
		result = mPolygon.getNeighbor(index, down);
		System.out.println("Input     : " + mPolygon.points.get(index).toString());
		System.out.println("Direction : " + (down ? "down" : "up"));
		System.out.println("Output    : " + getPointString(result));
		System.out.println();
		
		System.out.println();
	}
	
	static void testGetIntersection() {
		Point a1, a2, b1, b2;
		Point intersection;
		
		MorphablePolygon mPolygon = new MorphablePolygon();
		System.out.println("Get Intersection Test");
	
		a1 = new Point(300, 100);
		a2 = new Point(300, 300);
		b1 = new Point(200, 200);
		b2 = new Point(350, 200);
		
		intersection = mPolygon.getIntersection(a1, a2, b1, b2);
		System.out.println(
			"a1 : " + getPointString(a1)
			+ " a2 : " + getPointString(a2)
			+ " b1 : " + getPointString(b1)
			+ " b2 : " + getPointString(b2));
		System.out.println("Intersection: " + getPointString(intersection));
		System.out.println();
		
		a1 = new Point(100, 100);
		a2 = new Point(100, 300);
		b1 = new Point(200, 200);
		b2 = new Point(0  , 200);
		
		intersection = mPolygon.getIntersection(a1, a2, b1, b2);
		System.out.println(
			"a1 : " + getPointString(a1)
			+ " a2 : " + getPointString(a2)
			+ " b1 : " + getPointString(b1)
			+ " b2 : " + getPointString(b2));
		System.out.println("Intersection: " + getPointString(intersection));
		System.out.println();
		
		System.out.println();
	}
	
	static void printMorphablePolygon(MorphablePolygon mPolygon, String label) {
		System.out.println("Size  : " + Integer.toString(mPolygon.points.size()));
		System.out.println("Center: (" 
			+ Integer.toString(mPolygon.center.x) + ", " 
			+ Integer.toString(mPolygon.center.y) + ")"); 
		for (int i = 0; i < mPolygon.points.size(); i++) {
			System.out.println("Index : " + Integer.toString(i) + " " 
				+ mPolygon.points.get(i).toString());
		}
	}
	
	//Tools
	static String getPointString(Point p) {
		return null != p
			? "(" + Integer.toString(p.x) + ", " + Integer.toString(p.y) + ")"
			: "(NULL)";
	}
}