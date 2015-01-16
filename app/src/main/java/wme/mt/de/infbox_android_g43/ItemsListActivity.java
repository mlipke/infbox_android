package wme.mt.de.infbox_android_g43;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import de.mt.wme.inf_box_lib.helper.InfboxTask;
import de.mt.wme.inf_box_lib.misc.IInfboxResultHandler;


public class ItemsListActivity extends ListActivity implements IInfboxResultHandler {
    private ListItemAdapter listItemAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_items_list);

        ArrayList<Item> items = new ArrayList<>();
        listItemAdapter = new ListItemAdapter(this, items);
        setListAdapter(listItemAdapter);

        ListHeader lh = new ListHeader("Items");
        //items.add(lh);

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
            ArrayList<Item> items = (ArrayList<Item>)InfboxDataConverter.getInfboxItemList(result);
            listItemAdapter.getItems().addAll(items);
        } catch (Exception e){
            e.printStackTrace();
        }

        listItemAdapter.notifyDataSetChanged();
    }

    private void startImageDetailActivity(ListItem item){
        Intent intent = new Intent(this, DetailActivity.class);

        intent.putExtra("url", item.getFile_url());
        intent.putExtra("title", item.getFilename());
        intent.putExtra("date", item.getMetadata().getCreation_date());
        intent.putExtra("size", Helper.humanReadableByteCount(item.getMetadata().getSize(), true));

        startActivity(intent);
    }

    private void startTextDetailActivity(ListItem item){
        Intent intent = new Intent(this, TextDetailActivity.class);

        intent.putExtra("url", item.getFile_url());
        intent.putExtra("title", item.getFilename());
        intent.putExtra("date", item.getMetadata().getCreation_date());
        intent.putExtra("size", Helper.humanReadableByteCount(item.getMetadata().getSize(), true));

        startActivity(intent);
    }
}
