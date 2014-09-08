package com.example.dobarprovod;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.webkit.WebView;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Location extends Activity {
	
	String clubName;
	private GoogleMap googleMap;
	Marker showOnMap;
	static final LatLng GALERY = new LatLng(45.782236, 15.924640);
	static final LatLng LEMON = new LatLng(45.811128, 15.976679);
	static final LatLng HARD_PLACE = new LatLng(45.796924, 15.977545);
	static final LatLng KSET = new LatLng(45.802029, 15.971537);
	static final LatLng PLAZA_BAR = new LatLng(45.814586, 15.998939);
	static final LatLng AQUARIUS = new LatLng(45.813268, 15.977985);	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		if (getIntent().getExtras() != null) {
			clubName = getIntent().getExtras().getString("clubName");
		}
		setTitle(clubName);
		ActionBar actionBar = getActionBar();
		actionBar.setDisplayHomeAsUpEnabled(true);
		try {
            // Loading map
            initilizeMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
	}
	
	@SuppressLint("NewApi")
	private void initilizeMap() {
        if (googleMap == null) {
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(
                    R.id.map)).getMap();
            if(clubName.equals("Galery")) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(GALERY)
                    .title("Galery"));
            googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(GALERY, 15));
            } else if(clubName.equals("Lemon")) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(LEMON)
                        .title("Lemon"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(LEMON, 15));
            } else if(clubName.equals("Hard Place")) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(HARD_PLACE)
                        .title("Hard Place"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(HARD_PLACE, 15));
            } else if(clubName.equals("KSET")) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(KSET)
                        .title("KSET"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(KSET, 15));
            } else if(clubName.equals("Plaza Bar")) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(PLAZA_BAR)
                        .title("Plaza Bar"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(PLAZA_BAR, 15));
            } else if(clubName.equals("Aquarius")) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(AQUARIUS)
                        .title("Aquarius"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(AQUARIUS, 15));
            }
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(Location.this,
				ClubInformation.class);
		startActivity(localIntent);
		return true;
	}

}
