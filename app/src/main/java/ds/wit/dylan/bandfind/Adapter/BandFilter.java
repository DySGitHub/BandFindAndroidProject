package ds.wit.dylan.bandfind.Adapter;

import android.util.Log;
import android.widget.Filter;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import ds.wit.dylan.bandfind.Model.Band;

import static com.google.android.gms.wearable.DataMap.TAG;
import static java.security.AccessController.getContext;

public class BandFilter extends Filter {
    private List<Band> sBandList;
    private String filterText;
    private BandListAdapter bandadapter;

    public BandFilter(List<Band> sBandList, String filterText,
                      BandListAdapter bandadapter) {
        super();
        this.sBandList = sBandList;
        this.filterText = filterText;
        this.bandadapter = bandadapter;
    }

    public void setFilter(String filterText) {
        this.filterText = filterText;
    }

    @Override
    protected FilterResults performFiltering(CharSequence prefix) {
        FilterResults results = new FilterResults();

        if (sBandList == null) {
            sBandList = new ArrayList<>();
        }
        if (prefix == null || prefix.length() == 0) {
            List<Band> newBands = new ArrayList<>();
            if (filterText.equals("all")) {
                results.values = sBandList;
                results.count = sBandList.size();
            } else {
                if (filterText.equals("bandfav")) {
                    for (Band b : sBandList)
                        if (b.bandfav)
                            newBands.add(b);
                }
                results.values = newBands;
                results.count = newBands.size();
            }
        } else {
            String prefixString = prefix.toString().toLowerCase();
            final ArrayList<Band> newBands = new ArrayList<>();

            for (Band b : sBandList) {
                final String itemName = b.name.toLowerCase();
                final String itemGenre = b.genre.toLowerCase();
                final String itemAd = b.address.toLowerCase();
                if (itemName.contains(prefixString) || itemGenre.contains(prefixString) || itemAd.contains(prefixString)) {
                    if (filterText.equals("all")) {
                        newBands.add(b);
                    }
                    else if (sBandList==null)
                    {
                    Log.d(TAG, "Filter null");
                    }
                }
            }
            results.values = newBands;
            results.count = newBands.size();
        }
        return results;
    }

    @SuppressWarnings("unchecked")
    @Override
    protected void publishResults(CharSequence prefix, FilterResults results) {
        bandadapter.bandList = (ArrayList<Band>) results.values;
        if (results.count >= 0)
            bandadapter.notifyDataSetChanged();
        else {
            bandadapter.notifyDataSetInvalidated();
            bandadapter.bandList = sBandList;
        }
    }
}
