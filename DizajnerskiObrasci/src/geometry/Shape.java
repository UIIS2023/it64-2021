package geometry;

import java.awt.Color;
import java.awt.Graphics;
import java.io.Serializable;

public abstract class Shape implements Moveable, Comparable, Serializable {
	private boolean selected;
	private Color edgeColor;
	
	public abstract boolean contains(int x,int y);
	
	public abstract void draw(Graphics g); 
	
	public Shape() {
		
	}
	
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	public Shape(boolean selected) {
		this.selected = selected;
	}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}
	
	
	

}
