package com.example.dobarprovod;

import java.util.HashMap;
import java.util.Map;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class ClubPage extends ActionBarActivity {
	
	static String clubName;
	WebView clubPage;
	static Map<String, String> mapOfClubUrls = new HashMap<String, String>();
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.club_page);
		
		if (getIntent().getExtras() != null) {
			clubName = getIntent().getExtras().getString("clubName");
			setTitle(clubName);
		}
		
		populateMapOfClubURls();
		
		initializeVariables();
		ActionBar actionBar = getSupportActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}

	private void initializeVariables() {
		clubPage = (WebView) findViewById(R.id.webVClubPage);
		WebSettings webSettings = clubPage.getSettings();
		webSettings.setJavaScriptEnabled(true);
		
		if(clubName != null && !clubName.isEmpty()) {
			clubPage.loadUrl(mapOfClubUrls.get(clubName));
			setContentView(clubPage);
		} else {
            Toast.makeText(getApplicationContext(), "No link to web page!", Toast.LENGTH_SHORT).show();
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(ClubPage.this,
				ClubInformation.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(localIntent);
		return true;
	}
	
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
}
