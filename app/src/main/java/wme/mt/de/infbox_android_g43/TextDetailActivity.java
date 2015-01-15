package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.widget.TextView;

public class TextDetailActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_text_detail);

        TextView view = (TextView)findViewById(R.id.textView4);
        view.setTypeface(Typeface.MONOSPACE);
        Intent intent = getIntent();

        DownloadTextTask dtt = new DownloadTextTask(view);
        dtt.execute(intent.getExtras().getString("url"));
    }
}
