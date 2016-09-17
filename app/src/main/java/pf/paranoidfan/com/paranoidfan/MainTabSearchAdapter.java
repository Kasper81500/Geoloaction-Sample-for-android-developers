package pf.paranoidfan.com.paranoidfan;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Filter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import pf.paranoidfan.com.paranoidfan.Model.SearchItem;

/**
 * Created by KasperStar on 9/5/2016.
 */

public class MainTabSearchAdapter extends ArrayAdapter<SearchItem> {
    private final String MY_DEBUG_TAG = "SearchItemAdapter";
    private ArrayList<SearchItem> items;
    private ArrayList<SearchItem> itemsAll;
    private ArrayList<SearchItem> suggestions;
    private int viewResourceId;

    public MainTabSearchAdapter(Context context, int viewResourceId, ArrayList<SearchItem> items) {
        super(context, viewResourceId, items);
        this.items = items;
        this.itemsAll = (ArrayList<SearchItem>) items.clone();
        this.suggestions = new ArrayList<SearchItem>();
        this.viewResourceId = viewResourceId;
    }

    public View getView(int position, View convertView, ViewGroup parent) {
        View v = convertView;
        if (v == null) {
            LayoutInflater vi = (LayoutInflater) getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            v = vi.inflate(viewResourceId, null);
        }
        SearchItem searchItem = items.get(position);
        if (searchItem != null) {
            TextView searchItemNameLabel = (TextView) v.findViewById(R.id.txt_item);

            ImageView pinImage = (ImageView) v.findViewById(R.id.img_item);

            if(searchItem.pinType.equals("City")){
                searchItemNameLabel.setText(searchItem.title);
                pinImage.setImageResource(R.mipmap.ic_city);
            }
            else if(!searchItem.pinType.equals("Beer")
                    &&!searchItem.pinType.equals("Watch Party")
                    &&!searchItem.pinType.equals("Fan")){
                searchItemNameLabel.setText(searchItem.title);
                if(searchItem.distance != null) {
                    pinImage.setImageResource(R.mipmap.ic_venue);
                }
            }
            else if(searchItem.pinType.equals("Fan")){
                searchItemNameLabel.setText(searchItem.title + " Fans");
                pinImage.setImageResource(R.mipmap.ic_social);
            }
            else if(searchItem.pinType.equals("Beer")){
                searchItemNameLabel.setText(searchItem.title + " Bars");
                pinImage.setImageResource(R.mipmap.map_beer);
            }
            else if(searchItem.pinType.equals("Watch Party")){
                searchItemNameLabel.setText(searchItem.title + " Watch Party");
                pinImage.setImageResource(R.mipmap.map_watching);
            }

        }

        return v;
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        @Override
        public String convertResultToString(Object resultValue) {
            String str = ((SearchItem)(resultValue)).title;
            return str;
        }
        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if(constraint != null) {
                suggestions.clear();
                for (SearchItem searchItem : itemsAll) {
                    String searchString = String.format("%s %s", searchItem.title, searchItem.tags);
                    if(searchString.toLowerCase().contains(constraint.toString().toLowerCase())){
                        suggestions.add(searchItem);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new Filter.FilterResults();
            }
        }
        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            if(results != null && results.count > 0) {
                ArrayList<SearchItem> filteredList = new ArrayList<SearchItem>((ArrayList<SearchItem>)results.values);
                clear();
                for (SearchItem c : filteredList) {
                    add(c);
                }
                notifyDataSetChanged();
            }
        }
    };

}