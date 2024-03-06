package read;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFileChooser;

import geometry.Shape;
import mvc.DrawingModel;

public class ReadBinaryStrategy implements ReadStrategy {

	@Override
	public List<Object> read() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select file to read");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int answer=fileChooser.showOpenDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			try {
				FileInputStream inputStream = new FileInputStream(fileChooser.getSelectedFile().getAbsolutePath());
				ObjectInputStream objectInputStream = new ObjectInputStream(inputStream);
				DrawingModel model = (DrawingModel)objectInputStream.readObject();
				objectInputStream.close();
				objectInputStream.close();
				
				List<Object> returnList = new ArrayList<Object>();
				for (Shape s: model.getShapes()) {
					returnList.add(s);
				}
				return returnList;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
