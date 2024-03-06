package command.modify;

import command.Command;
import geometry.Rectangle;

public class CmdModifyRectangle implements Command {

	private Rectangle oldRectangle;
	private Rectangle newRectangle;
	private Rectangle original;
	
	public CmdModifyRectangle(Rectangle oldRectangle,Rectangle newRectangle) {
		super();
		this.oldRectangle = oldRectangle;
		this.newRectangle = newRectangle;
	}
	@Override
	public void execute() {
		original = oldRectangle.clone();
		
		oldRectangle.getUpperLeft().setX(newRectangle.getUpperLeft().getX());
		oldRectangle.getUpperLeft().setY(newRectangle.getUpperLeft().getY());
		oldRectangle.setWidth(newRectangle.getWidth());
		oldRectangle.setHeight(newRectangle.getHeight());
		oldRectangle.setEdgeColor(newRectangle.getEdgeColor());
		oldRectangle.setInnerColor(newRectangle.getInnerColor());
		
		oldRectangle.setSelected(true);
	}

	@Override
	public void unexecute() {
		oldRectangle.getUpperLeft().setX(original.getUpperLeft().getX());
		oldRectangle.getUpperLeft().setY(original.getUpperLeft().getY());
		oldRectangle.setWidth(original.getWidth());
		oldRectangle.setHeight(original.getHeight());
		oldRectangle.setEdgeColor(original.getEdgeColor());
		oldRectangle.setInnerColor(original.getInnerColor());
		
		oldRectangle.setSelected(original.isSelected());
		
	}
	public Rectangle getOldRectangle() {
		return oldRectangle;
	}
	public void setOldRectangle(Rectangle oldRectangle) {
		this.oldRectangle = oldRectangle;
	}
	public Rectangle getNewRectangle() {
		return newRectangle;
	}
	public void setNewRectangle(Rectangle newRectangle) {
		this.newRectangle = newRectangle;
	}
	public Rectangle getOriginal() {
		return original;
	}
	public void setOriginal(Rectangle original) {
		this.original = original;
	}
	

}
