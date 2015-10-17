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
	public MorphablePolygon(Polygon p1, Point2D c1, Polygon p2, Point2D c2) {
		setPoints(p1, c1, p2, c2);
	}
	
	//set the points by way of two polygons & two center points
	//p1 & p2 = polygon 1 & 2 respectively
	//c1 & c2 = center 1 & 2 respectively
	public void setPoints(Polygon p1, Point2D c1, Polygon p2, Point2D c2) {
		//todo
	}
}
