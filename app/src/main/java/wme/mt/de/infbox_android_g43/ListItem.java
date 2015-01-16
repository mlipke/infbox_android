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

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;

        if (convertView == null){
            view = inflater.inflate(R.layout.list_item_layout, null);
        } else {
            view = convertView;
        }

        ImageView imageView = (ImageView)view.findViewById(R.id.thumb);
        imageView.setTag(url);

        DownloadImageTask dit = new DownloadImageTask(imageView);
        dit.execute(url);

        TextView title = (TextView)view.findViewById(R.id.title);
        TextView size = (TextView)view.findViewById(R.id.size);
        TextView date = (TextView)view.findViewById(R.id.date);

        title.setText(Helper.cutString(filename));
        size.setText(Helper.humanReadableByteCount(metadata.getSize(), true));
        date.setText(Helper.readableDate(metadata.getCreation_date()));

        return view;
    }

    @Override
    public boolean isHeader() {
        return false;
    }
}
