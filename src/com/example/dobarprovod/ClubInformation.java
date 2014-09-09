package com.example.dobarprovod;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import com.google.android.gms.maps.*;
import com.google.android.gms.maps.model.*;

@SuppressLint({ "NewApi", "SimpleDateFormat" })
public class ClubInformation extends Activity implements OnClickListener {

	static String clubName;
	String urlGalery = "http://gallery.hr/upcoming-events/";
	String urlLemon = "http://lemon.hr/hr/";
	String urlHard_Place = "http://www.hardplace.hr/";
	String urlKSET = "https://www.kset.org/arhiva/dogadaji/";
	String urlPlaza_Bar = "http://www.plazabar.hr/program";
	String urlAquarius = "http://www.aquarius.hr/program/";
	List<String> clubEventList = new ArrayList<String>();
	ListView eventName;
	ArrayAdapter<String> eventNameAdapter;
	ProgressDialog mProgressDialog;
	Button callCab, showOnMap, commentOnClub;

	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.clubinformation);
		if (getIntent().getExtras() != null) {
			clubName = getIntent().getExtras().getString("clubName");
		}
		setTitle(clubName);
		intializeVariables();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		new Events().execute();
	}

	private void intializeVariables() {
		eventName = (ListView) findViewById(R.id.lVEventName);
		callCab = (Button) findViewById(R.id.bCallCab);
		showOnMap = (Button) findViewById(R.id.bShowOnMap);
		commentOnClub = (Button) findViewById(R.id.bClubsPage);
		callCab.setOnClickListener(this);
		showOnMap.setOnClickListener(this);
		commentOnClub.setOnClickListener(this);

	}

	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.bCallCab:
			final Dialog dialog = new Dialog(ClubInformation.this);
			// Include cabdialog.xml file
			dialog.setContentView(R.layout.cabdialog);
			// Set dialog title
			dialog.setTitle("Nazovi taxi");

			// set values for custom dialog components - text and button
			TextView tvCameo = (TextView) dialog.findViewById(R.id.tVCameo);
			tvCameo.setText("Cameo: 6kn/km");

			Button callCameo = (Button) dialog.findViewById(R.id.bCameo);
			callCameo.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:1245"));
					startActivity(callIntent);
				}
			});

			TextView tvRadioTaxi = (TextView) dialog
					.findViewById(R.id.tVRadioTaxi);
			tvRadioTaxi.setText("Radio Taxi: 4,90kn/km");

			Button callRadioTaxi = (Button) dialog
					.findViewById(R.id.bRadioTaxi);
			callRadioTaxi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:123456"));
					startActivity(callIntent);
				}
			});
			
			TextView tvEcoTaxi = (TextView) dialog
					.findViewById(R.id.tVEcoTaxi);
			tvEcoTaxi.setText("Radio Taxi: 5,50kn/km");

			Button callEcoTaxi = (Button) dialog
					.findViewById(R.id.bEcoTaxi);
			callEcoTaxi.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					Intent callIntent = new Intent(Intent.ACTION_CALL);
					callIntent.setData(Uri.parse("tel:1234"));
					startActivity(callIntent);
				}
			});

			Button cancel = (Button) dialog.findViewById(R.id.bCancel);
			cancel.setOnClickListener(new OnClickListener() {
				@Override
				public void onClick(View v) {
					dialog.cancel();
				}
			});

			dialog.show();
			break;
		case R.id.bShowOnMap:
			Intent localIntent = new Intent(ClubInformation.this,
					Location.class);
			localIntent.putExtra("clubName", clubName);
			startActivity(localIntent);
			break;
		case R.id.bClubsPage:
			Intent localIntent2 = new Intent(ClubInformation.this,
					ClubPage.class);
			localIntent2.putExtra("clubName", clubName);
			startActivity(localIntent2);
			break;
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(ClubInformation.this,
				MainActivity.class);
		startActivity(localIntent);
		return true;
	}

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
					Document document = Jsoup.connect(urlGalery).get();
					// Using Elements to get the Meta data
					Elements description = document.select(
							"h1[class=entry-head]").select("a");
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
					Document document = Jsoup.connect(urlLemon).get();
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
			} else if (clubName.equals("Hard Place")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(urlHard_Place).get();

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
					Document document = Jsoup.connect(urlKSET).get();
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
			else if (clubName.equals("Plaza Bar")) {
				try {
					// Connect to the web site
					Document document = Jsoup.connect(urlPlaza_Bar).get();

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
					Document document = Jsoup.connect(urlAquarius).get();

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
