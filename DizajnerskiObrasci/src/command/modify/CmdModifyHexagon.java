package command.modify;

import adapter.Adapter;
import command.Command;
import geometry.Rectangle;
import hexagon.Hexagon;

public class CmdModifyHexagon implements Command{

	private Adapter oldHexagon;
	private Adapter newHexagon;
	private Adapter original;
	
	public CmdModifyHexagon(Adapter oldHexagon,Adapter newHexagon) {
		super();
		this.oldHexagon = oldHexagon;
		this.newHexagon = newHexagon;
	}
	@Override
	public void execute() {
        original = oldHexagon.clone();
		
        oldHexagon.setX(newHexagon.getX());
        oldHexagon.setY(newHexagon.getY());
        oldHexagon.setRadius(newHexagon.getRadius());
        oldHexagon.setInnerColor(newHexagon.getInnerColor());
        oldHexagon.setEdgeColor(newHexagon.getEdgeColor());
        
        oldHexagon.setSelected(true);
		
	}

	@Override
	public void unexecute() {
		oldHexagon.setX((original).getX());
		oldHexagon.setY((original).getY());
		oldHexagon.setRadius(((Adapter) original).getRadius());
		oldHexagon.setInnerColor(((Adapter) original).getInnerColor());
		oldHexagon.setEdgeColor(((Adapter) original).getEdgeColor());
        
		oldHexagon.setSelected(original.isSelected());
		
	}
	
	public Adapter getOldHexagon() {
		return oldHexagon;
	}
	public void setOldHexagon(Adapter oldHexagon) {
		this.oldHexagon = oldHexagon;
	}
	public Adapter getNewHexagon() {
		return newHexagon;
	}
	public void setNewHexagon(Adapter newHexagon) {
		this.newHexagon = newHexagon;
	}
	public Adapter getOriginal() {
		return original;
	}
	public void setOriginal(Adapter original) {
		this.original = original;
	}
	
	
}
