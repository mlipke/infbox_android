package wme.mt.de.infbox_android_g43;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.text.Html;
import android.widget.CheckBox;
import android.widget.TextView;


public class RegisterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        ActionBar actionBar = getActionBar();
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(false);

        CheckBox checkBox = (CheckBox)findViewById(R.id.checkBox);
        String nb = "<font color='#7AC943'>&nbsp;NUTZUNGSBEDINGUNGEN</font>";
        checkBox.append(Html.fromHtml(nb));
    }

    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
