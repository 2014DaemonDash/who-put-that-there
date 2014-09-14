package edu.umd.cs.daemondash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

import edu.umd.cs.daemondash.sqlite.DatabaseHelper;
import edu.umd.cs.daemondash.sqlite.model.ContainerEntry;
import edu.umd.cs.daemondash.sqlite.model.ContainerFood;
import android.app.ActionBar.Tab;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Intent;
import android.graph.PieGraph;
import android.graph.PieGraphFood;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.os.Build;
import android.support.v4.view.ViewPager;

public class DataActivity extends Activity {
	ActionBar actionbar;
	ViewPager viewpager;
	FragmentPageAdapter ft;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_data);
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
		List<ContainerEntry> entries = db.getHistory(128492648392L);
		List<ContainerFood> foods = db.getContainerFoods();
		List<Integer> rows = new ArrayList<Integer>();
		rows.add(R.id.row1);
		rows.add(R.id.row2);
		rows.add(R.id.row3);
		for (int i = 0; i < entries.size(); i++) {
			TextView textView = (TextView) findViewById(rows.get(i));
			GregorianCalendar gc = new GregorianCalendar();
			gc.setTimeInMillis(entries.get(i).getTstamp());
			for (ContainerFood currFood : foods) {
				if (currFood.getTstamp().equals(entries.get(i).getTstamp())) {
					textView.setText("Date: " + format(gc) + ", Diner: " + entries.get(i).getDiner().getName() +
							", Food: " + currFood.getFood().getName());
				}
			}
			
		}
		
	}
	
	public void foodDistHandler (View view)
    {
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
    	PieGraphFood pie = new PieGraphFood();
    	Intent lineIntent = pie.getIntent(this, db);
        startActivity(lineIntent);
    }
	
	public void dinerDistHandler (View view) {
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
    	PieGraph pie = new PieGraph();
    	Intent lineIntent = pie.getIntent(this, db);
        startActivity(lineIntent);
	}
	
	public static String format(GregorianCalendar calendar){
	    SimpleDateFormat fmt = new SimpleDateFormat("dd-MMM-yyyy");
	    fmt.setCalendar(calendar);
	    String dateFormatted = fmt.format(calendar.getTime());
	    return dateFormatted;
	}

}
