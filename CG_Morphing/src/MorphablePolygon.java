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
		//todo
	}
}
