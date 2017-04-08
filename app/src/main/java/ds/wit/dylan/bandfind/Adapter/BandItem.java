package ds.wit.dylan.bandfind.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import ds.wit.dylan.bandfind.R;
import ds.wit.dylan.bandfind.Model.Band;

public class BandItem {
    View v;

    public BandItem(Context context, ViewGroup parent, OnClickListener deleteListener, Band band) {
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        v = inflater.inflate(R.layout.bandrow, parent, false);
        v.setId(band.bandId);
        BandInfo(band);
        ImageView imgDelete = (ImageView) v.findViewById(R.id.imgDelete);
        imgDelete.setTag(band);
        imgDelete.setOnClickListener(deleteListener);
    }

    private void BandInfo(Band band) {
        ((TextView) v.findViewById(R.id.rowBandName)).setText(band.name);
        ((TextView) v.findViewById(R.id.rowBandGenre)).setText(band.genre);
        ((TextView) v.findViewById(R.id.rowBandPrice)).setText("€" + String.format("%.2f", band.price));
        ((TextView) v.findViewById(R.id.rowBandAddress)).setText(band.address);

        ImageView imgIcon = (ImageView) v.findViewById(R.id.RowImg);
        if (band.bandfav == true) {
            imgIcon.setImageResource(R.drawable.ic_favourite_on);
        } else {
            imgIcon.setImageResource(R.drawable.ic_favourite_off);
        }
    }
}
