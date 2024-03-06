package mvc;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JToggleButton;
import javax.swing.border.EmptyBorder;
import java.awt.GridLayout;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.border.TitledBorder;

import dialogs.DlgCircle;
import dialogs.DlgDonut;
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

import javax.swing.UIManager;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.ButtonGroup;
import javax.swing.DefaultListModel;

import java.awt.Component;
import javax.swing.border.LineBorder;
import java.awt.SystemColor;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.awt.Cursor;
import java.awt.Font;
import javax.swing.JScrollPane;
import javax.swing.JList;
import java.awt.GridBagLayout;

public class DrawingFrame extends JFrame implements Observer {
	
	private DrawingView pnlDrawing = new DrawingView();
	//private JPanel pnlDrawing = new JPanel();
	private DrawingController controller;

	private final int OPERATION_DRAWING = 1;
	private final int OPERATION_EDIT_DELETE = 0;
	
	private int activeOperation = OPERATION_DRAWING;

	private ButtonGroup btnsOperation = new ButtonGroup();
	private ButtonGroup btnsShapes = new ButtonGroup();
	private JToggleButton btnOperationDrawing = new JToggleButton("Drawing");
	private JToggleButton btnOperationEditOrDelete = new JToggleButton("Select");
	private JButton btnActionEdit = new JButton("Modify");
	private JButton btnActionDelete = new JButton("Delete");
	private JToggleButton btnShapePoint = new JToggleButton("Point");
	private JToggleButton btnShapeLine = new JToggleButton("Line");
	private JToggleButton btnShapeRectangle = new JToggleButton("Rectangle");
	private JToggleButton btnShapeCircle = new JToggleButton("Circle");
	private JToggleButton btnShapeDonut = new JToggleButton("Donut");
	
	private Color edgeColor = Color.BLACK, innerColor = Color.WHITE;
	
	private JPanel contentPane;
	private final JButton btnEdgeColor = new JButton("Edge Color");
	private final JButton btnInnerColor = new JButton("Inner Color");
	private final JToggleButton tglbtnHexagon = new JToggleButton("Hexagon");
	private final JPanel logPanel = new JPanel();
	private final JButton btnMoveUp = new JButton("Move up");
	private final JButton btnMoveDown = new JButton("Move down");
	private final JButton btnMoveToBottom = new JButton("Move bottom");
	private final JButton btnMoveToTop = new JButton("Move top");
	private final JButton btnUndo = new JButton("Undo");
	private final JButton btnRedo = new JButton("Redo");
	private final JPanel panel_5 = new JPanel();
	private final JScrollPane logScrollPane = new JScrollPane();
	private final JList listForLogs = new JList();
	private final DefaultListModel<String> logListModel = new DefaultListModel<String>();
	private final JButton btnBinarySave = new JButton("Save binary");
	private final JButton btnLogSave = new JButton("Save log");
	private String appLog = "";
	private final JButton btnReadBinary = new JButton("Read binary");
	private final JButton btnReadLog = new JButton("Read log");
	private final JButton btnNextLog = new JButton("Process next log");
	
