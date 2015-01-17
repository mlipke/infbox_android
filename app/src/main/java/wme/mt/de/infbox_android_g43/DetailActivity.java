package wme.mt.de.infbox_android_g43;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
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

import de.mt.wme.inf_box_lib.helper.ConnectionChecker;

public class DetailActivity extends Activity implements ImageHandler {
    Intent intent;
    ImageView imageView;

    @Override
    public void handleResult(Bitmap bitmap) {
        String tag = "";

        try {
            tag = imageView.getTag().toString();
        } catch (Exception e){
            e.printStackTrace();
            imageView.setImageResource(R.drawable.ic_img_failure);
        }

        if (tag.equals(intent.getExtras().getString("url")) && bitmap != null){
            imageView.setImageBitmap(bitmap);
        } else {
            imageView.setImageResource(R.drawable.ic_img_failure);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Context context = getApplicationContext();

        LayoutInflater inflater = getLayoutInflater();
        View detailLayout = inflater.inflate(R.layout.activity_detail, null);

        intent = getIntent();

        ScrollView scrollView = new ScrollView(context);
        scrollView.setLayoutParams(new ActionBar.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
        scrollView.addView(detailLayout);

        setContentView(scrollView);

        imageView = (ImageView)findViewById(R.id.detailView);

        TextView title = (TextView)findViewById(R.id.title);
        TextView size = (TextView)findViewById(R.id.size);
        TextView date = (TextView)findViewById(R.id.date);

        Bundle extras = intent.getExtras();

        title.setText(extras.getString("title"));
        size.setText(extras.getString("size"));
        date.setText(Helper.readableDate(extras.getString("date")));

        imageView.setTag(intent.getExtras().getString("url"));

        if (ConnectionChecker.isDeviceConnected(getApplicationContext())) {
            DownloadImageTask dit = new DownloadImageTask(imageView);
            dit.setHandler(this);
            dit.execute(intent.getExtras().getString("url"));
        }
    }
}
