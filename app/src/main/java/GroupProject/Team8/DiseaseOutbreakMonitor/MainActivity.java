package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText editTextUserId, editTextPassword;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (hasToken()){
            skipLogin();
        }
        else{ // do normal stuff
            setTitle(R.string.login_activity_title);
            setContentView(R.layout.activity_main);
            //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            editTextUserId = findViewById(R.id.editTextUserId);
            editTextPassword = findViewById(R.id.editTextPassword);
        }

    }

    public void confirmLoginDetails(View view) {

        Intent intent = new Intent(this, NewPatientActivity.class);
        if (TextUtils.isEmpty((editTextUserId.getText())))
            Toast.makeText(MainActivity.this, "Enter your User ID", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty((editTextPassword.getText())))
            Toast.makeText(MainActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        else {
            username = editTextUserId.getText().toString();
            password = editTextPassword.getText().toString();

            // DO LOGIN STUFF
            // send userid + password to server for verification
            // store token on device

            startActivity(intent);
        }
    }

    /*
        Returns true if a valid token exists on the device
     */
    public boolean hasToken() {
        // check for token
        /*
            if (validTokenExists)
                return true
            else
                return false
         */
        return false;
    }

    public void skipLogin (){
        Intent intent = new Intent(this, NewPatientActivity.class);
        startActivity(intent);
    }

}
