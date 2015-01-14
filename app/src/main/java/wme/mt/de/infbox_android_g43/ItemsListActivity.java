package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.app.ListActivity;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.objects.Item;
import de.mt.wme.inf_box_lib.objects.Metadata;


public class ItemsListActivity extends ListActivity {
    private ListView list;
    private ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        list = getListView();

//        String[] values = new String[]{"Blabla", "lalala", "blubb", "flop"};
//        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, values);
//        listView.setAdapter(adapter);

        listItemAdapter = new ListItemAdapter(this, createItems(), getResources());
        list.setAdapter(listItemAdapter);

    }

    private ArrayList<Item> createItems(){
        ArrayList<Item> items = new ArrayList<>();

        String size = Byte.toString((byte)3973246);

        Metadata meta1 = new Metadata(3973246, "10.10.2010", "image/jpeg");
        Metadata meta2 = new Metadata(34561, "2.4.2016", "image/jpeg");
        Item item1 = new Item("amsterdam.jpg", "http://wme.lehre.imld.de:8080/wme14-15/files/img/amsterdam.jpg");
        item1.setMetadata(meta1);
        Item item2 = new Item("teufelchen.jpg", "http://wme.lehre.imld.de:8080/wme14-15/files/img/teufelchen.jpg");
        item2.setMetadata(meta2);

        items.add(item1);
        items.add(item2);

        return items;
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item)listItemAdapter.getItem(position);
        Intent intent = new Intent(this, DetailActivity.class);
        intent.putExtra("url", item.getFile_url());
        intent.putExtra("title", item.getFilename());
        intent.putExtra("date", item.getMetadata().getCreation_date());
        intent.putExtra("size", humanReadableByteCount(item.getMetadata().getSize(), true));

        startActivity(intent);
    }

    public static String humanReadableByteCount(long bytes, boolean si) {
        int unit = si ? 1000 : 1024;
        if (bytes < unit) return bytes + " B";
        int exp = (int) (Math.log(bytes) / Math.log(unit));
        String pre = (si ? "kMGTPE" : "KMGTPE").charAt(exp-1) + (si ? "" : "i");
        return String.format("%.1f %sB", bytes / Math.pow(unit, exp), pre);
    }
}
