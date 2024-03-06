package command;

import java.util.ArrayList;

import geometry.Shape;

public class CmdDeselect implements Command {

	private ArrayList<Shape> shapes;
	
	public CmdDeselect(ArrayList<Shape> shapes) {
		this.shapes = shapes;
	}
	
	@Override
	public void execute() {
		for (Shape shape : shapes) {
			shape.setSelected(false);
		}
		
	}

	@Override
	public void unexecute() {
		for (Shape shape : shapes) {
			shape.setSelected(true);
		}
		
	}

}
