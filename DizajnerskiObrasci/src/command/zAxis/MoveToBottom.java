package command.zAxis;

import command.Command;
import geometry.Shape;
import mvc.DrawingModel;

public class MoveToBottom implements Command {

	private int currentIndexOfSelectedShape;
	private DrawingModel model;
	public int getIndex() {
		return currentIndexOfSelectedShape;
	}
	public MoveToBottom(int index, DrawingModel model) {
		this.currentIndexOfSelectedShape = index;
		this.model = model;
	}
	
	@Override
	public void execute() {
		Shape temp = model.getShape(currentIndexOfSelectedShape);
		model.remove(temp);
		model.addShapeOnIndex(temp, 0);
	}

	@Override
	public void unexecute() {
		Shape temp = model.getShape(0);
		model.remove(temp);
		model.addShapeOnIndex(temp, currentIndexOfSelectedShape);
	}

}
