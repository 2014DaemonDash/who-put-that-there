package android.graph;

import java.util.List;

import org.achartengine.ChartFactory;
import org.achartengine.model.CategorySeries;
import org.achartengine.renderer.DefaultRenderer;
import org.achartengine.renderer.SimpleSeriesRenderer;

import edu.umd.cs.daemondash.sqlite.DatabaseHelper;
import edu.umd.cs.daemondash.sqlite.model.ContainerEntry;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;

public class PieGraph {

	public Intent getIntent(Context context, DatabaseHelper db) {
		List<ContainerEntry> entries = db.getContainerEntries();
		int northDinerCount = 0, southDinerCount = 0, north251 = 0;
		for (ContainerEntry curr : entries) {
			if (curr.getDiner().getName().equals("North Diner")) {
				northDinerCount++;
			} else if (curr.getDiner().getName().equals("South Diner")) {
				southDinerCount++;
			} else {
				north251++;
			}
		}
		
		Log.i("Entries", "Entries size: " + entries.size());
		
		Log.i("North Count", "North count: " + northDinerCount);
		Log.i("South Count", "South count: " + southDinerCount);
		Log.i("251 North", "251 North: " + north251);
				
		CategorySeries series = new CategorySeries("Diner Distribution");
		series.add("South Diner", southDinerCount);
		series.add("North Diner", northDinerCount);
		series.add("251 North", north251);

		int[] colors = new int[] { Color.BLUE, Color.GREEN, Color.RED};

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
		Intent intent = ChartFactory.getPieChartIntent(context, series, renderer, "Diner Distribution");
		return intent;
	}
}