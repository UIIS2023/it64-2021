package save;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import mvc.DrawingModel;

public class BinaryStrategy implements SaveStrategy {
	
	DrawingModel model;
	
	public BinaryStrategy(DrawingModel model) {
		this.model=model;
	}

	@Override
	public void save() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Where do you want to save file?");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int answer=fileChooser.showSaveDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			try {
				FileOutputStream outputStream = new FileOutputStream(fileChooser.getSelectedFile().getAbsolutePath());
				ObjectOutputStream objectOutputStream = new ObjectOutputStream(outputStream);
				objectOutputStream.writeObject(model);
				objectOutputStream.close();
				outputStream.close();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
