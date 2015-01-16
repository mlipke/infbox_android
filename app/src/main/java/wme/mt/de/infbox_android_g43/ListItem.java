package wme.mt.de.infbox_android_g43;

import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import de.mt.wme.inf_box_lib.objects.Metadata;

public class ListItem implements Item {
    private int id;
    private String filename;
    private String url;

    private Metadata metadata;

    public ListItem(){}

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

    class ViewHolder {
        ImageView thumbnail;
        TextView title;
        TextView size;
        TextView date;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;

        if (convertView == null){
            view = inflater.inflate(R.layout.list_item_layout, null);

            ViewHolder holder = new ViewHolder();

            holder.thumbnail = (ImageView)view.findViewById(R.id.thumb);
            holder.thumbnail.setTag(url);

            DownloadImageTask dit = new DownloadImageTask(holder.thumbnail);
            dit.execute(url);

            holder.title = (TextView)view.findViewById(R.id.title);
            holder.size = (TextView)view.findViewById(R.id.size);
            holder.date = (TextView)view.findViewById(R.id.date);

            holder.title.setText(Helper.cutString(filename));
            holder.size.setText(Helper.humanReadableByteCount(metadata.getSize(), true));
            holder.date.setText(Helper.readableDate(metadata.getCreation_date()));

            view.setTag(holder);
        } else {
            view = convertView;
        }

        return view;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
