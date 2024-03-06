package command.modify;

import command.Command;
import geometry.Line;

public class CmdModifyLine implements Command {

	private Line oldLine;
	private Line newLine;
	private Line original;
	
	public CmdModifyLine(Line oldLine, Line newLine) {
		super();
		this.oldLine = oldLine;
		this.newLine = newLine;
	}

	@Override
	public void execute() {
		original = oldLine.clone();
		
		oldLine.getStartPoint().setX(newLine.getStartPoint().getX());
		oldLine.getStartPoint().setY(newLine.getStartPoint().getY());
		oldLine.getEndPoint().setX(newLine.getEndPoint().getX());
		oldLine.getEndPoint().setY(newLine.getEndPoint().getY());
		oldLine.setEdgeColor(newLine.getEdgeColor());
		oldLine.setSelected(true);
	}

	@Override
	public void unexecute() {
		oldLine.getStartPoint().setX(original.getStartPoint().getX());
		oldLine.getStartPoint().setY(original.getStartPoint().getY());
		oldLine.getEndPoint().setX(original.getEndPoint().getX());
		oldLine.getEndPoint().setY(original.getEndPoint().getY());
		oldLine.setEdgeColor(original.getEdgeColor());
		oldLine.setSelected(original.isSelected());
		
	}

	public Line getOldLine() {
		return oldLine;
	}

	public void setOldLine(Line oldLine) {
		this.oldLine = oldLine;
	}

	public Line getNewLine() {
		return newLine;
	}

	public void setNewLine(Line newLine) {
		this.newLine = newLine;
	}

	public Line getOriginal() {
		return original;
	}

	public void setOriginal(Line original) {
		this.original = original;
	}
	

}
