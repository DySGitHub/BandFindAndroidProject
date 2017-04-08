package ds.wit.dylan.bandfind.Fragment;


import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;

import com.google.android.gms.location.LocationListener;

import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Activity.Base;
import ds.wit.dylan.bandfind.Activity.Home;
import ds.wit.dylan.bandfind.Model.Band;

import static android.content.ContentValues.TAG;

public class AddFragment extends Fragment implements View.OnClickListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener, LocationListener {

    private String bandName;
    private String bandGenre;
    private Double bandPrice;
    private Switch clSwitch;
    private Spinner genre;
    private Spinner price;
    private double bandLat;
    private double bandLongi;
    private EditText name;
    private EditText clText;
    private String bandAddress;
    private GoogleApiClient GAPI;
    private LocationRequest lr;
    private Location loc;


    public AddFragment() {
    }

    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(GAPI, this);
    }

    public void onDestroy() {
        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(GAPI, this);
    }

    public static AddFragment newInstance() {
        AddFragment fragment = new AddFragment();
        return fragment;
    }

    public void buildGoogleApiClient() {
        GAPI = new GoogleApiClient.Builder(getContext())
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
        GAPI.connect();
    }

    @Override
    public void onConnected(Bundle b) {
        LocationCheck();
    }

    protected void LocationCheck() {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        lr = LocationRequest.create();
        lr.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
        lr.setInterval(10000);
        LocationServices.FusedLocationApi.requestLocationUpdates(GAPI, lr, this);
        Log.d(TAG, "Location Check Start");
    }

    @Override
    public void onConnectionSuspended(int i) {
        LocationServices.FusedLocationApi.removeLocationUpdates(GAPI, this);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_add, container, false);
        bandLat = 0.0;
        bandLongi = 0.0;
        buildGoogleApiClient();
        Button saveButton = (Button) v.findViewById(R.id.saveBandButton);
        name = (EditText) v.findViewById(R.id.nameEditText);
        genre = (Spinner) v.findViewById(R.id.genreSpinner);
        price = (Spinner) v.findViewById(R.id.priceSpinner) ;
        clText = (EditText) v.findViewById(R.id.clText2);
        clSwitch = (Switch) v.findViewById(R.id.clSwitch);
        clSwitch.setChecked(true);
        clSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    clText.setVisibility(View.VISIBLE);
                } else {
                    clText.setText("");
                    clText.setVisibility(View.INVISIBLE);
                }
            }
        });

        List<String> genreType = new ArrayList<String>();
        ArrayAdapter<String> adaper = new ArrayAdapter<String>(getActivity(), R.layout.spintextitem, genreType);
        adaper.setDropDownViewResource(R.layout.spintextitem);

        genre.setAdapter(adaper);
        genre.setPrompt("Genre");
        adaper.add("Band Genre");
        adaper.add("Classical");
        adaper.add("Electronic");
        adaper.add("Traditional");
        adaper.add("Pop");
        adaper.add("Rap");
        adaper.add("Jazz");
        adaper.add("Rock");
        adaper.add("Country");

        List<Double> priceType = new ArrayList<Double>();
        ArrayAdapter<Double> adaper2 = new ArrayAdapter<Double>(getActivity(), R.layout.spintextitem, priceType);
        adaper.setDropDownViewResource(R.layout.spintextitem);

        price.setAdapter(adaper2);
        price.setPrompt("Price");
        adaper2.add(0.00);
        adaper2.add(10.00);
        adaper2.add(15.00);
        adaper2.add(20.00);
        adaper2.add(25.00);
        adaper2.add(30.00);
        adaper2.add(35.00);
        adaper2.add(40.00);
        adaper2.add(45.00);
        adaper2.add(50.00);
        adaper2.add(55.00);
        adaper2.add(60.00);
        adaper2.add(65.00);
        adaper2.add(70.00);
        adaper2.add(75.00);
        adaper2.add(80.00);
        adaper2.add(85.00);
        adaper2.add(90.00);
        adaper2.add(95.00);
        adaper2.add(100.00);

        saveButton.setOnClickListener(this);
        return v;
    }


    @Override
    public void onResume() {
        super.onResume();
    }


    public void onClick(View v) {
        bandName = name.getText().toString();
        bandGenre = genre.getSelectedItem().toString();
        bandPrice = (Double) price.getSelectedItem();
        loc = LocationServices.FusedLocationApi.getLastLocation(
                GAPI);
        if (clText.length() <= 0) {
            if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                return;
            }
            if (loc != null) {
                bandLat = loc.getLatitude();
                bandLongi = loc.getLongitude();
                Log.v(TAG, "" + bandLat + bandLongi);

                Geocoder geocoder2;
                List<android.location.Address> addressList2 = null;
                geocoder2 = new Geocoder(getActivity());
                if (bandLat != 0.0 && bandLongi != 0.0) {
                    try {
                        addressList2 = geocoder2.getFromLocation(bandLat, bandLongi, 1);
                        String i = addressList2.get(0).getAddressLine(0);
                        String ii = addressList2.get(0).getLocality();
                        String iii = addressList2.get(0).getCountryName();
                        bandAddress = (i + " " + ii + " " + iii);

                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                } else if (bandLat == 0.0 && bandLongi == 0.0) {
                    Toast.makeText(getActivity(), "Location not enabled", Toast.LENGTH_SHORT).show();
                }
            }
        }


        if (clText.length() > 0) {
            String loc = clText.getText().toString();
            List<android.location.Address> addressList = null;

            if (loc != null || loc.isEmpty() == false) {
                Geocoder geocoder = new Geocoder(getActivity());
                try {
                    addressList = geocoder.getFromLocationName(loc, 1);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                if (!addressList.isEmpty()) {
                    android.location.Address address = addressList.get(0);
                    LatLng findll = new LatLng(address.getLatitude(), address.getLongitude());
                    bandLat = findll.latitude;
                    bandLongi = findll.longitude;
                    String i = addressList.get(0).getAddressLine(0);
                    String ii = addressList.get(0).getLocality();
                    String iii = addressList.get(0).getCountryName();
                    bandAddress = (i + " " + ii + " " + iii);
                }
            } else if (addressList.isEmpty()) {

            }
        }

        if ((bandName.length() > 0) && (bandGenre.length() > 0) && (bandLat != 0.0) && (bandLongi != 0.0) && (bandGenre != "Band Genre")) {
            Band b = new Band(bandName, bandGenre, false, bandLat, bandLongi, bandAddress, bandPrice);
            Base.app.dbManager.insert(b);
            Intent intent = new Intent(getActivity(), Home.class);
            getActivity().startActivity(intent);
            Log.d(TAG, "" + bandName + bandGenre + bandLat + bandLongi + bandAddress);
        } else {
            Toast.makeText(getActivity(), "You left a field empty or do not have location enabled.", Toast.LENGTH_SHORT).show();

        }
    }


    public void onLocationChanged(Location location) {

    }
}





