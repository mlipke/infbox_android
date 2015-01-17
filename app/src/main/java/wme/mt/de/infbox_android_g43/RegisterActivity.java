package wme.mt.de.infbox_android_g43;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
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


        final EditText name = (EditText)findViewById(R.id.editText);
        name.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(name.getText().toString().length() == 0){
                    name.setError( "Bitte geben Sie ihren Namen ein.");
                }

            }
        });


        final EditText email = (EditText)findViewById(R.id.editText2);

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Is_Valid_Email(email);

            }
        });

    }


    public void startLoginActivity(View view){
        final EditText email = (EditText)findViewById(R.id.editText2);
        final EditText name = (EditText)findViewById(R.id.editText);
        final CheckBox cbox = (CheckBox)findViewById(R.id.checkBox);
        if((name.getText().toString().length() == 0)){
            name.setError("Bitte geben Sie ihren Namen ein");

        }else if((email.getText().toString().length() == 0) || (Is_Valid_Email(email) == false) ) {
            email.setError("Bitte geben Sie ihre E-Mail-Adresse ein");
        }else if(!cbox.isChecked()){
            cbox.setError("Sie müssen die Nutzungsbedingungen akzeptieren.");
        }else {
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }

    }


    public boolean Is_Valid_Email(EditText email){
        String valid_email;
        if(email.getText().toString() == null){
            email.setError("Bitte valide E-MailAdresse angeben!");
            valid_email = null;
            return false;
        } else if( isEmailValid(email.getText().toString()) == false ) {
            email.setError( "Bitte valide E-MailAdresse angeben!" );
            valid_email = null;
            return false;
        } else {
            valid_email = email.getText().toString();
            return true;
        }
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }
}
