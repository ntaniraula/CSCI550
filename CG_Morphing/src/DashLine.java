import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Point;

public class DashLine 
{
	private Point start=new Point();
	private Point end=new Point();
	private int dashLength,gapLength;
	private int numDash,numGap;
	float distance;
	Dimension dash=new Dimension(),gap=new Dimension();
	
	DashLine(int dashLength, int gapLength)
	{		
		this.dashLength=dashLength;
		this.gapLength=gapLength;
		
	}
	public void draw(Graphics g,int x1,int y1,int x2,int y2,Color color)
	{
		
		start.x=x1;start.y=y1;end.x=x2;end.y=y2;	
		distance = (float) Math.sqrt((end.x - start.x)*(end.x - start.x) + (end.y - start.y) * (end.y - end.y));
		
		numDash=Math.round((float)(distance)/(dashLength + gapLength));
		numGap=numDash-1;
		dash.width = Math.round((float)(end.x-start.x)/(2*numDash-1));
		dash.height = Math.round((float)(end.y -start.y)/(2*numDash-1));
		gap.width = Math.round((float)(end.x-start.x)/(2*numDash-1));
		gap.height = Math.round((float)(end.y -start.y)/(2*numDash-1));
		
		g.setColor(color);
		for(int i=0;i<numGap;i++)
		{
			//g.drawLine(start.x + 2*i*dash.width, start.y + 2*i*dash.height, start.x +(2*i +1)*dash.width,start.y +(2*i +1)*dash.height);
			int xx2 = x1+dash.width;
			int yy2 = y1+dash.height;
			g.drawLine(x1,y1,xx2,yy2);
			x1 = xx2 + gap.width;
			y1 = yy2 + gap.height;
			

		}
		g.drawLine(x1, y1, end.x, end.y);
	}
}