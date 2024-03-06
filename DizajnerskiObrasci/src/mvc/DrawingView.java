package mvc;

import javax.swing.JPanel;

import geometry.Point;
import geometry.Shape;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;

public class DrawingView extends JPanel {

	private DrawingModel model;
	
	public DrawingView() {
		setBackground(Color.WHITE);
	}
	
	
	public void paint(Graphics g) {
		super.paint(g);
		Iterator<Shape> it = model.getShapes().iterator();
		while(it.hasNext())
			it.next().draw(g);
	}
	
	public void setModel(DrawingModel model) {
		this.model = model;
	}
	
	
}
