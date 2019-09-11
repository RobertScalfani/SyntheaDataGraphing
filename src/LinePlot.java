import java.awt.Color;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataSource;
import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.data.Row;
import de.erichseifert.gral.io.data.DataReader;
import de.erichseifert.gral.io.data.DataReaderFactory;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class LinePlot extends JFrame {

	public LinePlot(String fileName) {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		
		
		DataTable[] fullList = importCSV(fileName);
		
		
		// Insert Data into plot here
		XYPlot plot = new XYPlot(fullList);
		
		Random r = new Random();
		
		// Render and clean up plots here
		LineRenderer lines = new DefaultLineRenderer2D();
		for(DataTable dt : fullList) {
			Color color = new Color(r.nextInt(255), r.nextInt(255), r.nextInt(255));
			plot.getPointRenderers(dt).get(0).setColor(color);
			plot.setLineRenderers(dt, lines);
		}
		
		// Display Graph
		getContentPane().add(new InteractivePanel(plot));
	}

	public static void main(String[] args) {
		LinePlot allFrame = new LinePlot("C:\\Users\\robis\\OneDrive\\Documents\\payer_transitions_ALL.csv");
		allFrame.setVisible(true);
		
		LinePlot midFrame = new LinePlot("C:\\Users\\robis\\OneDrive\\Documents\\payer_transitions_MID.csv");
		midFrame.setVisible(true);
		
		LinePlot noFrame = new LinePlot("C:\\Users\\robis\\OneDrive\\Documents\\payer_transitions_NONE.csv");
		noFrame.setVisible(true);
	}
	
	public DataTable[] importCSV(String fileName) {
		
		// Each data table in the list corresponds to one patient's age and QOL data.
		DataTable[] data = null;
		
		DataReaderFactory factory = DataReaderFactory.getInstance();
		DataReader reader = factory.get("text/csv");
		try {
			
			// Imports PATIENT_ID, AGE, QOLS
			DataSource ds = reader.read(new FileInputStream(fileName), String.class, Integer.class, Double.class);
			
			data = new DataTable[99];
			
			String prevPatientID = (String) ds.get(0,0);
			int row = 0;
			int numPatients = 0;
			
			// Loop through all rows in the csv.
			while(numPatients < 99) {
				
				
				DataTable currentDataTable = new DataTable(Integer.class, Double.class);
				//currentDataTable.setName(prevPatientID);
				
				// Loop to fill current data table until patient ID changes.
				while(true) {
					
					// Get the current Row.
					Row currentRow = ds.getRow(row);
					
					if (!prevPatientID.equals(currentRow.get(0))) {
						prevPatientID = (String) currentRow.get(0);
						break;
					}
					
					// Add the current row's Age and QOLS to the current data table.
					currentDataTable.add(currentRow.get(1), currentRow.get(2));
					
					row++;
				}
				// Add current data table to list of data tables.	
				data[numPatients] = (currentDataTable);
				numPatients++;

			}
						
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return data;
	}
}