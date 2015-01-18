package wme.mt.de.infbox_android_g43;

import android.app.ListActivity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.LruCache;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.helper.InfboxDataConverter;
import de.mt.wme.inf_box_lib.helper.InfboxTask;
import de.mt.wme.inf_box_lib.misc.IInfboxResultHandler;

/*
 ListActivity that displays the items of a user.
 */
public class ItemsListActivity extends ListActivity implements IInfboxResultHandler {
    private ListItemAdapter listItemAdapter;
    private LruCache<String, Bitmap> thumbnailCache;

    private ArrayList<Item> items;
    private ArrayList<Item> sortedItems;

    private boolean sorted = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        thumbnailCache = new LruCache<>(1024 * 1024 * 3);

        items = new ArrayList<>();

        listItemAdapter = new ListItemAdapter(this, items);
        setListAdapter(listItemAdapter);

        InfboxTask task = new InfboxTask();
        task.setResultHandler(this);
        task.execute(Helper.BASE_URL + "users/1/items", null);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu_sort, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_sort:
                toggleSorting();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
            items = Helper.convertItemList((ArrayList<de.mt.wme.inf_box_lib.objects.Item>)InfboxDataConverter.getInfboxItemList(result), thumbnailCache);
            sortedItems = Helper.insertHeaders(items);
            listItemAdapter.getItems().addAll(items);
        } catch (Exception e){
            e.printStackTrace();
        }

        listItemAdapter.notifyDataSetChanged();
    }

    private void toggleSorting(){
        listItemAdapter.getItems().clear();

        if (sorted){
            listItemAdapter.getItems().addAll(items);
            sorted = false;
        } else {
            listItemAdapter.getItems().addAll(sortedItems);
            sorted = true;
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
