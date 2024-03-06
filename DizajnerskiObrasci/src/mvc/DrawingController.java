package mvc;

import java.awt.Color;
import java.awt.event.MouseEvent;
import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;
import javax.swing.JOptionPane;

import adapter.Adapter;
import command.CmdAddShape;
import command.CmdDelete;
import command.CmdDeselect;
import command.CmdSelect;
import command.Command;
import command.modify.CmdModifyLine;
import command.modify.CmdModifyCircle;
import command.modify.CmdModifyDonut;
import command.modify.CmdModifyHexagon;
import command.modify.CmdModifyPoint;
import command.modify.CmdModifyRectangle;
import command.zAxis.MoveDown;
import command.zAxis.MoveToBottom;
import command.zAxis.MoveToTop;
import command.zAxis.MoveUp;
import common.Logger;
import dialogs.DlgCircle;
import dialogs.DlgDonut;
import dialogs.DlgHexagon;
import dialogs.DlgLine;
import dialogs.DlgPoint;
import dialogs.DlgRectangle;
import geometry.Circle;
import geometry.Donut;
import geometry.Line;
import geometry.Point;
import geometry.Rectangle;
import geometry.Shape;
import observer.Observable;
import observer.Observer;
import read.ReadBinaryStrategy;
import read.ReadLogStrategy;
import read.ReadStrategyManager;
import save.BinaryStrategy;
import save.LogStrategy;
import save.StrategyManager;

public class DrawingController implements Observable {

	private DrawingModel model;
	private DrawingFrame frame;
	private final int OPERATION_DRAWING = 1;
	private final int OPERATION_EDIT_DELETE = 0;
	
	boolean lineWaitingForEndPoint = false;
	private Point startPoint;
	
	private ArrayList<Command> executedCommands = new ArrayList<Command>();
	private ArrayList<Command> redoList = new ArrayList<Command>();
	private ArrayList<Observer> observers = new ArrayList<Observer>();
	private StrategyManager strategyManager = new StrategyManager();
	private ReadStrategyManager readStrategyManager = new ReadStrategyManager();
	private List<String> fileLogs = new ArrayList<String>();
	
	private final Logger logger = new Logger();

	public void setModel(DrawingModel model) {
		this.model = model;
	}

	public void setFrame(DrawingFrame frame) {
		this.frame = frame;
	}
	
