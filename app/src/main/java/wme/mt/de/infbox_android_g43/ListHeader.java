package wme.mt.de.infbox_android_g43;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

public class ListHeader implements Item {
    private String title;

    public ListHeader(){}

    public ListHeader(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public boolean isHeader() {
        return true;
    }

    @Override
    public View getView(LayoutInflater inflater, View convertView) {
        View view;

        if (convertView == null){
            view = inflater.inflate(R.layout.list_header_layout, null);
        } else {
            view = convertView;
        }

        TextView header = (TextView)view.findViewById(R.id.header);
        header.setText(title);

        return view;
    }
}
