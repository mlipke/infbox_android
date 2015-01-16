package wme.mt.de.infbox_android_g43;

import java.util.Comparator;

public class ListItemComparator implements Comparator<ListItem> {
    @Override
    public int compare(ListItem one, ListItem another) {
        return one.getMetadata().getMimetype().compareTo(another.getMetadata().getMimetype());
    }
}
