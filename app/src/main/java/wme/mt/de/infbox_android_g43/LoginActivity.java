package wme.mt.de.infbox_android_g43;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;


public class LoginActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_login, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_info:
                Intent intent = new Intent(this, InfoActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }

    public void startRegisterActivity(View view){
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void startItemsListActivity(View view){

        final EditText email = (EditText)findViewById(R.id.editText);
        final EditText pass = (EditText)findViewById(R.id.editText2);

        if((email.getText().toString().length() == 0) || (Is_Valid_Email(email) == false) ) {
            email.setError("Bitte geben Sie ihre E-Mail-Adresse ein");
        }else if((pass.getText().toString().length() == 0)){
            pass.setError("Bitte geben Sie ihr Passwort ein");
        }else{
            Intent intent = new Intent(this, ItemsListActivity.class);
            startActivity(intent);
        }


    }


    public boolean Is_Valid_Email(EditText email){

        if(email.getText().toString() == null){


            return false;
        } else if( isEmailValid(email.getText().toString()) == false ) {


            return false;
        } else {

            return true;
        }
    }


    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }




}
