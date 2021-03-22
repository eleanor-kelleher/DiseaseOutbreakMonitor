package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class SignInActivity extends AppCompatActivity {

    EditText editTextUserId, editTextPassword;
    String username, password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTitle(R.string.sign_in_activity_title);
        setContentView(R.layout.activity_sign_in);
        //getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        editTextUserId = findViewById(R.id.editTextUserId);
        editTextPassword = findViewById(R.id.editTextPassword);
    }

    public void confirmLoginDetails(View view) {

        Intent intent = new Intent(this, MainActivity.class);
        if (TextUtils.isEmpty((editTextUserId.getText())))
            Toast.makeText(SignInActivity.this, "Enter your User ID", Toast.LENGTH_SHORT).show();
        else if (TextUtils.isEmpty((editTextPassword.getText())))
            Toast.makeText(SignInActivity.this, "Enter your Password", Toast.LENGTH_SHORT).show();
        else {
            username = editTextUserId.getText().toString();
            password = editTextPassword.getText().toString();

            // DO LOGIN STUFF
            // send userid + password to server for verification
            // store authorisation token on device

            startActivity(intent);
        }
    }

}