	public DrawingFrame() {
		setTitle("Jovana Savic IT-64/2021");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 1100, 700);
		setLocationRelativeTo(null);
		setMinimumSize(new Dimension(1100, 700));
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(0, 0, 0, 0));
		contentPane.setLayout(new BorderLayout(0, 0));
		setContentPane(contentPane);

		contentPane.add(pnlDrawing, BorderLayout.CENTER);
		
		pnlDrawing.add(panel_5);
		panel_5.setLayout(new BorderLayout(0, 0));
		btnShapePoint.setOpaque(true);
		btnShapePoint.setBackground(new Color(0, 108, 35));
		pnlDrawing.add(btnShapePoint);
		
		btnShapePoint.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapePoint);
		btnShapeLine.setOpaque(true);
		btnShapeLine.setBackground(new Color(0, 108, 35));
		pnlDrawing.add(btnShapeLine);
		
		btnShapeLine.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeLine);
		btnShapeRectangle.setOpaque(true);
		btnShapeRectangle.setBackground(new Color(0, 108, 35));
		pnlDrawing.add(btnShapeRectangle);
		
		btnShapeRectangle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeRectangle);
		btnShapeCircle.setBackground(new Color(0, 108, 35));
		btnShapeCircle.setOpaque(true);
		pnlDrawing.add(btnShapeCircle);
		
		btnShapeCircle.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeCircle);
		btnShapeDonut.setOpaque(true);
		btnShapeDonut.setBackground(new Color(0, 108, 35));
		btnShapeDonut.setFont(new Font("Tahoma", Font.PLAIN, 10));
		pnlDrawing.add(btnShapeDonut);
		
		btnShapeDonut.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsShapes.add(btnShapeDonut);
		tglbtnHexagon.setBackground(new Color(0, 108, 35));
		tglbtnHexagon.setOpaque(true);
		tglbtnHexagon.setFont(new Font("Tahoma", Font.PLAIN, 8));
		pnlDrawing.add(tglbtnHexagon);
		btnsShapes.add(tglbtnHexagon);
		pnlDrawing.add(btnEdgeColor);
		
		btnEdgeColor.setBackground(new Color(0, 108, 35));
		pnlDrawing.add(btnInnerColor);
		btnInnerColor.setBackground(new Color(0, 108, 35));
		
		btnInnerColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				innerColor = JColorChooser.showDialog(null, "Choose inner color", innerColor);
				if (innerColor != null) {
					btnInnerColor.setBackground(innerColor);
				}
			}
		});
		
		btnEdgeColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				edgeColor = JColorChooser.showDialog(null, "Choose edge color", edgeColor);
				if (edgeColor != null) {
					btnEdgeColor.setBackground(edgeColor);
				}
			}
		});
		
		pnlDrawing.addMouseListener(pnlDrawingClickListener());
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, BorderLayout.WEST);
		panel_1.setLayout(new GridLayout(4, 0, 0, 0));
		
		JPanel panel_2 = new JPanel();
		panel_1.add(panel_2);
		panel_2.setLayout(new BoxLayout(panel_2, BoxLayout.Y_AXIS));
		btnOperationEditOrDelete.setBackground(new Color(120, 207, 145));
		btnOperationEditOrDelete.setOpaque(true);
		
		btnOperationEditOrDelete.setEnabled(false);
		btnOperationEditOrDelete.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationEditDelete();
			}
		});
		btnOperationDrawing.setBackground(new Color(120, 207, 145));
		btnOperationDrawing.setOpaque(true);
		btnOperationDrawing.setFont(new Font("Tahoma", Font.PLAIN, 10));
		
		btnOperationDrawing.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				setOperationDrawing();
			}
		});
		btnOperationDrawing.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationDrawing);
		panel_2.add(btnOperationDrawing);
		
		btnOperationDrawing.setSelected(true);
		btnOperationEditOrDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		btnsOperation.add(btnOperationEditOrDelete);
		panel_2.add(btnOperationEditOrDelete);
		
		JPanel panel_3 = new JPanel();
		panel_1.add(panel_3);
		panel_3.setLayout(new GridLayout(4, 2, 0, 0));
		btnActionEdit.setBackground(new Color(120, 207, 145));
		
		btnActionEdit.setEnabled(false);
		btnActionEdit.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnActionEdit);
		btnActionEdit.addActionListener(btnActionEditClickListener());
		btnActionDelete.setBackground(new Color(120, 207, 145));
		
		btnActionDelete.setEnabled(false);
		btnActionDelete.setAlignmentX(Component.CENTER_ALIGNMENT);
		panel_3.add(btnActionDelete);
		btnMoveUp.setBackground(new Color(120, 207, 145));
		btnMoveUp.setEnabled(false);
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveUp();
			}
		});
		
		btnMoveDown.setBackground(new Color(120, 207, 145));
		btnMoveDown.setEnabled(false);
		btnMoveDown.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveDown();
			}
		});
		
		btnMoveToBottom.setBackground(new Color(120, 207, 145));
		btnMoveToBottom.setEnabled(false);
		btnMoveToBottom.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveToBottom();
			}
		});
		
		btnMoveToTop.setBackground(new Color(120, 207, 145));
		btnMoveToTop.setEnabled(false);
		btnMoveToTop.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.moveToTop();
			}
		});
		
		btnUndo.setBackground(new Color(120, 207, 145));
		btnUndo.setEnabled(false);
		btnUndo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.undo();
			}
		});
		
		btnRedo.setBackground(new Color(120, 207, 145));
		btnRedo.setEnabled(false);
		btnRedo.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.redo();
			}
		});
		
		panel_3.add(btnMoveUp);
		panel_3.add(btnMoveDown);
		panel_3.add(btnMoveToBottom);
		panel_3.add(btnMoveToTop);
		panel_3.add(btnUndo);
		panel_3.add(btnRedo);
		btnActionDelete.addActionListener(btnActionDeleteClickListener());
		
		
		JPanel panel_4 = new JPanel();
		panel_1.add(panel_4);
		panel_4.setLayout(new GridLayout(5, 2, 0, 0));
		btnBinarySave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveBinary();
			}
		});
		
		panel_4.add(btnBinarySave);
		btnLogSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.saveLog();
			}
		});
		
		panel_4.add(btnLogSave);
		btnReadBinary.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.readBinary();
			}
		});
		
		panel_4.add(btnReadBinary);
		btnReadLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.readLog();
			}
		});
		
		panel_4.add(btnReadLog);
		btnNextLog.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.processNextLog();
			}
		});
		
		panel_4.add(btnNextLog);
		
		contentPane.add(logPanel, BorderLayout.SOUTH);
		logPanel.setLayout(new GridLayout(0, 1, 0, 0));
		
		logPanel.add(logScrollPane);
		logScrollPane.setViewportView(listForLogs);
		listForLogs.setModel(logListModel);
		setOperationDrawing();
		
	}
	
	private MouseAdapter pnlDrawingClickListener() {
		
		return new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				controller.drawShape(e, activeOperation);
			}
		};
	}
	
	private ActionListener btnActionEditClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.editShape();
			}
		};
	}
	
	
	private ActionListener btnActionDeleteClickListener() {
		return new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				controller.deleteShape();
			}
		};
	}
	
	private void setOperationDrawing() {
	
		activeOperation = OPERATION_DRAWING;
		
		//TODO
		//pnlDrawing.deselect();
		
		btnShapePoint.setEnabled(true);
		btnShapeLine.setEnabled(true);
		btnShapeRectangle.setEnabled(true);
		btnShapeCircle.setEnabled(true);
		btnShapeDonut.setEnabled(true);
		
	}
	

	private void setOperationEditDelete() {
		activeOperation = OPERATION_EDIT_DELETE;
		
		btnShapePoint.setEnabled(false);
		btnShapeLine.setEnabled(false);
		btnShapeRectangle.setEnabled(false);
		btnShapeCircle.setEnabled(false);
		btnShapeDonut.setEnabled(false);
		
	}

	public DrawingView getPnlDrawing() {
		return pnlDrawing;
		//return null;
	}

	public void setController(DrawingController controller) {
		this.controller = controller;
	}

	public JToggleButton getBtnShapePoint() {
		return btnShapePoint;
	}

	public JToggleButton getBtnShapeLine() {
		return btnShapeLine;
	}

	public JToggleButton getBtnShapeRectangle() {
		return btnShapeRectangle;
	}

	public JToggleButton getBtnShapeCircle() {
		return btnShapeCircle;
	}

	public JToggleButton getBtnShapeDonut() {
		return btnShapeDonut;
	}
	
	public JToggleButton getBtnShapeHexagon() {
		return tglbtnHexagon;
	}

	public Color getEdgeColor() {
		return edgeColor;
	}

	public void setEdgeColor(Color edgeColor) {
		this.edgeColor = edgeColor;
	}

	public Color getInnerColor() {
		return innerColor;
	}

	public void setInnerColor(Color innerColor) {
		this.innerColor = innerColor;
	}

	@Override
	public void update(int numberOfShapesOnScreen, int numberOfSelectedShapes, boolean isFirst, boolean isLast, int numberOfElementsInUndoList, int numberOfElementsInRedoList, int numberOfLogsForNext) {
		if (numberOfShapesOnScreen > 0) {
			btnOperationEditOrDelete.setEnabled(true);
		} else {
			btnOperationEditOrDelete.setEnabled(false);
		}
		
		if (numberOfSelectedShapes == 1) {
			btnActionEdit.setEnabled(true);
		} else {
			btnActionEdit.setEnabled(false);
		}
		
		if (numberOfSelectedShapes > 0) {
			btnActionDelete.setEnabled(true);
		} else {
			btnActionDelete.setEnabled(false);
		}
		
		if (numberOfSelectedShapes == 1 && numberOfShapesOnScreen > 1) {
			if (isFirst) {
				btnMoveDown.setEnabled(false);
				btnMoveToBottom.setEnabled(false);
				
				btnMoveUp.setEnabled(true);
				btnMoveToTop.setEnabled(true);
			} else if (isLast) {
				btnMoveDown.setEnabled(true);
				btnMoveToBottom.setEnabled(true);
				
				btnMoveUp.setEnabled(false);
				btnMoveToTop.setEnabled(false);
			} else {
				btnMoveDown.setEnabled(true);
				btnMoveToBottom.setEnabled(true);
				btnMoveUp.setEnabled(true);
				btnMoveToTop.setEnabled(true);
			}
		} else {
			btnMoveDown.setEnabled(false);
			btnMoveToBottom.setEnabled(false);
			btnMoveUp.setEnabled(false);
			btnMoveToTop.setEnabled(false);
		}
		
		if (numberOfElementsInUndoList > 0) {
			btnUndo.setEnabled(true);
		} else {
			btnUndo.setEnabled(false);
		}
		
		if (numberOfElementsInRedoList > 0) {
			btnRedo.setEnabled(true);
		} else {
			btnRedo.setEnabled(false);
		}
		
		if (numberOfLogsForNext > 0) {
			btnNextLog.setEnabled(true);
		} else {
			btnNextLog.setEnabled(false);
		}
	}

	public void addLog(String createdLog) {
		logListModel.addElement(createdLog);
		appLog += createdLog + "\n";
	}
	public String getAppLog() {
		return appLog;
	}
	
	public void removeAllLogs() {
		logListModel.clear();
		appLog = "";
	}
	

	
	
}
