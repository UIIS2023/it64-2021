package command.modify;

import command.Command;
import geometry.Circle;

public class CmdModifyCircle implements Command {

	private Circle oldCircle;
	private Circle original;
	private Circle newCircle;
	
	public CmdModifyCircle (Circle oldCircle,Circle newCircle) {
	 this.oldCircle = oldCircle;
	 this.newCircle = newCircle;
	}
	@Override
	public void execute() {
		original = oldCircle.clone();
		
		oldCircle.setCenter(newCircle.getCenter());
		try {
			oldCircle.setRadius(newCircle.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldCircle.setEdgeColor(newCircle.getEdgeColor());
		oldCircle.setInnerColor(newCircle.getInnerColor());
		
		oldCircle.setSelected(true);
		
		
	}

	@Override
	public void unexecute() {
		oldCircle.setCenter(original.getCenter());
		try {
			oldCircle.setRadius(original.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldCircle.setEdgeColor(original.getEdgeColor());
		oldCircle.setInnerColor(original.getInnerColor());
		
	}
	public Circle getOldCircle() {
		return oldCircle;
	}
	public void setOldCircle(Circle oldCircle) {
		this.oldCircle = oldCircle;
	}
	public Circle getNewCircle() {
		return newCircle;
	}
	public void setNewCircle(Circle newCircle) {
		this.newCircle = newCircle;
	}
	public Circle getOriginal() {
		return original;
	}
	public void setOriginal(Circle original) {
		this.original = original;
	}
	

}
