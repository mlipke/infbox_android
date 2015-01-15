package wme.mt.de.infbox_android_g43;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.helper.InfboxDataConverter;
import de.mt.wme.inf_box_lib.helper.InfboxTask;
import de.mt.wme.inf_box_lib.misc.IInfboxResultHandler;
import de.mt.wme.inf_box_lib.objects.Item;


public class ItemsListActivity extends ListActivity implements IInfboxResultHandler {
    private ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        ArrayList<Item> items = new ArrayList<>();
        listItemAdapter = new ListItemAdapter(this, items);
        setListAdapter(listItemAdapter);

        InfboxTask task = new InfboxTask();
        task.setResultHandler(this);
        task.execute(Helper.BASE_URL + "users/1/items", null);
    }

    @Override
    protected void onListItemClick(ListView l, View v, int position, long id) {
        Item item = (Item)getListAdapter().getItem(position);
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("url", item.getFile_url());
        intent.putExtra("title", item.getFilename());
        intent.putExtra("date", item.getMetadata().getCreation_date());
        intent.putExtra("size", Helper.humanReadableByteCount(item.getMetadata().getSize(), true));

        startActivity(intent);
    }

    @Override
    public void handleResult(String result) {
        try {
            ArrayList<Item> items = (ArrayList<Item>)InfboxDataConverter.getInfboxItemList(result);
            listItemAdapter.getItems().addAll(items);
        } catch (Exception e){
            e.printStackTrace();
        }

        listItemAdapter.notifyDataSetChanged();
    }
}
