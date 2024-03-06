package mvc;

public class Application {

	public static void main(String[] args) {
		DrawingModel model = new DrawingModel();
		DrawingFrame frame = new DrawingFrame();
		
		frame.getPnlDrawing().setModel(model);
		
		DrawingController controller = new DrawingController();
		controller.setModel(model);
		controller.setFrame(frame);
		
		frame.setController(controller);
		
		controller.addObserver(frame);
		
		frame.setVisible(true);
	}

}
