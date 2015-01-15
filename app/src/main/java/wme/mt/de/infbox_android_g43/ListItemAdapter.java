package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.misc.IInfboxResultHandler;
import de.mt.wme.inf_box_lib.objects.Item;

public class ListItemAdapter extends BaseAdapter {
    private LruCache<String, Bitmap> thumbCache;
    private ArrayList items;

    private static LayoutInflater inflater = null;

    public ListItemAdapter(Activity a, ArrayList itemsList){
        items = itemsList;

        inflater = (LayoutInflater) a.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        thumbCache = new LruCache<String, Bitmap>(1024*1024*3){
            @Override
            protected int sizeOf(String key, Bitmap value){
                return value.getByteCount() / 1024;
            }
        };
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
            Item temp = (Item)items.get(index);

            holder.title.setText(temp.getFilename());
            holder.size.setText(humanReadableByteCount(temp.getMetadata().getSize(),true));
            holder.date.setText(Helper.readableDate(temp.getMetadata().getCreation_date()));

            String thumbUrl = Helper.getThumbnailUrlString(temp.getId());
            holder.thumb.setTag(thumbUrl);

            if (thumbCache.get(thumbUrl) == null) {
                DownloadImageTask dit = new DownloadImageTask(holder.thumb, thumbCache);
                dit.execute(thumbUrl);
            } else {
                holder.thumb.setImageBitmap(thumbCache.get(thumbUrl));
            }
        }

        return v;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
