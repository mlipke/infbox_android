package wme.mt.de.infbox_android_g43;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.util.LruCache;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.Collections;

import de.mt.wme.inf_box_lib.helper.InfboxDataConverter;
import de.mt.wme.inf_box_lib.helper.InfboxTask;
import de.mt.wme.inf_box_lib.misc.IInfboxResultHandler;


public class ItemsListActivity extends ListActivity implements IInfboxResultHandler {
    private ListItemAdapter listItemAdapter;
    private LruCache<String, Bitmap> thumbnailCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        thumbnailCache = new LruCache<>(1024 * 1024 * 3);

        ArrayList<Item> items = new ArrayList<>();

        ListHeader header = new ListHeader("Items");
        items.add(header);

        listItemAdapter = new ListItemAdapter(this, items);
        setListAdapter(listItemAdapter);

        InfboxTask task = new InfboxTask();
        task.setResultHandler(this);
        task.execute(Helper.BASE_URL + "users/1/items", null);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        ListItem item = (ListItem)getListAdapter().getItem(position);

        String mimetype = item.getMetadata().getMimetype();

        if (mimetype.equals("image/jpeg")){
            startImageDetailActivity(item);
        } else if (mimetype.equals("txt/plain")){
            startTextDetailActivity(item);
        }
    }

    @Override
    public void handleResult(String result) {
        try {
            ArrayList<Item> items = Helper.convertItemList((ArrayList<de.mt.wme.inf_box_lib.objects.Item>)InfboxDataConverter.getInfboxItemList(result), thumbnailCache);
            listItemAdapter.getItems().addAll(items);
        } catch (Exception e){
            e.printStackTrace();
        }

        listItemAdapter.notifyDataSetChanged();
    }

    private void startImageDetailActivity(ListItem item){
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("url", item.getUrl());
        intent.putExtra("title", item.getFilename());
        intent.putExtra("date", item.getMetadata().getCreation_date());
        intent.putExtra("size", Helper.humanReadableByteCount(item.getMetadata().getSize(), true));

        startActivity(intent);
    }

    private void startTextDetailActivity(ListItem item){
        Intent intent = new Intent(this, TextDetailActivity.class);

        intent.putExtra("url", item.getUrl());
        intent.putExtra("title", item.getFilename());
        intent.putExtra("date", item.getMetadata().getCreation_date());
        intent.putExtra("size", Helper.humanReadableByteCount(item.getMetadata().getSize(), true));

        startActivity(intent);
    }
}
