package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
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

    public int getCount(){
        return items.size();
    }

    public Object getItem(int index){
        return items.get(index);
    }

    public long getItemId(int index){
        return index;
    }

    public View getView(int index, View convertView, ViewGroup parent){
        return items.get(index).getView(inflater, convertView);
    }

    public ArrayList<Item> getItems() {
        return items;
    }
}
