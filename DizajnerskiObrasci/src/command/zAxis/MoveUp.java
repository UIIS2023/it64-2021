 package command.zAxis;

import command.Command;
import mvc.DrawingModel;

public class MoveUp implements Command {

	private int currentIndexOfSelectedShape;
	private DrawingModel model;
	
	public int getIndex() {
		return currentIndexOfSelectedShape;
	}
	public MoveUp(int index, DrawingModel model) {
		this.currentIndexOfSelectedShape = index;
		this.model = model;
	}
	
	@Override
	public void execute() {
		model.changePositions(currentIndexOfSelectedShape, currentIndexOfSelectedShape + 1);
	}

	@Override
	public void unexecute() {
		model.changePositions(currentIndexOfSelectedShape, currentIndexOfSelectedShape + 1);
		
	}

}
