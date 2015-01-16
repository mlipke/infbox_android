package wme.mt.de.infbox_android_g43;

import android.view.LayoutInflater;
import android.view.View;

public interface Item {
    public boolean isHeader();
    public View getView(LayoutInflater inflater, View convertView);
}
