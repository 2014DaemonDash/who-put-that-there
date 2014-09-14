package edu.umd.cs.daemondash;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import tw.com.quickmark.sdk.demo.CaptureActivity;

import edu.umd.cs.daemondash.sqlite.DatabaseHelper;
import edu.umd.cs.daemondash.sqlite.model.ContainerEntry;
import edu.umd.cs.daemondash.sqlite.model.ContainerFood;
import edu.umd.cs.daemondash.sqlite.model.Diner;
import edu.umd.cs.daemondash.sqlite.model.Food;
import edu.umd.cs.daemondash.sqlite.model.User;
import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.content.Intent;
import android.graph.PieGraph;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.os.Build;

public class MainActivity extends Activity {

	public int pressed_ct = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (savedInstanceState == null) {
			getFragmentManager().beginTransaction()
					.add(R.id.container, new PlaceholderFragment()).commit();
		}
		Intent intent = new Intent(this, CaptureActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, 90);
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
		db.clearDb();
		db.populateTestData();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void buttonPress(View view) {
		Button p1_button = (Button) findViewById(R.id.button1);
		pressed_ct++;
		p1_button.setText("Clicked! " + pressed_ct);

		// grab date/time objects

		// grab gps stuff?

		Intent intent = new Intent(this, CaptureActivity.class);
		intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
		startActivityForResult(intent, 90);

	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		long barcode;
		switch (requestCode) {
		case (90): {
			if (resultCode == Activity.RESULT_OK) {
				// TODO Extract the data returned from the child Activity.
				
				String code = (data.getExtras().get("code")).toString();
				barcode = Long.parseLong(code);
				Storage storage = new Storage();
				storage.setBarcode(barcode);
				storage.setDb(new DatabaseHelper(getApplicationContext()));
				// GOTO FORM SUBMISSION ACTIVITY
				Intent intent = new Intent(this, FormSubmissionActivity.class);
//				Bundle bundle = new Bundle();
//				bundle.putLong("barcode", barcode);
//				intent.putExtras(bundle);
				startActivity(intent);
			}
			break;
		}
		}
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();
		if (id == R.id.action_settings) {
			return true;
		}
		return super.onOptionsItemSelected(item);
	}

	public void pieGraphHandler (View view)
    {
		DatabaseHelper db = new DatabaseHelper(getApplicationContext());
    	PieGraph pie = new PieGraph();
    	Intent lineIntent = pie.getIntent(this, db);
        startActivity(lineIntent);
    }
	/**
	 * A placeholder fragment containing a simple view.
	 */
	public static class PlaceholderFragment extends Fragment {

		public PlaceholderFragment() {
		}

		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container,
				Bundle savedInstanceState) {
			View rootView = inflater.inflate(R.layout.fragment_main, container,
					false);
			return rootView;
		}
	}
}
