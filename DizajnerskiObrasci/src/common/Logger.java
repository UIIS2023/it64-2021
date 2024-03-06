package common;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import adapter.Adapter;
import command.CmdAddShape;
import command.CmdDeselect;
import command.Command;
import command.modify.CmdModifyCircle;
import command.modify.CmdModifyDonut;
import command.modify.CmdModifyHexagon;
import command.modify.CmdModifyLine;
import command.modify.CmdModifyPoint;
import command.modify.CmdModifyRectangle;
import command.zAxis.MoveDown;
import command.zAxis.MoveToBottom;
import command.zAxis.MoveToTop;
import command.zAxis.MoveUp;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import hexagon.Hexagon;
import mvc.DrawingModel;

public class Logger {
	
	public String createLogForAddPoint(CmdAddShape cmd) {
		Point point = (Point)cmd.getShape();
		String commandText = "added_point_x,y,color_";
		commandText = commandText + point.getX() + ",";
		commandText = commandText + point.getY() + ",";
		commandText = commandText + point.getEdgeColor().getRGB();
		
		return commandText;
	}
	
	public String createLogForAddCircle(CmdAddShape cmd) {
		Circle circle = (Circle)cmd.getShape();
		String commandText = "added_circle_x,y,radius,edgeColor,innerColor_";
		commandText = commandText + circle.getCenter().getX() + ",";
		commandText = commandText + circle.getCenter().getY() + ",";
		commandText = commandText + circle.getRadius() + ",";
		commandText = commandText + circle.getEdgeColor().getRGB() + ",";
		commandText = commandText + circle.getInnerColor().getRGB();
		
		return commandText;
	}
	public String createLogForAddLine(CmdAddShape cmd) {
		Line line =(Line)cmd.getShape();
		String commandText = "added_line_startPointX,startPointY,endPointX,endPointY,edgecolor_";
		commandText = commandText + line.getStartPoint().getX() + ",";
		commandText = commandText + line.getStartPoint().getY() + ",";
		commandText = commandText + line.getEndPoint().getX() + ",";
		commandText = commandText + line.getEndPoint().getY() + ",";
		commandText = commandText + line.getEdgeColor().getRGB();
		
		return commandText;
	}
	public String createLogForAddRectangle(CmdAddShape cmd) {
		Rectangle rectangle = (Rectangle)cmd.getShape();
		String commandText = "added_rectangle_upperLeftPointX,upperLeftPointY,width,length,innerColor,edgeColor_";
		commandText = commandText + rectangle.getUpperLeft().getX() + ",";
		commandText = commandText + rectangle.getUpperLeft().getY() + ",";
		commandText = commandText + rectangle.getWidth() + ",";
		commandText = commandText + rectangle.getHeight()+ ",";
		commandText = commandText + rectangle.getInnerColor().getRGB()+ ",";
		commandText = commandText + rectangle.getEdgeColor().getRGB();
		return commandText;
	}
	public String createLogForAddDonut(CmdAddShape cmd) {
		Donut donut = (Donut)cmd.getShape();
		String commandText = "added_donut_centerPointX,centerPointY, radius, innerRadius, edgeColor, innerColor_";
		commandText = commandText + donut.getCenter().getX() + ",";
		commandText = commandText + donut.getCenter().getY() + ",";
		commandText = commandText + donut.getRadius() + ",";
		commandText = commandText + donut.getInnerRadius() + ",";
		commandText = commandText + donut.getEdgeColor().getRGB() + ",";
		commandText = commandText + donut.getInnerColor().getRGB();
		
		return commandText;
	}
	public String createLogForAddHexagon(CmdAddShape cmd) {
		Adapter hexagon = (Adapter)cmd.getShape();
		String commandText = "added_hexagon_R, X, Y, areaColor, borderColor_";
		commandText = commandText + hexagon.getRadius() + ",";
		commandText = commandText + hexagon.getX() + ",";
		commandText = commandText + hexagon.getY() + ",";
		commandText = commandText + hexagon.getInnerColor().getRGB() + ",";
		commandText = commandText + hexagon.getEdgeColor().getRGB();
		
		return commandText;
	}
	public String createLogForDeselect(ArrayList<Integer> indices) {
		String commandText = "deselect_at_index_";
		
		for (Integer index : indices) {	
			commandText = commandText + index + ":";
		}
		
		commandText = commandText.substring(0, commandText.length() - 1);
		return commandText;
	}
	public String createLogForDelete(HashMap<Integer, Shape> oldState) {
		String commandText = "delete_";
		
		Set<Integer> keys = oldState.keySet();
		for (Integer key : keys) {
			Shape currentShape = oldState.get(key); 
			commandText = commandText + (currentShape.getClass().getSimpleName().equals("Adapter") ? "Hexagon" : currentShape.getClass().getSimpleName()) + "-" + "index:" + key + "_";  
		}
		return commandText;
	}
	public String createLogForSelect(Shape shape, DrawingModel model) {
		String commandText = "select_";
		commandText = commandText + (shape.getClass().getSimpleName().equals("Adapter") ? "Hexagon" : shape.getClass().getSimpleName()) + "_on_index_" + model.getIndex(shape);
		return commandText;
	}
	public String createLogForModifyPoint(CmdModifyPoint cmd) { //command/modify da se poklapa
		String commandText = "modifyedPoint_oldState_x,y,color_@newState_x,y,color_";
		commandText = commandText + cmd.getOriginal().getX()  + ",";
		commandText = commandText + cmd.getOriginal().getY() + ",";
		commandText = commandText + cmd.getOriginal().getEdgeColor().getRGB() + "@";
		
		commandText = commandText + cmd.getNewState().getX()  + ",";
		commandText = commandText + cmd.getNewState().getY() + ",";
		commandText = commandText + cmd.getNewState().getEdgeColor().getRGB();
		
		return commandText;
	}
	public String createLogForModifyLine(CmdModifyLine cmd) {
		//
		String commandText = "modifyedLine_oldLine_startPointX,startPointY,endPointX,endPointY,edgecolor_@newLine_startPointX,startPointY,endPointX,endPointY,edgecolor_";
		commandText = commandText + cmd.getOriginal().getStartPoint().getX() + ",";
		commandText = commandText + cmd.getOriginal().getStartPoint().getY() + ",";
		commandText = commandText + cmd.getOriginal().getEndPoint().getX() + ",";
		commandText = commandText + cmd.getOriginal().getEndPoint().getY() + ",";
		commandText = commandText + cmd.getOriginal().getEdgeColor().getRGB() + "@";
		
		commandText = commandText + cmd.getNewLine().getStartPoint().getX() + ",";
		commandText = commandText + cmd.getNewLine().getStartPoint().getY() + ",";
		commandText = commandText + cmd.getNewLine().getEndPoint().getX() + ",";
		commandText = commandText + cmd.getNewLine().getEndPoint().getY() + ",";
		commandText = commandText + cmd.getNewLine().getEdgeColor().getRGB();
		
		return commandText;
	}
	public String createLogForModifyCircle(CmdModifyCircle cmd) {
		//
		String commandText = "modifyedCircle_oldCircle_x,y,radius,edgeColor,innerColor_@newCircle_x,y,radius,edgeColor,innerColor_";
		commandText = commandText + cmd.getOriginal().getCenter().getX() + ",";
		commandText = commandText + cmd.getOriginal().getCenter().getY() + ",";
		commandText = commandText + cmd.getOriginal().getRadius() + ",";
		commandText = commandText + cmd.getOriginal().getEdgeColor().getRGB() + ",";
		commandText = commandText + cmd.getOriginal().getInnerColor().getRGB() + "@";
		
		commandText = commandText + cmd.getNewCircle().getCenter().getX() + ",";
		commandText = commandText + cmd.getNewCircle().getCenter().getY() + ",";
		commandText = commandText + cmd.getNewCircle().getRadius() + ",";
		commandText = commandText + cmd.getNewCircle().getEdgeColor().getRGB() + ",";
		commandText = commandText + cmd.getNewCircle().getInnerColor().getRGB();
		
		return commandText;
	}
	public String createLogForModifyRectangle(CmdModifyRectangle cmd) {
		//
		String commandText = "modifyedRectangle_oldRectangle_upperLeftPointX,upperLeftPointY,width,length,edgeColor,InnerColor_@newRectangle_upperLeftPointX,upperLeftPointY,width,length,edgeColor,InnerColor_";
		commandText = commandText + cmd.getOriginal().getUpperLeft().getX() + ",";
		commandText = commandText + cmd.getOriginal().getUpperLeft().getY() + ",";
		commandText = commandText + cmd.getOriginal().getWidth() + ",";
		commandText = commandText + cmd.getOriginal().getHeight()+ ",";
		commandText = commandText + cmd.getOriginal().getInnerColor().getRGB()+ ",";
		commandText = commandText + cmd.getOriginal().getEdgeColor().getRGB() + "@";
		
		commandText = commandText + cmd.getNewRectangle().getUpperLeft().getX() + ",";
		commandText = commandText + cmd.getNewRectangle().getUpperLeft().getY() + ",";
		commandText = commandText + cmd.getNewRectangle().getWidth() + ",";
		commandText = commandText + cmd.getNewRectangle().getHeight()+ ",";
		commandText = commandText + cmd.getNewRectangle().getInnerColor().getRGB()+ ",";
		commandText = commandText + cmd.getNewRectangle().getEdgeColor().getRGB();
		
		return commandText;
	}
	public String createLogForModifyDonut(CmdModifyDonut cmd) {
		//
		String commandText = "modifyedDonut_oldDonut_centerPointX,centerPointY, radius, innerRadius, edgeColor, innerColor_@newDonut_centerPointX,centerPointY, radius, innerRadius, edgeColor, innerColor_";
		commandText = commandText + cmd.getOriginal().getCenter().getX() + ",";
		commandText = commandText + cmd.getOriginal().getCenter().getY() + ",";
		commandText = commandText + cmd.getOriginal().getRadius() + ",";
		commandText = commandText + cmd.getOriginal().getInnerRadius() + ",";
		commandText = commandText + cmd.getOriginal().getEdgeColor().getRGB() + ",";
		commandText = commandText + cmd.getOriginal().getInnerColor().getRGB() + "@";
		
		commandText = commandText + cmd.getNewDonut().getCenter().getX() + ",";
		commandText = commandText + cmd.getNewDonut().getCenter().getY() + ",";
		commandText = commandText + cmd.getNewDonut().getRadius() + ",";
		commandText = commandText + cmd.getNewDonut().getInnerRadius() + ",";
		commandText = commandText + cmd.getNewDonut().getEdgeColor().getRGB() + ",";
		commandText = commandText + cmd.getNewDonut().getInnerColor().getRGB();
		
		return commandText;
	}
	
