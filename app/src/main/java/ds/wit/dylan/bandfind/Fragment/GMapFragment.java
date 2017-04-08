package ds.wit.dylan.bandfind.Fragment;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import java.io.IOException;
import java.util.List;
import ds.wit.dylan.bandfind.Activity.Base;
import ds.wit.dylan.bandfind.Model.Band;
import ds.wit.dylan.bandfind.R;


public class GMapFragment extends android.app.Fragment implements OnMapReadyCallback, GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {
    private GoogleMap mMap;
    EditText mapET;
    View mv;
    GoogleApiClient GAPI;
    Switch favswitch;

    public static GMapFragment newInstance() {
        GMapFragment fragment = new GMapFragment();
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


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if (mv != null) {
            ViewGroup parent = (ViewGroup) mv.getParent();
            if (parent != null)
                parent.removeView(mv);
        }
        try {
            mv = inflater.inflate(R.layout.fragment_map, container, false);
        } catch (InflateException e) {
        }
        mapET = ((EditText) mv.findViewById(R.id.mapeditText));
        favswitch =((Switch) mv.findViewById(R.id.favswitch));
       MapFragment mapFragment = (MapFragment) this.getChildFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
        return mv;
    }

    @Override
    public void onStart() {
        super.onStart();
    }


    public void onSearch(View v) {
        String loc = mapET.getText().toString();
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
                mMap.addMarker(new MarkerOptions().position(findll).title(addressList.get(0).getAddressLine(0).toString()).snippet("You searched for " + mapET.getText().toString().toUpperCase()));
                mMap.animateCamera(CameraUpdateFactory.newLatLng(findll));
            } else if (addressList.isEmpty()) {
                Toast.makeText(getContext(), "Location " + loc + " can't be retrieved", Toast.LENGTH_SHORT).show();
            }
        }

    }


    @Override
    public void onMapReady(final GoogleMap googleMap) {

        mMap = googleMap;
        buildGoogleApiClient();
        GAPI.connect();


        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {

            return;
        }

        {
            favswitch.setChecked(true);
            if (favswitch.isChecked()) {
                for (Band band : Base.app.dbManager.getAll())
                    if (band.bandfav == true) {
                        mMap.addMarker(new MarkerOptions().position(new LatLng(band.latitude,
                                band.longitude)).title("Band Name: " + band.name +  " " + "Genre: " + band.genre).snippet("Address: " + band.address).icon(BitmapDescriptorFactory.fromResource(R.drawable.favonicon)));

                    }
            }
            favswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                        if (isChecked)
                    {
                        mMap.clear();
                        for (Band band : Base.app.dbManager.getAll())
                            if (band.bandfav == true) {
                                mMap.addMarker(new MarkerOptions().position(new LatLng(band.latitude,
                                        band.longitude)).title("Band Name: " + band.name +  " " + "Genre: " + band.genre).snippet("Address: " + band.address).icon(BitmapDescriptorFactory.fromResource(R.drawable.favonicon)));


                            }

                    } else
                    {
                        mMap.clear();
                        for (Band band : Base.app.dbManager.getAll())
                            if (band.bandfav == false) {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(band.latitude,
                                    band.longitude)).title("Band Name: " + band.name +  " " + "Genre: " + band.genre).snippet("Address: " + band.address).icon(BitmapDescriptorFactory.fromResource(R.drawable.favofficon)));


                        } else {
                            mMap.addMarker(new MarkerOptions().position(new LatLng(band.latitude,
                                    band.longitude)).title("Band Name: " + band.name +  " " + "Genre: " + band.genre).snippet("Address: " + band.address).icon(BitmapDescriptorFactory.fromResource(R.drawable.favonicon)));
                        }
                    }
                }
            });

            mMap.setMyLocationEnabled(true);
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setZoomControlsEnabled(true);
            mMap.getUiSettings().setCompassEnabled(true);
            mMap.getUiSettings().isMapToolbarEnabled();
            mMap.getUiSettings().setMapToolbarEnabled(true);
            mMap.getUiSettings().setAllGesturesEnabled(true);

        }
    }


    @Override
    public void onConnected(@Nullable Bundle bundle) {
        if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            return;
        }
        Location location = LocationServices.FusedLocationApi.getLastLocation(GAPI);
        if (location != null) {
            LatLng curloc = new LatLng(location.getLatitude(), location.getLongitude());
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curloc, 15));
            Log.d(getClass().getSimpleName(), String.valueOf(location.getLatitude()) + ", " + String.valueOf(location.getLongitude()));
        } else {
            LatLng curloc1 = new LatLng(0, 0);
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(curloc1, 0));
            Toast.makeText(getActivity(), "No Location Found", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}