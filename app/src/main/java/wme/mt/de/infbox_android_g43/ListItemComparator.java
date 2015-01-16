package wme.mt.de.infbox_android_g43;

import java.util.Comparator;

public class ListItemComparator implements Comparator<Item> {
    @Override
    public int compare(Item one, Item another) {
        ListItem lhs = (ListItem)one;
        ListItem rhs = (ListItem)another;

        return lhs.getMetadata().getMimetype().compareTo(rhs.getMetadata().getMimetype());
    }
}
