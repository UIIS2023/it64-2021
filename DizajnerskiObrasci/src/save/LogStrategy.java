package save;

import java.io.BufferedWriter;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.ObjectOutputStream;

import javax.swing.JFileChooser;

import mvc.DrawingFrame;

public class LogStrategy implements SaveStrategy {

	private DrawingFrame frame;
	
	@Override
	public void save() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Where do you want to save file?");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int answer=fileChooser.showSaveDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			 try {
				BufferedWriter writer = new BufferedWriter(new FileWriter(fileChooser.getSelectedFile().getAbsolutePath() + ".txt", true));
				String log = frame.getAppLog();
				writer.write(log);
			    
			    writer.close();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public void setFrame(DrawingFrame frame) {
		this.frame = frame;
	}

	
}
