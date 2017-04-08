package ds.wit.dylan.bandfind.Fragment;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.os.Bundle;
import android.app.Fragment;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.model.LatLng;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Activity.Base;
import ds.wit.dylan.bandfind.Model.Band;
import static android.content.ContentValues.TAG;

public class EditFragment extends Fragment implements com.google.android.gms.location.LocationListener, GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener {

    Band aBand;
    Boolean isFav;

    EditText ename;
    Double bandLat;
    Double bandLongi;
    Switch locswitch;
    String bandAddress;
    Location loc;
    Spinner egenre;
    Spinner eprice;

    ImageView favimg;
    GoogleApiClient GAPI;
    LocationRequest lr;
    EditText cltext;

    private OnFragmentInteractionListener mListener;

    public EditFragment() {
    }

    public void onPause() {
        super.onPause();
        LocationServices.FusedLocationApi.removeLocationUpdates(GAPI, this);
    }

    public void onDestroy() {
        super.onDestroy();
        LocationServices.FusedLocationApi.removeLocationUpdates(GAPI, this);
    }


    public static EditFragment newInstance(Bundle bandBundle) {
        EditFragment fragment = new EditFragment();
        fragment.setArguments(bandBundle);
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
        if (getArguments() != null)
            aBand = getObjectBand(getArguments().getInt("bandId"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_edit, container, false);
        buildGoogleApiClient();
        ename = ((EditText) v.findViewById(R.id.nameEditText));
        cltext = ((EditText) v.findViewById(R.id.clText2));
        ename.setText(aBand.name);
        egenre = ((Spinner) v.findViewById(R.id.editGenreSpinner));
        eprice = ((Spinner) v.findViewById(R.id.editPriceSpinner));
        eprice = ((Spinner) v.findViewById(R.id.editPriceSpinner));
        locswitch = ((Switch) v.findViewById(R.id.locswitch));
        locswitch.setChecked(true);
        locswitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView,
                                         boolean isChecked) {

                if (isChecked) {
                    cltext.setVisibility(View.VISIBLE);
                } else {
                    cltext.setText("");
                    cltext.setVisibility(View.INVISIBLE);
                }

            }
        });

        List<String> genreType = new ArrayList<String>();
        ArrayAdapter<String> editadapter = new ArrayAdapter<String>(getActivity(), R.layout.spintextitem, genreType);
        editadapter.setDropDownViewResource(R.layout.spintextitem);



        egenre.setAdapter(editadapter);
        egenre.setPrompt("Genre");
        editadapter.add(aBand.genre);
        editadapter.add("Classical");
        editadapter.add("Electronic");
        editadapter.add("Traditional");
        editadapter.add("Pop");
        editadapter.add("Rap");
        editadapter.add("Jazz");
        editadapter.add("Rock");
        editadapter.add("Country");


        List<Double> priceType = new ArrayList<Double>();
        ArrayAdapter<Double> editadapter2 = new ArrayAdapter<Double>(getActivity(), R.layout.spintextitem, priceType);
        editadapter2.setDropDownViewResource(R.layout.spintextitem);

        eprice.setAdapter(editadapter2);
        eprice.setPrompt("Price");
        editadapter2.add(aBand.price);
        editadapter2.add(0.00);
        editadapter2.add(10.00);
        editadapter2.add(15.00);
        editadapter2.add(20.00);
        editadapter2.add(25.00);
        editadapter2.add(30.00);
        editadapter2.add(35.00);
        editadapter2.add(40.00);
        editadapter2.add(45.00);
        editadapter2.add(50.00);
        editadapter2.add(55.00);
        editadapter2.add(60.00);
        editadapter2.add(65.00);
        editadapter2.add(70.00);
        editadapter2.add(75.00);
        editadapter2.add(80.00);
        editadapter2.add(85.00);
        editadapter2.add(90.00);
        editadapter2.add(95.00);
        editadapter2.add(100.00);

        favimg = (ImageView) v.findViewById(R.id.FavImgView);

        if (aBand.bandfav == true) {
            favimg.setImageResource(R.drawable.ic_favourite_on);
            isFav = true;
        } else {
            favimg.setImageResource(R.drawable.ic_favourite_off);
            isFav = false;
        }


        return v;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onLocationChanged(Location location) {

    }


    public interface OnFragmentInteractionListener {
        void toggle(View v);

        void update(View v);
    }

    private Band getObjectBand(int id) {
        for (Band b : Base.app.dbManager.getAll())
            if (b.bandId == id)
                return b;

        return null;
    }

    public void toggle(View view) {

        if (mListener != null) {
            String fav = null;

            if (isFav) {
                aBand.bandfav = false;
                fav = "Removed band " + aBand.name + " from Favourites";
                isFav = false;
                favimg.setImageResource(R.drawable.ic_favourite_off);
            } else {
                aBand.bandfav = true;
                fav = "Added band " + aBand.name + " to Favourites";
                isFav = true;
                favimg.setImageResource(R.drawable.ic_favourite_on);
            }
            Toast.makeText(getActivity(), fav, Toast.LENGTH_SHORT).show();
        }
    }

    public void update(View view) {


        if (mListener != null) {
            String bandName = ename.getText().toString();
            String bandGenre = egenre.getSelectedItem().toString();
            Double bandPrice = (Double) eprice.getSelectedItem();
            bandLat = aBand.latitude;
            bandLongi = aBand.longitude;
            if ((bandName.length() > 0) && (bandGenre != "Band Genre") && (bandLat != null) && (bandLongi != null)) {
                aBand.name = bandName;
                aBand.genre = bandGenre;
                aBand.price = bandPrice;
                loc = LocationServices.FusedLocationApi.getLastLocation(
                        GAPI);
                if ((locswitch.isChecked())) {
                    if (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                        return;
                    }
                    if (cltext.length() <= 0 && loc!=null) {
                        bandLat = loc.getLatitude();
                        bandLongi = loc.getLongitude();
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
                        Log.d(TAG, "" + bandLat + bandLongi);
                        if (loc != null) {
                            aBand.latitude = bandLat;
                            aBand.longitude = bandLongi;
                            aBand.address = bandAddress;
                            if (getFragmentManager().getBackStackEntryCount() > 0) {
                                Log.d(TAG, "" + bandName + bandGenre + bandLat + bandLongi + bandAddress);
                                Base.app.dbManager.update(aBand);
                                getFragmentManager().popBackStack();
                                return;
                            }

                        } else if (loc == null) {
                            Toast.makeText(getContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(getContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
                        }
                    }

                    if (cltext.length() > 0) {
                        String loc = cltext.getText().toString();
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
                                aBand.latitude = bandLat;
                                aBand.longitude = bandLongi;
                                aBand.address = bandAddress;
                                Log.d(TAG, "" + address.getLatitude() + "            " + bandLat);
                            }
                        } else if (addressList.isEmpty()) {
                        }
                        if (getFragmentManager().getBackStackEntryCount() > 0) {
                            Log.d(TAG, "" + bandName + bandGenre + bandPrice + bandLat + bandLongi + bandAddress);
                            Base.app.dbManager.update(aBand);
                            getFragmentManager().popBackStack();
                            return;
                        }

                    } else if (loc == null) {
                        Toast.makeText(getContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getContext(), "Location can't be retrieved", Toast.LENGTH_SHORT).show();
                    }


                } else if (!locswitch.isChecked()) {
                    bandLat = aBand.latitude;
                    bandLongi = aBand.longitude;
                    bandAddress = aBand.address;
                    Base.app.dbManager.update(aBand);
                    if (getFragmentManager().getBackStackEntryCount() > 0) {
                        Log.d(TAG, "" + bandName + bandGenre + bandPrice + bandLat + bandLongi + bandAddress);
                        getFragmentManager().popBackStack();
                        return;
                    }
                }
            } else
                Toast.makeText(getActivity(), "You must Enter Something for Name, Genre, or Location is not enabled", Toast.LENGTH_SHORT).show();
        }
    }
}

