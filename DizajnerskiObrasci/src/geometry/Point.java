package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Point extends Shape {

	 private int x;
	 private int y;
	
	 
	 public double distance(int x2, int y2) {
		 double dx= x-x2;
		 double dy = y - y2;
		 double d = Math.sqrt(dx*dx + dy*dy);
		 return d;
	 }
	 
	 public int getX() {
		 return x;
	 }
	 public void setX(int x) {
		 this.x=x;
	 }
	 public int getY() {
		 return this.y;
	 }
	 public void setY(int y) {
		 this.y=y;
	 }
	 
	 //konstruktori
	 
	 public Point() {
		 
	 }
	 public Point(int x, int y) {
		 this.x = x;
		 this.y = y;
	 }
	 public Point (int x,int y,boolean selected) {
		 
		 this(x,y);
		 setSelected(selected);
	 }
	 
	 public Point(int x, int y, boolean selected, Color color) {
		this(x, y, selected);
		this.setEdgeColor(color);
	}

	public String toString() {
		 
		 return "(" + x + ", " + y + ")";
	 }
	 public  boolean  contains (int x,int y){
		 return this.distance(x,y) <=2;
	 }
	 
	 public boolean equals (Object obj) {
		 if (obj instanceof Point)
		 {
			 Point help = (Point) obj;
			 
			 if (this.x == help.getX() && this.y == help.getY()) {
				 return  true;
			 }
			 else {
				 return false;
			 }
			 }
		 else {
			 return false;
		 }
		 }

	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawLine(x-2, y, x+2, y);
		g.drawLine(x, y-2, x, y+2);
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(x-2, y-2, 4, 4);
		}
	}


	@Override
	public void moveBy(int byX, int byY) {
		this.x = this.x + byX;
		this.y += byY;
		
	}

	@Override
	public int compareTo(Object obj) {
		if (obj instanceof Point) {
			return (int) (this.distance(0,0) - ((Point) obj).distance(0,0));
		}
		return 0;
	}
	
	public Point clone() {
		Point point = new Point();
		point.setX(this.x);
		point.setY(this.y);
		point.setEdgeColor(this.getEdgeColor());
		
		return point;
	}
	 
}
