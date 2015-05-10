package com.example.dobarprovod;

import java.util.HashMap;
import java.util.Map;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarActivity;
import android.view.MenuItem;
import android.widget.Toast;

@SuppressLint("NewApi")
public class Location extends ActionBarActivity {
	
	String clubName;
	private GoogleMap googleMap;
	Marker showOnMap;
	static Map<String, LatLng> mapOfCLubLocations = new HashMap<String, LatLng>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.location);
		
		if(mapOfCLubLocations.isEmpty()) {
			populateMapOfClubLoc();
		}
		
		if (getIntent().getExtras() != null) {
			clubName = getIntent().getExtras().getString("clubName");
			setTitle(clubName);
		}
		
		ActionBar actionBar = getSupportActionBar();
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
            googleMap = ((MapFragment) getFragmentManager().findFragmentById(R.id.map)).getMap();
            
            //Place marker on map
            if(clubName != null && !clubName.isEmpty()) {
            	showOnMap = googleMap.addMarker(new MarkerOptions().position(mapOfCLubLocations.get(clubName))
                        .title("clubName"));
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(mapOfCLubLocations.get(clubName), 15));
            }
            
            // check if map is created successfully or not
            if (googleMap == null) {
                Toast.makeText(getApplicationContext(),
                        "Sorry! unable to create maps", Toast.LENGTH_SHORT)
                        .show();
            }
        }
    }
	
	/**
	 * Fills map with longitude and latitude of clubs geo locations.
	 */
	private void populateMapOfClubLoc() {

		LatLng Galery = new LatLng(45.782236, 15.924640);
		LatLng Lemon = new LatLng(45.811128, 15.976679);
		LatLng Hard_Place = new LatLng(45.796924, 15.977545);
		LatLng KSET = new LatLng(45.802029, 15.971537);
		LatLng Plaza_Bar = new LatLng(45.814586, 15.998939);
		LatLng Aquarius = new LatLng(45.813268, 15.977985);

		mapOfCLubLocations.put("Galery", Galery);
		mapOfCLubLocations.put("Lemon", Lemon);
		mapOfCLubLocations.put("Hard_Place", Hard_Place);
		mapOfCLubLocations.put("KSET", KSET);
		mapOfCLubLocations.put("Plaza_Bar", Plaza_Bar);
		mapOfCLubLocations.put("Aquarius", Aquarius);
	}
 
    @Override
    protected void onResume() {
        super.onResume();
        initilizeMap();
    }
    
    public boolean onOptionsItemSelected(MenuItem item) {
		Intent localIntent = new Intent(Location.this,
				ClubInformation.class);
		localIntent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
		startActivity(localIntent);
		return true;
	}

}
