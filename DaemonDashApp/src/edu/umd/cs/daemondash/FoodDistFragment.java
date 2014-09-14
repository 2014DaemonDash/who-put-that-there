package edu.umd.cs.daemondash;

import android.content.Intent;
import android.graph.PieGraph;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class FoodDistFragment extends Fragment {
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
		PieGraph pie = new PieGraph();
    	Intent lineIntent = pie.getIntent(getActivity(), Storage.getDb());
        getActivity().startActivity(lineIntent);
		return inflater.inflate(R.layout.food_dist_layout, container, false);
	}
	
}
