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

    class ViewHolder {
        TextView title;
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

            ViewHolder holder = new ViewHolder();

            holder.title = (TextView)view.findViewById(R.id.header);
            holder.title.setText(title);

            view.setTag(holder);
        } else {
            view = convertView;
        }

        return view;
    }
}
