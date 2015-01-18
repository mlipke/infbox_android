package wme.mt.de.infbox_android_g43;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

/*
 A header to be used to separate ListItems by category.
 */
public class ListHeader implements Item {
    private String title;
    private HeaderViewHolder holder;

    public ListHeader(String title){
        this.title = title;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    class HeaderViewHolder {
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

            holder = new HeaderViewHolder();

            holder.title = (TextView)view.findViewById(R.id.header);

            view.setTag(holder);
        } else {
            holder = (HeaderViewHolder)convertView.getTag();
            view = convertView;
        }

        holder.title.setText(title);

        return view;
    }
}
