package read;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.swing.JFileChooser;

import geometry.Shape;
import mvc.DrawingModel;

public class ReadLogStrategy implements ReadStrategy {

	@Override
	public List<Object> read() {
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle("Select file to read");
		fileChooser.setFileSelectionMode(JFileChooser.FILES_ONLY);
		int answer=fileChooser.showOpenDialog(null);
		if (answer == JFileChooser.APPROVE_OPTION) {
			try {
				File file = new File(fileChooser.getSelectedFile().getAbsolutePath());
				List<Object> returnList = new ArrayList<Object>();
		        Scanner myReader = new Scanner(file);
		        while (myReader.hasNextLine()) {
		           String log = myReader.nextLine();
		           returnList.add(log);
		        }
				
		        myReader.close();
				return returnList;
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return null;
	}

}
