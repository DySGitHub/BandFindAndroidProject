package ds.wit.dylan.bandfind.Activity;

import android.app.Fragment;
import android.app.FragmentTransaction;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;

import ds.wit.dylan.bandfind.Fragment.GMapFragment;
import ds.wit.dylan.bandfind.Fragment.InfoFragment;
import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Fragment.AddFragment;
import ds.wit.dylan.bandfind.Fragment.BandFragment;
import ds.wit.dylan.bandfind.Fragment.EditFragment;
import ds.wit.dylan.bandfind.Fragment.EditFragment.OnFragmentInteractionListener;


public class Home extends Base
        implements NavigationView.OnNavigationItemSelectedListener, OnFragmentInteractionListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();

        BandFragment fragment = BandFragment.newInstance();
        ft.replace(R.id.fragment_Frame, fragment);
        ft.commit();

        if (Base.app.dbManager.getAll().isEmpty()) {
            Base.app.dbManager.LoadList();
        } else {
        }
    }


    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        int id = item.getItemId();
        Fragment fragment;
        FragmentTransaction ft = getFragmentManager().beginTransaction();

        if (id == R.id.nav_home) {
            fragment = BandFragment.newInstance();
            ((BandFragment) fragment).bandfav = false;
            ft.replace(R.id.fragment_Frame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_add) {
            fragment = AddFragment.newInstance();
            ft.replace(R.id.fragment_Frame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_fav) {
            fragment = BandFragment.newInstance();
            ((BandFragment) fragment).bandfav = true;
            ft.replace(R.id.fragment_Frame, fragment);
            ft.addToBackStack(null);
            ft.commit();


        } else if (id == R.id.nav_about) {
            fragment = InfoFragment.newInstance();
            ft.replace(R.id.fragment_Frame, fragment);
            ft.addToBackStack(null);
            ft.commit();

        } else if (id == R.id.nav_bandmap) {
            ft.replace(R.id.fragment_Frame, new GMapFragment()).addToBackStack("Map").commit();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    @Override
    public void toggle(View v) {
        EditFragment editFrag = (EditFragment)
                getFragmentManager().findFragmentById(R.id.fragment_Frame);

        if (editFrag != null) {
            editFrag.toggle(v);
        }
    }

    @Override
    public void update(View v) {
        EditFragment editFrag = (EditFragment)
                getFragmentManager().findFragmentById(R.id.fragment_Frame);

        if (editFrag != null) {
            editFrag.update(v);
        }
    }

    public void onSearch(View v) {
        GMapFragment searchFrag = (GMapFragment)
                getFragmentManager().findFragmentById(R.id.fragment_Frame);

        if (searchFrag != null) {
            searchFrag.onSearch(v);
        }
    }

}
