package wme.mt.de.infbox_android_g43;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.TextView;


public class InfoActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        ActionBar actionBar = getActionBar();

        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(false);
    }
}
