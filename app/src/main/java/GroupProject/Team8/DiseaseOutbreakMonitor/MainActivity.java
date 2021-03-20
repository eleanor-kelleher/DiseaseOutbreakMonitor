package GroupProject.Team8.DiseaseOutbreakMonitor;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    int age;
    String sex;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (!hasToken()){   // no authorisation token --> make the user sign in
            goToSignIn();
        }
        else {              // do normal stuff
            setContentView(R.layout.activity_main);

            Intent intent = getIntent();
            if (intent.getExtras() != null){
                if (intent.getIntExtra(Constants.AGE, -1) != -1){
                    age = intent.getIntExtra(Constants.AGE, -1);
                }
                if (intent.getStringExtra(Constants.SEX) != null){
                    sex = intent.getStringExtra(Constants.SEX);
                }
            }
        }
    }

    public void addNewPatient(View view) {
        Intent intent = new Intent(this, PatientDetailsActivity.class);
        if (age > 0) {
            intent.putExtra(Constants.AGE, age);
        }
        if (sex != null && !sex.isEmpty()) {
            intent.putExtra(Constants.SEX, sex);
        }
        startActivity(intent);
    }

    /*
        Returns true if a valid token exists on the device
     */
    private boolean hasToken() {
        /*
            ... check for token ...
            if (validTokenExists)
                return true
            else
                return false
         */
        return true;
    }

    private void goToSignIn (){
        Intent intent = new Intent(this, SignInActivity.class);
        startActivity(intent);
    }
}
