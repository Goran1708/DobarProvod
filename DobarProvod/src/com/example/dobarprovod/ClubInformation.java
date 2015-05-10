package com.example.dobarprovod;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class ClubInformation extends ActionBarActivity implements OnClickListener {

	static String clubName;
	static Map<String, String> mapOfClubUrls = new HashMap<String, String>();
	List<String> clubEventList = new ArrayList<String>();
	ListView eventName;
	ArrayAdapter<String> eventNameAdapter;
	ProgressDialog mProgressDialog;
	Button callCab, showOnMap, commentOnClub;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clubinformation);
		if (getIntent().getExtras() != null) {
			clubName = getIntent().getExtras().getString("clubName");
			setTitle(clubName);
		}
		populateMapOfClubURls();
		intializeVariables();
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		new Events().execute();
	}

	/**
	 * Maps all views and buttons to their xml ID.
	 */
	private void intializeVariables() {
		eventName = (ListView) findViewById(R.id.lVEventName);
		callCab = (Button) findViewById(R.id.bCallCab);
		showOnMap = (Button) findViewById(R.id.bShowOnMap);
		commentOnClub = (Button) findViewById(R.id.bClubsPage);
		callCab.setOnClickListener(this);
		showOnMap.setOnClickListener(this);
		commentOnClub.setOnClickListener(this);

	}

	/**
	 * On click listener for all buttons in activity.
	 */
	public void onClick(View view) {

		Intent callIntentForCabs = new Intent(Intent.ACTION_CALL);

		switch (view.getId()) {
		case R.id.bCallCab:
			createDialogForCabs();
			break;
		case R.id.bShowOnMap:
			Intent localIntent = new Intent(ClubInformation.this, Location.class);
			localIntent.putExtra("clubName", clubName);
			startActivity(localIntent);
			break;
		case R.id.bClubsPage:
			Intent localIntent2 = new Intent(ClubInformation.this, ClubPage.class);
			localIntent2.putExtra("clubName", clubName);
			startActivity(localIntent2);
			break;
		case R.id.bCameo:
			callIntentForCabs.setData(Uri.parse("tel:1245"));
			startActivity(callIntentForCabs);
			break;
		case R.id.bRadioTaxi:
			callIntentForCabs.setData(Uri.parse("tel:123456"));
			startActivity(callIntentForCabs);
			break;
		case R.id.bEcoTaxi:
			callIntentForCabs.setData(Uri.parse("tel:1234"));
			startActivity(callIntentForCabs);
			break;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(ClubInformation.this, MainActivity.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(localIntent);
		return true;
	}
	
	/**
	 * Dialog for cab information and their number which can be called from application.
	 */
	private void createDialogForCabs() {
		final Dialog dialog = new Dialog(ClubInformation.this);
		// Include cabdialog.xml file
		dialog.setContentView(R.layout.cabdialog);
		// Set dialog title
		dialog.setTitle("Nazovi taxi");

		// set values for custom dialog components - text and button
		
		//cameo textview and button.
		TextView tvCameo = (TextView) dialog.findViewById(R.id.tVCameo);
		tvCameo.setText("Cameo: 6kn/km");

		Button callCameo = (Button) dialog.findViewById(R.id.bCameo);
		callCameo.setOnClickListener(this);

		//RadioTaxi textview and button.
		TextView tvRadioTaxi = (TextView) dialog
				.findViewById(R.id.tVRadioTaxi);
		tvRadioTaxi.setText("Radio Taxi: 4,90kn/km");

		Button callRadioTaxi = (Button) dialog
				.findViewById(R.id.bRadioTaxi);
		callRadioTaxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});
		
		//EcoTaxi textview and button.
		TextView tvEcoTaxi = (TextView) dialog
				.findViewById(R.id.tVEcoTaxi);
		tvEcoTaxi.setText("Radio Taxi: 5,50kn/km");

		Button callEcoTaxi = (Button) dialog
				.findViewById(R.id.bEcoTaxi);
		callEcoTaxi.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
			}
		});

		//Exit dialog.
		Button cancel = (Button) dialog.findViewById(R.id.bCancel);
		cancel.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				dialog.cancel();
			}
		});

		//Create dialog.
		dialog.show();
		
	}
	
	/**
	 * Fill map with club Urls.
	 */
	private void populateMapOfClubURls() {
		
		String Galery = "http://gallery.hr/upcoming-events/";
		String Lemon = "http://lemon.hr/hr/";
		String Hard_Place = "http://www.hardplace.hr/";
		String KSET = "https://www.kset.org/arhiva/dogadaji/";
		String Plaza_Bar = "http://www.plazabar.hr/program";
		String Aquarius = "http://www.aquarius.hr/program/";

		mapOfClubUrls.put("Galery", Galery);
		mapOfClubUrls.put("Lemon", Lemon);
		mapOfClubUrls.put("Hard_Place", Hard_Place);
		mapOfClubUrls.put("KSET", KSET);
		mapOfClubUrls.put("Plaza_Bar", Plaza_Bar);
		mapOfClubUrls.put("Aquarius", Aquarius);
		
	}

	/**
	 * Async task for parsing DOM of URL pages and returning events and date when they will occur.
	 * @author GoranLaptop
	 *
	 */
	private class Events extends AsyncTask<Void, Void, Void> {

		String Event;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			mProgressDialog = new ProgressDialog(ClubInformation.this);
			mProgressDialog.setTitle("Fetching events for club");
			mProgressDialog.setMessage("Loading...");
			mProgressDialog.setIndeterminate(false);
			mProgressDialog.show();
		}

		@Override
		protected Void doInBackground(Void... params) {
			if (clubName.equals("Galery")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(mapOfClubUrls.get(clubName)).get();
					// Using Elements to get the Meta data
					Elements description = document.select("h1[class=entry-head]").select("a");
					// Get the html document title
					Event = description.attr("title");
					clubEventList.add(Event);
					if(clubEventList.isEmpty()) {
						clubEventList.add("No events");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (clubName.equals("Lemon")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(mapOfClubUrls.get(clubName)).get();
					Elements description = document
							.select("div[class=vijesti_paragraf_bar]");
					for (int i = 0; i < description.size(); i++) {
						
						Element e = description.get(i);
						Event = e.select("p")
								.select("span[style=color: #ff6600;]").first()
								.text();
						clubEventList.add(Event);
					}
					if(clubEventList.isEmpty()) {
						clubEventList.add("No events");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (clubName.equals("Hard_Place")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(mapOfClubUrls.get(clubName)).get();

					Elements description = document.select(
							"div[class=description]").select("span");
					for (int i = 0; i < description.size(); i++) {
						Event = description.get(i).text();
						clubEventList.add(Event);
					}
					if(clubEventList.isEmpty()) {
						clubEventList.add("No events");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else if (clubName.equals("KSET")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(mapOfClubUrls.get(clubName)).get();
					Elements description = document
							.select("td[class=archive-title]");
					
					for (int i = 0; i < description.size(); i++) {
						Event = description.get(i).select("a").text();
						clubEventList.add(Event);
					}
					if(clubEventList.isEmpty()) {
						clubEventList.add("No events");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (clubName.equals("Plaza_Bar")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(mapOfClubUrls.get(clubName)).get();

					Elements description = document
							.select("div[class=article]");
					
					for (int i = 0; i < description.size(); i++) {
						Event = description.get(i).select("h2").text();
						clubEventList.add(Event);
					}
					if(clubEventList.isEmpty()) {
						clubEventList.add("No events");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			else if (clubName.equals("Aquarius")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(mapOfClubUrls.get(clubName)).get();

					Elements description = document
							.select("li").select("div").select("a");
					
					for (int i = 0; i < description.size(); i++) {
						Event = description.get(i).text();
						clubEventList.add(Event);
					}
					if(clubEventList.isEmpty()) {
						clubEventList.add("No events");
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			return null;
		}

		@Override
		protected void onPostExecute(Void result) {
			eventNameAdapter = new ArrayAdapter<String>(ClubInformation.this,
					android.R.layout.simple_list_item_1, clubEventList);
			eventName.setAdapter(eventNameAdapter);
			mProgressDialog.dismiss();
		}
	}

}
