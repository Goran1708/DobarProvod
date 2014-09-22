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
	ListView listOfDomesticClubs;
	ListView listOfAlternativeClubs;
	ListView listOfForeignClubs;
	ArrayAdapter<String> domesticClubAdapter;
	ArrayAdapter<String> alternativeClubAdapter;
	ArrayAdapter<String> foreignClubAdapter;
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

	private void initializeVariables() {
		typeOfMusic = (TabHost) findViewById(android.R.id.tabhost);
		typeOfMusic.setup();

		TabSpec specs = typeOfMusic.newTabSpec("Domaæa glazba");
		specs.setContent(R.id.tab1);
		specs.setIndicator("Domaæa glazba");
		typeOfMusic.addTab(specs);
		listOfDomesticClubs = (ListView) findViewById(R.id.lVDomesticClubs);
		domesticClubAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, domesticClubs);

		listOfDomesticClubs
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long id) {
						Intent localIntent = new Intent(MainActivity.this,
								ClubInformation.class);
						localIntent.putExtra("clubName", domesticClubs.get(i));
						startActivity(localIntent);
					}
				});

		specs = typeOfMusic.newTabSpec("Alternativna glazba");
		specs.setContent(R.id.tab2);
		specs.setIndicator("Alternativna glazba");
		typeOfMusic.addTab(specs);
		listOfAlternativeClubs = (ListView) findViewById(R.id.lVAlternativeClubs);
		alternativeClubAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, alternativeClubs);

		listOfAlternativeClubs
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long id) {
						Intent localIntent = new Intent(MainActivity.this,
								ClubInformation.class);
						localIntent.putExtra("clubName",
								alternativeClubs.get(i));
						startActivity(localIntent);
					}
				});

		specs = typeOfMusic.newTabSpec("Strana glazba");
		specs.setContent(R.id.tab3);
		specs.setIndicator("Strana glazba");
		typeOfMusic.addTab(specs);
		listOfForeignClubs = (ListView) findViewById(R.id.lVTechnoClubs);
		foreignClubAdapter = new ArrayAdapter<String>(this,
				android.R.layout.simple_list_item_1, foreignClubs);

		listOfForeignClubs
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					public void onItemClick(AdapterView<?> adapterView,
							View view, int i, long id) {
						Intent localIntent = new Intent(MainActivity.this,
								ClubInformation.class);
						localIntent.putExtra("clubName", foreignClubs.get(i));
						startActivity(localIntent);
					}
				});
	}

	private void addClubs() {
		domesticClubs.add("Galery");
		domesticClubs.add("Lemon");

		alternativeClubs.add("Hard Place");
		alternativeClubs.add("KSET");

		foreignClubs.add("Plaza Bar");
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