	public String createLogForModifyHexagon(CmdModifyHexagon cmd) {
		String commandText = "modifyedHexagon_oldHexagon_R, X, Y, areaColor, borderColor_@newHexagon_R, X, Y, areaColor, borderColor_";
		commandText = commandText + cmd.getOriginal().getX() + ",";
		commandText = commandText + cmd.getOriginal().getY() + ",";
		commandText = commandText + cmd.getOriginal().getRadius() + ",";
		commandText = commandText + cmd.getOriginal().getEdgeColor().getRGB() + ",";
		commandText = commandText + cmd.getOriginal().getInnerColor().getRGB() + "@";
		
		commandText = commandText + cmd.getNewHexagon().getX() + ",";
		commandText = commandText + cmd.getNewHexagon().getY() + ",";
		commandText = commandText + cmd.getNewHexagon().getRadius() + ",";
		commandText = commandText + cmd.getNewHexagon().getEdgeColor().getRGB() + ",";
		commandText = commandText + cmd.getNewHexagon().getInnerColor().getRGB();
		
		return commandText;
	}

	public String createLogForUp(MoveUp command) {
		return "moved_up_from_index_" + command.getIndex(); 
	}
	
	public String createLogForDown(MoveDown command) {
		return "moved_down_from_index_" + command.getIndex(); 
	}
	
	public String createLogForTop(MoveToTop command) {
		return "moved_to_top_from_index_" + command.getIndex(); 
	}
	
	public String createLogForBottom(MoveToBottom command) {
		return "moved_to_bottom_from_index_" + command.getIndex(); 
	}
	
}
