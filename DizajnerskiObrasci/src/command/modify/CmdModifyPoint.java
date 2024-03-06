package command.modify;

import command.Command;
import geometry.Point;

public class CmdModifyPoint implements Command {

	private Point original;
	private Point oldState;
	private Point newState;
	
	public CmdModifyPoint(Point oldState, Point newState) {
		this.oldState = oldState;
		this.newState = newState;
	}
	
	@Override
	public void execute() {
		original = oldState.clone();
		
		oldState.setX(newState.getX());
		oldState.setY(newState.getY());
		oldState.setEdgeColor(newState.getEdgeColor());
		oldState.setSelected(true);
	}

	@Override
	public void unexecute() {
		oldState.setX(original.getX());
		oldState.setY(original.getY());
		oldState.setEdgeColor(original.getEdgeColor());
		oldState.setSelected(original.isSelected());
	}

	public Point getOldState() {
		return oldState;
	}

	public void setOldState(Point oldState) {
		this.oldState = oldState;
	}

	public Point getNewState() {
		return newState;
	}

	public void setNewState(Point newState) {
		this.newState = newState;
	}

	public Point getOriginal() {
		return original;
	}

	public void setOriginal(Point original) {
		this.original = original;
	}
	
 
}
