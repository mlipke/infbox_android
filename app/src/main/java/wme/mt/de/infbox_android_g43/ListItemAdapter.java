package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.helper.ConnectionChecker;
import de.mt.wme.inf_box_lib.objects.Item;

public class ListItemAdapter extends BaseAdapter {
    private Activity activity;
    private LruCache<String, Bitmap> thumbCache;
    private ArrayList<Item> items;

    private static LayoutInflater inflater = null;

    public ListItemAdapter(Activity a, ArrayList<Item> itemsList){
        activity = a;
        items = itemsList;

        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

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
            holder.size.setText(Helper.humanReadableByteCount(temp.getMetadata().getSize(), true));
            holder.date.setText(Helper.readableDate(temp.getMetadata().getCreation_date()));

            if (temp.getMetadata().isThumbnail_available()){
                String thumbUrl = Helper.getThumbnailUrlString(temp.getId());
                holder.thumb.setTag(thumbUrl);
                holder.thumb.setVisibility(View.VISIBLE);
                holder.thumb.setScaleType(ImageView.ScaleType.CENTER_CROP);

                if (ConnectionChecker.isDeviceConnected(activity)) {
                    fetchThumbnail(thumbUrl, holder);
                }
            } else {
                holder.thumb.setVisibility(View.GONE);
            }
        }

        return v;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    private void fetchThumbnail(String url, ViewHolder holder){
        if (thumbCache.get(url) == null) {
                CachedDownloadImageTask dit = new CachedDownloadImageTask(holder.thumb, thumbCache);
                dit.execute(url);
        } else {
            if (holder.thumb.getTag().equals(url)) {
                holder.thumb.setImageBitmap(thumbCache.get(url));
            }
        }
    }
}
