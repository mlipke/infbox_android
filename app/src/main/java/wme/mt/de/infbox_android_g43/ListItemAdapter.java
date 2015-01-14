package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.res.Resources;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.objects.Item;

public class ListItemAdapter extends BaseAdapter {
    private Activity activity;
    private ArrayList items;
    private static LayoutInflater inflater = null;

    Item temp = null;

    public ListItemAdapter(Activity a, ArrayList itemsList, Resources res){
        activity = a;
        items = itemsList;

        inflater = (LayoutInflater)activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
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

    public static class ViewHolder {
        public TextView title;
        public TextView size;
        public TextView date;
        public ImageView thumb;
    }

    public View getView(int index, View convertView, ViewGroup parent){
        View v = convertView;
        ViewHolder holder;

        if (convertView == null){
            v = inflater.inflate(R.layout.list_item_layout, null);

            holder = new ViewHolder();
            holder.title = (TextView)v.findViewById(R.id.title);
            holder.size  = (TextView)v.findViewById(R.id.size);
            holder.date  = (TextView)v.findViewById(R.id.date);
            holder.thumb = (ImageView)v.findViewById(R.id.thumb);

            v.setTag(holder);
        } else {
            holder = (ViewHolder)v.getTag();
        }

        if (items.size() > 0){
            temp = null;
            temp = (Item)items.get(index);

            holder.title.setText(temp.getFilename());
            holder.size.setText(humanReadableByteCount(temp.getMetadata().getSize(),true));
          //  holder.size.setText("blagdfgdfgdf");
            holder.date.setText(temp.getMetadata().getCreation_date());
            holder.thumb.setImageResource(R.drawable.amsterdam_prev);
        }

        return v;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
