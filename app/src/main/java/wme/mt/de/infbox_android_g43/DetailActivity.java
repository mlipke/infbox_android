package wme.mt.de.infbox_android_g43;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import java.net.URL;

public class DetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        LayoutInflater inflater = getLayoutInflater();
        View detailLayout = inflater.inflate(R.layout.activity_detail, null);

        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.addView(detailLayout);

        setContentView(scrollView);

        Intent intent = getIntent();

        ImageView imageView = (ImageView)findViewById(R.id.detailView);

        TextView title = (TextView)findViewById(R.id.title);
        TextView size = (TextView)findViewById(R.id.size);
        TextView date = (TextView)findViewById(R.id.date);

        title.setText(intent.getExtras().getString("title"));
        size.setText(intent.getExtras().getString("size"));
        date.setText(intent.getExtras().getString("date"));

        imageView.setTag(intent.getExtras().getString("url"));
        imageView.setScaleType(ImageView.ScaleType.CENTER);

        DownloadImageTask dit = new DownloadImageTask(imageView);
        dit.execute(intent.getExtras().getString("url"));
    }
}
