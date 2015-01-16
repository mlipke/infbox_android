package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.ArrayList;


public class ListItemAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList<Item> items;

    private static LayoutInflater inflater = null;

    public ListItemAdapter(Activity a, ArrayList<Item> itemsList){
        activity = a;
        items = itemsList;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getViewTypeCount(){
        return 2;
    }

    @Override
    public int getItemViewType(int position) {
        if (items.get(position).isHeader())
            return 0;
        else {
            return 1;
        }
    }

    @Override
    public int getCount(){
        return items.size();
    }

    @Override
    public Object getItem(int index){
        return items.get(index);
    }

    @Override
    public long getItemId(int index){
        return index;
    }

    @Override
    public View getView(int index, View convertView, ViewGroup parent){
        return items.get(index).getView(inflater, convertView);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
