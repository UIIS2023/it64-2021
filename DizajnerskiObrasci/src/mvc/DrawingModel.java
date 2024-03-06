package mvc;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import geometry.Point;
import geometry.Shape;

public class DrawingModel implements Serializable {

	private ArrayList<Shape> shapes = new ArrayList<Shape>();
	
	public void addShape(Shape shape) {
		shapes.add(shape);
	}
	
	public void addShapeOnIndex(Shape shape, int index) {
		shapes.add(index, shape);
	}
	
	public Shape getShape(int index) {
		return shapes.get(index);
	}
	
	public void setShape(int index, Shape shape) {
		shapes.set(index, shape);
	}
	
	
	public void removeSelected() {
		shapes.removeIf(shape -> shape.isSelected());
	}
	
	public void deselect() {
		shapes.forEach(shape -> shape.setSelected(false));
	}

	public int getSelected() {
		for (int i = shapes.size()-1; i >= 0; i--) {
			if (shapes.get(i).isSelected()) {
				return i;
			}
		}
		return -1;
	}
	
	public boolean isEmpty() {
		return shapes.isEmpty();
	}
	
	public ArrayList<Shape> getShapes() {
		return shapes;
	}
	
	public void remove(Shape shape) {
		this.shapes.remove(shape);
	}
	
	public int getIndex(Shape shape) {
		return this.shapes.indexOf(shape);
	}
	
	public void changePositions(int firstIndex, int secondIndex) {
		Shape firstShape = shapes.get(firstIndex);
		Shape temp = firstShape;
		
		Shape secondShape = shapes.get(secondIndex);
		shapes.set(firstIndex, secondShape);
		shapes.set(secondIndex, temp);
	}

	public Shape getOnIndex(int index) {
		return shapes.get(index);
	}

}
