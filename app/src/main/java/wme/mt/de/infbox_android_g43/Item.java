package wme.mt.de.infbox_android_g43;

import android.view.LayoutInflater;
import android.view.View;

/*
 Item interface.
 Is implemented as a ListItem to display items from the API or
 as ListHeader to display a header in the ListView.
 */
public interface Item {
    public boolean isHeader();
    public View getView(LayoutInflater inflater, View convertView);
}