	public void drawShape(MouseEvent e, int activeOperation) {
		Point mouseClick = new Point(e.getX(), e.getY());
		
		if (activeOperation == OPERATION_EDIT_DELETE) {

			boolean anyShapeContainsPoint = false;
			
			for (int i = model.getShapes().size()-1; i >= 0; i--) {
				
				if (model.getShapes().get(i).contains(e.getX(), e.getY())) {
					anyShapeContainsPoint = true;
					
					if (model.getShapes().get(i).isSelected()) { // kliknuli smo na oblik, on sadrzi tacku, prethodno JE selektovan
						ArrayList<Shape> list = new ArrayList<Shape>();
						ArrayList<Integer> indices = new ArrayList<Integer>();
						list.add(model.getShapes().get(i));
						indices.add(model.getIndex(model.getShapes().get(i)));
						CmdDeselect cmd = new CmdDeselect(list);
						cmd.execute();
						executedCommands.add(cmd);
						String createdLog = logger.createLogForDeselect(indices);
						frame.addLog(createdLog);
						redoList.clear();
					} else { // kliknuli smo na oblik, on sadrzi tacku, ali prethodno NIJE selektovan
						CmdSelect cmd = new CmdSelect(model.getShapes().get(i));
						cmd.execute();
						executedCommands.add(cmd);
						redoList.clear();
						String createdLog = logger.createLogForSelect(model.getShapes().get(i), model);
						frame.addLog(createdLog);
					}
					
					break;
				}
				
			}
			
			if (!anyShapeContainsPoint) {
				ArrayList<Shape> selectedShapes = getSelectedShapes();
				ArrayList<Integer> indices = new ArrayList<Integer>();
				for (Shape s: selectedShapes ) {
					if (s.isSelected()) {
						indices.add(model.getIndex(s));
					}
				}
				
				// Ako smo kliknuli na belu povrsinu a nijedan oblik nije selektovan
				if (selectedShapes.size() > 0) {
					CmdDeselect cmd = new CmdDeselect(selectedShapes);
					cmd.execute();
					executedCommands.add(cmd);
					redoList.clear();
					notifyObservers();
					String createdLog = logger.createLogForDeselect(indices);
					frame.addLog(createdLog);
				}
			}
			
			frame.repaint();
			notifyObservers();
			return;
		}
		
		if (frame.getBtnShapePoint().isSelected()) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint(mouseClick);
			dlgPoint.setColors(frame.getEdgeColor());
			dlgPoint.setVisible(true);
			if(dlgPoint.getPoint() != null) {
				CmdAddShape command = new CmdAddShape(model, dlgPoint.getPoint());
				command.execute();
				executedCommands.add(command);
				redoList.clear();
				String createdLog = logger.createLogForAddPoint(command);
				frame.addLog(createdLog);
				notifyObservers();
			}
			
			frame.repaint();
			return;
			
		} else if (frame.getBtnShapeLine().isSelected()) {
			if(lineWaitingForEndPoint) {
				
				DlgLine dlgLine = new DlgLine();
				Line line = new Line(startPoint,mouseClick);
				dlgLine.setLine(line);
				dlgLine.setColors(frame.getEdgeColor());
				dlgLine.setVisible(true);
				
				if(dlgLine.getLine()!= null) {
					CmdAddShape command = new CmdAddShape(model, dlgLine.getLine());
					command.execute();
					executedCommands.add(command);
					redoList.clear();
					String createdLog = logger.createLogForAddLine(command);
					frame.addLog(createdLog);
					notifyObservers();
				}
					
				lineWaitingForEndPoint=false;
				frame.repaint();
				return;
			}
			startPoint = mouseClick;
			lineWaitingForEndPoint=true;
			frame.repaint();
			return;
			

		} else if (frame.getBtnShapeRectangle().isSelected()) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setPoint(mouseClick);
			dlgRectangle.setColors(frame.getEdgeColor(), frame.getInnerColor());
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				CmdAddShape command = new CmdAddShape(model, dlgRectangle.getRectangle());
				command.execute();
				executedCommands.add(command);
				redoList.clear();
				String createdLog = logger.createLogForAddRectangle(command);
				frame.addLog(createdLog);
				notifyObservers();
			}
			
			frame.repaint();
			return;
		} else if (frame.getBtnShapeCircle().isSelected()) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setPoint(mouseClick);
			dlgCircle.setColors(frame.getEdgeColor(), frame.getInnerColor());
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				CmdAddShape command = new CmdAddShape(model, dlgCircle.getCircle());
				command.execute();
				executedCommands.add(command);
				redoList.clear();
				String createdLog = logger.createLogForAddCircle(command);
				frame.addLog(createdLog);
				notifyObservers();
			}
			frame.repaint();
			return;
		} else if (frame.getBtnShapeDonut().isSelected()) {
			DlgDonut dlgDonut = new DlgDonut();
			dlgDonut.setPoint(mouseClick);
			dlgDonut.setColors(frame.getEdgeColor(), frame.getInnerColor());
			dlgDonut.setVisible(true);
			
			if(dlgDonut.getDonut() != null) {
				CmdAddShape command = new CmdAddShape(model, dlgDonut.getDonut());
				command.execute();
				executedCommands.add(command);
				redoList.clear();
				String createdLog = logger.createLogForAddDonut(command);
				frame.addLog(createdLog);
				notifyObservers();
			}
			frame.repaint();
			return;
		} else if (frame.getBtnShapeHexagon().isSelected()) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setPoint(mouseClick);
			dlgHexagon.setColors(frame.getEdgeColor(), frame.getInnerColor());
			dlgHexagon.setVisible(true);
			
			if(dlgHexagon.getHexagon() != null) {
				CmdAddShape command = new CmdAddShape(model, dlgHexagon.getHexagon());
				command.execute();
				executedCommands.add(command);
				redoList.clear();
				String createdLog = logger.createLogForAddHexagon(command);
				frame.addLog(createdLog);
				notifyObservers();
			}
			frame.repaint();
			return;
		}
		
		frame.repaint();
	}
	
	public void editShape() {
		int index = model.getSelected();
		if (index == -1) return;
		
		Shape shape = model.getShape(index);
		
		if (shape instanceof Point) {
			DlgPoint dlgPoint = new DlgPoint();
			dlgPoint.setPoint((Point)shape);
			dlgPoint.setVisible(true);
			
			if(dlgPoint.getPoint() != null) {
				CmdModifyPoint cmd = new CmdModifyPoint((Point)shape, dlgPoint.getPoint());
				cmd.execute();
				executedCommands.add(cmd);
				String createdLog = logger.createLogForModifyPoint(cmd);
				frame.addLog(createdLog);
				frame.repaint();
				notifyObservers();
			}
		} else if (shape instanceof Line) {
			DlgLine dlgLine = new DlgLine();
			dlgLine.setLine((Line)shape);
			dlgLine.setVisible(true);
			
			if(dlgLine.getLine() != null) {
				CmdModifyLine cmd = new CmdModifyLine((Line)shape, dlgLine.getLine());
				cmd.execute();
				executedCommands.add(cmd);
				String createdLog = logger.createLogForModifyLine(cmd);
				frame.addLog(createdLog);
				frame.repaint();
				notifyObservers();
			}
		} else if (shape instanceof Rectangle) {
			DlgRectangle dlgRectangle = new DlgRectangle();
			dlgRectangle.setRectangle((Rectangle)shape);
			dlgRectangle.setVisible(true);
			
			if(dlgRectangle.getRectangle() != null) {
				CmdModifyRectangle cmd = new CmdModifyRectangle((Rectangle)shape, dlgRectangle.getRectangle());
				cmd.execute();
				executedCommands.add(cmd);
				String createdLog = logger.createLogForModifyRectangle(cmd);
				frame.addLog(createdLog);
				frame.repaint();
				notifyObservers();
			}
		
		}else if (shape instanceof Donut) {
				DlgDonut dlgDonut = new DlgDonut();
				dlgDonut.setDonut((Donut)shape);
				dlgDonut.setVisible(true);
				
				if(dlgDonut.getDonut() != null) {
					CmdModifyDonut cmd = new CmdModifyDonut((Donut)shape, dlgDonut.getDonut());
					cmd.execute();
					executedCommands.add(cmd);
					String createdLog = logger.createLogForModifyDonut(cmd);
					frame.addLog(createdLog);
					frame.repaint();
					notifyObservers();
				}
		} else if (shape instanceof Circle) {
			DlgCircle dlgCircle = new DlgCircle();
			dlgCircle.setCircle((Circle)shape);
			dlgCircle.setVisible(true);
			
			if(dlgCircle.getCircle() != null) {
				CmdModifyCircle cmd = new CmdModifyCircle((Circle)shape, dlgCircle.getCircle());
				cmd.execute();
				executedCommands.add(cmd);
				String createdLog = logger.createLogForModifyCircle(cmd);
				frame.addLog(createdLog);
				frame.repaint();
				notifyObservers();
			}
		} else if (shape instanceof Adapter) {
			DlgHexagon dlgHexagon = new DlgHexagon();
			dlgHexagon.setHexagon((Adapter)shape);
			dlgHexagon.setVisible(true);
			
			if(dlgHexagon.getHexagon() != null) {
				CmdModifyHexagon cmd = new CmdModifyHexagon((Adapter)shape, dlgHexagon.getHexagon());
				cmd.execute();
				executedCommands.add(cmd);
				String createdLog = logger.createLogForModifyHexagon(cmd);
				frame.addLog(createdLog);
				frame.repaint();
				notifyObservers();
			}
		} 
	}
	
	public void deleteShape() {
		if (model.isEmpty()) return;
		if (JOptionPane.showConfirmDialog(null, "Do you really want to delete selected shape?", "Yes", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == 0) {
			CmdDelete cmd = new CmdDelete(model, getSelectedShapes());
			cmd.execute();
			executedCommands.add(cmd);
			String createdLog = logger.createLogForDelete(cmd.getDeleteState());
			frame.addLog(createdLog);
			redoList.clear();
		}
		
		frame.repaint();
		notifyObservers();
	}
	
	private ArrayList<Shape> getSelectedShapes() {
		ArrayList<Shape> selectedShapes = new ArrayList<Shape>();
		for (Shape shape : model.getShapes()) {
			if (shape.isSelected()) {
				selectedShapes.add(shape);
			}
		}
		
		return selectedShapes;
	}

	@Override
	public void addObserver(Observer observer) {
		observers.add(observer);
	}

	@Override
	public void removeObserver(Observer observer) {
		observers.remove(observer);
	}

	@Override
	public void notifyObservers() {
		int numberOfShapesOnSceen = model.getShapes().size();
		int numberOfSelectedShapes = getSelectedShapes().size();
		boolean isFirst = false;
		boolean isLast = false;
		
		if (numberOfSelectedShapes == 1) {
			Shape selectedShape = getSelectedShapes().get(0);
			int indexOfSelectedShape = model.getIndex(selectedShape);
			if (indexOfSelectedShape == 0) {
				isFirst = true;
			}
			
			if (indexOfSelectedShape == model.getShapes().size() - 1) {
				isLast = true;
			}
		} else {
			isFirst = true;
			isLast = true;
		}
		
		int numberOfElementsInUndoList = executedCommands.size();
		int numberOfElementsInRedoList = redoList.size();
		
		int numbeOfLogsForNext = fileLogs.size();
		
		for (int i = 0; i < observers.size(); i++) {
			Observer currentObserver = observers.get(i);
			currentObserver.update(numberOfShapesOnSceen, numberOfSelectedShapes, isFirst, isLast, numberOfElementsInUndoList, numberOfElementsInRedoList, numbeOfLogsForNext);
		}
		
	}
	
	public void moveUp() {
		Shape shape = getSelectedShapes().get(0);
		
		int currentIndex = model.getIndex(shape);
		MoveUp command = new MoveUp(currentIndex, model);
		command.execute();
		frame.repaint();
		executedCommands.add(command);
		String createdLog = logger.createLogForUp(command);
		frame.addLog(createdLog);
		notifyObservers();
	}

	public void moveDown() {
		Shape shape = getSelectedShapes().get(0);
		
		int currentIndex = model.getIndex(shape);
		MoveDown command = new MoveDown(currentIndex, model);
		command.execute();
		frame.repaint();
		executedCommands.add(command);
		String createdLog = logger.createLogForDown(command);
		frame.addLog(createdLog);
		notifyObservers();
		
	}

	public void moveToBottom() {
		Shape shape = getSelectedShapes().get(0);
		
		int currentIndex = model.getIndex(shape);
		MoveToBottom command = new MoveToBottom(currentIndex, model);
		command.execute();
		frame.repaint();
		executedCommands.add(command);
		String createdLog = logger.createLogForBottom(command);
		frame.addLog(createdLog);
		notifyObservers();
		
	}

	public void moveToTop() {
		Shape shape = getSelectedShapes().get(0);
		
		int currentIndex = model.getIndex(shape);
		MoveToTop command = new MoveToTop(currentIndex, model);
		command.execute();
		frame.repaint();
		executedCommands.add(command);
		String createdLog = logger.createLogForTop(command);
		frame.addLog(createdLog);
		notifyObservers();
		
	}

	public void undo() {
		int lastCommandIndex = executedCommands.size() - 1; 
		Command lastCommand = executedCommands.get(lastCommandIndex);
		lastCommand.unexecute();
		executedCommands.remove(lastCommandIndex);
		redoList.add(lastCommand);
		frame.addLog("Undo");
		frame.repaint();
		notifyObservers();
	}

	public void redo() {
		int lastCommandIndex = redoList.size() - 1; 
		Command lastCommand = redoList.get(lastCommandIndex);
		lastCommand.execute();
		redoList.remove(lastCommandIndex);
		executedCommands.add(lastCommand);
		frame.repaint();
		frame.addLog("Redo");
		notifyObservers();
		
	}

	public void saveBinary() {
		BinaryStrategy bs = new BinaryStrategy(model);
		strategyManager.setStrategy(bs);
		strategyManager.save();
	}

	public void saveLog() {
		LogStrategy ls = new LogStrategy();
		ls.setFrame(frame);
		strategyManager.setStrategy(ls);
		strategyManager.save();
	}

	public void readBinary() {
		ReadBinaryStrategy strategy = new ReadBinaryStrategy();
		readStrategyManager.setStrategy(strategy);
		List<Object> listOfShapes = readStrategyManager.read();
		model.getShapes().removeAll(model.getShapes());
		
		for (Object o : listOfShapes) {
			model.addShape((Shape)o);
		}
		executedCommands.removeAll(executedCommands);
		redoList.removeAll(redoList);
		frame.removeAllLogs();
		fileLogs.removeAll(fileLogs);
		frame.repaint();
		notifyObservers();
	}

	public void readLog() {
		ReadLogStrategy strategy = new ReadLogStrategy();
		readStrategyManager.setStrategy(strategy);
		List<Object> listOfLogs = readStrategyManager.read();
		
		fileLogs.removeAll(fileLogs);
		for (Object o : listOfLogs) {
			fileLogs.add((String)o);
		}
		
		model.getShapes().removeAll(model.getShapes());
		executedCommands.removeAll(executedCommands);
		redoList.removeAll(redoList);
		frame.removeAllLogs();
		frame.repaint();
		notifyObservers();
		
	}

	public void processNextLog() {
		String log = fileLogs.get(0);
		System.out.println(log);
		if (log.startsWith("added_point")) {
			Point point = new Point();
			
			String[] splittedLog = log.split("_");
			String attributes = splittedLog[3];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String color = attributes.split(",")[2];
			
			point.setX(Integer.parseInt(x));
			point.setY(Integer.parseInt(y));
			point.setEdgeColor(new Color(Integer.parseInt(color))); 
			CmdAddShape cmd = new CmdAddShape(model, point);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("added_line")) {
			Line line = new Line();
			
			String[] splittedLog = log.split("_");
			String attributes = splittedLog[3];
			String x1 = attributes.split(",")[0];
			String y1 = attributes.split(",")[1];
			String x2 = attributes.split(",")[2];
			String y2 = attributes.split(",")[3];
			String color = attributes.split(",")[4];
			
			line.setStartPoint(new Point());
			line.getStartPoint().setX(Integer.parseInt(x1));
			line.getStartPoint().setY(Integer.parseInt(y1));
			line.setEndPoint(new Point());
			line.getEndPoint().setX(Integer.parseInt(x2));
			line.getEndPoint().setY(Integer.parseInt(y2));
			line.setEdgeColor(new Color(Integer.parseInt(color))); 
			CmdAddShape cmd = new CmdAddShape(model, line);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		}else if(log.startsWith("added_circle")) {
			Circle circle = new Circle();
			
			String[] splittedLog = log.split("_");
			String attributes = splittedLog[3];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
		    String r = attributes.split(",")[2];
		    String eColor = attributes.split(",")[3];
		    String iColor = attributes.split(",")[4];
		    
		    circle.setCenter(new Point());
		    circle.getCenter().setX(Integer.parseInt(x));
		    circle.getCenter().setY(Integer.parseInt(y)); // setovati centar preko x i y tacaka NISAM SIGURNA
			try {
				circle.setRadius(Integer.parseInt(r));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			circle.setEdgeColor(new Color(Integer.parseInt(eColor)));
			circle.setInnerColor(new Color(Integer.parseInt(iColor)));
			CmdAddShape cmd = new CmdAddShape(model, circle);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		}else if(log.startsWith("added_rectangle")) {
			Rectangle rectangle = new Rectangle();
			
			String[] splittedLog = log.split("_");
			String attributes = splittedLog[3];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
		    String w = attributes.split(",")[2];
		    String h = attributes.split(",")[3];
		    String iColor = attributes.split(",")[4];
		    String eColor = attributes.split(",")[5];
		    
		    rectangle.setUpperLeft(new Point());
		    rectangle.getUpperLeft().setX(Integer.parseInt(x));
		    rectangle.getUpperLeft().setY(Integer.parseInt(y));
		    rectangle.setWidth(Integer.parseInt(w));
		    rectangle.setHeight(Integer.parseInt(h));
		    rectangle.setInnerColor(new Color(Integer.parseInt(iColor)));
		    rectangle.setEdgeColor(new Color(Integer.parseInt(eColor)));
		    CmdAddShape cmd = new CmdAddShape(model, rectangle);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		    
		}else if(log.startsWith("added_donut")) {
			Donut donut = new Donut();
			
			String[] splittedLog = log.split("_");
			String attributes = splittedLog[3];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String r = attributes.split(",")[2];
		    String ir = attributes.split(",")[3];
		    String eColor = attributes.split(",")[4];
		    String iColor = attributes.split(",")[5];
		    
		    donut.setCenter(new Point());
		    donut.getCenter().setX(Integer.parseInt(x));
		    donut.getCenter().setY(Integer.parseInt(y));
		    try {
				donut.setRadius(Integer.parseInt(r));
			} catch (NumberFormatException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		    donut.setInnerRadius(Integer.parseInt(ir));
		    donut.setEdgeColor(new Color(Integer.parseInt(eColor)));
		    donut.setInnerColor(new Color(Integer.parseInt(iColor)));
		    CmdAddShape cmd = new CmdAddShape(model, donut);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
			
		} else if(log.startsWith("added_hexagon")) {
			String[] splittedLog = log.split("_");
			String attributes = splittedLog[3];
			String r = attributes.split(",")[0];
			String x = attributes.split(",")[1];
			String y = attributes.split(",")[2];
		    String iColor = attributes.split(",")[3];
		    String eColor = attributes.split(",")[4];
		    
		    Adapter hexagon = new Adapter(Integer.parseInt(x), Integer.parseInt(y), Integer.parseInt(r),new Color(Integer.parseInt(eColor)), new Color(Integer.parseInt(iColor)));
		    
			CmdAddShape cmd = new CmdAddShape(model, hexagon);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
			
		} else if (log.startsWith("modifyedHexagon")) {
			System.out.println(log);
			String[] splittedLog = log.split("@");
			String attributes = splittedLog[2];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String radius = attributes.split(",")[2];
			String color = attributes.split(",")[3];
			String innerColor = attributes.split(",")[4];
			
			int xInt = Integer.parseInt(x);
			int yInt = Integer.parseInt(y);
			int radiusInt = Integer.parseInt(radius);
			Color colorAsColor = new Color(Integer.parseInt(color));
			Color innerColorAsColor = new Color(Integer.parseInt(innerColor));
			Adapter newHexagon = new Adapter(xInt, yInt, radiusInt, colorAsColor, innerColorAsColor);
			
			int index = model.getSelected();
			Adapter oldAdapter = (Adapter)model.getShape(index);
			
			CmdModifyHexagon cmd = new CmdModifyHexagon(oldAdapter, newHexagon);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if(log.startsWith("modifyedPoint")) {
			String[] splittedLog = log.split("@");
			System.out.println(splittedLog[0]);
			String attributes = splittedLog[2];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String color = attributes.split(",")[2];
			
			int xInt = Integer.parseInt(x);
			int yInt = Integer.parseInt(y);
			Color colorAsColor = new Color(Integer.parseInt(color));
			Point newState = new Point(xInt, yInt, true, colorAsColor);
			
			int index = model.getSelected();
			Point oldState = (Point)model.getShape(index);
			
			CmdModifyPoint cmd = new CmdModifyPoint(oldState, newState);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("modifyedLine")) {
			String[] splittedLog = log.split("@");
			String attributes = splittedLog[2];
			String x1 = attributes.split(",")[0];
			String y1 = attributes.split(",")[1];
			String x2 = attributes.split(",")[2];
			String y2 = attributes.split(",")[3];
			String color = attributes.split(",")[4];
			
			int x1Int = Integer.parseInt(x1);
			int y1Int = Integer.parseInt(y1);
			int x2Int = Integer.parseInt(x2);
			int y2Int = Integer.parseInt(y2);
			Color colorAsColor = new Color(Integer.parseInt(color));
			Line newLine = new Line(new Point(x1Int, y1Int), new Point(x2Int, y2Int), true, colorAsColor);
			
			int index = model.getSelected();
			Line oldLine = (Line)model.getShape(index);
			
			CmdModifyLine cmd = new CmdModifyLine(oldLine, newLine);// fali boja
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
			
		} else if(log.startsWith("modifyedCircle")) {
			String[] splittedLog = log.split("@");
			String attributes = splittedLog[2];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String radius = attributes.split(",")[2];
			String color = attributes.split(",")[3];
			String innerColor = attributes.split(",")[4];
			
			int xInt = Integer.parseInt(x);
			int yInt = Integer.parseInt(y);
			int radiusInt = Integer.parseInt(radius);
			Color colorAsColor = new Color(Integer.parseInt(color));
			Color innerColorAsColor = new Color(Integer.parseInt(innerColor));
			Circle newCircle = new Circle(new Point(xInt, yInt), radiusInt, true, colorAsColor, innerColorAsColor);
			
			int index = model.getSelected();
			Circle oldCircle = (Circle)model.getShape(index);
			
			CmdModifyCircle cmd = new CmdModifyCircle(oldCircle, newCircle);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		
		} else if(log.startsWith("modifyedRectangle")) {
			String[] splittedLog = log.split("@");
			String attributes = splittedLog[2];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String w = attributes.split(",")[2];
			String h = attributes.split(",")[3];
			String innerColor = attributes.split(",")[4];
			String color = attributes.split(",")[5];
			
			int xInt = Integer.parseInt(x);
			int yInt = Integer.parseInt(y);
			int wInt = Integer.parseInt(w);
			int hInt = Integer.parseInt(h);
			Color colorAsColor = new Color(Integer.parseInt(color));
			Color innerColorAsColor = new Color(Integer.parseInt(innerColor));
			Rectangle newRectangle = new Rectangle(new Point(xInt, yInt), wInt, hInt, true, colorAsColor, innerColorAsColor);
			
			int index = model.getSelected();
			Rectangle oldRectangle = (Rectangle)model.getShape(index);
			
			CmdModifyRectangle cmd = new CmdModifyRectangle(oldRectangle, newRectangle);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
			
		} else if (log.startsWith("modifyedDonut")) {
			String[] splittedLog = log.split("@");
			String attributes = splittedLog[2];
			String x = attributes.split(",")[0];
			String y = attributes.split(",")[1];
			String r = attributes.split(",")[2];
			String ir = attributes.split(",")[3];
			String color = attributes.split(",")[4];
			String innerColor = attributes.split(",")[5];
			
			int xInt = Integer.parseInt(x);
			int yInt = Integer.parseInt(y);
			int rInt = Integer.parseInt(r);
			int irInt = Integer.parseInt(ir);
			Color colorAsColor = new Color(Integer.parseInt(color));
			Color innerColorAsColor = new Color(Integer.parseInt(innerColor));
			Donut newDonut = new Donut(new Point(xInt, yInt), rInt, irInt, true, colorAsColor, innerColorAsColor);
			
			int index = model.getSelected();
			Donut oldDonut = (Donut)model.getShape(index);
			
			CmdModifyDonut cmd = new CmdModifyDonut(oldDonut, newDonut);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("select")) {
			String[] splittedLog = log.split("_");
			String index = splittedLog[4];
			
			int indexAsInt = Integer.parseInt(index);
			Shape shape = model.getOnIndex(indexAsInt);
			CmdSelect cmd = new CmdSelect(shape);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("Undo")) {
			int lastCommandIndex = executedCommands.size() - 1; 
			Command lastCommand = executedCommands.get(lastCommandIndex);
			lastCommand.unexecute();
			executedCommands.remove(lastCommandIndex);
			redoList.add(lastCommand);
			frame.addLog("Undo");
			frame.repaint();
			notifyObservers();
		}else if (log.startsWith("Redo")) {
			int lastCommandIndex = redoList.size() - 1; 
			Command lastCommand = redoList.get(lastCommandIndex);
			lastCommand.execute();
			redoList.remove(lastCommandIndex);
			executedCommands.add(lastCommand);
			frame.addLog("Redo");
			frame.repaint();
			notifyObservers();
		} else if (log.startsWith("deselect")) {
			String[] splittedLog = log.split("_");
			String indices = splittedLog[3];
			String[] splittedIndices = indices.split(":");
			
			ArrayList<Shape> shapesForDeselect = new ArrayList<Shape>();
			for (String index : splittedIndices) {
				int indexAsInt = Integer.parseInt(index);
				Shape shape = model.getOnIndex(indexAsInt);
				shapesForDeselect.add(shape);
			}
			
			CmdDeselect cmd = new CmdDeselect(shapesForDeselect);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("moved_up")) {
			String[] splittedLog = log.split("_");
			String index = splittedLog[4];
			int indexAsInt = Integer.parseInt(index);
			MoveUp cmd = new MoveUp(indexAsInt, model);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("moved_down")) {
			String[] splittedLog = log.split("_");
			String index = splittedLog[4];
			int indexAsInt = Integer.parseInt(index);
			MoveDown cmd = new MoveDown(indexAsInt, model);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		} else if (log.startsWith("moved_to_top")) {
			String[] splittedLog = log.split("_");
			String index = splittedLog[5];
			int indexAsInt = Integer.parseInt(index);
			MoveToTop cmd = new MoveToTop(indexAsInt, model);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		}else if (log.startsWith("moved_to_bottom")) {
			String[] splittedLog = log.split("_");
			String index = splittedLog[5];
			int indexAsInt = Integer.parseInt(index);
			MoveToBottom cmd = new MoveToBottom(indexAsInt, model);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		
		
		} else if (log.startsWith("delete")) {
			ArrayList<Shape> shapesToDelete = new ArrayList<Shape>();
			for (Shape s : model.getShapes()) {
				if (s.isSelected()) {
					shapesToDelete.add(s);
				}
			}
			
			CmdDelete cmd = new CmdDelete(model, shapesToDelete);
			cmd.execute();
			executedCommands.add(cmd);
			frame.repaint();
			notifyObservers();
			frame.addLog(log);
		}
		
		fileLogs.remove(0);
		notifyObservers();
	}
	
}
