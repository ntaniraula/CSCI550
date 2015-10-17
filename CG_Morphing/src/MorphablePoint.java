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
	//toStateA : the direction of the transition.
	//           true if it's going from stateB to stateA, false otherwise.
	public Point2D unitVector(boolean toStateA) {
		float direction = toStateA ? -1.0f : 1f;
		float uX = 0f, uY = 0f;
		float distance = distance();
		if (distance != 0 &&
			stateA != null && stateB != null) {
			uX = direction * ((stateB.x - stateA.x)/distance);
			uY = direction * ((stateB.y - stateA.y)/distance);
		}
		return new Point2D(uX, uY);
	}
	
	//get the transition point between stateA & stateB
	//maxSteps : the maximum number of transition steps.
	//step     : the current step no. for the requested transition point.
	//           if transition is going from stateB to stateA:
	//              step 0            == exactly at stateB
	//              step maxSteps + 1 == exactly at stateA
	//toStateA : the direction of the transition.
	//           true if it's going from stateB to stateA, false otherwise.
	public Point2D transitionPoint(int maxSteps, int step, boolean toStateA) {
		float unitDistance = (maxSteps + 1)/distance();
		Point2D originState = toStateA ? stateA : stateB;
		
		float tX = originState.x + step * unitVector(toStateA).x * unitDistance;
		float tY = originState.y + step * unitVector(toStateA).y * unitDistance;
		return new Point2D(tX, tY);
	}
}
