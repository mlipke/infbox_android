package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

import de.mt.wme.inf_box_lib.helper.ConnectionChecker;

/*
 Activity to display a text file.
 Starts a DownloadTextTask with an URL given as an extra in the intent
 */
public class TextDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detail);

        TextView view = (TextView)findViewById(R.id.textView4);
        view.setTypeface(Typeface.MONOSPACE);
        Intent intent = getIntent();

        if (ConnectionChecker.isDeviceConnected(getApplicationContext())) {
            DownloadTextTask dtt = new DownloadTextTask(view);
            dtt.execute(intent.getExtras().getString("url"));
        }
    }
}
