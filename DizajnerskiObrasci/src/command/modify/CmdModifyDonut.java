package command.modify;

import command.Command;
import geometry.Donut;

public class CmdModifyDonut implements Command{

	private Donut oldDonut;
	private Donut newDonut;
	private Donut original;
	
	public CmdModifyDonut(Donut oldDonut,Donut newDonut) {
		super();
		this.oldDonut = oldDonut;
		this.newDonut = newDonut;
	}
	@Override
	public void execute()  {
        original = oldDonut.clone();
		
		oldDonut.getCenter().setX(newDonut.getCenter().getX());
		oldDonut.getCenter().setY(newDonut.getCenter().getY());
		oldDonut.setInnerRadius(newDonut.getInnerRadius());
		try {
			oldDonut.setRadius(newDonut.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldDonut.setEdgeColor(newDonut.getEdgeColor());
		oldDonut.setInnerColor(newDonut.getInnerColor());
		
		oldDonut.setSelected(true);
		
	}

	@Override
	public void unexecute()  {
		oldDonut.getCenter().setX(original.getCenter().getX());
		oldDonut.getCenter().setY(original.getCenter().getY());
		oldDonut.setInnerRadius(original.getInnerRadius());
		try {
			oldDonut.setRadius(original.getRadius());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		oldDonut.setEdgeColor(original.getEdgeColor());
		oldDonut.setInnerColor(original.getInnerColor());
		
		newDonut.setSelected(original.isSelected());
		
	}
	public Donut getOldDonut() {
		return oldDonut;
	}
	public void setOldDonut(Donut oldDonut) {
		this.oldDonut = oldDonut;
	}
	public Donut getNewDonut() {
		return newDonut;
	}
	public void setNewDonut(Donut newDonut) {
		this.newDonut = newDonut;
	}
	public Donut getOriginal() {
		return original;
	}
	public void setOriginal(Donut original) {
		this.original = original;
	}
	

}
