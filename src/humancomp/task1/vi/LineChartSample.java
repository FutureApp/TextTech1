package humancomp.task1.vi;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;

import javax.imageio.ImageIO;

import org.apache.commons.io.FileUtils;

import javafx.application.Application;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.Scene;
import javafx.scene.SnapshotParameters;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.image.WritableImage;
import javafx.stage.Stage;
 
 
public class LineChartSample extends Application {
 
    Integer resolutionX=1200;
    Integer resolutionY=800;

	@Override public void start(Stage stage) throws IOException, InterruptedException {
        stage.setTitle("Programm Results - Successful Count");
        File dataFile = new File("Results/NamingGame/results1000A3000R100000S.csv");
        List<String> measure = FileUtils.readLines(dataFile, "UTF-8");
        //defining the axes
        final NumberAxis xAxis = new NumberAxis("Stages [#ID]",0,measure.size(),10000);
        final NumberAxis yAxis = new NumberAxis("Successful communications [#]",0,1,0.10);
        //creating the chart
        
        
        final LineChart<Number,Number> lineChart = 
                new LineChart<Number,Number>(xAxis,yAxis);
        lineChart.setTitle("Successful Counts / Stages");
        //defining a series
        XYChart.Series series = new XYChart.Series();
        series.setName("1000 Agents -- 3000 Rounds -- 100000 Stages");
        //populating the series with data
        for (int i = 1; i < measure.size(); i++) {
        	String[] data = measure.get(i).split(" ");
        	float itemA = Float.parseFloat(data[3]);
        	float itemB = Float.parseFloat(data[4]);
        	float item = itemA/
//        			itemB;
        			(measure.size()-1);
        	if(i%1000 == 0){
        		System.out.println(i+"/"+measure.size());
        	}
        	series.getData().add(new XYChart.Data<Integer, Float>(i, item));
		}
        lineChart.getData().add(series);
        Scene scene  = new Scene(lineChart,resolutionX,resolutionY);
        saveResultsAsPNG(lineChart);
        
        stage.setScene(scene);
        stage.show();
        
    }
 
    private void saveResultsAsPNG(LineChart<Number, Number> lineChart) {
    	SnapshotParameters parameters = new SnapshotParameters();
        WritableImage wi = new WritableImage(resolutionX, resolutionY);
        lineChart.getStylesheets().add("file:HumanComp/Task1/MetaData/shit.css");
        WritableImage snapshot = lineChart.snapshot(new SnapshotParameters(), wi);

        File output = new File("results_sucAVG.png");
        try {
			ImageIO.write(SwingFXUtils.fromFXImage(snapshot, null), "png", output);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
        launch(args);
       
    }
}