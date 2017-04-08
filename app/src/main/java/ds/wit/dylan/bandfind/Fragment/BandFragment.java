package ds.wit.dylan.bandfind.Fragment;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.DialogInterface;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Activity.Base;
import ds.wit.dylan.bandfind.Adapter.BandFilter;
import ds.wit.dylan.bandfind.Adapter.BandListAdapter;
import ds.wit.dylan.bandfind.Model.Band;

public class BandFragment extends Fragment implements AdapterView.OnItemClickListener, View.OnClickListener, TextWatcher {
    protected static BandListAdapter listAdapter;
    protected ListView listView;
    protected BandFilter bandFilter;
    public boolean bandfav = false;
    public EditText search;
    private static final String[] LOCATION = {Manifest.permission.ACCESS_FINE_LOCATION};
    private static final int LOCATION_REQUEST = 0;


    public BandFragment() {
    }

    public static BandFragment newInstance() {
        BandFragment fragment = new BandFragment();
        return fragment;
    }

    private boolean canAccessLocation() {
        return (ActivityCompat.checkSelfPermission(getActivity(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view;
        view = inflater.inflate(R.layout.fragment_home, container, false);
        if (canAccessLocation()) {

        } else {
            requestPermissions(LOCATION, LOCATION_REQUEST);
        }
        listView = (ListView) view.findViewById(R.id.bandList);
        EditText search = (EditText) view.findViewById(R.id.searchBand);
        search.addTextChangedListener(this);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();

        ((ImageView) getActivity().findViewById(R.id.listempty)).setImageResource(R.drawable.emptylist);
        listAdapter = new BandListAdapter(getActivity(), this, Base.app.dbManager.getAll());
        bandFilter = new BandFilter(Base.app.dbManager.getAll(), "all", listAdapter);
        if (bandfav) {
            ((ImageView) getActivity().findViewById(R.id.listempty)).setImageResource(R.drawable.emptyfav);
            bandFilter.setFilter("bandfav");
            bandFilter.filter(null);
            listAdapter.notifyDataSetChanged();
        }
        listView.setAdapter(listAdapter);
        listView.setOnItemClickListener(this);
        listView.setEmptyView(getActivity().findViewById(R.id.listempty));
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public void onClick(View view) {
        if (view.getTag() instanceof Band) {
            onDeleteBand((Band) view.getTag());
        }
    }

    public void onDeleteBand(final Band band) {
        String Bandname = band.name;
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage("Would you like to delete band " + Bandname + " ?");
        builder.setCancelable(false);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                Base.app.dbManager.delete(band.bandId);
                listAdapter.bandList.remove(band);
                listAdapter.notifyDataSetChanged();
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.cancel();
            }
        });
        AlertDialog alert = builder.create();
        alert.show();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Bundle activityInfo = new Bundle();
        activityInfo.putInt("bandId", view.getId());

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        Fragment fragment = EditFragment.newInstance(activityInfo);
        ft.replace(R.id.fragment_Frame, fragment);
        ft.addToBackStack(null);
        ft.commit();
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
        bandFilter.filter(charSequence);

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }
}

