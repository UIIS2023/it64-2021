package command;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdDelete implements Command {

	private DrawingModel model;
	private ArrayList<Shape> shapes;
	private HashMap<Integer, Shape> oldState = new HashMap<Integer, Shape>();
	
	public CmdDelete(DrawingModel model, ArrayList<Shape> shapes) {
		this.model = model;
		this.shapes = shapes;
	}
	
	@Override
	public void execute() {
		for (Shape shape : shapes) {
			oldState.put(model.getIndex(shape), shape);
		}
		
		model.removeSelected();
	}

	@Override
	public void unexecute() {
		Set<Integer> keys = oldState.keySet();
		for (Integer key : keys) {
			Shape currentShape = oldState.get(key); 
			model.addShapeOnIndex(currentShape, key);
		}
	}
	
	public HashMap<Integer, Shape> getDeleteState() {
		return oldState;
	}

}
