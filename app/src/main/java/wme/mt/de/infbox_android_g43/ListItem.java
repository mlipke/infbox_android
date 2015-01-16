package wme.mt.de.infbox_android_g43;

import android.graphics.Bitmap;
import android.util.Log;
import android.util.LruCache;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.mt.wme.inf_box_lib.objects.Metadata;

public class ListItem implements Item, ImageHandler {
    private int id;
    private String filename;
    private String url;

    private Metadata metadata;

    private String thumbnailUrl;
    private LruCache<String, Bitmap> thumbnailCache;
    private ItemViewHolder holder;

    public ListItem(){}

    public ListItem(LruCache<String, Bitmap> thumbnailCache){
        this.thumbnailCache = thumbnailCache;
    }

    public ListItem(String filename, String url){
        this.filename = filename;
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Metadata getMetadata() {
        return metadata;
    }

    public void setMetadata(Metadata metadata) {
        this.metadata = metadata;
    }

    @Override
    public void handleResult(Bitmap bitmap) {
        String tag = "";

        try {
            tag = holder.thumbnail.getTag().toString();
        } catch (Exception e){
            e.printStackTrace();
            holder.thumbnail.setImageResource(R.drawable.ic_img_failure);
        }

        if (tag.equals(thumbnailUrl) && bitmap != null){
            thumbnailCache.put(thumbnailUrl, bitmap);
            holder.thumbnail.setImageBitmap(bitmap);
        } else {
            holder.thumbnail.setImageResource(R.drawable.ic_img_failure);
        }
    }

    class ItemViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView size;
        TextView date;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;
        holder = new ItemViewHolder();

        if (convertView == null){
            view = inflater.inflate(R.layout.list_item_layout, null);

            holder.thumbnail = (ImageView)view.findViewById(R.id.thumb);
            holder.title = (TextView)view.findViewById(R.id.title);
            holder.size = (TextView)view.findViewById(R.id.size);
            holder.date = (TextView)view.findViewById(R.id.date);

            view.setTag(holder);
        } else {
            holder = (ItemViewHolder)convertView.getTag();
            view = convertView;
        }

        holder.title.setText(Helper.cutString(filename));
        holder.size.setText(Helper.humanReadableByteCount(metadata.getSize(), true));
        holder.date.setText(Helper.readableDate(metadata.getCreation_date()));

        if (metadata.isThumbnail_available()) {
            thumbnailUrl = Helper.getThumbnailUrlString(id);

            holder.thumbnail.setTag(thumbnailUrl);
            holder.thumbnail.setVisibility(View.VISIBLE);
            holder.thumbnail.setScaleType(ImageView.ScaleType.CENTER_CROP);

            if (thumbnailCache.get(thumbnailUrl) == null) {
                DownloadImageTask dit = new DownloadImageTask(holder.thumbnail);
                dit.setHandler(this);
                dit.execute(thumbnailUrl);
            } else {
                holder.thumbnail.setImageBitmap(thumbnailCache.get(thumbnailUrl));
            }
        } else {
            holder.thumbnail.setVisibility(View.GONE);
        }

        Log.v("View", url);

        return view;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
