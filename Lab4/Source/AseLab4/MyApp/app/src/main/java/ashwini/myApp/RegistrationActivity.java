package ashwini.myApp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class RegistrationActivity extends AppCompatActivity {

    public void transfer1(View v)
    {
        //This code redirects the from login page to the home page.
        Intent redirect = new Intent(RegistrationActivity.this, MainActivity.class);
        startActivity(redirect);

    }
    public void transfer2(View v)
    {
        //This code redirects the from login page to the home page.
        Intent redirect = new Intent(RegistrationActivity.this, LoginActivity.class);
        startActivity(redirect);

    }

}
