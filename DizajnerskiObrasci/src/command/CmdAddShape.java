package command;

import geometry.Shape;
import mvc.DrawingModel;

public class CmdAddShape implements Command {

	private DrawingModel model;
	private Shape shape;
	
	public CmdAddShape(DrawingModel model, Shape shape) {
		this.model = model;
		this.shape = shape;
	}
	
	@Override
	public void execute() {
		model.addShape(shape);
	}

	@Override
	public void unexecute() {
		model.remove(shape);
		
	}
	
	public Shape getShape() {
		return shape;
	}

}
