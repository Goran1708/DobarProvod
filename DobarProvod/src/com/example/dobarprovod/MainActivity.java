package com.example.dobarprovod;

import java.util.ArrayList;
import java.util.List;

import android.support.v7.app.ActionBarActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.TabHost.TabSpec;

public class MainActivity extends ActionBarActivity {

	TabHost typeOfMusic;
	ListView listOfDomesticClubs, listOfAlternativeClubs, listOfForeignClubs;
	ArrayAdapter<String> domesticClubAdapter, alternativeClubAdapter, foreignClubAdapter;
	static List<String> domesticClubs = new ArrayList<String>();
	static List<String> alternativeClubs = new ArrayList<String>();
	static List<String> foreignClubs = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		if (domesticClubs.isEmpty()) {
			addClubs();
		}
		initializeVariables();
		listOfDomesticClubs.setAdapter(domesticClubAdapter);
		listOfAlternativeClubs.setAdapter(alternativeClubAdapter);
		listOfForeignClubs.setAdapter(foreignClubAdapter);
	}

	/**
	 * Creates tabhost at top of screen, fills it with tabs and fills the tabs with listView items.
	 */
	private void initializeVariables() {
		
		typeOfMusic = (TabHost) findViewById(android.R.id.tabhost);
		typeOfMusic.setup();

		/** Creates tab for Domestic music and onClickListener. */
		
		TabSpec specs = typeOfMusic.newTabSpec("Domestic music");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Domestic music");
		typeOfMusic.addTab(specs);
		listOfDomesticClubs = (ListView) findViewById(R.id.lVDomesticClubs);
		domesticClubAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, domesticClubs);

		listOfDomesticClubs
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
						Intent localIntent = new Intent(MainActivity.this, ClubInformation.class);
						localIntent.putExtra("clubName", domesticClubs.get(i));
						startActivity(localIntent);
						finish();
					}
				});

		/** Creates tab for Alternative music and onClickListener. */
		
		specs = typeOfMusic.newTabSpec("Alternative music");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Alternative music");
		typeOfMusic.addTab(specs);
		listOfAlternativeClubs = (ListView) findViewById(R.id.lVAlternativeClubs);
		alternativeClubAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, alternativeClubs);

		listOfAlternativeClubs
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
						Intent localIntent = new Intent(MainActivity.this, ClubInformation.class);
						localIntent.putExtra("clubName", alternativeClubs.get(i));
						startActivity(localIntent);
						finish();
					}
				});

		/** Creates tab for Foreign music and onClickListener. */
		
		specs = typeOfMusic.newTabSpec("Foreign music");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Foreign music");
		typeOfMusic.addTab(specs);
		listOfForeignClubs = (ListView) findViewById(R.id.lVTechnoClubs);
		foreignClubAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, foreignClubs);

		listOfForeignClubs
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView, View view, int i, long id) {
						Intent localIntent = new Intent(MainActivity.this, ClubInformation.class);
						localIntent.putExtra("clubName", foreignClubs.get(i));		
						startActivity(localIntent);
						finish();
					}
				});
	}

	/**
	 * Fills list with name of clubs for listViews and adapters.
	 */
	private void addClubs() {
		domesticClubs.add("Galery");
		domesticClubs.add("Lemon");

		alternativeClubs.add("Hard_Place");
		alternativeClubs.add("KSET");

		foreignClubs.add("Plaza_Bar");
		foreignClubs.add("Aquarius");
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
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
}
