package ds.wit.dylan.bandfind.Adapter;

import android.content.Context;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import java.util.List;

import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Model.Band;

public class BandListAdapter extends ArrayAdapter<Band> {
    private Context context;
    private OnClickListener deleteListener;
    public List<Band> bandList;

    public BandListAdapter(Context context, OnClickListener deleteListener,
                           List<Band> bandList) {
        super(context, R.layout.bandrow, bandList);

        this.context = context;
        this.deleteListener = deleteListener;
        this.bandList = bandList;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        BandItem item = new BandItem(context, parent, deleteListener,
                bandList.get(position));
        return item.v;
    }

    @Override
    public int getCount() {
        return bandList.size();
    }

    public List<Band> getBandList() {
        return this.bandList;
    }

    @Override
    public Band getItem(int position) {
        return bandList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getPosition(Band b) {
        return bandList.indexOf(b);
    }
}
