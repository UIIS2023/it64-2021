package command.zAxis;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class MoveToTop implements Command {

	private int currentIndexOfSelectedShape;
	private DrawingModel model;
	public int getIndex() {
		return currentIndexOfSelectedShape;
	}
	public MoveToTop(int index, DrawingModel model) {
		this.currentIndexOfSelectedShape = index;
		this.model = model;
	}
	
	@Override
	public void execute() {
		Shape temp = model.getShape(currentIndexOfSelectedShape);
		model.remove(temp);
		model.addShape(temp);
	}

	@Override
	public void unexecute() {
		Shape temp = model.getShape(model.getShapes().size() - 1);
		model.remove(temp);
		model.addShapeOnIndex(temp, currentIndexOfSelectedShape);
		
	}

}
