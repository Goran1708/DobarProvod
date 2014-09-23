package com.example.dobarprovod;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;

@SuppressLint({ "NewApi", "SetJavaScriptEnabled" })
public class ClubPage extends Activity {
	
	static String clubName;
	WebView clubPage;
	
	@SuppressLint("NewApi")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.club_page);
		if (getIntent().getExtras() != null) {
			clubName = getIntent().getExtras().getString("clubName");
		}
		setTitle(clubName);
		initializeVariables();
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
	}
	
	private void initializeVariables() {
		clubPage = (WebView) findViewById(R.id.webVClubPage);
		WebSettings webSettings = clubPage.getSettings();
		webSettings.setJavaScriptEnabled(true);
		if(clubName.equals("Galery")) {
			clubPage.loadUrl("http://gallery.hr/upcoming-events/");
			setContentView(clubPage);
		} else if(clubName.equals("Lemon")) {
			clubPage.loadUrl("http://lemon.hr/hr/");
			setContentView(clubPage);
		} else if(clubName.equals("Hard Place")) {
			clubPage.loadUrl("http://www.hardplace.hr/");
			setContentView(clubPage);
		} else if(clubName.equals("KSET")) {
			clubPage.loadUrl("https://www.kset.org/arhiva/dogadaji/");
			setContentView(clubPage);
		} else if(clubName.equals("Plaza Bar")) {
			clubPage.loadUrl("http://www.plazabar.hr/program");
			setContentView(clubPage);
		} else if(clubName.equals("Aquarius")) {
			clubPage.loadUrl("http://www.aquarius.hr/program/");
			setContentView(clubPage);
		}
	}

	public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(ClubPage.this,
				ClubInformation.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(localIntent);
		return true;
	}

	
}
