package geometry;

import java.awt.Color;
import java.awt.Graphics;

public class Rectangle extends SurfaceShape {

	private Point upperLeft;
	private int width;
	private int height;
	
	// Konstruktori 
	
	public Rectangle () {
		
	}
	public Rectangle (Point upperLeft, int width, int height) {
		this.upperLeft = upperLeft;
		this.height = height;
		this.width = width;
	}

	public Rectangle (Point upperLeft, int width, int height, boolean selected) {
		this (upperLeft, width, height);
		setSelected(selected);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color) {
		this(upperLeftPoint, width, height, selected);
		this.setEdgeColor(color);
	}
	
	public Rectangle(Point upperLeftPoint, int width, int height, boolean selected, Color color, Color innerColor) {
		this(upperLeftPoint, width, height, selected, color);
		this.setInnerColor(innerColor);
	}
	
	public boolean contains(int x, int y) {
		return (upperLeft.getX() < x && upperLeft.getX() + width > x && upperLeft.getY() < y
				&& upperLeft.getY() + height > y);
	}
	public boolean contains(Point p) {
		return upperLeft.getX() < p.getX() && upperLeft.getX() + width > p.getX()
				&& upperLeft.getY() < p.getY() && upperLeft.getY() + height > p.getY();
	}
	// Povrsina pravougaonika
	public int area() {
		return width * height;
	}
	// Obim pravougaonika 
	
	public int circumference() {
		return 2*width + 2*height;
	}
	
	// Metode pristupa
	public Point getUpperLeft() {
		return upperLeft;
	}
	public void setUpperLeft(Point upperLeft) {
		this.upperLeft = upperLeft;
	}
	
	public int getWidth() {
		return width;
	}
	public void setWidth(int width) {
		this.width = width;
	}
	
	public int getHeight() {
		return height;
	}
	public void setHeight(int height) {
		this.height = height;
	}
	

	public boolean equals(Object obj) {
		if (obj instanceof Rectangle) {
			Rectangle pomocni = (Rectangle) obj;
			if (this.upperLeft.equals(pomocni.getUpperLeft()) && this.width == pomocni.width && this.height == pomocni.height) {
				return true;
			} else {
				return false;
			}
		}else {
			return false;
		}
	}
	public String toString() {
		return "Upper left point: " + upperLeft + " ,width= " + width + " ,height= " + height;
	}
	@Override
	public void draw(Graphics g) {
		g.setColor(getEdgeColor());
		g.drawRect(upperLeft.getX(), upperLeft.getY(), width, height);
		
		if(isSelected()) {
			g.setColor(Color.BLUE);
			g.drawRect(upperLeft.getX()-2, upperLeft.getY()-2, 4, 4);
			g.drawRect(upperLeft.getX()+ width -2, upperLeft.getY()-2, 4, 4);
			g.drawRect(upperLeft.getX()-2, upperLeft.getY()+height-2, 4, 4);
			g.drawRect(upperLeft.getX()+width-2, upperLeft.getY()+height-2, 4, 4);
		}
		
		this.fill(g);
		
	}
	
	@Override
	public void fill(Graphics g) {
		g.setColor(getInnerColor());
		g.fillRect(this.upperLeft.getX() + 1, this.upperLeft.getY() + 1, this.width - 1, this.height - 1);
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		upperLeft.moveBy(byX, byY);
	}
	
	@Override
	public int compareTo(Object o) {
		if (o instanceof Rectangle) {
			return (int) (this.area() - ((Rectangle) o).area());
		}
		return 0;
	}
	public Rectangle clone(){
		Rectangle rectangle = new Rectangle();
		Point point = new Point();
		point.setX(this.getUpperLeft().getX());
		point.setY(this.getUpperLeft().getY());
		
		rectangle.setUpperLeft(point);
		rectangle.setWidth(this.getWidth());
		rectangle.setHeight(this.getHeight());
		rectangle.setEdgeColor(this.getEdgeColor());
		rectangle.setInnerColor(this.getInnerColor());
		
		return rectangle;
		
		}
}
