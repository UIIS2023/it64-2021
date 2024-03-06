package adapter;

import java.awt.Color;
import java.awt.Graphics;

import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import geometry.SurfaceShape;
import hexagon.Hexagon;

public class Adapter extends SurfaceShape {

	private Hexagon externalHexagon;
	
	public Adapter(int x, int y, int radius, Color edgeColor, Color innerColor) {
		this.externalHexagon = new Hexagon(x, y, radius);
		this.externalHexagon.setBorderColor(edgeColor);
		this.externalHexagon.setAreaColor(innerColor);
	}
	
	@Override
	public void moveBy(int byX, int byY) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int compareTo(Object o) {
		return 0;
	}

	@Override
	public boolean contains(int x, int y) {
		return this.externalHexagon.doesContain(x, y);
	}

	@Override
	public void draw(Graphics g) {
		this.externalHexagon.paint(g);
	}
	
	public int getX() {
		return this.externalHexagon.getX();
	}
	
	public int getY() {
		return this.externalHexagon.getY();
	}
	
	public int getRadius() {
		return this.externalHexagon.getR();
	}
	
	@Override
	public Color getEdgeColor() {
		return this.externalHexagon.getBorderColor();
	}
	
	@Override
	public Color getInnerColor() {
		return this.externalHexagon.getAreaColor();
	}
	
	@Override
	public void setSelected(boolean selected) {
		super.setSelected(selected);
		this.externalHexagon.setSelected(selected);
	}
	
	@Override
	public boolean isSelected() {
		return super.isSelected();
	}

	@Override
	public void fill(Graphics g) {
		
		
	}

	public void setX(int x) {
	  this.externalHexagon.setX(x);
	}

	public void setY(int y) {
		this.externalHexagon.setY(y);
		
	}

	public void setRadius(int radius) {
		this.externalHexagon.setR(radius);
		
	}
	
	@Override
	public void setEdgeColor(Color edgeColor) {
		this.externalHexagon.setBorderColor(edgeColor);
	}
	
	@Override
	public void setInnerColor(Color innerColor) {
		this.externalHexagon.setAreaColor(innerColor);
	};
	
	public Adapter clone(){
		Adapter hexagon = new Adapter(this.getX(), this.getY(), this.getRadius(), getEdgeColor(), getInnerColor());
		return hexagon;
		
	}
		

}
