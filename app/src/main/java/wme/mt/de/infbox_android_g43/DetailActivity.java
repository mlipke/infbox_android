package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import java.net.URL;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        Intent intent = getIntent();

        ImageView imageView = (ImageView)findViewById(R.id.detailView);
        TextView title = (TextView)findViewById(R.id.title);
        TextView size = (TextView)findViewById(R.id.size);
        TextView date = (TextView)findViewById(R.id.date);

        title.setText(intent.getExtras().getString("title"));
        size.setText(intent.getExtras().getString("size"));
        date.setText(intent.getExtras().getString("date"));

        imageView.setTag(intent.getExtras().getString("url"));

        DownloadImageTask dit = new DownloadImageTask(imageView);
        dit.execute(intent.getExtras().getString("url"));
    }
}
