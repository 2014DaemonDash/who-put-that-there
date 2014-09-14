package android.graph;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import edu.umd.cs.daemondash.sqlite.DatabaseHelper;
import edu.umd.cs.daemondash.sqlite.model.ContainerEntry;
import edu.umd.cs.daemondash.sqlite.model.ContainerFood;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

public class PieGraphFood {

	public Intent getIntent(Context context, DatabaseHelper db) {
		List<ContainerFood> foods = db.getContainerFoods();
		int pizza = 0, chickenStrips = 0, wraps = 0, valueMeal = 0, hamburgers = 0;
		for (ContainerFood currFood : foods) {
			
			Log.i("INFO", "Curr food: " + currFood.getFood().getName());
			
			if (currFood.getFood().getName().equals("Pizza")) {
				pizza++;
			} else if (currFood.getFood().getName().equals("Chicken Strips")) {
				chickenStrips++;
			} else if (currFood.getFood().getName().equals("Wrap")) {
				wraps++;
			} else if (currFood.getFood().getName().equals("Value Meal")) {
				valueMeal++;
			} else {
				hamburgers++;
			}
		}
				
		CategorySeries series = new CategorySeries("Food Distribution");
		series.add("Pizza", pizza);
		series.add("Chicken Strips", chickenStrips);
		series.add("Wraps", wraps);
		series.add("Value Meals", valueMeal);
		series.add("Hamburgers", hamburgers);
		
		Log.i("Food", "Pizza: " + pizza);
		Log.i("Food", "Chicken Strips: " + chickenStrips);
		Log.i("Food", "Wraps: " + wraps);
		Log.i("Food", "Value Meals: " + valueMeal);
		Log.i("Food", "Hamburgers: " + hamburgers);

		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.RED, Color.YELLOW, Color.MAGENTA};

		DefaultRenderer renderer = new DefaultRenderer();
		for (int color : colors) {
			SimpleSeriesRenderer r = new SimpleSeriesRenderer();
			r.setColor(color);
			renderer.addSeriesRenderer(r);
		}
		renderer.setChartTitleTextSize(50);
		renderer.setZoomButtonsVisible(true);
		renderer.setPanEnabled(false);
		renderer.setLabelsTextSize(35);
		renderer.setLegendTextSize(35);
		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Food Distribution");
		return intent;
	}
}