//class to represent a morphable points
//for now, let only be able to represent 2 states: stateA & stateB
public class MorphablePoint {
	public Point2D stateA;
	public Point2D stateB;
	
	public MorphablePoint() {}
	public MorphablePoint(Point2D stateA, Point2D stateB) {
		this.stateA = stateA;
		this.stateB = stateB;
	}
}
