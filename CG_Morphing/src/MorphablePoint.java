//class to represent a morphable points
//for now, let it only be able to represent 2 states: stateA & stateB
public class MorphablePoint {
	public Point2D stateA;
	public Point2D stateB;
	
	public MorphablePoint() {}
	public MorphablePoint(Point2D stateA, Point2D stateB) {
		this.stateA = stateA;
		this.stateB = stateB;
	}
	
	//get the distance between stateA & stateB
	public float distance() {
		float dX = 0f, dY = 0f;
		if (stateA != null && stateB != null) {
			dX = stateB.x - stateA.x;
			dY = stateB.y - stateB.y;
		}
		return (float)Math.sqrt(dX * dX + dY * dY);
	}
	
	//get the unit vector between stateA & stateB
	public Point2D unitVector() {
		float dX = 0f, dY = 0f;
		float distance = distance();
		if (distance != 0 &&
			stateA != null && stateB != null) {
			dX = (stateB.x - stateA.x)/distance;
			dY = (stateB.y - stateB.y)/distance;
		}
		return new Point2D(dX, dY);
	}
}
