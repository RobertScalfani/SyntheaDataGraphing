import java.awt.Color;

import javax.swing.JFrame;

import de.erichseifert.gral.data.DataTable;
import de.erichseifert.gral.plots.XYPlot;
import de.erichseifert.gral.plots.lines.DefaultLineRenderer2D;
import de.erichseifert.gral.plots.lines.LineRenderer;
import de.erichseifert.gral.ui.InteractivePanel;

public class LinePlot extends JFrame {

	public LinePlot() {
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setSize(800, 600);
		
		// Extract Data Here
		DataTable data1 = new DataTable(Double.class, Double.class);
		for (double x = -5.0; x <= 5.0; x+=0.25) {
		    double y = 5.0*Math.sin(x);
		    data1.add(x, y);
		}
		
		DataTable data2 = new DataTable(Double.class, Double.class);
		for (double x = 5.0; x >= 0.0; x-=0.25) {
		    data2.add(x, x);
		}
		
		// Insert Data into plot here
		XYPlot plot = new XYPlot(data2, data1);
		
		// Render and clean up plots here
		LineRenderer lines = new DefaultLineRenderer2D();
		plot.setLineRenderers(data1, lines);
		plot.setLineRenderers(data2, lines);
		Color color = new Color(0.0f, 0.3f, 1.0f);
		plot.getPointRenderers(data1).get(0).setColor(color);
		plot.getLineRenderers(data1).get(0).setColor(color);
		plot.getPointRenderers(data2).get(0).setColor(color);
		plot.getLineRenderers(data2).get(0).setColor(color);
		
		// Display Graph
		getContentPane().add(new InteractivePanel(plot));
	}

	public static void main(String[] args) {
		LinePlot frame = new LinePlot();
		frame.setVisible(true);
	}
}