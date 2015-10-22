import java.awt.Point;

//class to represent a morphable points
//for now, let it only be able to represent 2 states: stateA & stateB
public class MorphablePoint {
	public Point stateA;
	public Point stateB;
	
	public MorphablePoint() {}
	public MorphablePoint(Point stateA, Point stateB) {
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
	public Point transitionPoint(int maxSteps, int step, boolean toStateA) {
		float xUnit = (stateA.x - stateB.x)/(maxSteps + 1);
		float yUnit = (stateA.y - stateB.y)/(maxSteps + 1);
		int direction = toStateA ? 1 : -1;
		Point originState = toStateA ? stateB : stateA;
		
		int tX = Math.round(originState.x + step * direction * xUnit);
		int tY = Math.round(originState.y + step * direction * yUnit);
		
		return new Point(tX, tY);
	}
	
	public String toString() {
		return 
			"[("  + (stateA != null 
				? Integer.toString(stateA.x) + ", " + Integer.toString(stateA.y) : "NULL") +
			"),(" + (stateB != null 
				? Integer.toString(stateB.x) + ", " + Integer.toString(stateB.y) : "NULL") +
			")]";
	}	
}
